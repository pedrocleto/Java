package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

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
public class AuditTabbedPane extends JPanel{
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  public AuditTabbedPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();
  }
  private void initialize(){
    setLayout(new BorderLayout());
    setOpaque(false);
    tabbedPane.addTab(res.getString("audit.label.auditDefinition"), new AuditActionsDefinitionTabbedPane(datasource, fwCListener));
    tabbedPane.addTab(res.getString("audit.label.auditTeam"), new AuditTeamPane(datasource, fwCListener));
    add(tabbedPane, BorderLayout.CENTER);
  }
}
