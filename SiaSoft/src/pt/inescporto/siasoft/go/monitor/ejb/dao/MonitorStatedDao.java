package pt.inescporto.siasoft.go.monitor.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplTimestamp;
import java.io.Serializable;

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
public class MonitorStatedDao implements Serializable {
  public TplString monitorPlanID = new TplString(0, "monitorPlanID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplTimestamp monitorDate = new TplTimestamp(1, "monitorDate", TmplKeyTypes.PKKEY, true);
  public TplTimestamp monitorEndDate = new TplTimestamp(2, "monitorEndDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp monitorRealStartDate = new TplTimestamp(3, "monitorRealStartDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp monitorRealEndDate = new TplTimestamp(4, "monitorRealEndDate", TmplKeyTypes.NOKEY, false);
  public TplString monitorResult = new TplString(5, "monitorResult", 255, TmplKeyTypes.NOKEY, false);
  public TplString fkUserID = new TplString(6, "fkUserID", 20, TmplKeyTypes.FKKEY, true);
  public TplString status = new TplString(7, "status", 1, TmplKeyTypes.NOKEY, false);

  public MonitorStatedDao() {
  }

  public void setMonitor(MonitorDao monDao) {
    monitorPlanID.setValue(monDao.monitorPlanID.getValue());
    monitorDate.setValue(monDao.monitorDate.getValue());
    monitorEndDate.setValue(monDao.monitorEndDate.getValue());
    monitorRealStartDate.setValue(monDao.monitorRealStartDate.getValue());
    monitorRealEndDate.setValue(monDao.monitorRealEndDate.getValue());
    monitorResult.setValue(monDao.monitorResult.getValue());
    fkUserID.setValue(monDao.fkUserID.getValue());
  }
}
