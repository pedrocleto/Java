package pt.inescporto.siasoft.asq.ejb.dao;

import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TplBoolean;
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
public class EnterpriseHasRegulatoryDao implements Serializable {
  public TplString enterpriseID = new TplString("enterpriseID", TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString regId = new TplString("regId", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplBoolean addDir = new TplBoolean("app_dir", TmplKeyTypes.NOKEY, false);
  public TplBoolean addInd = new TplBoolean("app_ind", TmplKeyTypes.NOKEY, false);
  public TplBoolean addInf = new TplBoolean("app_inf", TmplKeyTypes.NOKEY, false);

  public EnterpriseHasRegulatoryDao() {
  }
}
