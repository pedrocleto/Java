package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbRoleDao implements java.io.Serializable {
  public TplString roleId = new TplString(0, "role_id", 20, TmplKeyTypes.PKKEY, true);
  public TplString roleName = new TplString(1, "role_name", 100, TmplKeyTypes.NOKEY, true);

  public GlbRoleDao() {
  }
}
