package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbMenuItemDao implements java.io.Serializable {
  public TplString configId = new TplString(0, "menu_config_id", 20, TmplKeyTypes.PKKEY, true);
  public TplString itemId = new TplString(1, "menu_item_id", 20, TmplKeyTypes.PKKEY, true);

  public GlbMenuItemDao() {
  }
}
