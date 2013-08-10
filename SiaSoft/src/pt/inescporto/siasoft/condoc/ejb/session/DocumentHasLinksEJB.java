package pt.inescporto.siasoft.condoc.ejb.session;

import pt.inescporto.template.elements.TplString;
import javax.ejb.SessionContext;
import pt.inescporto.template.ejb.session.GenericQuery;
import pt.inescporto.template.elements.TplObject;
import java.rmi.RemoteException;
import java.util.Hashtable;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.DAO;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import java.util.Vector;
import javax.ejb.SessionBean;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentDao;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentHasLinksNode;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentHasLinksDao;
import com.sun.jmx.snmp.Timestamp;

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
public class DocumentHasLinksEJB implements SessionBean {
  protected transient GenericQuery gQuery;
  protected transient Document document;
  protected SessionContext ctx;
  protected DocumentDao attrs = new DocumentDao();
  protected DAO dao = null;
  protected String linkCondition = null;

  public DocumentHasLinksEJB() {
    System.out.println("New DocumentHasLinksEJB Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
    // get GenericQuery Remote
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/GenericQuery");
      GenericQueryHome gHome = (GenericQueryHome)PortableRemoteObject.narrow(objref, GenericQueryHome.class);
      gQuery = gHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate GenericQuery Home");
      re.printStackTrace();
    }

    // get MonitorPlan
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/Document");
      DocumentHome documentHome = (DocumentHome)PortableRemoteObject.narrow(objref, DocumentHome.class);
      document = documentHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Document Home");
      re.printStackTrace();
    }

  }

  /**
   * EJB Required methods
   */

  public void ejbActivate() throws RemoteException {
    getEJBReferences();
  }

  public void ejbPassivate() throws RemoteException {}

  public void ejbRemove() throws RemoteException {}

  public void ejbCreate() {
  }

  public void setSessionContext(SessionContext context) throws RemoteException {
    ctx = context;
  }

  public void unsetSessionContext() {
    this.ctx = null;
  }

  /**
   * Private methods
   */
  private Hashtable<String, Vector> getCoEmergList(String documentID, String enterpriseID) throws UserException {
    Hashtable<String, Vector> coEmergList = new Hashtable<String, Vector>();


    DocumentDao pattern = new DocumentDao();
    pattern.documentID.setValue(documentID);
    pattern.fkEnterpriseID.setValue(enterpriseID);

    try {
      if (documentID == null)
        document.setLinkCondition(pattern.documentID.getField() + " IS NULL", new Vector());

      if (documentID == null)
        document.setLinkCondition(null, null);
    }
    catch (RemoteException ex) {
    }
          Vector binds = new Vector();
          binds.add(new TplString(enterpriseID));
          binds.add(new TplString(documentID));

          try {
            Vector<Vector<TplObject>> result = gQuery.doQuery(
			"select c.fkdocumentID, a.emergSitID, a.emergSitDescription from EmergencySituation a, EmergencyScenario b, emergencyplandocs c, Document d " +
                        "where a.fkEnterpriseID = ? AND c.fkdocumentid = ? AND b.emergSitID = a.emergSitID AND c.emergSitID = b.emergSitID AND c.scenarioID = b.scenarioID AND c.fkdocumentid = d.documentid  ",binds);

            for (Vector<TplObject> row : result) {
              if (coEmergList.containsKey(documentID)) {
                Vector v = coEmergList.get(documentID);
                DocumentHasLinksNode node = new DocumentHasLinksNode();
                node.setDocumentID((String)row.elementAt(0).getValueAsObject());
                node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                node.setModuleName("CoEmerg");
                node.setRefKey((String)row.elementAt(1).getValueAsObject());
                v.add(node);
                coEmergList.remove(documentID);
                coEmergList.put(documentID, v);
              }
              else {
                Vector v = new Vector();
                DocumentHasLinksNode node = new DocumentHasLinksNode();
                node.setDocumentID((String)row.elementAt(0).getValueAsObject());
                node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                node.setModuleName("CoEmerg");
                node.setRefKey((String)row.elementAt(1).getValueAsObject());
                v.add(node);
                coEmergList.put(documentID, v);
              }
            }
          }
          catch (RemoteException ex) {
            ctx.setRollbackOnly();
            throw new UserException("Erro remoto");
          }

    return coEmergList;
  }
  private Hashtable<String, Vector> getAuditorList(String documentID, String enterpriseID) throws UserException {
   Hashtable<String, Vector> auditorList = new Hashtable<String, Vector>();


   DocumentDao pattern = new DocumentDao();
   pattern.documentID.setValue(documentID);
   pattern.fkEnterpriseID.setValue(enterpriseID);

   try {
     if (documentID == null)
       document.setLinkCondition(pattern.documentID.getField() + " IS NULL", new Vector());

     if (documentID == null)
       document.setLinkCondition(null, null);
   }
   catch (RemoteException ex) {
   }
         Vector binds = new Vector();
         binds.add(new TplString(enterpriseID));
         binds.add(new TplString(documentID));

         try {
           Vector<Vector<TplObject>> result = gQuery.doQuery(
                       "select a.fkdocumentID, a.auditPlanID, a.fkuserid from audit a, Document d " +
                       "where d.fkEnterpriseID = ? AND a.fkdocumentid = ? AND a.fkdocumentid = d.documentid  ",binds);

           for (Vector<TplObject> row : result) {
             if (auditorList.containsKey(documentID)) {
               Vector v = auditorList.get(documentID);
               DocumentHasLinksNode node = new DocumentHasLinksNode();
               node.setDocumentID((String)row.elementAt(0).getValueAsObject());
               node.setModuleKey((String)row.elementAt(1).getValueAsObject());
               node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
               node.setModuleName("Auditor");
               node.setRefKey((String)row.elementAt(1).getValueAsObject());
               v.add(node);

               auditorList.remove(documentID);
               auditorList.put(documentID, v);
             }
             else {
               Vector v = new Vector();
               DocumentHasLinksNode node = new DocumentHasLinksNode();
               node.setDocumentID((String)row.elementAt(0).getValueAsObject());
               node.setModuleKey((String)row.elementAt(1).getValueAsObject());
               node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
               node.setModuleName("Auditor");
               node.setRefKey((String)row.elementAt(1).getValueAsObject());
               v.add(node);
               auditorList.put(documentID, v);
             }
           }
         }
         catch (RemoteException ex) {
           ctx.setRollbackOnly();
           throw new UserException("Erro remoto");
         }

   return auditorList;
 }



  /**
   * Public methods
   */
  public DocumentHasLinksDao getLinks(String documentID, String enterpriseID) throws UserException {
    DocumentHasLinksDao dao = new DocumentHasLinksDao();
    dao.coeList = getCoEmergList(documentID, enterpriseID);
    dao.auditList = getAuditorList(documentID, enterpriseID);
    return dao;
  }
}
