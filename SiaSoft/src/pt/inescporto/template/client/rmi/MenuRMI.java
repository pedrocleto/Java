package pt.inescporto.template.client.rmi;

import java.util.Vector;
//import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Cursor;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.rmi.RemoteException;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.beans.MenuNode;
import pt.inescporto.template.utils.beans.FormData;
import pt.inescporto.template.ejb.session.MenuControl;
import pt.inescporto.template.rmi.beans.LoggedUser;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.siasoft.comun.client.UserData;
import java.awt.Container;


/**
 * <p>Title: Template</p>
 * <p>Description: Framework for EJB Development</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class MenuRMI extends JPanel implements TmplMenuSelection, MenuInterface {
  private static final String envType = "RMI";
  JTree jTreeMenu = null;
  TmplMenuShortcut menuShortcut;
  DefaultMutableTreeNode root = null;
  private BorderLayout borderLayout1 = new BorderLayout();
  private MenuControl menu = null;
  private LoggedUser usrInfo = null;
  private JPopupMenu popup = new JPopupMenu();
  private Vector wndCache = new Vector();

  /**
   * Build a new menu for designer
   */
  public MenuRMI() {
    this( null );
  }

  /**
   * Build a new menu for current logged user
   * @param usrInfo user information
   */
  public MenuRMI(LoggedUser usrInfo) {
    this.usrInfo = usrInfo;
    MenuSingleton.setMenu( this );

    try {
      menu = (MenuControl)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.template.ejb.session.MenuControl");
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public String getEnvironmentType() {
    return envType;
  }

  /**
   * Provider of role identificaion for form permissions
   * @return the role for current logged user
   */
  public String getRole() {
    return usrInfo.getRoleId();
  }

  /**
   * Provider of role identificaion for form permissions
   * @return the role for current logged user
   */
  public String getName() {
    return usrInfo.getUsrId();
  }

  /**
   * Provider of enterprise user belongs to
   * @return enterprise ID
   */
  public String getEnterprise() {
    return usrInfo.getEnterpriseId();
  }

  public String getSelectedEnterprise() {
    return null;
  }

  public UserData getUserData() {
    return UserData.getInstance();
  }

  public void addContainerFW(Container container) {
//    System.out.println("XIT!");
  }

  public boolean isSupplier() {
    return usrInfo.isSupplier();
  }

  public void addItemToMenu(String regID, Object regRef) {
  }
  /**
   * Shortcut component to hold most used menu references
   * @param menuShortcut the shortcut list maintainer
   */
  public void setShortcut( TmplMenuShortcut menuShortcut ) {
    this.menuShortcut = menuShortcut;
  }

  private void jbInit() throws Exception {
    // get root node
    Vector menuList = menu.getRootNodes( usrInfo.getMenuConfig() );

    // build root node and childrens
    MenuNode menuNode = new MenuNode();
    try {
      menuNode.setMenuId( ((TplString)((Vector)((Vector)menuList).elementAt(0)).elementAt(0)).getValue() );
      if (((TplString)((Vector)((Vector)menuList).elementAt(0)).elementAt(2)).getValue() == null)
        menuNode.setMenuDescription( ((TplString)((Vector)((Vector)menuList).elementAt(0)).elementAt(1)).getValue() );
      else
        menuNode.setMenuDescription(TmplResourceSingleton.getString(((TplString)((Vector)((Vector)menuList).elementAt(0)).elementAt(2)).getValue()));
      root = new DefaultMutableTreeNode( menuNode );
      buildTreeForNode( root, menuNode.getMenuId() );
    }
    catch( Exception ex ) {
    }

    // create tree and setup
    if( root != null )
      jTreeMenu = new JTree(root);
    else
      jTreeMenu = new JTree();
    jTreeMenu.setFont(new java.awt.Font("Dialog", 0, 10));
    jTreeMenu.setRootVisible(true);
    jTreeMenu.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        int selRow = jTreeMenu.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = jTreeMenu.getPathForLocation(e.getX(), e.getY());
        if (selRow != -1) {
          if (e.getClickCount() == 1) {
            singleClick( e );
          }
          else if (e.getClickCount() == 2) {
            doubleClick();
          }
        }
      }
      public void mouseReleased(MouseEvent e) {
        int selRow = jTreeMenu.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = jTreeMenu.getPathForLocation(e.getX(), e.getY());
        if (selRow != -1) {
          if (e.getClickCount() == 1) {
            singleClick( e );
          }
          else if (e.getClickCount() == 2) {
            doubleClick();
          }
        }
      }
    });

    // show tree
    JScrollPane jspTreeMenu = new JScrollPane( jTreeMenu );
    this.setLayout(borderLayout1);
    this.add( jspTreeMenu, BorderLayout.CENTER );

    // create popup menu
    JMenuItem item;
    popup.add( item = new JMenuItem( TmplResourceSingleton.getString("popup.label.addShortcut") ) );
    item.setActionCommand("ADD_SHORTCUT");
    item.addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent e ) {
        addToShortcut( e );
      }
    });
  }

  /**
   * Process single click mouse events
   * @param e the mouse event
   */
  public void singleClick( MouseEvent e ) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTreeMenu.getLastSelectedPathComponent();

    jTreeMenu.setSelectionPath(jTreeMenu.getPathForLocation(e.getX(), e.getY()));

    if( e.isPopupTrigger() && node != null && node.isLeaf() ) {
      popup.show( this, e.getX(), e.getY() );
    }
  }

  /**
   * Process double click.
   */
  public void doubleClick() {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTreeMenu.getLastSelectedPathComponent();

    setCursor(new Cursor(Cursor.WAIT_CURSOR));
    try {
      if( node.isLeaf() ) {
        JFrame newWindow = getFrameFromCache(((MenuNode)node.getUserObject()).getMenuId());
        if ( newWindow == null ) {
          FormData formData = menu.getFormData( usrInfo.getMenuConfig(), ((MenuNode)node.getUserObject()).getMenuId() );
          String objName = formData.getFormClass();
          String s = objName.substring( 0, objName.lastIndexOf('.') );
          Class objClass = Class.forName( s );
          newWindow = (JFrame)objClass.newInstance();
          newWindow.setTitle( formData.getPageTitle() );
          newWindow.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
              removeFrameFromCache((JFrame)e.getComponent());
            }
            public void windowClosed(WindowEvent e) {
              removeFrameFromCache((JFrame)e.getComponent());
            }
          });
          if (newWindow instanceof pt.inescporto.template.client.rmi.TmplBasicFrame)
            ((TmplBasicFrame)newWindow).MinimumPack();
/*          else if (newWindow instanceof pt.inescporto.euroshoe.client.rmi.objects.JPackedFrame)
            ((pt.inescporto.euroshoe.client.rmi.objects.JPackedFrame)newWindow).MinimumPack();*/
          else
            newWindow.pack();

          Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
          Dimension frameSize = newWindow.getSize();
          if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
          }
          if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
          }
          newWindow.setLocation( (screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
          newWindow.setVisible(true);
          addFrameToCache(new WindowNode(((MenuNode)node.getUserObject()).getMenuId(), ((JFrame)newWindow)));
        }
        else {
          newWindow.setVisible(false);
          newWindow.setVisible(true);
        }
      }
    }
    catch( Exception ex ) {
      JOptionPane.showMessageDialog(this, TmplResourceSingleton.getString("info.dialog.header"),
                                    TmplResourceSingleton.getString("info.dialog.msg.notMade"),
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  /**
   * Process popup menu selections
   * @param e the action event produced by popup menu
   */
  public void addToShortcut( ActionEvent e ) {
    if( e.getActionCommand().compareTo("ADD_SHORTCUT") == 0 ) {
      if( menuShortcut != null ) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTreeMenu.getLastSelectedPathComponent();
        menuShortcut.addShortcut( node );
      }
    }
  }

  /**
   * Recursive call menu data provider EJB for tree creation
   * @param root tree node base
   * @param name the new child node name
   */
  void buildTreeForNode( DefaultMutableTreeNode root, String name ) {
    Vector result = null;

    try {
      result = menu.getChildrenForNode(usrInfo.getMenuConfig(), name);
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }

    for( int i = 0; i < result.size(); i++ ) {
      MenuNode menuNode = new MenuNode();
      menuNode.setMenuId( ((TplString)((Vector)result.elementAt(i)).elementAt(0)).getValue() );

      menuNode.setMenuDescription( ((TplString)((Vector)result.elementAt(i)).elementAt(1)).getValue() );
      if (((TplString)((Vector)result.elementAt(i)).elementAt(1)).getValue() == null)
        menuNode.setMenuDescription( ((TplString)((Vector)result.elementAt(i)).elementAt(1)).getValue() );
      else
        menuNode.setMenuDescription(TmplResourceSingleton.getString(((TplString)((Vector)result.elementAt(i)).elementAt(2)).getValue()));

      DefaultMutableTreeNode node = new DefaultMutableTreeNode( menuNode );
      buildTreeForNode( node, menuNode.getMenuId() );
      root.add( node );
    }
  }

  /**
   * Set selection in the menu based on tree node
   * @param node tree node to select
   */
  public void setSelection( TreeNode node ) {
    DefaultTreeModel model = (DefaultTreeModel)jTreeMenu.getModel();
    TreeNode[] i = model.getPathToRoot( (TreeNode)node );
    jTreeMenu.setSelectionPath( new TreePath(i));
    doubleClick();
  }

  protected void addFrameToCache(WindowNode node) {
    wndCache.add(node);
  }

  protected void removeFrameFromCache(JFrame wnd) {
    for (int i = 0; i < wndCache.size(); i++ ) {
      WindowNode node = (WindowNode) wndCache.elementAt(i);
      if (node.frmWindow.equals(wnd)) {
        wndCache.remove(i);
        break;
      }
    }
  }

  protected JFrame getFrameFromCache( String menuId ) {
    for( int i = 0; i < wndCache.size(); i++ ) {
      WindowNode node = (WindowNode) wndCache.elementAt(i);
      if (node.menuId.compareTo(menuId) == 0)
        return node.frmWindow;
    }
    return null;
  }

  public boolean loadForm(String formID, Object keyValue) {
    return false;
  }
}

class WindowNode {
  String menuId;
  JFrame frmWindow;

  public WindowNode( String menuId, JFrame frmWindow ) {
    this.menuId = menuId;
    this.frmWindow = frmWindow;
  }
}
