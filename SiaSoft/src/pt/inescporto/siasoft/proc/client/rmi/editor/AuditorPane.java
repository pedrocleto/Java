package pt.inescporto.siasoft.proc.client.rmi.editor;

import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.awt.Container;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import java.util.ResourceBundle;

import pt.inescporto.siasoft.go.auditor.client.rmi.forms.AuditPlanLinkPane;
import pt.inescporto.siasoft.go.auditor.client.rmi.forms.AuditPlanDefinitionPane;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditPlanDao;
import pt.inescporto.template.client.rmi.FW_JPanelNav;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.TmplJButtonSave;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.design.TmplJButtonCancel;
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
public class AuditorPane extends FW_JPanelNav {
  TmplJButtonCancel jbtnCancel = new TmplJButtonCancel();
  TmplJButtonSave jbtnSave = new TmplJButtonSave();
  JPanel jpnlMenuNav = new JPanel();
  JTabbedPane auditTab = new JTabbedPane();
  JTabbedPane jTabbedPane = new JTabbedPane();
  DataSourceRMI master = null;

  String activityID = null;
  String fkEnterpriseID = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");

  public AuditorPane(String activityID, String fkEnterpriseID) {
    this.activityID = activityID;
    this.fkEnterpriseID = fkEnterpriseID;
    master = new DataSourceRMI("AuditPlan");
    master.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");

    DataSourceRMI dsAuditPlanActions = new DataSourceRMI("AuditPlanActions");
    dsAuditPlanActions.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlanActions");

    DSRelation aaRelation = new DSRelation("AuditPlanAuditPlanActions");
    aaRelation.setMaster(master);
    aaRelation.setDetail(dsAuditPlanActions);
    aaRelation.addKey(new RelationKey("auditPlanID", "auditPlanID"));

    try {
      master.addDataSourceLink(aaRelation);
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

    addFWComponentListener(jbtnSave);
    addFWComponentListener(jbtnCancel);

    dataSource.addDatasourceListener(jbtnSave);
    dataSource.addDatasourceListener(jbtnCancel);

    start();

    doInsert();
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    add(jpnlMenuNav, java.awt.BorderLayout.SOUTH);

    jTabbedPane = new JTabbedPane();
    add(jTabbedPane, BorderLayout.CENTER);

    jTabbedPane.addTab(res.getString("label.definition"), new AuditPlanDefinitionPane(dataSource, this));
    jTabbedPane.addTab(res.getString("label.links"), new AuditPlanLinkPane(dataSource, this,auditTab));

    jpnlMenuNav.add(jbtnSave, null);
    jpnlMenuNav.add(jbtnCancel, null);

    jbtnSave.addActionListener(this);
    jbtnCancel.addActionListener(this);

    setBorder(BorderFactory.createTitledBorder(res.getString("label.title")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
  }

  protected void doInsert() {
    try {
      AuditPlanDao daoEA = (AuditPlanDao)dataSource.getCurrentRecord();
      daoEA.fkActivityID.setLinkKey();
      daoEA.fkEnterpriseID.setLinkKey();
      daoEA.fkActivityID.setValueAsObject(activityID);
      daoEA.fkEnterpriseID.setValueAsObject(fkEnterpriseID);
      dataSource.reLinkAttrs();
      super.doInsert();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  protected boolean doSave() {
    if (super.doSave()) {
      Container parent = getParent();
      while (parent != null && !(parent instanceof JDialog))
        parent = parent.getParent();
      ((JDialog)parent).dispose();
    }
    return true;
  }

  protected void doCancel() {
    Container parent = getParent();
    while (parent != null && !(parent instanceof JDialog))
      parent = parent.getParent();
    ((JDialog)parent).dispose();
  }
 }
