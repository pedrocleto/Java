package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbMenuConfigDao implements java.io.Serializable {
  public TplString configId = new TplString(0, "menu_config_id", 20, TmplKeyTypes.PKKEY, true);
  public TplString configName = new TplString(1, "menu_config_descr", 100, TmplKeyTypes.NOKEY, true);

  public GlbMenuConfigDao() {
  }
}
