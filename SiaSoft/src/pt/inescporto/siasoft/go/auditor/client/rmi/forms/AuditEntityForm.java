package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ResourceBundle;
import javax.swing.JPanel;

import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
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
public class AuditEntityForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  TmplJLabel jlblAuditEntityID = new TmplJLabel();
  TmplJTextField jtfldAuditEntityID = new TmplJTextField();

  TmplJLabel jlblAuditEntityName = new TmplJLabel();
  TmplJTextField jtfldAuditEntityName = new TmplJTextField();

  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public AuditEntityForm() {
    super();
    DataSourceRMI master = new DataSourceRMI("AuditEntity");
    master.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditEntity");

    setDataSource(master);
    init();
    allHeaders = new String[] {res.getString("label.code"), res.getString("auditEntity.label.name")};
    start();
  }

  public void init() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    dataSource.addDatasourceListener(jtfldAuditEntityID);
    dataSource.addDatasourceListener(jtfldAuditEntityName);

    addFWComponentListener(jtfldAuditEntityID);
    addFWComponentListener(jtfldAuditEntityName);
  }

  private void jbInit() throws Exception {

    jlblAuditEntityID.setText(res.getString("label.code"));
    jtfldAuditEntityID.setField("auditEntityID");
    jtfldAuditEntityID.setHolder(jlblAuditEntityID);

    jlblAuditEntityName.setText(res.getString("auditEntity.label.name"));
    jtfldAuditEntityName.setField("auditEntityName");
    jtfldAuditEntityName.setHolder(jlblAuditEntityName);

    add(jPanel1, java.awt.BorderLayout.CENTER);
    jPanel1.setLayout(borderLayout1);
    jPanel2.setLayout(gridBagLayout1);
    jPanel2.setOpaque(false);
    jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);
    jPanel2.add(jlblAuditEntityID, new GridBagConstraints(0, 0, 1, 1, 70.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jtfldAuditEntityID, new GridBagConstraints(1, 0, 1, 1, 100.0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jlblAuditEntityName, new GridBagConstraints(0, 1, 1, 1, 70.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jtfldAuditEntityName, new GridBagConstraints(1, 1, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }
}
