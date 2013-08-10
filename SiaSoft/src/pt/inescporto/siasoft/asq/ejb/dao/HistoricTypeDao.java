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
 * @author not attributable
 * @version 0.1
 */
public class HistoricTypeDao implements java.io.Serializable {
  public TplString historicTypeId = new TplString(0, "historicTypeID", 20, TmplKeyTypes.PKKEY, true);
  public TplString historicTypeDescription = new TplString(1, "historicTypeDescription", 100, TmplKeyTypes.NOKEY, true);

  public HistoricTypeDao() {
  }
}
