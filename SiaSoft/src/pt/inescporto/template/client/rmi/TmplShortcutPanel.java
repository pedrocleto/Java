package pt.inescporto.template.client.rmi;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TplObjRef;
import pt.inescporto.template.rmi.beans.LoggedUser;
import pt.inescporto.template.client.beans.MenuNode;
import pt.inescporto.template.utils.beans.FormData;
import pt.inescporto.template.ejb.session.MenuControl;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import javax.naming.NamingException;
import java.rmi.RemoteException;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class TmplShortcutPanel
    extends JPanel
    implements TmplMenuShortcut, ShortcutInterface {
  TmplMenuSelection menu = null;
  LoggedUser usrInfo = null;
  BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

  public TmplShortcutPanel() {
    this(null);
  }

  public TmplShortcutPanel(LoggedUser usrInfo) {
    this.usrInfo = usrInfo;
    this.buildList();
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(boxLayout);
  }

  public void setMenu(TmplMenuSelection menu) {
    this.menu = menu;
  }

  public void buildList() {
    if (usrInfo == null)
      return;

    try {
      MenuControl menu = (MenuControl) TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.template.ejb.session.MenuControl");

      // get profile shortcuts
      Vector list = menu.getShortcutsData(usrInfo.getMenuConfig());
      for (int i = 0; i < list.size(); i++) {
        MenuNode menuNode = new MenuNode();
        menuNode.setMenuId( ( (TplString) ( (Vector) list.elementAt(i)).elementAt(0)).getValue());
        menuNode.setMenuDescription( ( (TplString) ( (Vector) list.elementAt(i)).elementAt(1)).getValue());
        Object obj = ( (TplObjRef) ( (Vector) list.elementAt(i)).elementAt(3)).getValue();
        if (obj != null)
          menuNode.setScIcon((ImageIcon)obj);
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(menuNode);
        addShortcutToList(node);
      }

      // get user shortcuts
      list = menu.getShortcutsForUserData(usrInfo.getMenuConfig(), usrInfo.getUsrId());
      for (int i = 0; i < list.size(); i++) {
        MenuNode menuNode = new MenuNode();
        menuNode.setMenuId( ( (TplString) ( (Vector) list.elementAt(i)).elementAt(0)).getValue());
        menuNode.setMenuDescription( ( (TplString) ( (Vector) list.elementAt(i)).elementAt(1)).getValue());
        Object obj = ( (TplObjRef) ( (Vector) list.elementAt(i)).elementAt(3)).getValue();
        if (obj != null)
          menuNode.setScIcon((ImageIcon)obj);
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(menuNode);
        addShortcutToList(node);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Add a new shortcut to the list
   * @param node reference node to add to the list
   */
  public boolean addShortcutToList(DefaultMutableTreeNode node) {
    boolean found = false;
    Component list[] = getComponents();
    MenuButton btn = null;

    for (int i = 0; i < list.length; i++) {
      if (list[i] instanceof MenuButton) {
        btn = (MenuButton)list[i];
        if(((MenuNode)btn.getMenuNode().getUserObject()).getMenuId().compareTo(((MenuNode)node.getUserObject()).getMenuId()) == 0) {
          found = true;
          break;
        }
      }
    }

    if (!found) {
      btn = new MenuButton(this);
      btn.setMenuNode(node);
      btn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          setSelection(e);
        }
      });
      this.add(btn, null);
      revalidate();
    }
    else
      btn.requestFocus(true);
    return !found;
  }

  public void addShortcut(DefaultMutableTreeNode node) {
    // add to list
    if (addShortcutToList(node)) {
      // add to database
      try {
        MenuControl menu = (MenuControl) TmplEJBLocater.getInstance().
            getEJBRemote("pt.inescporto.template.ejb.session.MenuControl");
        menu.saveShortcutForUser(usrInfo.getMenuConfig(), usrInfo.getUsrId(), ((MenuNode)node.getUserObject()).getMenuId());
      }
      catch (NamingException ex) {
        ex.printStackTrace();
      }
      catch (RemoteException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Remove a shortcut from user list
   * @param e Action to perform
   */
  public void removeShortcut(MenuButton btn) {
    try {
      MenuControl menu = (MenuControl) TmplEJBLocater.getInstance().
          getEJBRemote("pt.inescporto.template.ejb.session.MenuControl");

      int count = menu.removeShortcutForUser(usrInfo.getMenuConfig(), usrInfo.getUsrId(), ((MenuNode)(btn.getMenuNode()).getUserObject()).getMenuId());
      if (count == 0)
        JOptionPane.showMessageDialog(this,
                                      TmplResourceSingleton.getString("error.msg.removeShortcut"),
                                      TmplResourceSingleton.getString("error.dialog.header"),
                                      JOptionPane.ERROR_MESSAGE);
      else {
        remove(btn);
        revalidate();
      }
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }
  }

  public void setSelection(ActionEvent e) {
    DefaultMutableTreeNode node = ( (MenuButton) e.getSource()).getMenuNode();
    setCursor(new Cursor(Cursor.WAIT_CURSOR));
    menu.setSelection(node);
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }
}
