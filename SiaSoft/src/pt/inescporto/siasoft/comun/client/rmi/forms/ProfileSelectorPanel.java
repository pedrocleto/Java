package pt.inescporto.siasoft.comun.client.rmi.forms;

import javax.swing.JPanel;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJRadioButton;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.elements.TplString;
import java.awt.event.ActionEvent;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import javax.swing.BoxLayout;
import javax.swing.tree.TreePath;
import javax.swing.BorderFactory;
import pt.inescporto.siasoft.comun.ejb.session.ProfileBuilder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Vector;
import pt.inescporto.siasoft.comun.ejb.dao.ProfileBuilderNode;
import java.util.ResourceBundle;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import pt.inescporto.template.client.event.EventSynchronizer;
import pt.inescporto.template.client.event.EventSynchronizerWatcher;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class ProfileSelectorPanel extends JPanel implements ActionListener, EventSynchronizerWatcher {
  ApplyProfileInterface api = null;
  TmplJButton jbtnNew = new TmplJButton();

  // header panel
  JPanel jpnlHeader = new JPanel();
  TmplJLabel jlblHeader = new TmplJLabel("Gestão de Clientes/Perfis");

  // footer panel
  JPanel jpnlFooter = new JPanel();
  TmplJButton jbtnSelectAll = new TmplJButton();
  TmplJButton jbtnClearSelection = new TmplJButton();
  TmplJButton jbtnDoit = new TmplJButton();

  // tree panel
  JPanel jpnlTree = new JPanel(new BorderLayout());
  JScrollPane jspTree = new JScrollPane();
  JTree jtProfile = new JTree();

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.comun.client.rmi.forms.FormResources");

  public ProfileSelectorPanel(ApplyProfileInterface api) {
    this.api = api;

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    EventSynchronizer.getInstance().addEventSynchronizerWatcher(this, SyncronizerSubjects.sysGroupProfile);
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());

    jbtnNew.setText(res.getString("profSelector.label.new"));
    jbtnNew.setActionCommand("NEW");
    jbtnNew.addActionListener(this);

    // header panel
    jpnlHeader.add(jlblHeader);

    // footer panel
    jbtnSelectAll.setText(res.getString("profSelector.label.all"));
    jbtnSelectAll.setActionCommand("ALL");
    jbtnSelectAll.addActionListener(this);

    jbtnClearSelection.setText(res.getString("profSelector.label.clear"));
    jbtnClearSelection.setActionCommand("CLEAN");
    jbtnClearSelection.addActionListener(this);

    jbtnDoit.setText(res.getString("profSelector.label.apply"));
    jbtnDoit.setActionCommand("APPLY");
    jbtnDoit.addActionListener(this);

    jpnlFooter.add(jbtnNew);
    jpnlFooter.add(jbtnSelectAll);
    jpnlFooter.add(jbtnClearSelection);
    jpnlFooter.add(jbtnDoit);

    // tree panel
    jtProfile.setRootVisible(false);
    ((DefaultTreeModel)jtProfile.getModel()).setRoot(getProfileTree());

    jspTree.getViewport().add(jtProfile);
    jspTree.setBorder(BorderFactory.createEtchedBorder());
    jpnlTree.add(jspTree, BorderLayout.CENTER);
    jpnlTree.add(jpnlHeader, BorderLayout.NORTH);
    jpnlTree.add(jpnlFooter, BorderLayout.SOUTH);

    // add panels
    add(jpnlTree, BorderLayout.CENTER);
  }

  public void eventSynchronizerTriggered(String subject) {
    if (subject.equals(SyncronizerSubjects.sysGroupProfile)) {
      ((DefaultTreeModel)jtProfile.getModel()).setRoot(getProfileTree());
      jtProfile.invalidate();
    }
  }

  private DefaultMutableTreeNode getProfileTree() {
    String level[] = new String[3];
    DefaultMutableTreeNode rootLevel[] = new DefaultMutableTreeNode[3];
    DefaultMutableTreeNode root = new DefaultMutableTreeNode();

    try {
      ProfileBuilder pb = (ProfileBuilder)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.comun.ejb.session.ProfileBuilder");
      Vector treeList = pb.getProfileTree();

      for (int i = 0; i < treeList.size(); i++) {
        Vector row = (Vector)treeList.elementAt(i);
        String profileGroupID = (String)((TplString)row.elementAt(0)).getValue();
        String enterpriseID = (String)((TplString)row.elementAt(2)).getValue();
        String userID = (String)((TplString)row.elementAt(4)).getValue();
        String userProfileID = (String)((TplString)row.elementAt(6)).getValue();

        if (level[0] != null && !level[0].equals(profileGroupID)) {
          rootLevel[1].add(rootLevel[2]);
          rootLevel[0].add(rootLevel[1]);
          root.add(rootLevel[0]);
          rootLevel[0] = null;
          rootLevel[1] = null;
          rootLevel[2] = null;
          level[0] = null;
          level[1] = null;
          level[2] = null;
        }
        // level 0 (profile group) !exists create
        if (level[0] == null) {
          level[0] = profileGroupID;
          ProfileBuilderNode tbn = new ProfileBuilderNode();
          tbn.setNodeID(profileGroupID);
          tbn.setNodeDescription((String)((TplString)row.elementAt(1)).getValue());
          tbn.setNodeType(ProfileBuilderNode.PROFILE_GROUP);
          rootLevel[0] = new DefaultMutableTreeNode(tbn);
        }

        if (level[1] != null && !level[1].equals(enterpriseID)) {
          rootLevel[1].add(rootLevel[2]);
          rootLevel[0].add(rootLevel[1]);
          rootLevel[1] = null;
          rootLevel[2] = null;
          level[1] = null;
          level[2] = null;
        }
        // level 1 (enterprise) !exists create
        if (level[1] == null) {
          level[1] = enterpriseID;
          ProfileBuilderNode tbn = new ProfileBuilderNode();
          tbn.setNodeID(enterpriseID);
          tbn.setNodeDescription((String)((TplString)row.elementAt(3)).getValue());
          tbn.setNodeType(ProfileBuilderNode.ENTERPRISE);
          rootLevel[1] = new DefaultMutableTreeNode(tbn);
        }

        if (level[2] != null && !level[2].equals(userID)) {
          rootLevel[1].add(rootLevel[2]);
          rootLevel[2] = null;
          level[2] = null;
        }
        // level 2 (user) !exists create
        if (level[2] == null) {
          level[2] = userID;
          ProfileBuilderNode tbn = new ProfileBuilderNode();
          tbn.setNodeID(userID);
          tbn.setNodeDescription((String)((TplString)row.elementAt(5)).getValue());
          tbn.setNodeType(ProfileBuilderNode.USER);
          rootLevel[2] = new DefaultMutableTreeNode(tbn);
        }

        // add profile
        ProfileBuilderNode tbn = new ProfileBuilderNode();
        tbn.setNodeID(userProfileID);
        tbn.setNodeDescription((String)((TplString)row.elementAt(7)).getValue());
        tbn.setNodeType(ProfileBuilderNode.PROFILE);
        rootLevel[2].add(new DefaultMutableTreeNode(tbn));
      }

      //final add
      if (rootLevel[2] != null)
        rootLevel[1].add(rootLevel[2]);
      if (rootLevel[1] != null)
        rootLevel[0].add(rootLevel[1]);
      root.add(rootLevel[0]);
    }
    catch (Exception ex) {
      //ex.printStackTrace();
    }

    return root;
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("NEW")) {
      ProfileGroupForm pgForm = new ProfileGroupForm((JFrame)SwingUtilities.windowForComponent(this), res.getString("userProfile.title"), true);
      pgForm.setVisible(true);
    }
    if (e.getActionCommand().equals("APPLY")) {
      if (api != null) {
        Integer app = new Integer(0);
        ApplicabilityDialog dlg = new ApplicabilityDialog((JFrame)SwingUtilities.getWindowAncestor(this), app);
        dlg.setVisible(true);
        app = new Integer(dlg.getValue());
        if (app != -1) {
	  TreePath[] selections = jtProfile.getSelectionPaths();
	  api.applyProfile(selections, app);
	}
      }
    }
    if (e.getActionCommand().equals("ALL")) {
      int rowCount = jtProfile.getRowCount();
      TreePath treeList[] = new TreePath[rowCount];
      for (int i = 0; i < rowCount; i++) {
	treeList[i] = jtProfile.getPathForRow(i);
      }
      jtProfile.setSelectionPaths(treeList);
    }
    if (e.getActionCommand().equals("CLEAN")) {
      jtProfile.clearSelection();
    }
  }
}
