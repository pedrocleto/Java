package pt.inescporto.siasoft.go.fun.ejb.dao;

import java.io.Serializable;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplInt;

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
public class EnterpriseCellDao implements Serializable {
  public TplString enterpriseID = new TplString(0, "enterpriseID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString enterpriseCellID = new TplString(1, "enterpriseCellID", 20, TmplKeyTypes.PKKEY, true);
  public TplString enterpriseCellDescription = new TplString(2, "enterpriseCellDescription", 50, TmplKeyTypes.NOKEY, true);
  public TplString enterpriseCellObs = new TplString(3, "enterpriseCellObs", 255, TmplKeyTypes.NOKEY, false);
  public TplInt posX = new TplInt(4, "posX", TmplKeyTypes.NOKEY, false);
  public TplInt posY = new TplInt(5, "posY", TmplKeyTypes.NOKEY, false);
  public TplInt dx = new TplInt(6, "dx", TmplKeyTypes.NOKEY, false);
  public TplInt dy = new TplInt(7, "dy", TmplKeyTypes.NOKEY, false);
  public TplString fatherCellID = new TplString(8, "fatherCellID", 20, TmplKeyTypes.FKKEY, false);

  public EnterpriseCellDao() {
  }

  public String toString() {
    return enterpriseCellDescription.getValue();
  }
}
