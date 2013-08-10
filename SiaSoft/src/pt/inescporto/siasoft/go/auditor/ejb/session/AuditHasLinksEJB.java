package pt.inescporto.siasoft.go.auditor.ejb.session;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationDao;
import javax.ejb.SessionContext;
import pt.inescporto.template.ejb.session.GenericQuery;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationHasLinksDao;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationHasLinksNode;
import pt.inescporto.siasoft.go.coe.ejb.session.EmergencySituation;
import java.rmi.RemoteException;
import java.util.Hashtable;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import pt.inescporto.template.dao.UserException;
import javax.naming.InitialContext;
import pt.inescporto.template.dao.DAO;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import java.util.Vector;
import pt.inescporto.siasoft.go.coe.ejb.session.EmergencySituationHome;
import javax.ejb.SessionBean;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditPlanDao;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditHasLinksDao;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditHasLinksNode;

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
public class AuditHasLinksEJB  implements SessionBean {
  protected transient GenericQuery gQuery;
  protected transient AuditPlan auditPlan;
  protected SessionContext ctx;
  protected AuditPlanDao attrs = new AuditPlanDao();
  protected DAO dao = null;
  protected String linkCondition = null;

  public AuditHasLinksEJB() {
    System.out.println("New AuditHasLinksEJB Session Bean created by EJB container ...");
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

    // get EmergencySituation
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/AuditPlan");
      AuditPlanHome auditPlanHome = (AuditPlanHome)PortableRemoteObject.narrow(objref, AuditPlanHome.class);
      auditPlan = auditPlanHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate AuditPlan Home");
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
  private Hashtable<String, Vector> getDepartmentList(String auditPlanID, String enterpriseID) throws UserException {
    Hashtable<String, Vector> departList = new Hashtable<String, Vector>();


    AuditPlanDao pattern = new AuditPlanDao();
    pattern.auditPlanID.setValue(auditPlanID);
    pattern.fkEnterpriseID.setValue(enterpriseID);

    try {
      if (auditPlanID == null)
        auditPlan.setLinkCondition(pattern.auditPlanID.getField() + " IS NULL", new Vector());

      if (auditPlanID == null)
        auditPlan.setLinkCondition(null, null);
    }
    catch (RemoteException ex) {
    }
          Vector binds = new Vector();
          binds.add(new TplString(enterpriseID));
          binds.add(new TplString(auditPlanID));

          try {
            Vector<Vector<TplObject>> result = gQuery.doQuery(
              "select m.auditplanid, m.fkEnterpriseCellID, e.enterprisecelldescription from auditplan m, EnterpriseCell e " +
                      "where m.fkenterpriseID=? AND m.fkenterpriseID=e.enterpriseid AND m.fkEnterpriseCellID=e.enterprisecellid AND m.auditplanid= ?", binds);

            for (Vector<TplObject> row : result) {
              if (departList.containsKey(auditPlanID)) {
                Vector v = departList.get(auditPlanID);
                AuditHasLinksNode node = new AuditHasLinksNode();
                node.setAuditPlanID((String)row.elementAt(0).getValueAsObject());
                node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                node.setModuleName("EnterpriseCellID");
                node.setRefKey((String)row.elementAt(1).getValueAsObject());
                v.add(node);
                departList.remove(auditPlanID);
                departList.put(auditPlanID, v);
              }
              else {
                Vector v = new Vector();
                AuditHasLinksNode node = new AuditHasLinksNode();
                node.setAuditPlanID((String)row.elementAt(0).getValueAsObject());
                node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                node.setModuleName("EnterpriseCellID");
                node.setRefKey((String)row.elementAt(1).getValueAsObject());
                v.add(node);
                departList.put(auditPlanID, v);
              }
            }
          }
          catch (RemoteException ex) {
            ctx.setRollbackOnly();
            throw new UserException("Erro remoto");
          }

    return departList;
  }


  /**
   * Public methods
   */
  public AuditHasLinksDao getLinks(String auditPlanID, String enterpriseID) throws UserException {
    AuditHasLinksDao dao = new AuditHasLinksDao();
    dao.departList = getDepartmentList(auditPlanID, enterpriseID);
    return dao;
  }
}

