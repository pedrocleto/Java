package pt.inescporto.siasoft.proc.ejb.dao;

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
public class ActivityRegulatoryDao implements Serializable {
  public TplString fkActivityID = new TplString(0, "fkActivityID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString fkRegID = new TplString(1, "fkRegID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);

  public ActivityRegulatoryDao() {
  }

  public ActivityRegulatoryDao(String activityID, String regID) {
    fkActivityID.setValue(activityID);
    fkRegID.setValue(regID);
  }
}
