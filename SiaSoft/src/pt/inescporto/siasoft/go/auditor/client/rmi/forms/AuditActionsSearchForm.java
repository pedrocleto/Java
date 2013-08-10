package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JTabbedPane;

import pt.inescporto.template.client.rmi.FW_InternalFrameBasic;

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
public class AuditActionsSearchForm extends FW_InternalFrameBasic {

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  JTabbedPane tabbedPane = null;

  public AuditActionsSearchForm() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    start();
  }
  public void jbInit(){
    tabbedPane = new JTabbedPane();
    tabbedPane.addTab(res.getString("label.search"), new AuditActionsSearchDefinition());
    tabbedPane.addTab(res.getString("label.gantt"), new AuditActionsGanttDefinition());
    add(tabbedPane, BorderLayout.CENTER);
  }
}
