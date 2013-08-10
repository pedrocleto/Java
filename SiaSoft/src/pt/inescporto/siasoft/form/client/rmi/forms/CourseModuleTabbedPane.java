package pt.inescporto.siasoft.form.client.rmi.forms;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
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
public class CourseModuleTabbedPane  extends JPanel{
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.form.client.rmi.forms.FormResources");

  public CourseModuleTabbedPane(DataSource datasource, FW_ComponentListener fwCListener) {

    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

  }
  private void initialize(){
    setLayout(new BorderLayout());
    setOpaque(false);
    CoursePane coursePane = new CoursePane(datasource, fwCListener);
    tabbedPane.addTab(res.getString("course.label.definition"), coursePane);

    CourseModulePane courseModulePane = new CourseModulePane(datasource, coursePane.getMasterListener());
    tabbedPane.add(res.getString("course.label.courseModuleDefinition"), courseModulePane);
    tabbedPane.add(res.getString("course.label.courseModuleUsers"), new CourseModuleUsersPane(datasource, courseModulePane.getMasterListener()));
    add(tabbedPane, BorderLayout.CENTER);
  }
}
