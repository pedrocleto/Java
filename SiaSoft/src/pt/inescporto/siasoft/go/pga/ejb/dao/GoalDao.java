package pt.inescporto.siasoft.go.pga.ejb.dao;

import java.io.Serializable;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
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
public class GoalDao implements Serializable {
  public TplString goalID = new TplString(0, "goalID", 20, TmplKeyTypes.PKKEY, true);
  public TplString goalDescription = new TplString(1, "goalDescription", 128, TmplKeyTypes.NOKEY, true);
  public TplTimestamp deadEndDate = new TplTimestamp(2, "deadEndDate", TmplKeyTypes.NOKEY, true);
  public TplString enterpriseID = new TplString(3, "fkEnterpriseID", 20, TmplKeyTypes.FKKEY, true);
  public TplString resume = new TplString(4, "resume", 20, TmplKeyTypes.NOKEY, false);
  public TplString regID = new TplString(5, "fkRegID", 20, TmplKeyTypes.FKKEY, false);

  public GoalDao() {
  }
}
