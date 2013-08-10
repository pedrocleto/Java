package pt.inescporto.siasoft.go.aa.ejb.dao;

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
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class EnvAspSIAMethodDao implements Serializable {
  public TplString envAspectID = new TplString(0, "envAspID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY, true);
  public TplString envSignificance = new TplString(1, "significance",10, TmplKeyTypes.NOKEY, true);
  public TplString envSignificanceDesc = new TplString(2, "significanceDescription",20, TmplKeyTypes.NOKEY, true);
  public TplString envGravityID = new TplString(3, "gravityID",20, TmplKeyTypes.NOKEY, true);
  public TplString envGravity = new TplString(4, "gravity",20, TmplKeyTypes.NOKEY, true);
  public TplString envProbabilityID = new TplString(5, "probabilityID",20, TmplKeyTypes.NOKEY, true);
  public TplString envProbability = new TplString(6, "probability",20, TmplKeyTypes.NOKEY, true);
  public TplString envRiskID = new TplString(7, "riskID",20, TmplKeyTypes.NOKEY, true);
  public TplString envRisk = new TplString(8, "risk",20, TmplKeyTypes.NOKEY, true);
  public TplString envControlID = new TplString(9, "controlID",20, TmplKeyTypes.NOKEY, true);
  public TplString envControl = new TplString(10, "control",20, TmplKeyTypes.NOKEY, true);

  public EnvAspSIAMethodDao() {
  }
}
