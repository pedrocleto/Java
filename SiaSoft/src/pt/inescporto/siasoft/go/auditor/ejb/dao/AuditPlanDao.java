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
public class AuditPlanDao implements Serializable {
  public TplString auditPlanID = new TplString(0, "auditPlanID", 20, TmplKeyTypes.PKKEY, true);
  public TplString auditPlanScope = new TplString(1, "auditPlanScope", 128, TmplKeyTypes.NOKEY, true);
  public TplString auditPlanPropose = new TplString(2, "auditPlanPropose", 512, TmplKeyTypes.NOKEY, true);
  public TplString periodicity = new TplString(3, "periodicity", 1, TmplKeyTypes.NOKEY, true);
  public TplString auditType = new TplString(4, "auditType", 1, TmplKeyTypes.NOKEY, true);
  public TplTimestamp nextAuditDate = new TplTimestamp(5, "nextAuditDate", TmplKeyTypes.NOKEY, true);
  public TplString fkEnterpriseID = new TplString(6, "fkEnterpriseID", 20, TmplKeyTypes.FKKEY, true);
  public TplString fkEnterpriseCellID = new TplString(7, "fkEnterpriseCellID", 20, TmplKeyTypes.FKKEY, false);
  public TplString fkActivityID = new TplString(8, "fkActivityID", 20, TmplKeyTypes.FKKEY, false);

  public AuditPlanDao() {
  }
}
