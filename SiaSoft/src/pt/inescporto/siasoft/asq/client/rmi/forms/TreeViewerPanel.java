package pt.inescporto.siasoft.asq.client.rmi.forms;

import pt.inescporto.template.client.rmi.TmplBasicPanel;
import javax.swing.JTree;
import java.awt.BorderLayout;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.siasoft.asq.ejb.session.TreeBuilder;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.tree.DefaultTreeModel;
import java.awt.Cursor;
import java.awt.Component;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.siasoft.comun.ejb.dao.UserProfileDao;
import java.sql.Timestamp;
import pt.inescporto.template.elements.TplTimestamp;
import java.util.Calendar;
import java.awt.Container;
import javax.swing.JInternalFrame;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.siasoft.asq.ejb.session.LegalRequirement;
import pt.inescporto.siasoft.asq.ejb.dao.LegalRequirementDao;
import javax.naming.*;
import pt.inescporto.template.dao.*;
import pt.inescporto.template.dao.*;
import java.rmi.*;
import javax.ejb.FinderException;
import java.util.Collection;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class TreeViewerPanel extends TmplBasicPanel implements TreeSelectionListener, ActionListener, TreeRefreshListener {
  JScrollPane jsp = new JScrollPane();
  JInternalFrame parent = null;
  JTree tree = null;
  RegulatoryListInterface populate = null;
  int order = 0;
  String lastKeys[] = null;
  RegulatoryTreePopupMenu popup = null;

  public TreeViewerPanel(JInternalFrame parent) {
    this.parent = parent;

    url = "pt.inescporto.siasoft.asq.ejb.session.TreeBuilder";

    init();
    start();
  }

  public void setPopulateListener(RegulatoryListInterface populate) {
    this.populate = populate;
  }

  public void init() {
    super.init();

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());

    tree = new JTree();
    tree.setFont(new Font("Dialog", Font.PLAIN, 10));
    tree.setRootVisible(false);

    jsp.getViewport().add(tree);
    add(jsp, BorderLayout.CENTER);

    //register some listeners
    tree.addTreeSelectionListener(this);
    tree.addMouseListener(new RegulatoryTreePopupMenu());
    popup = new RegulatoryTreePopupMenu();

    setMinimumSize(new Dimension(300, 400));

//    javax.swing.SwingUtilities.invokeLater(new BuildTreeThread(parent, url, tree, order, MenuSingleton.getEnterprise()));
  }

  public void valueChanged(TreeSelectionEvent event) {
    boolean fillList = false;
    TreePath path = event.getPath();
    if (event.getNewLeadSelectionPath() != null && ((TreeBuilderNode)((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject()).getNodeType() >= 4) {
      String keys[] = new String[path.getPathCount() - 1];
      for (int i = 1; i < path.getPathCount(); i++) {
	keys[i - 1] = ((TreeBuilderNode)((DefaultMutableTreeNode)path.getPathComponent(i)).getUserObject()).getNodeID();
	if (lastKeys != null && i <= lastKeys.length && !(keys[i - 1].equals(lastKeys[i - 1])) && i <= 5 && i < path.getPathCount()) {
	  fillList = true;
	}
      }
      // catch minor selection to regulatory selection in the same tree path (previous path comparition give a false result = in the same path)
      if (lastKeys != null && fillList == false && lastKeys.length >= 4 && lastKeys.length >= keys.length)
	fillList = true;

      // for populating new regulatory list
      if (fillList || ((TreeBuilderNode)((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject()).getNodeType() == TreeBuilderNode.MINOR_THEME_TYPE) {
	String majorThemeDesc = ((TreeBuilderNode)((DefaultMutableTreeNode)path.getPathComponent(3)).getUserObject()).getNodeDescription();
	String minorThemeDesc = ((TreeBuilderNode)((DefaultMutableTreeNode)path.getPathComponent(4)).getUserObject()).getNodeDescription();
	populate.setLink(majorThemeDesc, minorThemeDesc, keys, order);
      }
      // for regulatory selection
      if (((TreeBuilderNode)((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject()).getNodeType() == TreeBuilderNode.REGULATORY) {
	populate.selectRegulatory(((TreeBuilderNode)((DefaultMutableTreeNode)path.getPathComponent(5)).getUserObject()).getNodeID(), false);
      }
      else
	if (((TreeBuilderNode)((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject()).getNodeType() == TreeBuilderNode.LEGAL_REQUIREMENT) {
	  populate.selectRegulatory(((TreeBuilderNode)((DefaultMutableTreeNode)path.getPathComponent(5)).getUserObject()).getNodeID(), true);
	}

      // save this path to further comparition!
      lastKeys = keys;
    }
    else
      populate.setLink(null, null, null, order);
  }

  public void actionPerformed(ActionEvent e) {
    try {
      if (e.getActionCommand().equals("THEME")) {
	order = 0;
      }
      if (e.getActionCommand().equals("CRON")) {
	order = 1;
      }
      if (e.getActionCommand().equals("APPL")) {
	order = 2;
      }
      if (e.getActionCommand().equals("LIST")) {
	order = 3;
      }

      javax.swing.SwingUtilities.invokeLater(new BuildTreeThread(parent, url, tree, order, MenuSingleton.getEnterprise()));
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void refreshTree(String regulatoryID, String lrID, int action) {
    LegalRequirement lr = null;

    // Find the path (regardless of visibility) that matches the specified sequence of names
    Vector<TreePath> pathList = findByName(tree, regulatoryID);

    if (pathList.size() != 0) {
      LegalRequirementDao pattern = new LegalRequirementDao();

      if (action != TmplFormModes.MODE_DELETE) {
	try {
	  lr = (LegalRequirement)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.LegalRequirement");
	}
	catch (NamingException ex) {
	  ex.printStackTrace();
	  return;
	}

	pattern.regId.setValue(regulatoryID);
	pattern.legalReqId.setValue(lrID);
	try {
	  Collection col = lr.findExact(pattern);
	  pattern = (LegalRequirementDao)col.iterator().next();
	}
	catch (FinderException ex1) {
	  ex1.printStackTrace();
	  return;
	}
	catch (RowNotFoundException ex1) {
	  ex1.printStackTrace();
	  return;
	}
	catch (RemoteException ex1) {
	  ex1.printStackTrace();
	  return;
	}
      }

      switch (action) {
	case TmplFormModes.MODE_INSERT:
          for (TreePath path : pathList) {
	    TreeBuilderNode tbn = new TreeBuilderNode();
	    tbn.setNodeID(lrID);
	    tbn.setNodeDescription(pattern.legalReqDescription.getValue());
	    tbn.setNodeType(TreeBuilderNode.LEGAL_REQUIREMENT);
	    DefaultMutableTreeNode tn = new DefaultMutableTreeNode(tbn);
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode)path.getLastPathComponent();
            TreeBuilderNode userNode = (TreeBuilderNode)parent.getUserObject();
            if (userNode.getNodeType() == TreeBuilderNode.REGULATORY) {
              ((DefaultTreeModel)tree.getModel()).insertNodeInto(tn, parent, parent.getChildCount());
	    }
	  }
	  break;
	case TmplFormModes.MODE_UPDATE:
          for (TreePath path : pathList) {
	    TreeBuilderNode tbn = new TreeBuilderNode();
	    tbn.setNodeID(lrID);
	    tbn.setNodeDescription(pattern.legalReqDescription.getValue());
	    tbn.setNodeType(TreeBuilderNode.LEGAL_REQUIREMENT);
	    DefaultMutableTreeNode parent = (DefaultMutableTreeNode)path.getLastPathComponent();
	    TreeBuilderNode userNode = (TreeBuilderNode)parent.getUserObject();
	    if (userNode.getNodeType() == TreeBuilderNode.REGULATORY) {
              for (Enumeration en = parent.children(); en.hasMoreElements();) {
                DefaultMutableTreeNode defMutableTreeNode = (DefaultMutableTreeNode)en.nextElement();
                TreeBuilderNode lastUserObj = (TreeBuilderNode)defMutableTreeNode.getUserObject();
                if (lastUserObj.getNodeID().equals(lrID)) {
                  defMutableTreeNode.setUserObject(tbn);
                  break;
                }
              }
	    }
	  }
          tree.repaint();
	  break;
	case TmplFormModes.MODE_DELETE:
          for (TreePath path : pathList) {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode)path.getLastPathComponent();
            TreeBuilderNode userNode = (TreeBuilderNode)parent.getUserObject();
            if (userNode.getNodeType() == TreeBuilderNode.REGULATORY) {
              for (Enumeration en = parent.children(); en.hasMoreElements();) {
                DefaultMutableTreeNode defMutableTreeNode = (DefaultMutableTreeNode)en.nextElement();
                TreeBuilderNode lastUserObj = (TreeBuilderNode)defMutableTreeNode.getUserObject();
                if (lastUserObj.getNodeID().equals(lrID)) {
                  ((DefaultTreeModel)tree.getModel()).removeNodeFromParent(defMutableTreeNode);
                  break;
                }
              }
            }
          }
          tree.repaint();
	  break;
      }
    }
    else {
      System.out.println("Regulatory <" + regulatoryID + "> not found!");
    }
  }

  // Finds the path in tree as specified by the node array. The node array is a sequence
  // of nodes where nodes[0] is the root and nodes[i] is a child of nodes[i-1].
  // Comparison is done using Object.equals(). Returns null if not found.
  public Vector<TreePath> findByName(JTree tree, String pattern) {
    Vector<TreePath> treePathList = new Vector<TreePath>();
    TreeNode root = (TreeNode)tree.getModel().getRoot();
    return find2(root, pattern, treePathList);
  }

  private Vector<TreePath> find2(TreeNode node, String pattern, Vector<TreePath> treePathList) {
    TreeBuilderNode userNode = (TreeBuilderNode)((DefaultMutableTreeNode)node).getUserObject();

    // If equal, add path do treePathList
    if (userNode != null && userNode.getNodeID().equals(pattern))
      treePathList.add(new TreePath(((DefaultTreeModel)tree.getModel()).getPathToRoot(node)));

    // Traverse children
    if (node.getChildCount() >= 0) {
      for (Enumeration e = node.children(); e.hasMoreElements(); ) {
	TreeNode n = (TreeNode)e.nextElement();
	treePathList = find2(n, pattern, treePathList);
      }
    }

    return treePathList;
  }
}

class BuildTreeThread implements Runnable {
  SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
  JInternalFrame wnd = null;
  String url = null;
  JTree tree = null;
  int order = -1;
  String enterpriseID = null;

  public BuildTreeThread(JInternalFrame wnd, String url, JTree tree, int order, String enterpriseID) {
    this.wnd = wnd;
    this.url = url;
    this.tree = tree;
    this.order = order;
    this.enterpriseID = enterpriseID;

    ((DefaultTreeModel)tree.getModel()).setRoot(null);
  }

  /**
   *
   * @return DefaultMutableTreeNode
   * @throws Exception
   */
  private synchronized DefaultMutableTreeNode buildTree(int order) throws Exception {
    String level[] = new String[5];
    DefaultMutableTreeNode rootLevel[] = new DefaultMutableTreeNode[5];
    DefaultMutableTreeNode root = new DefaultMutableTreeNode();

    wnd.setCursor(new Cursor(Cursor.WAIT_CURSOR));

    try {
      TreeBuilder treeBuilder = (TreeBuilder)TmplEJBLocater.getInstance().getEJBRemote(url);

      Vector treeList = null;
      if (order == 2) {
	UserProfileDao curProfile = MenuSingleton.getUserData().getApplicableProfileData().getProfile();
	if (curProfile != null) {
//          System.out.println("Using user profile <" + MenuSingleton.getUserData().getApplicableProfileData().getEnterpriseForUser() + ", " + curProfile.userID.getValue() + ", " + curProfile.profileID.getValue() + "> for building tree!");
	  treeList = treeBuilder.getRegulatoryByThemeApplicable(MenuSingleton.getUserData().getApplicableProfileData().getEnterpriseForUser(), curProfile.userID.getValue(),
	      curProfile.profileID.getValue());
	}
	else {
//          System.out.println("Using enterprise profile <" + enterpriseID + "> for building tree!");
	  treeList = treeBuilder.getRegulatoryByThemeApplicable(enterpriseID);
	}
      }
      else
	treeList = treeBuilder.getRegulatoryByTheme(order);

      if (treeList.size() == 0)
	return new DefaultMutableTreeNode();

      switch (order) {
        case 0:
	  root = makeTreeByTheme(treeList);
	  break;
        case 1:
	  root = makeTreeByDate(treeList);
	  break;
        case 2:
	  root = makeTreeByTheme(treeList);
	  break;
        case 3:
	  root = makeTreeByScope(treeList);
	  break;
      }
    }
    catch (Exception ex) {
      throw ex;
    }
    finally {
      wnd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    return root;
  }

  private DefaultMutableTreeNode makeTreeByTheme(Vector treeList) {
    String level[] = new String[5];
    DefaultMutableTreeNode rootLevel[] = new DefaultMutableTreeNode[5];
    DefaultMutableTreeNode root = new DefaultMutableTreeNode();

    for (int i = 0; i < treeList.size(); i++) {
      Vector row = (Vector)treeList.elementAt(i);
      String scope = (String)((TplString)row.elementAt(0)).getValue();
      String legislation = (String)((TplString)row.elementAt(2)).getValue();
      String theme = (String)((TplString)row.elementAt(4)).getValue();
      String minorTheme = (String)((TplString)row.elementAt(6)).getValue();
      String regulatory = (String)((TplString)row.elementAt(8)).getValue();
      String legReq = (String)((TplString)row.elementAt(10)).getValue();

      if (level[0] != null && !level[0].equals(scope)) {
	rootLevel[3].add(rootLevel[4]);
	rootLevel[2].add(rootLevel[3]);
	rootLevel[1].add(rootLevel[2]);
	rootLevel[0].add(rootLevel[1]);
	root.add(rootLevel[0]);
	rootLevel[0] = null;
	rootLevel[1] = null;
	rootLevel[2] = null;
	rootLevel[3] = null;
	rootLevel[4] = null;
	level[0] = null;
	level[1] = null;
	level[2] = null;
	level[3] = null;
	level[4] = null;
      }
      // level 0 (scope) !exists create
      if (level[0] == null) {
	level[0] = scope;
	TreeBuilderNode tbn = new TreeBuilderNode();
	tbn.setNodeID(scope);
	tbn.setNodeDescription((String)((TplString)row.elementAt(1)).getValue());
	tbn.setNodeType(TreeBuilderNode.SCOPE_TYPE);
	rootLevel[0] = new DefaultMutableTreeNode(tbn);
      }

      if (level[1] != null && !level[1].equals(legislation)) {
	rootLevel[3].add(rootLevel[4]);
	rootLevel[2].add(rootLevel[3]);
	rootLevel[1].add(rootLevel[2]);
	rootLevel[0].add(rootLevel[1]);
	rootLevel[1] = null;
	rootLevel[2] = null;
	rootLevel[3] = null;
	rootLevel[4] = null;
	level[1] = null;
	level[2] = null;
	level[3] = null;
	level[4] = null;
      }
      // level 1 (legislation) !exists create
      if (level[1] == null) {
	level[1] = legislation;
	TreeBuilderNode tbn = new TreeBuilderNode();
	tbn.setNodeID(legislation);
	tbn.setNodeDescription((String)((TplString)row.elementAt(3)).getValue());
	tbn.setNodeType(TreeBuilderNode.LEGISLATION_TYPE);
	rootLevel[1] = new DefaultMutableTreeNode(tbn);
      }

      if (level[2] != null && !level[2].equals(theme)) {
	rootLevel[3].add(rootLevel[4]);
	rootLevel[2].add(rootLevel[3]);
	rootLevel[1].add(rootLevel[2]);
	rootLevel[2] = null;
	rootLevel[3] = null;
	rootLevel[4] = null;
	level[2] = null;
	level[3] = null;
	level[4] = null;
      }
      // level 2 (major theme) !exists create
      if (level[2] == null) {
	level[2] = theme;
	TreeBuilderNode tbn = new TreeBuilderNode();
	tbn.setNodeID(theme);
	tbn.setNodeDescription((String)((TplString)row.elementAt(5)).getValue());
	tbn.setNodeType(TreeBuilderNode.MAJOR_THEME_TYPE);
	rootLevel[2] = new DefaultMutableTreeNode(tbn);
      }

      if (level[3] != null && !level[3].equals(minorTheme)) {
	rootLevel[3].add(rootLevel[4]);
	rootLevel[2].add(rootLevel[3]);
	rootLevel[3] = null;
	rootLevel[4] = null;
	level[3] = null;
	level[4] = null;
      }
      // level 3 (minor theme) !exists create
      if (level[3] == null) {
	level[3] = minorTheme;
	TreeBuilderNode tbn = new TreeBuilderNode();
	tbn.setNodeID(minorTheme);
	tbn.setNodeDescription((String)((TplString)row.elementAt(7)).getValue());
	tbn.setNodeType(TreeBuilderNode.MINOR_THEME_TYPE);
	rootLevel[3] = new DefaultMutableTreeNode(tbn);
      }
      if (level[4] != null && !level[4].equals(regulatory)) {
	rootLevel[3].add(rootLevel[4]);
	rootLevel[4] = null;
	level[4] = null;
      }
      // add regulatory
      if (level[4] == null) {
	level[4] = regulatory;
	TreeBuilderNode tbn = new TreeBuilderNode();
	tbn.setNodeID(regulatory);
	tbn.setNodeDescription((String)((TplString)row.elementAt(9)).getValue());
	tbn.setNodeType(TreeBuilderNode.REGULATORY);
	rootLevel[4] = new DefaultMutableTreeNode(tbn);

      }
      if (legReq != null) {
	TreeBuilderNode tbn = new TreeBuilderNode();
	tbn.setNodeID(legReq);
	tbn.setNodeDescription((String)((TplString)row.elementAt(11)).getValue());
	tbn.setNodeType(TreeBuilderNode.LEGAL_REQUIREMENT);
	rootLevel[4].add(new DefaultMutableTreeNode(tbn));
      }
    }

    //final add
    if (rootLevel[4] != null)
      rootLevel[3].add(rootLevel[4]);
    if (rootLevel[3] != null)
      rootLevel[2].add(rootLevel[3]);
    if (rootLevel[2] != null)
      rootLevel[1].add(rootLevel[2]);
    if (rootLevel[1] != null)
      rootLevel[0].add(rootLevel[1]);
    root.add(rootLevel[0]);

    return root;
  }

  private DefaultMutableTreeNode makeTreeByScope(Vector treeList) {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode();

    for (int i = 0; i < treeList.size(); i++) {
      Vector row = (Vector)treeList.elementAt(i);
      String scope = (String)((TplString)row.elementAt(0)).getValue();

      // level 0 (scope) !exists create
      TreeBuilderNode tbn = new TreeBuilderNode();
      tbn.setNodeID(scope);
      tbn.setNodeDescription((String)((TplString)row.elementAt(1)).getValue());
      tbn.setNodeType(TreeBuilderNode.SCOPE_TYPE);
      root.add(new DefaultMutableTreeNode(tbn));
    }

    return root;
  }

  private DefaultMutableTreeNode makeTreeByDate(Vector treeList) {
    String level[] = new String[5];
    DefaultMutableTreeNode rootLevel[] = new DefaultMutableTreeNode[5];
    DefaultMutableTreeNode root = new DefaultMutableTreeNode();

    for (int i = 0; i < treeList.size(); i++) {
      Vector row = (Vector)treeList.elementAt(i);
      String scope = (String)((TplString)row.elementAt(0)).getValue();
      String legislation = (String)((TplString)row.elementAt(2)).getValue();
      Timestamp date = (Timestamp)((TplTimestamp)row.elementAt(4)).getValue();
      DateFormat.getDateInstance();
      if (date == null)
	continue;
      Calendar cDate = Calendar.getInstance();
      cDate.setTimeInMillis(date.getTime());
      String year = new String("" + cDate.get(Calendar.YEAR));
      String mounth = new String("" + (cDate.get(Calendar.MONTH) + 1));
      String regulatory = (String)((TplString)row.elementAt(5)).getValue();
      String legReq = (String)((TplString)row.elementAt(7)).getValue();

      if (level[0] != null && !level[0].equals(scope)) {
	rootLevel[3].add(rootLevel[4]);
	rootLevel[2].add(rootLevel[3]);
	rootLevel[1].add(rootLevel[2]);
	rootLevel[0].add(rootLevel[1]);
	root.add(rootLevel[0]);
	rootLevel[0] = null;
	rootLevel[1] = null;
	rootLevel[2] = null;
	rootLevel[3] = null;
	rootLevel[4] = null;
	level[0] = null;
	level[1] = null;
	level[2] = null;
	level[3] = null;
	level[4] = null;

      }
      // level 0 (scope) !exists create
      if (level[0] == null) {
	level[0] = scope;
	TreeBuilderNode tbn = new TreeBuilderNode();
	tbn.setNodeID(scope);
	tbn.setNodeDescription((String)((TplString)row.elementAt(1)).getValue());
	tbn.setNodeType(TreeBuilderNode.SCOPE_TYPE);
	rootLevel[0] = new DefaultMutableTreeNode(tbn);
      }

      if (level[1] != null && !level[1].equals(legislation)) {
	rootLevel[3].add(rootLevel[4]);
	rootLevel[2].add(rootLevel[3]);
	rootLevel[1].add(rootLevel[2]);
	rootLevel[0].add(rootLevel[1]);
	rootLevel[1] = null;
	rootLevel[2] = null;
	rootLevel[3] = null;
	rootLevel[4] = null;
	level[1] = null;
	level[2] = null;
	level[3] = null;
	level[4] = null;

      }
      // level 1 (legislation) !exists create
      if (level[1] == null) {
	level[1] = legislation;
	TreeBuilderNode tbn = new TreeBuilderNode();
	tbn.setNodeID(legislation);
	tbn.setNodeDescription((String)((TplString)row.elementAt(3)).getValue());
	tbn.setNodeType(TreeBuilderNode.LEGISLATION_TYPE);
	rootLevel[1] = new DefaultMutableTreeNode(tbn);
      }

      if (level[2] != null && !level[2].equals(year)) {
	rootLevel[3].add(rootLevel[4]);
	rootLevel[2].add(rootLevel[3]);
	rootLevel[1].add(rootLevel[2]);
	rootLevel[2] = null;
	rootLevel[3] = null;
	rootLevel[4] = null;
	level[2] = null;
	level[3] = null;
	level[4] = null;
      }
      // level 2 (major theme) !exists create
      if (level[2] == null) {
	level[2] = year;
	TreeBuilderNode tbn = new TreeBuilderNode();
	tbn.setNodeID(year);
	tbn.setNodeDescription(year);
	tbn.setNodeType(TreeBuilderNode.MAJOR_THEME_TYPE);
	rootLevel[2] = new DefaultMutableTreeNode(tbn);
      }

      if (level[3] != null && !level[3].equals(mounth)) {
	rootLevel[3].add(rootLevel[4]);
	rootLevel[2].add(rootLevel[3]);
	rootLevel[3] = null;
	rootLevel[4] = null;
	level[3] = null;
	level[4] = null;

      }
      // level 3 (minor theme) !exists create
      if (level[3] == null) {
	level[3] = mounth;
	TreeBuilderNode tbn = new TreeBuilderNode();
	tbn.setNodeID(mounth);
	tbn.setNodeDescription(dateFormat.format(new Date(date.getTime())));
	tbn.setNodeType(TreeBuilderNode.MINOR_THEME_TYPE);
	rootLevel[3] = new DefaultMutableTreeNode(tbn);
      }

      if (level[4] != null && !level[4].equals(regulatory)) {
	rootLevel[3].add(rootLevel[4]);
	rootLevel[4] = null;
	level[4] = null;
      }
      // add regulatory
      if (level[4] == null) {
	level[4] = regulatory;
	TreeBuilderNode tbn = new TreeBuilderNode();
	tbn.setNodeID(regulatory);
	tbn.setNodeDescription((String)((TplString)row.elementAt(6)).getValue());
	tbn.setNodeType(TreeBuilderNode.REGULATORY);
	rootLevel[4] = new DefaultMutableTreeNode(tbn);

      }
      if (legReq != null) {
	TreeBuilderNode tbn = new TreeBuilderNode();
	tbn.setNodeID(legReq);
	tbn.setNodeDescription((String)((TplString)row.elementAt(8)).getValue());
	tbn.setNodeType(TreeBuilderNode.LEGAL_REQUIREMENT);
	rootLevel[4].add(new DefaultMutableTreeNode(tbn));
      }
    }

    //final add
    if (rootLevel[4] != null)
      rootLevel[3].add(rootLevel[4]);
    if (rootLevel[3] != null)
      rootLevel[2].add(rootLevel[3]);
    if (rootLevel[2] != null)
      rootLevel[1].add(rootLevel[2]);
    if (rootLevel[1] != null)
      rootLevel[0].add(rootLevel[1]);
    root.add(rootLevel[0]);

    return root;
  }

  public void run() {
    wnd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    try {
      ((DefaultTreeModel)tree.getModel()).setRoot(buildTree(order));
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    wnd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }
}
