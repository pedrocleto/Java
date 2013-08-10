package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbFieldDao implements java.io.Serializable {
  public TplString fieldId = new TplString(0, "pk_field_id", 20, TmplKeyTypes.PKKEY, true);
  public TplString fieldDesc = new TplString(1, "field_desc", 100, TmplKeyTypes.NOKEY, true);

  public GlbFieldDao() {
  }
}
