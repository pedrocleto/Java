package pt.inescporto.siasoft.go.coe.ejb.dao;

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
public class EmergencySituationDao implements Serializable {
  public TplString emergSitID = new TplString(0, "emergSitID", 20, TmplKeyTypes.PKKEY, true);
  public TplString emergSitDescription = new TplString(1, "emergSitDescription", 128, TmplKeyTypes.NOKEY, true);
  public TplString typeEmerg = new TplString(2, "typeEmerg", 1, TmplKeyTypes.NOKEY, true);
  public TplString fkEnterpriseID = new TplString(3, "fkEnterpriseID", 20, TmplKeyTypes.FKKEY, true);
  public TplString fkUserID = new TplString(4, "fkUserID", 20, TmplKeyTypes.FKKEY, true);
  public TplString fkEnvAspID = new TplString(5, "fkEnvAspID", 20, TmplKeyTypes.FKKEY, true);
  public TplString fkActivityID = new TplString(6, "fkActivityID", 20, TmplKeyTypes.FKKEY, false);

  public EmergencySituationDao() {
  }
}
