package pt.inescporto.siasoft.asq.ejb.dao;

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
public class RegulatoryHasClassDao implements Serializable {
  public TplString regID = new TplString(0, "regID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.LNKKEY, true);
  public TplString scopeID = new TplString(1, "scopeID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString legislID = new TplString(2, "legislID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString themeID = new TplString(3, "themeID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString theme1ID = new TplString(4, "theme1ID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);

  public RegulatoryHasClassDao() {
  }
}
