package pt.inescporto.siasoft.go.pga.ejb.dao;

import java.io.Serializable;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;

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
public class ObjectiveDao implements Serializable {
  public TplString goalID = new TplString(0, "goalID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.LNKKEY, true);
  public TplString objectiveID = new TplString(1, "objectiveID", 20, TmplKeyTypes.PKKEY, true);
  public TplString objectiveDescription = new TplString(2, "objectiveDescription", 128, TmplKeyTypes.NOKEY, true);
  public TplString indicator = new TplString(3, "indicator", 128, TmplKeyTypes.NOKEY, true);
  public TplString activityID = new TplString(4, "fkActivityID", 20, TmplKeyTypes.NOKEY, false);
  public TplString regID = new TplString(5, "fkRegID", 20, TmplKeyTypes.NOKEY, false);
  public TplString monitorID = new TplString(6, "fkMonitorID", 20, TmplKeyTypes.NOKEY, false);

  public ObjectiveDao() {
  }
}
