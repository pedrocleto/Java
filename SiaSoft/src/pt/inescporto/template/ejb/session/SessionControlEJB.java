package pt.inescporto.template.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import pt.inescporto.template.ejb.session.*;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.rmi.beans.LoggedUser;

public class SessionControlEJB implements SessionBean {
  protected SessionContext ctx;
  protected GenericQuery genericQuery = null;

  public SessionControlEJB() {
    System.out.println("New SessionControl Session Bean created by EJB container ...");

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

  public void ejbRemove() throws RemoteException {}

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

  public LoggedUser logginUser(String sessionType, String user, String pass) throws RemoteException {
    LoggedUser userInfo = new LoggedUser();
    Vector binds = new Vector();
    Vector result = null;

    System.out.println("Principal name : " + ctx.getCallerPrincipal().getName());
    System.out.println("User Name : " + user);

    try {
      binds.add(new TplString(user));
      result = genericQuery.doQuery("SELECT passwd FROM Users WHERE userID = ?", binds);
      if (result == null || ((Vector)result).size() == 0)
	return null;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }

    String passRead = ((TplString)((Vector)result.elementAt(0)).elementAt(0)).getValue();
    if (pass.compareTo(passRead) != 0)
      return null;

    try {
      binds.add(new TplString(sessionType));
      String stmt = "SELECT b.profile_id, b.language, b.currency, d.enterpriseID, b.menu_config_id, c.supplier  " +
	  "FROM glb_operator_has_profile a, glb_profile b, Enterprise c, Users d, glb_menu_config e " +
	  "WHERE a.profile_id = b.profile_id AND a.IDOperator = d.userID AND d.enterpriseID = c.enterpriseID " +
	  "AND def_prof = 'Y' AND a.IDOperator = ? AND b.menu_config_id = e.menu_config_id AND e.menu_type = ?";
      result = genericQuery.doQuery(stmt, binds);
      if (result == null || ((Vector)result).size() == 0)
	return null;

      Vector firstRow = (Vector)result.elementAt(0);
      userInfo.setUsrId(user);
      userInfo.setProfileId(((TplString)firstRow.elementAt(0)).getValue());
      userInfo.setEnterpriseId(((TplString)firstRow.elementAt(3)).getValue());
      userInfo.setMenuConfig(((TplString)firstRow.elementAt(4)).getValue());
      userInfo.setSupplier(((TplString)firstRow.elementAt(5)).getValue());
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }

    // get the role for the current user
    result = null;
    try {
      binds.remove(1);
      result = genericQuery.doQuery(
	  "SELECT role_id FROM glb_operator_is_in_role " +
	  "WHERE def_perm = 'Y' AND IDOperator = ?", binds);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    if (result != null) {
      Vector firstRow = (Vector)result.elementAt(0);
      userInfo.setRoleId(((TplString)firstRow.elementAt(0)).getValue());
    }

    return userInfo;
  }
}
