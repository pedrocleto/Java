package pt.inescporto.siasoft.go.aa.ejb.dao;

import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
import java.io.Serializable;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class ECritCategoryDao implements Serializable {
  public TplString evalCriterionID = new TplString(0, "evalCriterionID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.LNKKEY, true);
  public TplString eCritCategoryID = new TplString(1, "eCritCategoryID", 20, TmplKeyTypes.PKKEY, true);
  public TplString eCritCategoryDescription = new TplString(2, "eCritCategoryDescription", 100, TmplKeyTypes.NOKEY, true);

  public ECritCategoryDao() {
  }
}
