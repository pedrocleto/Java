package pt.inescporto.siasoft.comun.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplFloat;
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
public class UnitsDao implements java.io.Serializable {
  public TplString unitID = new TplString(0, "unitID", 20, TmplKeyTypes.PKKEY, true);
  public TplString unitDescription = new TplString(1, "unitDesc", 50, TmplKeyTypes.NOKEY, true);
  public TplFloat minValue = new TplFloat(2, "minValue", TmplKeyTypes.NOKEY, true);
  public TplFloat maxValue = new TplFloat(3, "maxValue", TmplKeyTypes.NOKEY, true);
  public TplInt precision = new TplInt(4, "precision", TmplKeyTypes.NOKEY, true);

  public UnitsDao() {
  }
}
