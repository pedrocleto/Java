package pt.inescporto.template.rmi.beans;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class LoggedUser implements java.io.Serializable {
  protected String usrId = null;
  protected String roleId = null;
  protected String profileId = null;
  protected String menuConfig = null;
  protected String enterpriseId = null;
  protected String supplier = null;

  public void setUsrId( String usrId ) {
    this.usrId = usrId;
  }

  public String getUsrId() {
    return usrId;
  }

  public void setRoleId( String roleId ) {
    this.roleId = roleId;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setProfileId( String profileId ) {
    this.profileId = profileId;
  }

  public String getProfileId() {
    return profileId;
  }

  public void setMenuConfig( String menuConfig ) {
    this.menuConfig = menuConfig;
  }

  public String getMenuConfig() {
    return menuConfig;
  }

  public void setEnterpriseId(String enterpriseId) {
    this.enterpriseId = enterpriseId;
  }

  public String getEnterpriseId() {
    return enterpriseId;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public boolean isSupplier() {
    return supplier.equalsIgnoreCase( "Y" );
  }
}
