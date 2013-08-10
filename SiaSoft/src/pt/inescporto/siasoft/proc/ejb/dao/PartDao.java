package pt.inescporto.siasoft.proc.ejb.dao;

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
 * @author jap
 * @version 0.1
 */
public class PartDao implements Serializable {
  public TplString partClassID = new TplString(0, "partClassID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.LNKKEY, true);
  public TplString partID = new TplString(1, "partID", 20, TmplKeyTypes.PKKEY, true);
  public TplString partDescription = new TplString(2, "partDescription", 100, TmplKeyTypes.NOKEY, true);
  public TplString partTypeID = new TplString(3, "partTypeID", 20, TmplKeyTypes.FKKEY, true);
  public TplString unitID = new TplString(4, "unitID", 20, TmplKeyTypes.FKKEY, true);

  public PartDao() {
  }

  public String toString() {
    return partDescription.getValue();
  }
}
