package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import java.util.ResourceBundle;
import pt.inescporto.template.client.rmi.FW_InternalFrameBasic;

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
public class MonitorSearchForm extends FW_InternalFrameBasic {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");
  JTabbedPane jTabbedPane = new JTabbedPane();

  public MonitorSearchForm() {

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    start();
  }

  private void jbInit() throws Exception {
    jTabbedPane = new JTabbedPane();
    add(jTabbedPane, BorderLayout.CENTER);
    jTabbedPane.add(res.getString("label.search"), new MonitorSearchDefinition());
    jTabbedPane.add(res.getString("label.gantt"), new MonitorGanttDefinition());
  }
}
