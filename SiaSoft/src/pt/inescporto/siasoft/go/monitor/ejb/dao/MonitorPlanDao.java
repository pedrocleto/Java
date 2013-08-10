package pt.inescporto.siasoft.go.monitor.ejb.dao;

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
public class MonitorPlanDao implements Serializable {
  public TplString monitorPlanID = new TplString(0, "monitorPlanID", 20, TmplKeyTypes.PKKEY, true);
  public TplString monitorPlanDescription = new TplString(1, "monitorPlanDescription", 128, TmplKeyTypes.NOKEY, true);
  public TplString periodicity = new TplString(2, "periodicity", 1, TmplKeyTypes.NOKEY, true);
  public TplTimestamp nextMonitorDate = new TplTimestamp(3, "nextMonitorDate", TmplKeyTypes.NOKEY, true);
  public TplString fkEnterpriseID = new TplString(4, "fkEnterpriseID", 20, TmplKeyTypes.FKKEY, true);
  public TplString envAspectID = new TplString(5, "fkEnvAspectID", 20, TmplKeyTypes.FKKEY, false);
  public TplString activityID = new TplString(6, "fkActivityID", 20, TmplKeyTypes.FKKEY, false);

  public MonitorPlanDao() {
  }
}
