package pt.inescporto.siasoft.go.pga.ejb.dao;

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
public class ObjectiveActionsDao implements Serializable {
  public TplString goalID = new TplString(0, "goalID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.LNKKEY, true);
  public TplString objectiveID = new TplString(1, "objectiveID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.LNKKEY, true);
  public TplString actionID = new TplString(2, "actionID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString actionDescription = new TplString(3, "actionDescription", 128, TmplKeyTypes.NOKEY, true);
  public TplString userID = new TplString(4, "fkUserID", 20, TmplKeyTypes.FKKEY, true);
  public TplTimestamp planStartDate = new TplTimestamp(5, "planStartDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp planEndDate = new TplTimestamp(6, "planEndDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp startDate = new TplTimestamp(7, "startDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp endDate = new TplTimestamp(8, "endDate", TmplKeyTypes.NOKEY, false);

  public ObjectiveActionsDao() {
  }
}