package pt.inescporto.siasoft.form.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
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
public class CourseModuleDao implements Serializable {
  public TplString coursePlanID = new TplString(0, "coursePlanID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.GENKEY, true);
  public TplString courseID = new TplString(1, "courseID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.GENKEY, true);
  public TplString courseModuleID = new TplString(2, "courseModuleID", 20, TmplKeyTypes.PKKEY, true);
  public TplString courseModuleDescription = new TplString(3, "courseModuleDescription", 128, TmplKeyTypes.NOKEY, true);
  public TplString courseModuleResume = new TplString(4, "courseModuleResume", 512, TmplKeyTypes.NOKEY, false);
  public TplString courseType = new TplString(5, "courseType", 1, TmplKeyTypes.NOKEY, true);
  public TplTimestamp courseModuleStartDate = new TplTimestamp(6, "courseModuleStartDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp courseModuleEndDate = new TplTimestamp(7, "courseModuleEndDate", TmplKeyTypes.NOKEY, false);
  public TplString fkUserID = new TplString(8, "fkUserID", 20, TmplKeyTypes.FKKEY, true);
  public TplString fkTeachingEntityID = new TplString(9, "fkTeachingEntityID", 20, TmplKeyTypes.FKKEY, false);

  public CourseModuleDao() {
  }
}
