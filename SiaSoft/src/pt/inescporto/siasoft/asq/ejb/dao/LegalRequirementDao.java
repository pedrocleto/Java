package pt.inescporto.siasoft.asq.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplBoolean;

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
public class LegalRequirementDao implements java.io.Serializable {
  public TplString regId = new TplString(0, "regId", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.LNKKEY, true);
  public TplString legalReqId = new TplString(1, "legalReqId", 20, TmplKeyTypes.PKKEY, true);
  public TplString legalReqDescription = new TplString(2, "legalReqDescription", 100, TmplKeyTypes.NOKEY, true);
  public TplString legalReqObs = new TplString(3, "legalReqObs", 1024, TmplKeyTypes.NOKEY, false);
  public TplBoolean supplierLock = new TplBoolean(4, "supplierLock", TmplKeyTypes.NOKEY, false);

  public LegalRequirementDao() {
  }
}
