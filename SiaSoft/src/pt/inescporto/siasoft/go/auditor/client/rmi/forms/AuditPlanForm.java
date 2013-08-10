package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.util.Vector;
import java.util.ResourceBundle;
import javax.swing.JTabbedPane;

import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditPlanDao;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.siasoft.go.auditor.ejb.session.AuditHasLinks;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import javax.naming.NamingException;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditHasLinksDao;
import java.rmi.RemoteException;
import java.awt.Dimension;
import java.util.Hashtable;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.design.TmplResourceSingleton;

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
public class AuditPlanForm extends FW_InternalFrame {
  JTabbedPane tabbedPane = null;
  DataSourceRMI master = null;
  JTabbedPane auditTab = new JTabbedPane();
  private Hashtable<String, Vector> departList = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");
  public AuditPlanForm() {
    master = new DataSourceRMI("AuditPlan");
    master.setUrl("pt.inescporto.siasoft.go.auditor.ejb.session.AuditPlan");

    if (!MenuSingleton.isSupplier()) {
      Vector binds = new Vector();
      binds.add(new TplString(MenuSingleton.getEnterprise()));
      try {
	master.setLinkCondition("fkEnterpriseID = ?", binds);
      }
      catch (DataSourceException ex) {
      }
    }
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
    allHeaders = new String[] {res.getString("label.code"), res.getString("auditPlan.label.scope"), res.getString("auditPlan.label.propose"),res.getString("auditPlan.label.periodicity"),res.getString("auditPlan.label.auditType"),res.getString("auditPlan.label.nextAuditDate"),res.getString("auditPlanLink.label.enterprise"),res.getString("auditPlanLink.label.enterpriseCell"),res.getString("auditPlanLink.label.activity")};
    start();
  }
  /*
  public AuditPlanForm(){

  }
  */

  private void jbInit() throws Exception {
    tabbedPane = new JTabbedPane();
    add(tabbedPane, BorderLayout.CENTER);
    tabbedPane.addTab(res.getString("label.definition"), new AuditPlanDefinitionPane(dataSource, this));
    tabbedPane.addTab(res.getString("label.links"), new AuditPlanLinkPane(dataSource, this,auditTab));

  }

  protected void doInsert() {
    try {
      if (!MenuSingleton.isSupplier()) {
	AuditPlanDao daoEC = (AuditPlanDao)dataSource.getCurrentRecord();
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
       AuditPlanDao attrs = (AuditPlanDao)dataSource.getAttrs();
       attrs.auditPlanID.setValue((String)keyValue);
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
 protected void updateTable(){
   AuditHasLinksPane mon = new AuditHasLinksPane();
   AuditHasLinksTab jAuditorLinksPane = new AuditHasLinksTab(mon);

   AuditPlanDao dt = null;
   String auditPlanID = null;
   String enterpriseID = null;
   try {
     dt = (AuditPlanDao)dataSource.getCurrentRecord();
     auditPlanID = dt.auditPlanID.getValue();
     enterpriseID = dt.fkEnterpriseID.getValue();
   }
   catch (DataSourceException ex) {

   }
   AuditHasLinksDao auditHasLinksDao = null;
   try {
     AuditHasLinks auditLinks = (AuditHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.auditor.ejb.session.AuditHasLinks");
     try {
       auditHasLinksDao = auditLinks.getLinks(auditPlanID, enterpriseID);
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
     departList = (Hashtable<String, Vector>)auditHasLinksDao.departList;
     if (departList.containsKey(auditPlanID)) {
       Vector v = auditHasLinksDao.departList.get(auditPlanID);
       jAuditorLinksPane.refresh(v);
     }
     auditTab.removeAll();
     auditTab.add(jAuditorLinksPane.getAuditHasLinksPane(), 0);
     auditTab.setTitleAt(0, res.getString("label.activelinks"));
   }
   catch(Exception ex){

   }
 }

}
