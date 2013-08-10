package pt.inescporto.siasoft.go.auditor.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import java.io.Serializable;
import pt.inescporto.template.elements.TplTimestamp;
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
 * @author not attributable
 * @version 0.1
 */
public class AuditActionsDao implements Serializable {
  public TplString auditPlanID = new TplString(0, "auditPlanID", 20, TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);
  public TplTimestamp auditDate = new TplTimestamp(1, "auditDate", TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);
  public TplString auditActionID = new TplString(2, "auditActionID", 20, TmplKeyTypes.PKKEY, true);
  public TplString auditActionDescription = new TplString(3, "auditActionDescription", 255, TmplKeyTypes.NOKEY, true);
  public TplTimestamp startPlanDate = new TplTimestamp(4, "startPlanDate", TmplKeyTypes.NOKEY, true);
  public TplTimestamp endPlanDate = new TplTimestamp(5, "endPlanDate", TmplKeyTypes.NOKEY, true);
  public TplTimestamp startDate = new TplTimestamp(6, "startDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp endDate = new TplTimestamp(7, "endDate", TmplKeyTypes.NOKEY, false);
  public TplBoolean ncValue = new TplBoolean(8, "ncValue", TmplKeyTypes.NOKEY, false);
  public TplString obs = new TplString(9, "obs", 512, TmplKeyTypes.NOKEY, false);
  public TplString fkUserID = new TplString(10, "fkUserID", 20, TmplKeyTypes.FKKEY, false);

  public AuditActionsDao() {
  }
}
