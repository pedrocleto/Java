package pt.inescporto.siasoft.go.monitor.ejb.dao;

import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
import java.io.Serializable;
import pt.inescporto.template.elements.TplFloat;
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
public class MonitorPlanParameterDao implements Serializable {
  public TplString monitorPlanID = new TplString(0, "monitorPlanID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY, true);
  public TplString parameterID = new TplString(1, "parameterID", 20, TmplKeyTypes.PKKEY, true);
  public TplString parameterDescription = new TplString(2, "parameterDescription", 128, TmplKeyTypes.NOKEY, true);
  public TplFloat maxValue = new TplFloat(3, "maxValue", TmplKeyTypes.NOKEY, false);
  public TplString unitID = new TplString(4, "fkUnitID", 20, TmplKeyTypes.FKKEY, false);
  public TplString type = new TplString(5, "type", 1, TmplKeyTypes.NOKEY, false);
  public TplString procDesc = new TplString(6, "procDesc", 512, TmplKeyTypes.NOKEY, false);
  public TplString userID = new TplString(7, "fkUserID", 20, TmplKeyTypes.FKKEY, false);
  public TplString regID = new TplString(8, "fkRegID", 20, TmplKeyTypes.FKKEY, false);
  public TplBoolean vleIS = new TplBoolean(9, "vleIS", TmplKeyTypes.NOKEY, false);
  public TplString documentID = new TplString(10, "fkDocumentID", 20, TmplKeyTypes.FKKEY, false);

  public MonitorPlanParameterDao() {
  }
}
