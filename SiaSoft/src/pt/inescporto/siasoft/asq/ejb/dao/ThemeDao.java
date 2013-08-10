package pt.inescporto.siasoft.asq.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplInt;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class ThemeDao implements java.io.Serializable {
  public TplString themeId = new TplString(0, "themeId", 20, TmplKeyTypes.PKKEY, true);
  public TplString description = new TplString(1, "description", 200, TmplKeyTypes.NOKEY, true);
  public TplInt orderIndex = new TplInt(2, "orderIndex", TmplKeyTypes.NOKEY, true);

  public ThemeDao() {
  }
}
