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
public class PreventiveActionsDao implements Serializable {
  public TplString auditPlanID = new TplString(0, "auditPlanID", 20, TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);
  public TplTimestamp auditDate = new TplTimestamp(1, "auditDate", TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);
  public TplString auditActionID = new TplString(2, "auditActionID", 20, TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);
  public TplString preventiveActionID = new TplString(3, "preventiveActionID", 20, TmplKeyTypes.PKKEY, true);
  public TplString preventiveActionDescription = new TplString(4, "preventiveActionDescription", 512, TmplKeyTypes.NOKEY, true);
  public TplTimestamp planStartDate = new TplTimestamp(5, "planStartDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp planEndDate = new TplTimestamp(6, "planEndDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp startDate = new TplTimestamp(7, "startDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp endDate = new TplTimestamp(8, "endDate", TmplKeyTypes.NOKEY, false);
  public TplString obs = new TplString(9, "obs", 512, TmplKeyTypes.NOKEY, false);
  public TplString fkUserID = new TplString(10, "fkUserID", 20, TmplKeyTypes.FKKEY, true);

  public PreventiveActionsDao() {
  }
}
