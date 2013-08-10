package pt.inescporto.siasoft.asq.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplInt;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class Theme1Dao implements java.io.Serializable {
  public TplString themeId = new TplString(0, "themeId", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.LNKKEY, true);
  public TplString theme1Id = new TplString(1, "theme1Id", 20, TmplKeyTypes.PKKEY, true);
  public TplString description = new TplString(2, "description", 200, TmplKeyTypes.NOKEY, true);
  public TplInt orderIndex = new TplInt(3, "orderIndex", TmplKeyTypes.NOKEY, true);

  public Theme1Dao() {
  }

  public String toString() {
    StringBuffer s = new StringBuffer();
    s.append(themeId.getField() + "<");
    s.append(themeId.getValue() + ">");
    s.append(", " + theme1Id.getField() + "<");
    s.append(theme1Id.getValue() + ">");
    s.append(", " + description.getField() + "<");
    s.append(description.getValue() + ">");

    return s.toString();
  }
}
