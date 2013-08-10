package pt.inescporto.siasoft.go.coe.client.rmi.forms;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationDao;

import java.util.ResourceBundle;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.util.Vector;
import javax.swing.JTabbedPane;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.siasoft.go.coe.ejb.session.EmergencySituationHasLinks;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationHasLinksDao;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.util.Hashtable;

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

public class EmergencySituationForm extends FW_InternalFrame {
  JTabbedPane jTabbedPane = new JTabbedPane();
  JTabbedPane jtpScenarios = new JTabbedPane(JTabbedPane.BOTTOM);
  JTabbedPane emergSittab = new JTabbedPane();
  DataSourceRMI master = null;
  private Hashtable<String, Vector> eaList = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.coe.client.rmi.forms.FormResources");

  public EmergencySituationForm() {
    master = new DataSourceRMI("EmergencySituation");
    master.setUrl("pt.inescporto.siasoft.go.coe.ejb.session.EmergencySituation");

    if (!MenuSingleton.isSupplier()) {
      Vector binds = new Vector();
      binds.add(new TplString(MenuSingleton.getEnterprise()));
      try {
	master.setLinkCondition("fkEnterpriseID = ?", binds);
      }
      catch (DataSourceException ex1) {
      }
    }

    DataSourceRMI dsScenario = new DataSourceRMI("EmergencyScenario");
    dsScenario.setUrl("pt.inescporto.siasoft.go.coe.ejb.session.EmergencyScenario");

    DSRelation scRelation = new DSRelation("EmergencySituationEmergencyScenario");
    scRelation.setMaster(master);
    scRelation.setDetail(dsScenario);
    scRelation.addKey(new RelationKey("emergSitID", "emergSitID"));
    try {
      master.addDataSourceLink(scRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    DataSourceRMI dsPlanDocs = new DataSourceRMI("EmergencyPlanDocs");
    dsPlanDocs.setUrl("pt.inescporto.siasoft.go.coe.ejb.session.EmergencyPlanDocs");

    DSRelation pdRelation = new DSRelation("EmergencyScenarioEmergencyPlanDocs");
    pdRelation.setMaster(dsScenario);
    pdRelation.setDetail(dsPlanDocs);
    pdRelation.addKey(new RelationKey("emergSitID", "emergSitID"));
    pdRelation.addKey(new RelationKey("scenarioID", "scenarioID"));
    try {
      dsScenario.addDataSourceLink(pdRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    DataSourceRMI dsPlanCourses = new DataSourceRMI("EmergencyPlanCourses");
    dsPlanCourses.setUrl("pt.inescporto.siasoft.go.coe.ejb.session.EmergencyPlanCourses");

    DSRelation crRelation = new DSRelation("EmergencyScenarioEmergencyPlanCourses");
    crRelation.setMaster(dsScenario);
    crRelation.setDetail(dsPlanCourses);
    crRelation.addKey(new RelationKey("emergSitID", "emergSitID"));
    crRelation.addKey(new RelationKey("scenarioID", "scenarioID"));

    try {
      dsScenario.addDataSourceLink(crRelation);
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

    allHeaders = new String[] {res.getString("emergSitdef.label.code"), res.getString("emergSitdef.label.description"), res.getString("emergSitlink.label.enterprise"),res.getString("emergSitdef.buttonlabel.resp"),res.getString("emergSitlink.label.aa"),res.getString("emergSitlink.label.activity")};
    start();

  }

  private void jbInit() throws Exception {
    jTabbedPane = new JTabbedPane();
    add(jTabbedPane, BorderLayout.CENTER);
    jTabbedPane.add(res.getString("emergSituation.label.def"), new EmergencySitDefinition(dataSource, this,jtpScenarios));
    jTabbedPane.add(res.getString("emergSituation.label.links"), new EmergencySitLink(dataSource, this,emergSittab));
  }

  protected void doInsert() {
    try {
      if (!MenuSingleton.isSupplier()) {
	EmergencySituationDao daoES = (EmergencySituationDao)dataSource.getCurrentRecord();
	daoES.fkEnterpriseID.setLinkKey();
	daoES.fkEnterpriseID.setValueAsObject(MenuSingleton.getEnterprise());
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
	EmergencySituationDao attrs = (EmergencySituationDao)dataSource.getAttrs();
	attrs.emergSitID.setValue((String)keyValue);
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
    emergencyControl();
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
    emergencyControl();
    updateTable();
  }

  protected void refresh() {
    try {
      dataSource.refresh();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    emergencyControl();
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
    emergencyControl();
    updateTable();
  }

  protected void emergencyControl() {
    EmergencySituationDao dt = null;
    try {
      dt = (EmergencySituationDao)dataSource.getCurrentRecord();
    }
    catch (DataSourceException ex) {
    }
    try{
      if (dt.typeEmerg.getValue().equals("E")) {
	jtpScenarios.setEnabledAt(1, true);
      }
      else {
	jtpScenarios.setEnabledAt(1, false);
      }
    }
    catch(Exception ex){
    }
  }

  protected void updateTable() {
   EmergencySitHasLinksPane mon = new EmergencySitHasLinksPane();
   EmergencySitHasLinksTab jMonitorLinksPane = new EmergencySitHasLinksTab(mon);

   EmergencySituationDao dt = null;
   String emergSitID = null;
   String enterpriseID = null;
   try {
     dt = (EmergencySituationDao)dataSource.getCurrentRecord();
     emergSitID = dt.emergSitID.getValue();
     enterpriseID = dt.fkEnterpriseID.getValue();
   }
   catch (DataSourceException ex) {

   }
   EmergencySituationHasLinksDao monitorHasLinksDao = null;
   try {
     EmergencySituationHasLinks monitorLinks = (EmergencySituationHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.coe.ejb.session.EmergencySituationHasLinks");
     try {
       monitorHasLinksDao = monitorLinks.getLinks(emergSitID, enterpriseID);
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
     if (eaList.containsKey(emergSitID)) {
       Vector v = monitorHasLinksDao.eaList.get(emergSitID);
       jMonitorLinksPane.refresh(v);
     }
     emergSittab.removeAll();
     emergSittab.add(jMonitorLinksPane.getEmergSitHasLinksPane(), 0);
     emergSittab.setTitleAt(0, res.getString("label.activelinks"));
   }
   catch(Exception ex){
   }
 }

}
