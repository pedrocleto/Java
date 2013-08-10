package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbMenuDao implements java.io.Serializable {
  public TplString menuId = new TplString(0, "menu_item_id", 20, TmplKeyTypes.PKKEY, true);
  public TplString menuName = new TplString(1, "menu_descr", 100, TmplKeyTypes.NOKEY, true);
  public TplString locRef = new TplString(2, "menu_loc_ref", 100, TmplKeyTypes.NOKEY, true);
  public TplInt index = new TplInt(3, "menu_index", TmplKeyTypes.NOKEY, true);
  public TplString fatherId = new TplString(4, "father_id", 20, TmplKeyTypes.NOKEY, false);
  public TplString formId = new TplString(5, "fk_form_id", 20, TmplKeyTypes.NOKEY, false);

  public GlbMenuDao() {
  }
}
