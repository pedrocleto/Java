package pt.inescporto.siasoft.comun.ejb.dao;

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
public class ProfileGroupHasEnterpriseDao implements Serializable {
  public TplString profileGroupID = new TplString(0, "profileGroupID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString enterpriseID = new TplString(1, "enterpriseID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);

  public ProfileGroupHasEnterpriseDao() {
  }
}
