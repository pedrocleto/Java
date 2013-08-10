package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbProfFieldDao implements java.io.Serializable {
  public TplString profileId = new TplString(0, "profile_id", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString formId = new TplString(1, "pk_form_id", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString fieldId = new TplString(2, "pk_field_id", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString visible = new TplString(3, "visible", 1, TmplKeyTypes.NOKEY, true);

  public GlbProfFieldDao() {
  }

  public GlbProfFieldDao(String profileId, String formId, String fieldId) {
    this.profileId.setValue(profileId);
    this.formId.setValue(formId);
    this.fieldId.setValue(fieldId);
  }
}
