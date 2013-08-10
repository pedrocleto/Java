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
public class AuditTeamDao implements Serializable {
  public TplString auditPlanID = new TplString(0, "auditPlanID", 20, TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);
  public TplTimestamp auditDate = new TplTimestamp(1, "auditDate", TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);
  public TplString fkUserID = new TplString(2, "fkUserID", 20, TmplKeyTypes.FKKEY | TmplKeyTypes.PKKEY, true);

  public AuditTeamDao() {
  }
}
