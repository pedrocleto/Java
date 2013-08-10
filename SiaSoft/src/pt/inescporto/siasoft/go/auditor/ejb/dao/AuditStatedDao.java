package pt.inescporto.siasoft.go.auditor.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
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
public class AuditStatedDao implements Serializable {
  public TplString auditPlanID = new TplString(0, "auditPlanID", 20, TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);
  public TplTimestamp auditDate = new TplTimestamp(1, "auditDate", TmplKeyTypes.PKKEY, true);
  public TplTimestamp auditEndDate = new TplTimestamp(2, "auditEndDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp auditRealStartDate = new TplTimestamp(3, "auditRealStartDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp auditRealEndDate = new TplTimestamp(4, "auditRealEndDate", TmplKeyTypes.NOKEY, false);
  public TplString auditResult = new TplString(5, "auditResult", 255, TmplKeyTypes.NOKEY, false);
  public TplString fkUserID = new TplString(6, "fkUserID", 20, TmplKeyTypes.FKKEY, true);
  public TplString fkAuditEntityID = new TplString(7, "fkAuditEntityID", 20, TmplKeyTypes.FKKEY, false);
  public TplString status = new TplString(8, "status", 1, TmplKeyTypes.NOKEY, false);

  public AuditStatedDao() {
  }
  public void setAudit(AuditDao auditDao){
    auditPlanID.setValue(auditDao.auditPlanID.getValue());
    auditDate.setValue(auditDao.auditDate.getValue());
    auditEndDate.setValue(auditDao.auditEndDate.getValue());
    auditRealStartDate.setValue(auditDao.auditRealStartDate.getValue());
    auditRealEndDate.setValue(auditDao.auditRealEndDate.getValue());
    auditResult.setValue(auditDao.auditResult.getValue());
    fkUserID.setValue(auditDao.fkUserID.getValue());
    fkAuditEntityID.setValue(auditDao.fkAuditEntityID.getValue());
  }


}
