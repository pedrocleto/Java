package pt.inescporto.siasoft.form.client.rmi.forms;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JTabbedPane;

import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.siasoft.events.SyncronizerSubjects;

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
public class CourseForm extends FW_InternalFrame {
  JTabbedPane tabbedPane = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.form.client.rmi.forms.FormResources");

  public CourseForm() {
    DataSourceRMI master = new DataSourceRMI("Course");
    master.setUrl("pt.inescporto.siasoft.form.ejb.session.Course");

    DataSourceRMI dsCourseModule = new DataSourceRMI("CourseModule");
    dsCourseModule.setUrl("pt.inescporto.siasoft.form.ejb.session.CourseModule");

    DSRelation dsCCModule = new DSRelation("CourseCourseModule");
    dsCCModule.setMaster(master);
    dsCCModule.setDetail(dsCourseModule);
    dsCCModule.addKey(new RelationKey("coursePlanID", "coursePlanID"));
    dsCCModule.addKey(new RelationKey("courseID", "courseID"));

    try {
      master.addDataSourceLink(dsCCModule);
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
    tabbedPane.addTab("", new CourseTabbedPane(dataSource,this));
    add(tabbedPane, BorderLayout.CENTER);
  }
}
