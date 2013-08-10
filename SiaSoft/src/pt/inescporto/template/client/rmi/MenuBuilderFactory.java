package pt.inescporto.template.client.rmi;

import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import pt.inescporto.template.ejb.session.MenuControl;
import javax.naming.*;
import java.util.Vector;
import java.rmi.RemoteException;
import javax.swing.JMenu;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.beans.MenuNode;
import java.awt.Font;

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
public class MenuBuilderFactory {
  private static MenuBuilderFactory instance = null;
  private MenuControl menuControl = null;

  public static MenuBuilderFactory getInstance() {
    if (instance == null)
      instance = new MenuBuilderFactory();
    return instance;
  }

  private MenuBuilderFactory() {
  }

  public JMenuBar getMenu(String menuConfig, ActionListener listener) throws RemoteException {
    try {
      menuControl = (MenuControl)TmplEJBLocater.getInstance().getEJBRemote(MenuControl.class.getName());
    }
    catch (NamingException ex) {
    }

    Vector rootNodes = menuControl.getRootNodes(menuConfig);

    if (rootNodes == null || rootNodes.elementAt(0) == null)
      return null;

    Vector firstLevel = menuControl.getChildrenForNode(menuConfig,((TplString)((Vector)((Vector)rootNodes).elementAt(0)).elementAt(0)).getValue());

    JMenuBar jmbDynamicMenu = new JMenuBar();
    jmbDynamicMenu.setFont(new Font("Dialog", Font.PLAIN, 10));

    // build root node and childrens
    for (int i = 0; i < firstLevel.size(); i++) {
      MenuNode menuNode = new MenuNode();
      try {
	menuNode.setMenuId(((TplString)((Vector)firstLevel.elementAt(i)).elementAt(0)).getValue());
	menuNode.setMenuDescription(((TplString)((Vector)firstLevel.elementAt(i)).elementAt(1)).getValue());
	menuNode.setMenuResourceId(((TplString)((Vector)firstLevel.elementAt(i)).elementAt(2)).getValue());
	JMenu item = new JMenu(menuNode.toString());
        item.setFont(new Font("Dialog", Font.PLAIN, 10));
	buildTreeForNode(menuConfig, item, menuNode.getMenuId(), listener);
	jmbDynamicMenu.add(item);
      }
      catch (Exception ex) {
      }
    }

    return jmbDynamicMenu;
  }

  /**
   * recursive call menu proxy for tree creation
   */
  void buildTreeForNode(String menuConfig, JMenu root, String name, ActionListener listener) {
    Vector result = null;
    try {
      result = menuControl.getChildrenForNode(menuConfig, name);
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }

    for (int i = 0; i < result.size(); i++) {
      MenuNode menuNode = new MenuNode();
      menuNode.setMenuId(((TplString)((Vector)result.elementAt(i)).elementAt(0)).getValue());
      menuNode.setMenuDescription(((TplString)((Vector)result.elementAt(i)).elementAt(1)).getValue());
      menuNode.setMenuResourceId(((TplString)((Vector)result.elementAt(i)).elementAt(2)).getValue());
      JMenu item = new JMenu(menuNode.toString());
      item.setFont(new Font("Dialog", Font.PLAIN, 10));
      buildTreeForNode(menuConfig, item, menuNode.getMenuId(), listener);
      if (item.getItemCount() == 0) {
        if (menuNode.toString().startsWith("---")) {
          root.addSeparator();
        }
        else {
	  TmplJMenuItem newItem = new TmplJMenuItem(menuNode.toString());
	  newItem.setMenuNode(menuNode);
	  newItem.setActionCommand(menuNode.getMenuId());
	  newItem.addActionListener(listener);
	  newItem.setFont(new Font("Dialog", Font.PLAIN, 10));
	  root.add(newItem);
	}
      }
      else
        root.add(item);
    }
  }
}
