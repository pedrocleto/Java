package pt.inescporto.siasoft.asq.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplTimestamp;
import pt.inescporto.template.elements.TplBoolean;

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
public class RegulatoryDao implements java.io.Serializable {
  public TplString regId = new TplString(0, "regId", 20, TmplKeyTypes.PKKEY, true);
  public TplString name = new TplString(1, "name", 255, TmplKeyTypes.NOKEY, true);
  public TplString resume = new TplString(2, "resume", 1024, TmplKeyTypes.NOKEY, true);
  public TplTimestamp publishDate = new TplTimestamp(3, "publishDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp startDate = new TplTimestamp(4, "startDate", TmplKeyTypes.NOKEY, true);
  public TplTimestamp comeIntoForceDate = new TplTimestamp(5, "comeIntoForceDate", TmplKeyTypes.NOKEY, false);
  public TplString sourceId = new TplString(6, "fk_sourceId", 20, TmplKeyTypes.FKKEY, true);
  public TplBoolean revocate = new TplBoolean(7, "revocate", TmplKeyTypes.NOKEY, true);
  public TplBoolean incompleted = new TplBoolean(8, "incompleted", TmplKeyTypes.NOKEY, true);
  public TplBoolean state = new TplBoolean(9, "state", TmplKeyTypes.NOKEY, true);
  public TplString docName = new TplString(10, "documentName", 250, TmplKeyTypes.NOKEY, false);
  public TplString notes = new TplString(11, "notes", 512, TmplKeyTypes.NOKEY, false);
  public TplBoolean supplierLock = new TplBoolean(12, "supplierLock", TmplKeyTypes.NOKEY, true);
  public TplTimestamp endDate = new TplTimestamp(13, "endDate", TmplKeyTypes.NOKEY, false);

  public RegulatoryDao() {
  }
}
