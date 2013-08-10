package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbFieldPermDao implements java.io.Serializable {
  public TplString roleId = new TplString(0, "role_id", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY, true);
  public TplString formId = new TplString(1, "pk_form_id", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString fieldId = new TplString(2, "pk_field_id", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplInt permission = new TplInt(3, "permission", TmplKeyTypes.NOKEY, true);

  public GlbFieldPermDao() {
  }

  public GlbFieldPermDao(String roleId, String formId, String fieldId) {
    this.roleId.setValue(roleId);
    this.formId.setValue(formId);
    this.fieldId.setValue(fieldId);
  }
}
