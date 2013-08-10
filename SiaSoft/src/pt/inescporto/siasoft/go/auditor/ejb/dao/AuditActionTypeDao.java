package pt.inescporto.siasoft.go.auditor.ejb.dao;

import java.io.Serializable;

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
 * @author not attributable
 * @version 0.1
 */
public class AuditActionTypeDao implements Serializable {
  public TplString auditPlanID = new TplString(0, "auditPlanID", 20, TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);
  public TplTimestamp auditDate = new TplTimestamp(1, "auditDate", TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);
  public TplString auditActionID = new TplString(2, "auditActionID", 20, TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);
  public TplString auditActionTypeID = new TplString(3, "auditActionTypeID", 20, TmplKeyTypes.PKKEY, true);
  public TplString auditActionTypeDescription = new TplString(4, "auditActionTypeDescription", 512, TmplKeyTypes.NOKEY, true);
  public TplTimestamp planStartDate = new TplTimestamp(5, "planStartDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp planEndDate = new TplTimestamp(6, "planEndDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp startDate = new TplTimestamp(7, "startDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp endDate = new TplTimestamp(8, "endDate", TmplKeyTypes.NOKEY, false);
  public TplString obs = new TplString(9, "obs", 512, TmplKeyTypes.NOKEY, false);
  public TplString type = new TplString(10, "type", 1, TmplKeyTypes.NOKEY, false);
  public TplString fkUserID = new TplString(10, "fkUserID", 20, TmplKeyTypes.FKKEY, true);
  public TplString status = new TplString(11, "status", 1, TmplKeyTypes.NOKEY, false);

  public void copyCorrectiveActions(CorrectiveActionsDao caDao) {
    auditPlanID.setValue(caDao.auditPlanID.getValue());
    auditDate.setValue(caDao.auditDate.getValue());
    auditActionID.setValue(caDao.auditActionID.getValue());
    auditActionTypeID.setValue(caDao.correctiveActionID.getValue());
    auditActionTypeDescription.setValue(caDao.correctiveActionDescription.getValue());
    planStartDate.setValue(caDao.planStartDate.getValue());
    planEndDate.setValue(caDao.planEndDate.getValue());
    startDate.setValue(caDao.startDate.getValue());
    endDate.setValue(caDao.endDate.getValue());
    obs.setValue(caDao.obs.getValue());
    fkUserID.setValue(caDao.fkUserID.getValue());
  }

  public void copyPreventiveActions(PreventiveActionsDao paDao) {
    auditPlanID.setValue(paDao.auditPlanID.getValue());
    auditDate.setValue(paDao.auditDate.getValue());
    auditActionID.setValue(paDao.auditActionID.getValue());
    auditActionTypeID.setValue(paDao.preventiveActionID.getValue());
    auditActionTypeDescription.setValue(paDao.preventiveActionDescription.getValue());
    planStartDate.setValue(paDao.planStartDate.getValue());
    planEndDate.setValue(paDao.planEndDate.getValue());
    startDate.setValue(paDao.startDate.getValue());
    endDate.setValue(paDao.endDate.getValue());
    obs.setValue(paDao.obs.getValue());
    fkUserID.setValue(paDao.fkUserID.getValue());
  }

  public AuditActionTypeDao() {
  }
}
