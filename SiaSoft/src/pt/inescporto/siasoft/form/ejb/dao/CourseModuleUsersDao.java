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
public class CourseModuleUsersDao implements Serializable {
  public TplString coursePlanID = new TplString(0, "coursePlanID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.GENKEY, true);
  public TplString courseID = new TplString(1, "courseID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.GENKEY, true);
  public TplString courseModuleID = new TplString(2, "courseModuleID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY | TmplKeyTypes.GENKEY, true);
  public TplString fkUserID = new TplString(3, "fkUserID", 20, TmplKeyTypes.PKKEY, true);

  public CourseModuleUsersDao() {
  }
}
