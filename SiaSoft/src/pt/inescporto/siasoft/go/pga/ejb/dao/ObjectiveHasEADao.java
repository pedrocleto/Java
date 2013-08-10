package pt.inescporto.siasoft.go.pga.ejb.dao;

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
public class ObjectiveHasEADao implements Serializable {
  public TplString goalID = new TplString(0, "goalID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.LNKKEY, true);
  public TplString objectiveID = new TplString(1, "objectiveID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.LNKKEY, true);
  public TplString envAspID = new TplString(2, "environmentalAspectID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString eaType = new TplString(3, "eaType", 20, TmplKeyTypes.NOKEY, true);

  public ObjectiveHasEADao() {
  }
}
