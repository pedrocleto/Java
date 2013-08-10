package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbProfFormDao implements java.io.Serializable {
  public TplString profileId  = new TplString(0, "profile_id", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true );
  public TplString formId     = new TplString(1, "pk_form_id", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true );
  public TplString visible    = new TplString(2, "visible", 1, TmplKeyTypes.NOKEY, true );

  public GlbProfFormDao() {
  }

  public GlbProfFormDao( String profileId, String formId ) {
    this.profileId.setValue( profileId );
    this.formId.setValue( formId );
  }
}
