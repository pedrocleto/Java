package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbFormIsInRoleDao implements java.io.Serializable {
  public TplString roleId = new TplString(0, "role_id", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString formId = new TplString(1, "pk_form_id", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplInt permission = new TplInt(2, "permission", TmplKeyTypes.NOKEY, true);

  public GlbFormIsInRoleDao() {
  }

  public GlbFormIsInRoleDao(String roleId, String formId) {
    this.roleId.setValue(roleId);
    this.formId.setValue(formId);
  }
}
