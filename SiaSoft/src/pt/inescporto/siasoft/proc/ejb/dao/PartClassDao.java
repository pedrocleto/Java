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
public class PartClassDao implements Serializable {
  public TplString partClassID = new TplString(0, "partClassID", 20, TmplKeyTypes.PKKEY, true);
  public TplString partClassDescription = new TplString(1, "partClassDescription", 100, TmplKeyTypes.NOKEY, true);
  public TplString enterpriseID = new TplString(2, "fkEnterpriseID", 20, TmplKeyTypes.FKKEY, true);

  public PartClassDao() {
  }

  public String toString() {
    return partClassDescription.getValue();
  }
}
