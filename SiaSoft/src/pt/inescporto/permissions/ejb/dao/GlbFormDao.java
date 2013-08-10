package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbFormDao implements java.io.Serializable {
  public TplString formId = new TplString(0, "pk_form_id", 20, TmplKeyTypes.PKKEY, true);
  public TplString formDesc = new TplString(1, "form_desc", 100, TmplKeyTypes.NOKEY, true);
  public TplString classRun = new TplString(2, "class", 200, TmplKeyTypes.NOKEY, true);
  public TplString archive = new TplString(3, "archives", 200, TmplKeyTypes.NOKEY, true);
  public TplString support = new TplString(4, "support", 100, TmplKeyTypes.NOKEY, false);
//  public TplObjRef scIcon = new TplObjRef( "scIcon", TmplKeyTypes.NOKEY, false );

  public GlbFormDao() {
  }

  public GlbFormDao(String formId) {
    this.formId.setValue(formId);
  }
}
