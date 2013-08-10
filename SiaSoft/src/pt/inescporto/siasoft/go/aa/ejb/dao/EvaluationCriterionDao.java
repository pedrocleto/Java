package pt.inescporto.siasoft.go.aa.ejb.dao;

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
public class EvaluationCriterionDao implements Serializable {
  public TplString evalCriterionID = new TplString(0, "evalCriterionID", 20, TmplKeyTypes.PKKEY, true);
  public TplString evalCriterionIDDescription = new TplString(1, "evalCriterionDescription", 100, TmplKeyTypes.NOKEY, true);

  public EvaluationCriterionDao() {
  }
}
