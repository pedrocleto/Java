package pt.inescporto.siasoft.proc.ejb.dao;

import java.io.Serializable;

import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TplFloat;

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
public class ActivityHasPartDao implements Serializable {
  public TplString activityID = new TplString(0, "activityID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString partClassID = new TplString(1, "partClassID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString partID = new TplString(2, "partID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString flowType = new TplString(4, "flowType", 1, TmplKeyTypes.PKKEY, true);
  public TplFloat qty = new TplFloat(3, "qty", TmplKeyTypes.NOKEY, true);

  public ActivityHasPartDao() {
  }
}
