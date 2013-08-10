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
public class AuditSearchForm extends FW_InternalFrameBasic {

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  JTabbedPane tabbedPane = null;

  public AuditSearchForm() {
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
    tabbedPane.addTab(res.getString("label.search"), new AuditSearchDefinition());
    tabbedPane.addTab(res.getString("label.gantt"), new AuditGanttDefinition());
    add(tabbedPane, BorderLayout.CENTER);
  }



}
