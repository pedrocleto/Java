package pt.inescporto.siasoft.go.coe.ejb.dao;

import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
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
public class EmergencyScenarioDao implements Serializable {
  public TplString emergSitID = new TplString(0, "emergSitID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY, true);
  public TplString scenarioID = new TplString(1, "scenarioID", 20, TmplKeyTypes.PKKEY, true);
  public TplString scenarioDescription = new TplString(2, "scenarioDescription", 128, TmplKeyTypes.NOKEY, true);
  public TplString documentID = new TplString(3, "fkDocumentID", 20, TmplKeyTypes.NOKEY, true);

  public EmergencyScenarioDao() {
  }
}