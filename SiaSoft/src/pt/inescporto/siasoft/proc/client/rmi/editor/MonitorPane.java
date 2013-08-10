package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.design.TmplJButtonCancel;
import pt.inescporto.template.client.design.TmplJButtonSave;
import pt.inescporto.template.client.rmi.FW_JPanelNav;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.siasoft.go.monitor.client.rmi.forms.ParametersChartDatasource;
import pt.inescporto.siasoft.go.monitor.client.rmi.forms.MonitorPlanDefinitionPane;
import pt.inescporto.siasoft.go.monitor.client.rmi.forms.MonitorPlanLinkPane;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorPlanDao;

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
public class MonitorPane extends FW_JPanelNav {
  TmplJButtonCancel jbtnCancel = new TmplJButtonCancel();
  TmplJButtonSave jbtnSave = new TmplJButtonSave();
  JPanel jpnlMenuNav = new JPanel();
  JTabbedPane montab = new JTabbedPane();
  JTabbedPane jTabbedPane = new JTabbedPane();
  DataSourceRMI master = null;

  String activityID = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");
  String fkEnterpriseID = null;
  public MonitorPane(String activityID, String fkEnterpriseID) {
    this.activityID = activityID;
    this.fkEnterpriseID = fkEnterpriseID;
    master = new DataSourceRMI("MonitorPlan");
    master.setUrl("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorPlan");

    DataSourceRMI dsObjective = new DataSourceRMI("MonitorPlanParameters");
    dsObjective.setUrl("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorPlanParameter");

    DSRelation mdRelation = new DSRelation("MPParameters");
    mdRelation.setMaster(master);
    mdRelation.setDetail(dsObjective);
    mdRelation.addKey(new RelationKey("monitorPlanID", "monitorPlanID"));
    try {
      master.addDataSourceLink(mdRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    ParametersChartDatasource dsChart = new ParametersChartDatasource();
    DSRelation chRelation = new DSRelation("MonitorParametersMPParameters");
    chRelation.setMaster(dsObjective);
    chRelation.setDetail(dsChart);
    chRelation.addKey(new RelationKey("monitorPlanID", "monitorPlanID"));
    chRelation.addKey(new RelationKey("parameterID", "parameterID"));

     try {
       dsObjective.addDataSourceLink(chRelation);
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
    add(jTabbedPane, java.awt.BorderLayout.CENTER);
    jTabbedPane.addTab(res.getString("label.definition"), new MonitorPlanDefinitionPane(dataSource, this));
    jTabbedPane.addTab(res.getString("label.links"), new MonitorPlanLinkPane(dataSource, this, montab));

    jpnlMenuNav.add(jbtnSave, null);
    jpnlMenuNav.add(jbtnCancel, null);

    jbtnSave.addActionListener(this);
    jbtnCancel.addActionListener(this);

    setBorder(BorderFactory.createTitledBorder(res.getString("label.title")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
  }

  protected void doInsert() {
    try {
      MonitorPlanDao daoEA = (MonitorPlanDao)dataSource.getCurrentRecord();
      daoEA.activityID.setLinkKey();
      daoEA.fkEnterpriseID.setLinkKey();
      daoEA.activityID.setValueAsObject(activityID);
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
