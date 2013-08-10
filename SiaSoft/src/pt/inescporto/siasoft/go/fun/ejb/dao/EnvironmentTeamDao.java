package pt.inescporto.siasoft.go.fun.ejb.dao;

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
public class EnvironmentTeamDao implements Serializable {
  public TplString enterpriseID = new TplString(0, "enterpriseID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);

  public EnvironmentTeamDao() {
  }
}
