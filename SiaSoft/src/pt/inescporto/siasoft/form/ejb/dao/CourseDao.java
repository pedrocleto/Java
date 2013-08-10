package pt.inescporto.siasoft.form.ejb.dao;

import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
import java.io.Serializable;
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
public class CourseDao implements Serializable {
  public TplString coursePlanID = new TplString(0, "coursePlanID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.GENKEY, true);
  public TplString courseID = new TplString(1, "courseID", 20, TmplKeyTypes.PKKEY, true);
  public TplString courseDescription = new TplString(2, "courseDescription", 128, TmplKeyTypes.NOKEY, true);
  public TplString courseResume = new TplString(3, "courseResume", 512, TmplKeyTypes.NOKEY, false);
  public TplTimestamp courseStartDate = new TplTimestamp(4, "courseStartDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp courseEndDate = new TplTimestamp(5, "courseEndDate", TmplKeyTypes.NOKEY, false);
  public TplString fkUserID = new TplString(6, "fkUserID", 20, TmplKeyTypes.FKKEY, true);

  public CourseDao() {
  }
}
