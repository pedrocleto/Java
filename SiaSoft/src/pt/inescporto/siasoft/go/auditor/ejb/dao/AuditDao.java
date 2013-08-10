package pt.inescporto.siasoft.go.auditor.ejb.dao;

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
public class AuditDao implements Serializable {
  public TplString auditPlanID = new TplString(0, "auditPlanID", 20, TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);
  public TplTimestamp auditDate = new TplTimestamp(1, "auditDate", TmplKeyTypes.PKKEY, true);
  public TplTimestamp auditEndDate = new TplTimestamp(2, "auditEndDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp auditRealStartDate = new TplTimestamp(3, "auditRealStartDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp auditRealEndDate = new TplTimestamp(4, "auditRealEndDate", TmplKeyTypes.NOKEY, false);
  public TplString auditResult = new TplString(5, "auditResult", 255, TmplKeyTypes.NOKEY, false);
  public TplString fkUserID = new TplString(6, "fkUserID", 20, TmplKeyTypes.FKKEY, true);
  public TplString fkAuditEntityID = new TplString(7, "fkAuditEntityID", 20, TmplKeyTypes.FKKEY, false);
  public TplString fkDocumentID = new TplString(8, "fkDocumentID", 20, TmplKeyTypes.FKKEY, false);

  public AuditDao() {
  }
}
