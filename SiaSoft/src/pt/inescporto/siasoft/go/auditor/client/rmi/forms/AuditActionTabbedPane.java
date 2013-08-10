package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
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
public class AuditActionTabbedPane extends JPanel{
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  public AuditActionTabbedPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();
  }

  private void initialize(){
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createTitledBorder(res.getString("auditAction.label.border")));
    setOpaque(false);
    AuditPlanActionsPane auditPlanActionPane = new AuditPlanActionsPane(datasource, fwCListener);
    tabbedPane.addTab(res.getString("label.definition"), auditPlanActionPane);
    add(tabbedPane, BorderLayout.CENTER);
  }
}
