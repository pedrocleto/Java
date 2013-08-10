package pt.inescporto.siasoft.proc.ejb.dao;

import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
import java.io.Serializable;
import pt.inescporto.template.elements.TplObjRef;
import pt.inescporto.template.elements.TplBoolean;

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
public class ActivityTypeDao implements Serializable {
  public TplString activityTypeID = new TplString(0, "activityTypeID", 20, TmplKeyTypes.PKKEY, true);
  public TplString activityTypeDescription = new TplString(1, "activityTypeDescription", 50, TmplKeyTypes.NOKEY, true);
  public TplObjRef palletGraph = new TplObjRef(2, "palletIcon", TmplKeyTypes.NOKEY, true);
  public TplObjRef graphGraph = new TplObjRef(3, "graphIcon", TmplKeyTypes.NOKEY, true);
  public TplString elementType = new TplString(4, "elementType", 1, TmplKeyTypes.NOKEY, true);
  public TplBoolean neadsParent = new TplBoolean(5, "neadsParent", TmplKeyTypes.NOKEY, true);
  public TplBoolean noChildren = new TplBoolean(6, "noChildren", TmplKeyTypes.NOKEY, true);
  public TplBoolean allLevels = new TplBoolean(7, "allLevels", TmplKeyTypes.NOKEY, true);
  public TplBoolean hasLinks = new TplBoolean(8, "hasLinks", TmplKeyTypes.NOKEY, true);

  public ActivityTypeDao() {
  }
}
