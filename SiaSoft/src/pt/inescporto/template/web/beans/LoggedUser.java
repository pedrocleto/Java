package pt.inescporto.template.web.beans;

import java.util.Vector;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.ejb.session.*;
import java.io.Serializable;

public class LoggedUser implements Serializable {
  protected String usrId = null;
  protected String roleId = null;
  protected String profileId = null;
  protected String enterprise = null;
  protected String urlBase = "";
  protected String menuConfig = null;
  protected String supplier = null;

  public void setUsrId(String usrId) {
    this.usrId = usrId;
    populate();
  }

  public String getUsrId() {
    return usrId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setProfileId(String profileId) {
    this.profileId = profileId;
  }

  public String getProfileId() {
    return profileId;
  }

  public void setEnterprise(String enterprise) {
    this.enterprise = enterprise;
  }

  public String getEnterpriseId() {
    return enterprise;
  }

  public void setUrlBase(String urlBase) {
    this.urlBase = urlBase;
  }

  public String getUrlBase() {
    return urlBase;
  }

  public void setMenuConfig(String menuConfig) {
    this.menuConfig = menuConfig;
  }

  public String getMenuConfig() {
    return menuConfig;
  }

  public boolean isSupplier() {
    return supplier.equalsIgnoreCase("Y");
  }

  private void populate() {
    GenericQuery genericQuery = null;

    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/GenericQuery");
      GenericQueryHome genericQueryHome = (GenericQueryHome)PortableRemoteObject.narrow(objref,
	  GenericQueryHome.class);
      genericQuery = genericQueryHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate GenericQuery Home");
      re.printStackTrace();
    }

    // get the profile for the user
    Vector result = null;
    try {
      result = genericQuery.doQuery(
	  "SELECT b.profile_id, b.language, b.currency, d.enterpriseID, b.menu_config_id, c.supplier " +
	  "FROM glb_operator_has_profile a, glb_profile b, Enterprise c, Users d, glb_menu_config e " +
	  "WHERE a.profile_id = b.profile_id AND a.IDOperator = d.userID AND d.enterpriseID = c.enterpriseID " +
	  "AND def_prof = 'Y' AND a.IDOperator = '" + usrId + "' AND b.menu_config_id = e.menu_config_id AND e.menu_type = 'WEB'", new Vector());
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    if (result != null) {
      if (!result.isEmpty()) {
	Vector firstRow = (Vector)result.elementAt(0);
	profileId = ((TplString)firstRow.elementAt(0)).getValue();
	enterprise = ((TplString)firstRow.elementAt(3)).getValue();
	menuConfig = ((TplString)firstRow.elementAt(4)).getValue();
	supplier = ((TplString)firstRow.elementAt(5)).getValue();
      }
      else
        return;
    }

    // get the url base (this is the same for all users)
    result = null;
    try {
      result = genericQuery.doQuery(
	  "SELECT url FROM Enterprise WHERE supplier = 'Y'", new Vector());
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    if (result != null) {
      if (!result.isEmpty()) {
	Vector firstRow = (Vector)result.elementAt(0);
	urlBase = ((TplString)firstRow.elementAt(0)).getValue();
      }
      else
        return;
    }

    // get the role for the current user
    result = null;
    try {
      result = genericQuery.doQuery(
	  "SELECT role_id FROM glb_operator_is_in_role " +
	  "WHERE def_perm = 'Y' AND IDOperator = '" + usrId + "'", new Vector());
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    if (result != null) {
      Vector firstRow = (Vector)result.elementAt(0);
      roleId = ((TplString)firstRow.elementAt(0)).getValue();
    }
  }
}
