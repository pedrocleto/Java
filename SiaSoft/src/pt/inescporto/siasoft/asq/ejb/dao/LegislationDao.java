package pt.inescporto.siasoft.asq.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;

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
public class LegislationDao implements java.io.Serializable {
  public TplString legisId = new TplString(0, "legislId", 20, TmplKeyTypes.PKKEY, true);
  public TplString legisDescription = new TplString(1, "legislDescription", 200, TmplKeyTypes.NOKEY, true);

  public LegislationDao() {
  }
}
