package pt.inescporto.siasoft.go.monitor.ejb.dao;

import java.io.Serializable;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TplTimestamp;
import pt.inescporto.template.elements.TplFloat;

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
public class MonitorParameterDao implements Serializable {
  public TplString monitorPlanID = new TplString(0, "monitorPlanID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY, true);
  public TplTimestamp monitorDate = new TplTimestamp(1, "monitorDate", TmplKeyTypes.PKKEY | TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY, true);
  public TplString parameterID = new TplString(2, "parameterID", 20, TmplKeyTypes.PKKEY, true);
  public TplString parameterDescription = new TplString(3, "parameterDescription", 128, TmplKeyTypes.NOKEY, true);
  public TplTimestamp startPlanDate = new TplTimestamp(4, "startPlanDate", TmplKeyTypes.NOKEY, true);
  public TplTimestamp endPlanDate = new TplTimestamp(5, "endPlanDate", TmplKeyTypes.NOKEY, true);
  public TplTimestamp startDate = new TplTimestamp(6, "startDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp endDate = new TplTimestamp(7, "endDate", TmplKeyTypes.NOKEY, false);
  public TplFloat valueReaded = new TplFloat(8, "valueReaded", TmplKeyTypes.NOKEY, false);
  public TplString obs = new TplString(9, "obs", 512, TmplKeyTypes.NOKEY, false);
  public TplString fkUserID = new TplString(10, "fkUserID", 20, TmplKeyTypes.FKKEY, false);


  public MonitorParameterDao() {
  }
}
