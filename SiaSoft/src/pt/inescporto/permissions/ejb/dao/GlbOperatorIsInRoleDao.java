package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbOperatorIsInRoleDao implements java.io.Serializable {
  public TplString operatorId = new TplString(0, "IDOperator", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString roleId = new TplString(1, "role_id", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString defPerm = new TplString(2, "def_perm", 1, TmplKeyTypes.NOKEY, true);

  public GlbOperatorIsInRoleDao() {
  }

  public GlbOperatorIsInRoleDao(String operatorId, String roleId) {
    this.operatorId.setValue(operatorId);
    this.roleId.setValue(roleId);
  }
}
