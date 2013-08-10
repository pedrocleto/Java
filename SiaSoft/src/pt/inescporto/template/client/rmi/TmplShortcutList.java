package pt.inescporto.template.client.rmi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TplObjRef;
import pt.inescporto.template.rmi.beans.LoggedUser;
import pt.inescporto.template.client.beans.MenuNode;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.template.utils.beans.FormData;
import pt.inescporto.template.ejb.session.MenuControl;
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

public class TmplShortcutList extends JList implements TmplMenuShortcut {
  BorderLayout borderLayout1 = new BorderLayout();
  TmplMenuSelection menu = null;
  LoggedUser usrInfo = null;
  JPopupMenu popup = new JPopupMenu();

  public TmplShortcutList() {
    this(null);
  }

  public TmplShortcutList(LoggedUser usrInfo) {
    this.usrInfo = usrInfo;
    this.setModel(new DefaultListModel());
    this.buildList();
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    setFont(new java.awt.Font("Dialog", 0, 10));
    addMouseListener( new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if (locationToIndex(new Point(e.getX(), e.getY())) != -1 ) {
          if (e.getClickCount() == 1) {
            singleClick( e );
          }
        }
      }
      public void mouseReleased(MouseEvent e) {
        if (locationToIndex(new Point(e.getX(), e.getY())) != -1 ) {
          if (e.getClickCount() == 1) {
            singleClick( e );
          }
        }
      }
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          if (locationToIndex(new Point(e.getX(), e.getY())) != -1 ) {
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
              menu.setSelection( (DefaultMutableTreeNode) getSelectedValue());
            }
            catch (Exception ex) {
            }
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
          }
        }
      }
    });

    // create popup menu
    JMenuItem item;
    popup.add( item = new JMenuItem( "Remove Shortcut" ) );
    item.setActionCommand(TmplResourceSingleton.getString("popup.label.removeShortcut"));
    item.addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent e ) {
        removeShortcut( e );
      }
    });
  }

  public void setMenu( TmplMenuSelection menu ) {
    this.menu = menu;
  }

  public void buildList() {
    if (usrInfo == null)
      return;

    try {
      MenuControl menu = (MenuControl) TmplEJBLocater.getInstance().
          getEJBRemote("pt.inescporto.template.ejb.session.MenuControl");

      Vector list = menu.getShortcutsData(usrInfo.getMenuConfig());
      for (int i = 0; i < list.size(); i++) {
        MenuNode menuNode = new MenuNode();
        menuNode.setMenuId( ( (TplString) ( (Vector) list.elementAt(i)).
                             elementAt(0)).getValue());
        menuNode.setMenuDescription( ( (TplString) ( (Vector) list.elementAt(i)).
                                      elementAt(1)).getValue());
        Object obj = ( (TplObjRef) ( (Vector) list.elementAt(i)).elementAt(3)).getValue();
        if (obj != null)
          menuNode.setScIcon((ImageIcon)obj);
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(menuNode);
        addShortcut(node);
      }
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public boolean addShortcutToList(DefaultMutableTreeNode node) {
    boolean found = false;
    DefaultListModel model = (DefaultListModel)getModel();
    Enumeration en = model.elements();
    while (en.hasMoreElements()) {
      DefaultMutableTreeNode curNode = (DefaultMutableTreeNode)en.nextElement();
      MenuNode menuNode = (MenuNode)curNode.getUserObject();
      if (menuNode.getMenuId().compareTo(((MenuNode)node.getUserObject()).getMenuId()) == 0) {
        setSelectedIndex(model.indexOf( (Object) curNode));
        found = true;
      }
    }

    // add to list
    if (!found)
      model.addElement( (Object) node);

    return !found;
  }

  /**
   * Add a new shortcut to the list
   * @param node reference node to add to the list
   */
  public void addShortcut( DefaultMutableTreeNode node ) {
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

  public void singleClick( MouseEvent e ) {
    if (e.isPopupTrigger()) {
      popup.show(this, e.getX(), e.getY());
    }
  }

  /**
   * Remove a shortcut from user list
   * @param e Action to perform
   */
  public void removeShortcut(ActionEvent e) {
    try {
      MenuControl menu = (MenuControl) TmplEJBLocater.getInstance().
          getEJBRemote("pt.inescporto.template.ejb.session.MenuControl");

      int count = menu.removeShortcutForUser(usrInfo.getMenuConfig(), usrInfo.getUsrId(), ((MenuNode)((DefaultMutableTreeNode)getSelectedValue()).getUserObject()).getMenuId());
      if (count == 0)
        JOptionPane.showMessageDialog(this,
                                      TmplResourceSingleton.getString("error.msg.removeShortcut"),
                                      TmplResourceSingleton.getString("error.dialog.header"),
                                      JOptionPane.ERROR_MESSAGE);
      else {
        DefaultListModel model = (DefaultListModel)getModel();
        model.removeElementAt(getSelectedIndex());
      }
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }
  }
}