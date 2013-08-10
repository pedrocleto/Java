package pt.inescporto.siasoft.proc.client.rmi.forms;


import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import com.jgoodies.forms.layout.CellConstraints;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplJButtonCancel;
import javax.swing.JToolBar;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import java.awt.Container;

import java.awt.event.ActionListener;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.siasoft.proc.client.rmi.editor.GraphNode;
import pt.inescporto.siasoft.proc.client.rmi.editor.Graph;


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
public class ActivityPane extends JPanel implements ActionListener {
  TmplLookupButton jlbtnActivityConnection = new TmplLookupButton();
  TmplJTextField jtfldActivityConnection = new TmplJTextField();
  TmplLookupField jlfldActivityConnection = new TmplLookupField();

  JToolBar jtbarNav = new JToolBar();
  TmplJButtonCancel jbtnCancel = new TmplJButtonCancel();
  TmplJButton jbtnOk = new TmplJButton();

  String activityID = null;
  Graph graph = null;

  public ActivityPane(String activityID, Graph graph) {
    this.activityID = activityID;
    this.graph = graph;

    try {
      jbInit();
    }
    catch (Exception ex) {
    }
  }

  private void jbInit() throws Exception {

    jtfldActivityConnection.setLabel("Activity");
    jtfldActivityConnection.setField("activityID");
    jlbtnActivityConnection.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    if (!MenuSingleton.isSupplier())
      jlbtnActivityConnection.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnActivityConnection.setText("Activity");
    jlbtnActivityConnection.setTitle("Activity List");
    jlbtnActivityConnection.setDefaultFill(jtfldActivityConnection);

    jlfldActivityConnection.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    if (!MenuSingleton.isSupplier())
      jlfldActivityConnection.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlfldActivityConnection.setDefaultRefField(jtfldActivityConnection);

    JPanel panel = new JPanel();
    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 150dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    panel.setLayout(formLayout);
    CellConstraints cc = new CellConstraints();

    panel.add(jlbtnActivityConnection, cc.xy(2, 6));
    panel.add(jtfldActivityConnection, cc.xy(4, 6));
    panel.add(jlfldActivityConnection, cc.xy(6, 6));

    panel.setOpaque(false);
    add(panel, BorderLayout.CENTER);
    jtbarNav.setFloatable(false);

    jbtnOk.setText("Ok");
    jbtnOk.setActionCommand("OK");
    jbtnOk.addActionListener(this);
    jbtnOk.setBorderPainted(false);
    jbtnOk.setOpaque(false);
    jbtnOk.setFocusable(false);

    jtbarNav.setOrientation(JToolBar.VERTICAL);
    jtbarNav.add(jbtnOk, null);
    jtbarNav.add(jbtnCancel, null);

    add(jtbarNav, BorderLayout.EAST);
    jbtnCancel.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e) {
    Container parent = getParent();
    if (e.getActionCommand().equals("CANCEL")) {
      while (parent != null && !(parent instanceof JDialog))
	parent = parent.getParent();
      ((JDialog)parent).dispose();
    }
    if (e.getActionCommand().equals("OK")) {
      String activityID = jtfldActivityConnection.getText();
      String activityName = jlfldActivityConnection.getText();


      GraphNode node = new GraphNode();
      node.setId(activityID);
      node.setName(activityName);


      while (parent != null && !(parent instanceof JDialog))
	parent = parent.getParent();
      ((JDialog)parent).dispose();
    }
  }
}
