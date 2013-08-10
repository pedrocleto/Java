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
 * @author jap
 * @version 0.1
 */
public class EnterpriseDao implements Serializable {
  public TplString enterpriseID = new TplString("enterpriseID", TmplKeyTypes.PKKEY, true);
  public TplString enterpriseName = new TplString("enterpriseName", TmplKeyTypes.NOKEY, true);
  public TplString cae = new TplString("CAE", TmplKeyTypes.NOKEY, false);
  public TplString address = new TplString("address", TmplKeyTypes.NOKEY, false);
  public TplString url = new TplString("URL", TmplKeyTypes.NOKEY, false);
  public TplString coordinator = new TplString("coordinator", TmplKeyTypes.NOKEY, false);
  public TplString supplier = new TplString("supplier", TmplKeyTypes.NOKEY, true);

  public EnterpriseDao() {
  }
}
