package pt.inescporto.siasoft.proc.ejb.dao;

import java.io.Serializable;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
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
 * @author jap
 * @version 0.1
 */
public class ActivityDao implements Serializable {
  public TplString activityID = new TplString(0, "activityID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.GENKEY, true);
  public TplString activityDescription = new TplString(1, "activityDescription", 50, TmplKeyTypes.NOKEY, true);
  public TplInt posX = new TplInt(2, "posX", TmplKeyTypes.NOKEY, true);
  public TplInt posY = new TplInt(3, "posY", TmplKeyTypes.NOKEY, true);
  public TplInt dx = new TplInt(4, "dx", TmplKeyTypes.NOKEY, true);
  public TplInt dy = new TplInt(5, "dy", TmplKeyTypes.NOKEY, true);
  public TplString activityTypeID = new TplString(6, "activityTypeID", 20, TmplKeyTypes.FKKEY, true);
  public TplString activityFatherID = new TplString(7, "activityFatherID", 20, TmplKeyTypes.FKKEY, false);
  public TplString enterpriseID = new TplString(8, "fkEnterpriseID", 20, TmplKeyTypes.FKKEY, true);
  public TplString activityPkSequence = new TplString(9, "activityPkSequence", 1024, TmplKeyTypes.NOKEY, true);
  public TplString fkActivityID = new TplString(10, "fkActivityID", 20, TmplKeyTypes.FKKEY, false);

  public ActivityDao() {
  }
}
