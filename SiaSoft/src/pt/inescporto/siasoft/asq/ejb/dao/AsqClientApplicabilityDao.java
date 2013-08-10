package pt.inescporto.siasoft.asq.ejb.dao;

import java.io.Serializable;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplBoolean;
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
public class AsqClientApplicabilityDao implements Serializable {
  public TplString recordID = new TplString(0, "recordID", 30, TmplKeyTypes.PKKEY, true);
  public TplString enterpriseID = new TplString(1, "enterpriseID", 20, TmplKeyTypes.FKKEY, true);
  public TplString userID = new TplString(2, "userID", 20, TmplKeyTypes.FKKEY, false);
  public TplString profileID = new TplString(3, "profileID", 20, TmplKeyTypes.FKKEY, false);
  public TplString regID = new TplString(4, "regID", 20, TmplKeyTypes.FKKEY, true);
  public TplString legalRequirmentID = new TplString(5, "legalRequirementID", 20, TmplKeyTypes.FKKEY, false);
  public TplBoolean addDir = new TplBoolean(6, "app_dir", TmplKeyTypes.NOKEY, true);
  public TplBoolean addInd = new TplBoolean(7, "app_ind", TmplKeyTypes.NOKEY, true);
  public TplBoolean addInf = new TplBoolean(8, "app_inf", TmplKeyTypes.NOKEY, true);
  public TplString obs= new TplString(9, "obs", 1024, TmplKeyTypes.NOKEY, false);
  public TplBoolean state = new TplBoolean(10, "state", TmplKeyTypes.NOKEY, false);

  public AsqClientApplicabilityDao() {
  }
}
