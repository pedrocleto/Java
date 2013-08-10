package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbProfileDao implements java.io.Serializable {
  public TplString profileId = new TplString(0, "profile_id", 20, TmplKeyTypes.PKKEY, true);
  public TplString profileName = new TplString(1, "profile_name", 100, TmplKeyTypes.NOKEY, true);
  public TplString language = new TplString(2, "language", 20, TmplKeyTypes.NOKEY, false);
  public TplString currency = new TplString(3, "currency", 20, TmplKeyTypes.NOKEY, false);
  public TplString menu = new TplString(4, "menu_config_id", 20, TmplKeyTypes.NOKEY, true);

  public GlbProfileDao() {
  }
}
