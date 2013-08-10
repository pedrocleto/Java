package pt.inescporto.siasoft.asq.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;

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
public class RegulatoryAsLegislationDao implements java.io.Serializable {
  public TplString regId = new TplString("regId", 20, TmplKeyTypes.PKKEY, true);
  public TplString legislId = new TplString("LegislId", 20, TmplKeyTypes.PKKEY, true);

  public RegulatoryAsLegislationDao() {
  }
}
