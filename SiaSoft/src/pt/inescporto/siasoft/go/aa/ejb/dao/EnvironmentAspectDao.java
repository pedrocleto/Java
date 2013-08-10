package pt.inescporto.siasoft.go.aa.ejb.dao;

import java.io.Serializable;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
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
public class EnvironmentAspectDao implements Serializable {
  public TplString envAspectID = new TplString(0, "envAspID", 20, TmplKeyTypes.PKKEY, true);
  public TplString envAspectName = new TplString(1, "envAspName", 128, TmplKeyTypes.NOKEY, true);
  public TplString envAspectDescription = new TplString(2, "envAspDescription", 512, TmplKeyTypes.NOKEY, false);
  public TplString envAspectClassID = new TplString(3, "envAspClassID", 20, TmplKeyTypes.FKKEY, true);
  public TplString activityID = new TplString(4, "activityID", 20, TmplKeyTypes.FKKEY, true);
  public TplString userID = new TplString(5, "userID", 20, TmplKeyTypes.FKKEY, true);
  public TplBoolean state = new TplBoolean(6, "state", TmplKeyTypes.NOKEY, true);
  public TplString functionality = new TplString(7, "functionality", 1, TmplKeyTypes.NOKEY, true);
  public TplString applicability = new TplString(8, "applicability", 1, TmplKeyTypes.NOKEY, true);
  public TplBoolean significance = new TplBoolean(9, "significance", TmplKeyTypes.NOKEY, false);
  public TplString significanceMethod = new TplString(10, "significanceMethod",1, TmplKeyTypes.NOKEY, false) ;
  public TplBoolean otherSignificance = new TplBoolean(11, "otherSignificance", TmplKeyTypes.NOKEY, false);
  public TplString significanceDesc = new TplString(12, "significanceDesc",20, TmplKeyTypes.NOKEY, false) ;



  public EnvironmentAspectDao() {
  }
}
