package pt.inescporto.siasoft.form.client.rmi.forms;

import java.awt.BorderLayout;
import java.util.Vector;
import java.util.ResourceBundle;
import javax.swing.JTabbedPane;

import pt.inescporto.siasoft.form.ejb.dao.CoursePlanDao;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class CoursePlanForm extends FW_InternalFrame {
  DataSourceRMI master = null;
  JTabbedPane tabbedPane = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.form.client.rmi.forms.FormResources");
  public CoursePlanForm() {

    master = new DataSourceRMI("CoursePlan");
    master.setUrl("pt.inescporto.siasoft.form.ejb.session.CoursePlan");

    if (!MenuSingleton.isSupplier()) {
      Vector binds = new Vector();
      binds.add(new TplString(MenuSingleton.getEnterprise()));
      try {
	master.setLinkCondition("fkEnterpriseID = ?", binds);
      }
      catch (DataSourceException ex) {
      }
    }
    DataSourceRMI dsCourse = new DataSourceRMI("Course");
    dsCourse.setUrl("pt.inescporto.siasoft.form.ejb.session.Course");

    DSRelation dsrCourse = new DSRelation("CoursePlanCourse");
    dsrCourse.setMaster(master);
    dsrCourse.setDetail(dsCourse);
    dsrCourse.addKey(new RelationKey("coursePlanID", "coursePlanID"));

    try {
      master.addDataSourceLink(dsrCourse);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    DataSourceRMI dsCourseModule = new DataSourceRMI("CourseModule");
    dsCourseModule.setUrl("pt.inescporto.siasoft.form.ejb.session.CourseModule");

    DSRelation dsCCModule = new DSRelation("CourseCourseModule");
    dsCCModule.setMaster(dsCourse);
    dsCCModule.setDetail(dsCourseModule);
    dsCCModule.addKey(new RelationKey("coursePlanID", "coursePlanID"));
    dsCCModule.addKey(new RelationKey("courseID", "courseID"));

    try {
      dsCourse.addDataSourceLink(dsCCModule);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    DataSourceRMI dsCourseModuleUsers = new DataSourceRMI("CourseModuleUsers");
    dsCourseModuleUsers.setUrl("pt.inescporto.siasoft.form.ejb.session.CourseModuleUsers");

    DSRelation dsModuleUsers = new DSRelation("CourseModuleCourseModuleUsers");
    dsModuleUsers.setMaster(dsCourseModule);
    dsModuleUsers.setDetail(dsCourseModuleUsers);
    dsModuleUsers.addKey(new RelationKey("coursePlanID", "coursePlanID"));
    dsModuleUsers.addKey(new RelationKey("courseID", "courseID"));
    dsModuleUsers.addKey(new RelationKey("courseModuleID", "courseModuleID"));

    try {
      dsCourseModule.addDataSourceLink(dsModuleUsers);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    setDataSource(master);
    allHeaders = new String[] {res.getString("coursePane.label.code"), res.getString("coursePane.label.name"), res.getString("coursePane.label.desc"), res.getString("coursePane.label.courseStartDate"), res.getString("coursePane.label.courseEndDate"), res.getString("course.label.enterprise"), res.getString("coursePane.label.user")};

    setPublisherEvent(SyncronizerSubjects.courseFORM);

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    start();
  }

  private void jbInit() throws Exception {
   tabbedPane = new JTabbedPane();
   tabbedPane.addTab(res.getString("coursePlan.label.definition"), new CoursePlanDefinitionPane(dataSource, this));
   add(tabbedPane, BorderLayout.CENTER);
 }

  protected void doInsert() {
    try {
      if (!MenuSingleton.isSupplier()) {
	CoursePlanDao daoEC = (CoursePlanDao)dataSource.getCurrentRecord();
	daoEC.fkEnterpriseID.setLinkKey();
	daoEC.fkEnterpriseID.setValueAsObject(MenuSingleton.getEnterprise());
	dataSource.reLinkAttrs();
      }
      super.doInsert();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }
}
