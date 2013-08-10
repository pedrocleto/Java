package pt.inescporto.siasoft.condoc.client.rmi.forms;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.util.Vector;
import javax.swing.JTabbedPane;
import java.util.ResourceBundle;

import pt.inescporto.siasoft.condoc.ejb.dao.DocumentDao;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import javax.naming.NamingException;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import java.rmi.RemoteException;
import java.util.Hashtable;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentHasLinksDao;
import pt.inescporto.siasoft.condoc.ejb.session.DocumentHasLinks;
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
public class DocumentForm extends FW_InternalFrame {
  JTabbedPane jTabbedPane = new JTabbedPane();
  JTabbedPane doctab = new JTabbedPane();
  DataSourceRMI master = null;
  private Hashtable<String, Vector> coeList = null;
  private Hashtable<String, Vector> auditList = null;


  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.condoc.client.rmi.forms.FormResources");

  public DocumentForm() {
    master = new DataSourceRMI("Document");
    master.setUrl("pt.inescporto.siasoft.condoc.ejb.session.Document");

    if (!MenuSingleton.isSupplier()) {
      Vector binds = new Vector();
      binds.add(new TplString(MenuSingleton.getEnterprise()));
      try {
	master.setLinkCondition("fkEnterpriseID = ?", binds);
      }
      catch (DataSourceException ex1) {
      }
    }

    DataSourceRMI dsRevisors = new DataSourceRMI("DocumentUserRevisors");
    dsRevisors.setUrl("pt.inescporto.siasoft.condoc.ejb.session.DocumentUserRevisors");

    DSRelation rvRelation = new DSRelation("DocumentDocumentUserRevisors");
    rvRelation.setMaster(master);
    rvRelation.setDetail(dsRevisors);
    rvRelation.addKey(new RelationKey("documentID", "documentID"));
    try {
      master.addDataSourceLink(rvRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    DataSourceRMI dsApprovals = new DataSourceRMI("DocumentUserApproval");
    dsApprovals.setUrl("pt.inescporto.siasoft.condoc.ejb.session.DocumentUserApproval");

    DSRelation apRelation = new DSRelation("DocumentDocumentUserApproval");
    apRelation.setMaster(master);
    apRelation.setDetail(dsApprovals);
    apRelation.addKey(new RelationKey("documentID", "documentID"));

    try {
      master.addDataSourceLink(apRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    DataSourceRMI dsHistory = new DataSourceRMI("DocumentHistory");
    dsHistory.setUrl("pt.inescporto.siasoft.condoc.ejb.session.DocumentHistory");

    DSRelation htRelation = new DSRelation("DocumentDocumentHistory");
    htRelation.setMaster(master);
    htRelation.setDetail(dsHistory);
    htRelation.addKey(new RelationKey("documentID", "documentID"));

    try {
      master.addDataSourceLink(htRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    setDataSource(master);

    allHeaders = new String[] {res.getString("documentdef.label.code"), res.getString("documentdef.label.description"), res.getString("documentdef.buttonlabel.type"), res.getString("documentdef.label.resp"), res.getString("documentdef.state.cstate"), res.getString("documentdef.label.approvaldate"), res.getString("docuserrevgroup.label.user"), res.getString("docuserappgroup.label.user"),res.getString("docuserapprev.label.user"), res.getString("document.label.enterprise"), res.getString("documentdef.label.url")};

    setPublisherEvent(SyncronizerSubjects.documentFORM);

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    start();
  }

  private void jbInit() throws Exception {
    jTabbedPane = new JTabbedPane();
    add(jTabbedPane, BorderLayout.CENTER);
    jTabbedPane.add(res.getString("document.label.definition"), new DocumentDefinition(dataSource, this));
    jTabbedPane.add(res.getString("document.label.links"), new DocumentLink(dataSource, this, doctab));
  }

  protected void doInsert() {
    try {
      if (!MenuSingleton.isSupplier()) {
	DocumentDao daoEC = (DocumentDao)dataSource.getCurrentRecord();
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
     setCursor(new Cursor(Cursor.WAIT_CURSOR));
     if (keyValue != null && keyValue instanceof pt.inescporto.siasoft.condoc.ejb.dao.DocumentDao) {
       DocumentDao attrs = (DocumentDao)dataSource.getAttrs();
       attrs.documentID.setValue((String)keyValue);
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
   DocumentHasLinksPane mon = new DocumentHasLinksPane();
    DocumentHasLinksTab jDocLinksPane = new DocumentHasLinksTab(mon);

    DocumentDao dt = null;
    String documentID = null;
    String enterpriseID = null;
    try {
      dt = (DocumentDao)dataSource.getCurrentRecord();
      documentID = dt.documentID.getValue();
      enterpriseID = dt.fkEnterpriseID.getValue();
    }
    catch (DataSourceException ex) {

    }
    DocumentHasLinksDao documentHasLinksDao = new DocumentHasLinksDao();
    try {
      DocumentHasLinks documentLinks = (DocumentHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.condoc.ejb.session.DocumentHasLinks");
      try {
        documentHasLinksDao = documentLinks.getLinks(documentID, enterpriseID);
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
    coeList = (Hashtable<String, Vector>)documentHasLinksDao.coeList;
    auditList = (Hashtable<String, Vector>)documentHasLinksDao.auditList;
    Vector<Vector> result = new Vector<Vector>(2);
    try {
      if (coeList.containsKey(documentID) && auditList.containsKey(documentID)) {
        result.add(0, documentHasLinksDao.coeList.get(documentID));
        result.add(1, documentHasLinksDao.auditList.get(documentID));
        jDocLinksPane.refresh(result);

      }
      else {
        if (coeList.containsKey(documentID)) {
          Vector v = documentHasLinksDao.coeList.get(documentID);
          jDocLinksPane.refreshOne(v);
        }

        if (auditList.containsKey(documentID)) {
          Vector v = documentHasLinksDao.auditList.get(documentID);
          jDocLinksPane.refreshOne(v);
        }
      }
    }
    catch (Exception ex) {}

     doctab.removeAll();
     doctab.add(jDocLinksPane.getDocumentHasLinksPane(), 0);
     doctab.setTitleAt(0, res.getString("label.activelinks"));

  }
}
