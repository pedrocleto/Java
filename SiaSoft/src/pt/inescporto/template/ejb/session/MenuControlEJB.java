package pt.inescporto.template.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import pt.inescporto.template.ejb.session.*;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.utils.beans.FormData;

public class MenuControlEJB implements SessionBean {
  protected SessionContext ctx;
  protected GenericQuery genericQuery = null;

  public MenuControlEJB() {
    System.out.println("New MenuControl Session Bean created by EJB container ...");

    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/GenericQuery");
      GenericQueryHome genericQueryHome = (GenericQueryHome)PortableRemoteObject.narrow(objref, GenericQueryHome.class);
      genericQuery = genericQueryHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate GenericQuery Home");
      re.printStackTrace();
    }
  }

  /**
   * EJB Required methods
   */

  public void ejbActivate() throws RemoteException {}

  public void ejbPassivate() throws RemoteException {}

  public void ejbRemove() throws RemoteException {
  }

  public void ejbCreate() {}

  public void setSessionContext(SessionContext context) throws RemoteException {
    ctx = context;
  }

  public void unsetSessionContext() {
    this.ctx = null;
  }

  /**
   * Public methods
   */

  public Vector getRootNodes(String menuConfigId) {
    if (genericQuery != null) {
      try {
	Vector binds = new Vector();
	String selectStmt = "SELECT a.menu_item_id, b.menu_descr, b.menu_loc_ref FROM glb_menu_items a, glb_menus b " +
	    "WHERE a.menu_item_id = b.menu_item_id AND a.menu_config_id = ? AND b.father_id IS NULL " +
	    "ORDER BY b.menu_index";

	binds.add(new TplString(menuConfigId));

	Vector result = genericQuery.doQuery(selectStmt, binds);

	return result;
      }
      catch (Exception ex) {
      }
    }
    return null;
  }

  public Vector getChildrenForNode(String menuConfigId, String menuId) {
    if (genericQuery != null) {
      try {
	Vector binds = new Vector();
	String selectStmt = "SELECT a.menu_item_id, b.menu_descr, b.menu_loc_ref FROM glb_menu_items a, glb_menus b " +
	    "WHERE a.menu_item_id = b.menu_item_id AND a.menu_config_id = ? AND b.father_id = ? " +
	    "ORDER BY b.menu_index";

	binds.add(new TplString(menuConfigId));
	binds.add(new TplString(menuId));

	Vector result = genericQuery.doQuery(selectStmt, binds);

	return result;
      }
      catch (Exception ex) {
      }
    }
    return null;
  }

  public FormData getFormData(String menuConfigId, String menuId) {
    if (genericQuery != null) {
      try {
	Vector binds = new Vector();
	String selectStmt =
	    "SELECT c.form_desc, c.class, c.archives, c.support " +
	    "FROM glb_menu_items a, glb_menus b, glb_form c " +
	    "WHERE a.menu_config_id = ? AND a.menu_item_id = ? AND a.menu_item_id = b.menu_item_id AND b.fk_form_id = c.pk_form_id";

	binds.add(new TplString(menuConfigId));
	binds.add(new TplString(menuId));

	Vector result = genericQuery.doQuery(selectStmt, binds);

	FormData formData = new FormData();
	formData.setPageTitle(((TplString)((Vector)result.elementAt(0)).elementAt(0)).getValue());
	formData.setFormClass(((TplString)((Vector)result.elementAt(0)).elementAt(1)).getValue());
	formData.setFormArchives(((TplString)((Vector)result.elementAt(0)).elementAt(2)).getValue());
	formData.setSupport(((TplString)((Vector)result.elementAt(0)).elementAt(3)).getValue());

	return formData;
      }
      catch (Exception ex) {
      }
    }
    return null;
  }

  public Vector getShortcutsData(String menuConfigId) {
    if (genericQuery != null) {
      try {
	Vector binds = new Vector();
	String selectStmt = "SELECT a.menu_item_id, b.menu_descr, b.menu_loc_ref, c.scIcon " +
	    "FROM glb_menu_shortcuts a, glb_menus b, glb_form c " +
	    "WHERE a.menu_item_id = b.menu_item_id AND a.menu_config_id = ? AND b.fk_form_id = c.pk_form_id " +
	    "ORDER BY a.seq_index";

	binds.add(new TplString(menuConfigId));

	Vector result = genericQuery.doQuery(selectStmt, binds);

	return result;
      }
      catch (Exception ex) {
      }
    }
    return null;
  }

  public Vector getShortcutsForUserData(String menuConfigId, String userId) {
    if (genericQuery != null) {
      try {
	Vector binds = new Vector();
	String selectStmt =
	    "SELECT a.menu_item_id, b.menu_descr, b.menu_loc_ref, c.scIcon " +
	    "FROM glb_menu_operator_scs a, glb_menus b, glb_form c " +
	    "WHERE a.menu_item_id = b.menu_item_id AND a.menu_config_id = ? AND a.IDOperator = ? AND b.fk_form_id = c.pk_form_id";

	binds.add(new TplString(menuConfigId));
	binds.add(new TplString(userId));

	Vector result = genericQuery.doQuery(selectStmt, binds);

	return result;
      }
      catch (Exception ex) {
      }
    }
    return null;
  }

  public void saveShortcutForUser(String menuConfigId, String usrId, String menuId) {
    if (genericQuery != null) {
      Vector binds = new Vector();
      String insertStmt =
	  "INSERT INTO glb_menu_operator_scs( IDOperator, menu_config_id, menu_item_id ) " +
	  "VALUES( ?, ?, ? )";
      binds.add(new TplString(usrId));
      binds.add(new TplString(menuConfigId));
      binds.add(new TplString(menuId));
      try {
	genericQuery.doExecuteQuery(insertStmt, binds);
      }
      catch (RemoteException ex) {
        ex.printStackTrace();
      }
    }
  }

  public int removeShortcutForUser(String menuConfigId, String usrId, String menuId) {
    if (genericQuery != null) {
      Vector binds = new Vector();
      String insertStmt =
	  "DELETE FROM glb_menu_operator_scs " +
	  "WHERE IDOperator = ? AND menu_config_id = ? AND menu_item_id = ?";
      binds.add(new TplString(usrId));
      binds.add(new TplString(menuConfigId));
      binds.add(new TplString(menuId));
      try {
	return genericQuery.doExecuteQuery(insertStmt, binds);
      }
      catch (RemoteException ex) {
        ex.printStackTrace();
      }
    }
    return -1;
  }
}
