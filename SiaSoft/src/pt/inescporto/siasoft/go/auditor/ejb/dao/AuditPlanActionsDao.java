package pt.inescporto.siasoft.go.auditor.ejb.dao;

import java.io.Serializable;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplInt;

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
public class AuditPlanActionsDao implements Serializable {
  public TplString auditPlanID = new TplString(0, "auditPlanID", 20, TmplKeyTypes.LNKKEY | TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString auditPlanActionID = new TplString(1, "auditPlanActionID", 20, TmplKeyTypes.PKKEY, true);
  public TplString auditPlanActionDescription = new TplString(2, "auditPlanActionDescription", 255, TmplKeyTypes.NOKEY, true);
  public TplInt startingDay = new TplInt(3, "startingDay", TmplKeyTypes.NOKEY, true);
  public TplInt durationDays = new TplInt(4, "durationDays", TmplKeyTypes.NOKEY, true);

  public AuditPlanActionsDao() {
  }
}
