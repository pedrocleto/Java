package pt.inescporto.siasoft.go.pga.client.rmi.forms;

import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JTabbedPane;


import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.go.pga.ejb.dao.GoalDao;

import java.awt.Cursor;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import javax.naming.NamingException;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import java.rmi.RemoteException;
import java.util.Hashtable;
import pt.inescporto.siasoft.go.pga.ejb.dao.GoalHasLinksDao;
import pt.inescporto.siasoft.go.pga.ejb.session.GoalHasLinks;
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
public class GoalsForm extends FW_InternalFrame {
  JTabbedPane jTabbedPane = new JTabbedPane();
  JTabbedPane goaltab = new JTabbedPane();
  DataSourceRMI master = null;
  private Hashtable<String, Vector> regulatoryList = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.pga.client.rmi.forms.FormResources");

  public GoalsForm() {
    master = new DataSourceRMI("Goals");
    master.setUrl("pt.inescporto.siasoft.go.pga.ejb.session.Goals");

    if (!MenuSingleton.isSupplier()) {
      Vector binds = new Vector();
      binds.add(new TplString(MenuSingleton.getEnterprise()));
      try {
	master.setLinkCondition("fkEnterpriseID = ?", binds);
      }
      catch (DataSourceException ex1) {
      }
    }

    DataSourceRMI dsObjective = new DataSourceRMI("Objective");
    dsObjective.setUrl("pt.inescporto.siasoft.go.pga.ejb.session.Objective");

    DSRelation mdRelation = new DSRelation("GoalsObjective");
    mdRelation.setMaster(master);
    mdRelation.setDetail(dsObjective);
    mdRelation.addKey(new RelationKey("goalID", "goalID"));
    try {
      master.addDataSourceLink(mdRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    // Datasource Objective Environmental Aspects
    DataSourceRMI dsObjectiveHasEA = new DataSourceRMI("ObjectiveHasEA");
    dsObjectiveHasEA.setUrl("pt.inescporto.siasoft.go.pga.ejb.session.ObjectiveHasEA");

    DSRelation dsrObjectiveHasEA = new DSRelation("ObjectiveObjectiveHasEA");
    dsrObjectiveHasEA.setMaster(dsObjective);
    dsrObjectiveHasEA.setDetail(dsObjectiveHasEA);
    dsrObjectiveHasEA.addKey(new RelationKey("goalID", "goalID"));
    dsrObjectiveHasEA.addKey(new RelationKey("objectiveID", "objectiveID"));
    try {
      dsObjective.addDataSourceLink(dsrObjectiveHasEA);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    // Datasource Objective Actions
    DataSourceRMI dsObjectiveActions = new DataSourceRMI("ObjectiveActions");
    dsObjectiveActions.setUrl("pt.inescporto.siasoft.go.pga.ejb.session.ObjectiveActions");

    DSRelation dsrObjectiveActions = new DSRelation("ObjectiveObjectiveActions");
    dsrObjectiveActions.setMaster(dsObjective);
    dsrObjectiveActions.setDetail(dsObjectiveActions);
    dsrObjectiveActions.addKey(new RelationKey("goalID", "goalID"));
    dsrObjectiveActions.addKey(new RelationKey("objectiveID", "objectiveID"));
    try {
      dsObjective.addDataSourceLink(dsrObjectiveActions);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    setDataSource(master);
    setAccessPermitionIdentifier("GOALS");

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    allHeaders = new String[] {res.getString("goal.label.code"), res.getString("goal.label.desc"), res.getString("goal.label.deadEndDate"),res.getString("goal.label.enterprise"),res.getString("goal.label.resume"),res.getString("goal.label.regulatory")};
    start();
  }

  private void jbInit() throws Exception {
    jTabbedPane = new JTabbedPane();
    add(jTabbedPane, java.awt.BorderLayout.CENTER);
    jTabbedPane.addTab(res.getString("label.definition"), new GoalsDefinitionPane(dataSource, this));
    jTabbedPane.addTab(res.getString("label.links"), new GoalsLinkPane(dataSource, this,goaltab));
  }

  protected void doInsert() {
    try {
      if (!MenuSingleton.isSupplier()) {
        GoalDao daoEC = (GoalDao)dataSource.getCurrentRecord();
        daoEC.enterpriseID.setLinkKey();
        daoEC.enterpriseID.setValueAsObject(MenuSingleton.getEnterprise());
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
	GoalDao attrs = (GoalDao)dataSource.getAttrs();
	attrs.goalID.setValue((String)keyValue);
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
    GoalHasLinksPane mon = new GoalHasLinksPane();
    GoalHasLinksTab jMonitorLinksPane = new GoalHasLinksTab(mon);

    GoalDao dt = null;
    String monitorPlanID = null;
    String enterpriseID = null;
    try {
      dt = (GoalDao)dataSource.getCurrentRecord();
      monitorPlanID = dt.goalID.getValue();
      enterpriseID = dt.enterpriseID.getValue();
    }
    catch (DataSourceException ex) {

    }
    GoalHasLinksDao goalHasLinksDao = null;
    try {
      GoalHasLinks goalLinks = (GoalHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.pga.ejb.session.GoalHasLinks");
      try {
        goalHasLinksDao = goalLinks.getLinks(monitorPlanID, enterpriseID);
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
      regulatoryList = (Hashtable<String, Vector>)goalHasLinksDao.regulatoryList;
      if (regulatoryList.containsKey(monitorPlanID)) {
	Vector v = goalHasLinksDao.regulatoryList.get(monitorPlanID);
	jMonitorLinksPane.refresh(v);
      }
      goaltab.removeAll();
      goaltab.add(jMonitorLinksPane.getGoalHasLinksPane(), 0);
      goaltab.setTitleAt(0, res.getString("label.activelinks"));
    }
    catch(Exception ex){
    }
  }

}
