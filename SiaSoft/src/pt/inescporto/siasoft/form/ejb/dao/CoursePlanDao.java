package pt.inescporto.siasoft.form.ejb.dao;

import java.io.Serializable;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplTimestamp;

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
public class CoursePlanDao implements Serializable {
  public TplString coursePlanID = new TplString(0, "coursePlanID", 20, TmplKeyTypes.PKKEY, true);
  public TplString coursePlanName = new TplString(1, "coursePlanName", 128, TmplKeyTypes.NOKEY, true);
  public TplString coursePlanResume = new TplString(2, "coursePlanResume", 512, TmplKeyTypes.NOKEY, true);
  public TplTimestamp coursePlanStartDate = new TplTimestamp(3, "coursePlanStartDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp coursePlanEndDate = new TplTimestamp(4, "coursePlanEndDate", TmplKeyTypes.NOKEY, false);
  public TplString fkEnterpriseID = new TplString(5, "fkEnterpriseID", 20, TmplKeyTypes.FKKEY, true);
  public TplString fkUserID = new TplString(6, "fkUserID", 20, TmplKeyTypes.FKKEY, true);

  public CoursePlanDao() {
  }
}
