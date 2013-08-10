package pt.inescporto.siasoft.asq.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplTimestamp;

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
public class RegulatoryHistoryDao implements java.io.Serializable {
  public TplString regIdNumber = new TplString(0, "regHistId", 20, TmplKeyTypes.PKKEY, true);
  public TplString regIdFather = new TplString(1, "regIdFather", 20, TmplKeyTypes.FKKEY, true);
  public TplString regIdSon = new TplString(2, "regIdSon", 20, TmplKeyTypes.FKKEY, true);
  public TplString actionType = new TplString(3, "actionType", 20, TmplKeyTypes.FKKEY, true);
  public TplString obs = new TplString(4, "obs", 255, TmplKeyTypes.NOKEY, false);
  public TplTimestamp histDate = new TplTimestamp(5, "histDate", TmplKeyTypes.NOKEY, false);

  public RegulatoryHistoryDao() {
  }
}
