package pt.inescporto.template.client.web;

import java.util.Vector;
import java.net.URL;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.web.util.*;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.client.beans.MenuNode;

public class MenuApplet extends JApplet {
  JTree jTreeMenu = null;
  String urlBase = null;
  DefaultMutableTreeNode root = null;

  public MenuApplet() {
  }

  public void init() {
    // get initialization parameters
    try {
      //System.out.println(getDocumentBase().toString());
      urlBase = getParameter("urlBase");
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    // get root node
    TmplHttpMessageSender msgSender = new TmplHttpMessageSender(urlBase + "/MenuProxy");
    TmplHttpMenuMsg msg = new TmplHttpMenuMsg(TmplMessages.MENU_GET_ROOT, null);
    msg = (TmplHttpMenuMsg)msgSender.doSendObject((Object)msg);

    // build root node and childrens
    MenuNode menuNode = new MenuNode();
    try {
      menuNode.setMenuId(((TplString)((Vector)((Vector)msg.getResult()).elementAt(0)).elementAt(0)).getValue());
      menuNode.setMenuDescription(((TplString)((Vector)((Vector)msg.getResult()).elementAt(0)).elementAt(1)).getValue());
      menuNode.setMenuResourceId(((TplString)((Vector)((Vector)msg.getResult()).elementAt(0)).elementAt(2)).getValue());
      root = new DefaultMutableTreeNode(menuNode);
      buildTreeForNode(root, menuNode.getMenuId());
    }
    catch (Exception ex) {
    }

    // create tree and setup
    if (root != null)
      jTreeMenu = new JTree(root);
    else
      jTreeMenu = new JTree();
    jTreeMenu.setFont(new Font("Dialog", Font.PLAIN, 10));
    jTreeMenu.addTreeSelectionListener(new TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        runThread(e);
      }
    });

    // show tree
    JScrollPane jspTreeMenu = new JScrollPane(jTreeMenu);
    getContentPane().add(jspTreeMenu, BorderLayout.CENTER);

    // request first screen
    if (root != null) {
      if (((MenuNode)root.getUserObject()).getMenuId() != null) {
        URL url = new URL(urlBase + "/MenuController?MENUID=" + ((MenuNode)root.getUserObject()).getMenuId());
        (getAppletContext()).showDocument(url, "content");
      }
    }
  }

  private void runThread(TreeSelectionEvent e) {
    javax.swing.SwingUtilities.invokeLater(new RunSelectionChanged(this, e));
  }

  /**
   * recursive call menu proxy for tree creation
   */
  void buildTreeForNode(DefaultMutableTreeNode root, String name) {
    TmplHttpMessageSender msgSender = new TmplHttpMessageSender(urlBase + "/MenuProxy");
    TmplHttpMenuMsg msg = null;

    msg = new TmplHttpMenuMsg(TmplMessages.MENU_GET_CHILDREN, name);
    msg = (TmplHttpMenuMsg)msgSender.doSendObject((Object)msg);
    Vector result = msg.getResult();

    for (int i = 0; i < result.size(); i++) {
      MenuNode menuNode = new MenuNode();
      menuNode.setMenuId(((TplString)((Vector)result.elementAt(i)).elementAt(0)).getValue());
      menuNode.setMenuDescription(((TplString)((Vector)result.elementAt(i)).elementAt(1)).getValue());
      menuNode.setMenuResourceId(((TplString)((Vector)result.elementAt(i)).elementAt(2)).getValue());
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(menuNode);
      buildTreeForNode(node, menuNode.getMenuId());
      root.add(node);
    }
  }

  /**
   * process user selections
   */
  void selectionChanged(TreeSelectionEvent e) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTreeMenu.getLastSelectedPathComponent();

    try {
      // redirect content to selection path if is root or leaf
      if (node == root || node.isLeaf()) {
        URL url = new URL(urlBase + "/MenuController?MENUID=" + ((MenuNode)node.getUserObject()).getMenuId());
        (getAppletContext()).showDocument(url, "content");
      }
      // for child roots, redirect to top root
      else {
        URL url = new URL(urlBase + "/MenuController?MENUID=" + ((MenuNode)root.getUserObject()).getMenuId());
        (getAppletContext()).showDocument(url, "content");
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public synchronized void setSelection(String menuId) {
    try {
      URL url = new URL(urlBase + "/MenuController?MENUID=" + menuId);
      (getAppletContext()).showDocument(url, "content");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}


class RunSelectionChanged implements Runnable {
  private MenuApplet menuApplet = null;
  private TreeSelectionEvent e = null;

  public RunSelectionChanged(MenuApplet menuApplet, TreeSelectionEvent e) {
    this.menuApplet = menuApplet;
    this.e = e;
  }

  public void run() {
    menuApplet.selectionChanged(e);
  }
}
