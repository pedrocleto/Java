package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import pt.inescporto.template.client.util.DataSourceRMI;
import javax.swing.JTabbedPane;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.util.DataSourceException;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorPlanDao;
import pt.inescporto.siasoft.go.monitor.client.rmi.forms.ParametersChartDatasource;
import java.util.ResourceBundle;
import java.awt.Cursor;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorHasLinksDao;
import java.awt.BorderLayout;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import javax.naming.NamingException;
import pt.inescporto.siasoft.go.monitor.ejb.session.MonitorHasLinks;
import java.rmi.RemoteException;
import java.util.Hashtable;
import pt.inescporto.template.client.util.TmplFormModes;

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
public class MonitorPlanForm extends FW_InternalFrame {
  JTabbedPane jTabbedPane = new JTabbedPane();
  JTabbedPane montab = new JTabbedPane();
  DataSourceRMI master = null;
  private Hashtable<String, Vector> eaList = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  public MonitorPlanForm() {
    master = new DataSourceRMI("MonitorPlan");
    master.setUrl("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorPlan");

    if (!MenuSingleton.isSupplier()) {
      Vector binds = new Vector();
      binds.add(new TplString(MenuSingleton.getEnterprise()));
      try {
	master.setLinkCondition("fkEnterpriseID = ?", binds);
      }
      catch (DataSourceException ex1) {
      }
    }

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
    setAccessPermitionIdentifier("MONITOR_PLAN");

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    allHeaders = new String[] {res.getString("monitorPlan.label.code"), res.getString("monitorPlan.label.desc"), res.getString("monitorPlan.label.periodicity"),
	res.getString("monitorPlan.label.nextMonitorDate"), res.getString("monitorPlan.label.enterprise"), res.getString("monitorPlan.label.envAspect"), res.getString("monitorPlan.label.activity")};
    start();
  }

  private void jbInit() throws Exception {
    jTabbedPane = new JTabbedPane();
    add(jTabbedPane, java.awt.BorderLayout.CENTER);
    jTabbedPane.addTab(res.getString("label.definition"), new MonitorPlanDefinitionPane(dataSource, this));
    jTabbedPane.addTab(res.getString("label.links"), new MonitorPlanLinkPane(dataSource, this, montab));
  }

  protected void doInsert() {
    try {
      if (!MenuSingleton.isSupplier()) {
	MonitorPlanDao daoEC = (MonitorPlanDao)dataSource.getCurrentRecord();
	daoEC.fkEnterpriseID.setLinkKey();
	daoEC.fkEnterpriseID.setValueAsObject(MenuSingleton.getEnterprise());
	dataSource.reLinkAttrs();
      }
      super.doInsert();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  public boolean setPrimaryKey(Object keyValue) {
    try {
      if (keyValue != null) {
	setCursor(new Cursor(Cursor.WAIT_CURSOR));
	MonitorPlanDao attrs = (MonitorPlanDao)dataSource.getAttrs();
	attrs.monitorPlanID.setValue((String)keyValue);
	dataSource.findByPK(attrs);

      }
    }
    catch (DataSourceException ex) {
      return false;
    }
    finally {
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    return true;
  }

  protected void doNext() {
    try {
      dataSource.next();
    }
    catch (DataSourceException dsex) {
      if (dsex.getStatusCode() == DataSourceException.LAST_RECORD)
	showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.last"));
      else
	showErrorMessage(dsex.getException().getMessage());
    }
    updateTable();
  }

  protected void doPrevious() {
    try {
      dataSource.previous();
    }
    catch (DataSourceException dsex) {
      if (dsex.getStatusCode() == DataSourceException.FIRST_RECORD)
	showInformationMessage(TmplResourceSingleton.getString("info.dialog.msg.first"));
      else
	showErrorMessage(dsex.getException().getMessage());
    }
    updateTable();
  }

  protected void insertAfter() {
    updateTable();
  }

  protected void updateAfter() {
    updateTable();
  }

  protected void refresh() {
    try {
      dataSource.refresh();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    updateTable();
  }

  protected void doCancel() {
    if (mode == TmplFormModes.MODE_INSERT || mode == TmplFormModes.MODE_FIND) {
      try {
	if (dataSource.first()) {
	  // set mode to VIEW
	  setMode(TmplFormModes.MODE_SELECT);
	}
	else {
	  doInsert();
	}
      }
      catch (DataSourceException dsex) {
	showErrorMessage(dsex.getMessage());
      }

    }
    else {
      setMode(TmplFormModes.MODE_SELECT);
      refresh();
    }
    updateTable();
  }

  protected void updateTable() {
    MonitorHasLinksPane mon = new MonitorHasLinksPane();
    MonitorHasLinksTab jMonitorLinksPane = new MonitorHasLinksTab(mon);

    MonitorPlanDao dt = null;
    String monitorPlanID = null;
    String enterpriseID = null;
    try {
      dt = (MonitorPlanDao)dataSource.getCurrentRecord();
      monitorPlanID = dt.monitorPlanID.getValue();
      enterpriseID = dt.fkEnterpriseID.getValue();
    }
    catch (DataSourceException ex) {

    }
    MonitorHasLinksDao monitorHasLinksDao = null;
    try {
      MonitorHasLinks monitorLinks = (MonitorHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorHasLinks");
      try {
	monitorHasLinksDao = monitorLinks.getLinks(monitorPlanID, enterpriseID);
      }
      catch (UserException ex) {
	ex.printStackTrace();
      }
      catch (RemoteException ex) {
	ex.printStackTrace();
      }
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
    try{
      eaList = (Hashtable<String, Vector>)monitorHasLinksDao.eaList;
      if (eaList.containsKey(monitorPlanID)) {
	Vector v = monitorHasLinksDao.eaList.get(monitorPlanID);
	jMonitorLinksPane.refresh(v);
      }
      montab.removeAll();
      montab.add(jMonitorLinksPane.getMonitorHasLinksPane(), 0);
      montab.setTitleAt(0, res.getString("label.activelinks"));
    }
    catch(Exception ex){

    }
  }

}
