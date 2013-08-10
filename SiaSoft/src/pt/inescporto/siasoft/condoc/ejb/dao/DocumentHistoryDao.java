package pt.inescporto.siasoft.condoc.ejb.dao;

import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
import java.io.Serializable;
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
 * @author not attributable
 * @version 0.1
 */
public class DocumentHistoryDao implements Serializable {
  public TplString documentID = new TplString(0, "documentID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.LNKKEY, true);
  public TplTimestamp actionDate = new TplTimestamp(1, "actionDate", TmplKeyTypes.PKKEY, true);
  public TplString fkUserID = new TplString(2, "fkUserID", 20, TmplKeyTypes.FKKEY, true);
  public TplString actionType = new TplString(3, "actionType", 1, TmplKeyTypes.NOKEY, true);
  public TplString fkActionUserID = new TplString(4, "fkActionUserID", 20, TmplKeyTypes.FKKEY, false);
  public TplString obs = new TplString(5, "obs", 512, TmplKeyTypes.NOKEY, false);
  public TplString fkEnterpriseID = new TplString(6, "fkEnterpriseID", 20, TmplKeyTypes.FKKEY, true);

  public DocumentHistoryDao() {
  }
}
