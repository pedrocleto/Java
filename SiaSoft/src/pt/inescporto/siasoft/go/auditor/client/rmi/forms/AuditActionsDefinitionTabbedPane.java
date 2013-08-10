package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import javax.swing.JLabel;
import pt.inescporto.template.client.design.TmplJLabel;
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
public class AuditActionsDefinitionTabbedPane extends JPanel {
  JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");
  DataSource datasource = null;
  FW_ComponentListener fwCListener = null;

  public AuditActionsDefinitionTabbedPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    AuditActionsPane auditActionsPane = new AuditActionsPane(datasource, fwCListener);
    PreventiveActionsPane preventiveActionsPane = new PreventiveActionsPane(datasource, auditActionsPane.getMasterListener(), auditActionsPane.getJlblAuditActionName());
    CorrectiveActionsPane correctiveActionsPane = new CorrectiveActionsPane(datasource, auditActionsPane.getMasterListener(), auditActionsPane.getJlblAuditActionName());
    tabbedPane.addTab(res.getString("label.definition"), auditActionsPane);
    tabbedPane.addTab(res.getString("auditDefinition.label.preventiveActions"), preventiveActionsPane);
    tabbedPane.addTab(res.getString("auditDefinition.label.correctiveActions"), correctiveActionsPane);

    add(tabbedPane, BorderLayout.CENTER);
  }
}
