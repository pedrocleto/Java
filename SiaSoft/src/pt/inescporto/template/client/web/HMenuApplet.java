package pt.inescporto.template.client.web;

import java.awt.*;
import java.applet.*;
import javax.swing.JMenuBar;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.utils.TmplMessages;
import pt.inescporto.template.web.util.TmplHttpMenuMsg;
import pt.inescporto.template.client.beans.MenuNode;
import pt.inescporto.template.web.util.TmplHttpMessageSender;
import java.util.Vector;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;

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


public class HMenuApplet extends Applet implements ActionListener {
  BorderLayout borderLayout1 = new BorderLayout();
  JMenuBar jmbDynamicMenu = new JMenuBar();
  String urlBase = null;

  //Construct the applet
  public HMenuApplet() {
  }

  //Initialize the applet
  public void init() {
    // get initialization parameters
    try {
      urlBase = getParameter("urlBase");
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception {
    // get root node
    TmplHttpMessageSender msgSender = new TmplHttpMessageSender(urlBase + "/MenuProxy");
    TmplHttpMenuMsg msg = new TmplHttpMenuMsg(TmplMessages.MENU_GET_CHILDREN, "START");
    msg = (TmplHttpMenuMsg)msgSender.doSendObject((Object)msg);
    Vector result = msg.getResult();

    // build root node and childrens
    for (int i = 0; i < result.size(); i++) {
      MenuNode menuNode = new MenuNode();
      try {
	menuNode.setMenuId(((TplString)((Vector)result.elementAt(i)).elementAt(0)).getValue());
	menuNode.setMenuDescription(((TplString)((Vector)result.elementAt(i)).elementAt(1)).getValue());
	menuNode.setMenuResourceId(((TplString)((Vector)result.elementAt(i)).elementAt(2)).getValue());
	JMenu item = new JMenu(menuNode.toString());
	buildTreeForNode(item, menuNode.getMenuId());
	jmbDynamicMenu.add(item);
      }
      catch (Exception ex) {
      }
    }
    setLayout(borderLayout1);
    add(jmbDynamicMenu, java.awt.BorderLayout.CENTER);
  }

  /**
   * recursive call menu proxy for tree creation
   */
  void buildTreeForNode(JMenu root, String name) {
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
      JMenu item = new JMenu(menuNode.toString());
      buildTreeForNode(item, menuNode.getMenuId());
      if (item.getItemCount() == 0) {
        JMenuItem newItem = new JMenuItem(menuNode.toString());
        newItem.setActionCommand(menuNode.getMenuId());
        newItem.addActionListener(this);
	root.add(newItem);
      }
      else
        root.add(item);
    }
  }

  public void actionPerformed(ActionEvent e) {
    try {
      URL url = new URL(urlBase + "/MenuController?MENUID=" + e.getActionCommand());
      (getAppletContext()).showDocument(url, "content");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  //Get Applet information
  public String getAppletInfo() {
    return "Template Menu Bar";
  }

  public void setSize(int width, int height) {
    super.setSize(width, height);
//    System.out.println("Resizing to (" + width + "," + height + ")");
    validate();
  }
}
