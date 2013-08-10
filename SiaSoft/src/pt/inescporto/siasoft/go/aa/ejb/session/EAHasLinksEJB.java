package pt.inescporto.siasoft.go.aa.ejb.session;

import java.rmi.RemoteException;
import java.util.Vector;
import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import pt.inescporto.template.dao.DAO;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.ejb.session.GenericQuery;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvironmentAspectDao;
import pt.inescporto.siasoft.go.aa.ejb.dao.EAHasLinksNode;
import pt.inescporto.siasoft.go.aa.ejb.dao.EAHasLinksDao;

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
public class EAHasLinksEJB implements SessionBean {
  protected transient GenericQuery gQuery;
  protected transient EnvironmentAspect envAsp;
  protected SessionContext ctx;
  protected EnvironmentAspectDao attrs = new EnvironmentAspectDao();
  protected DAO dao = null;
  protected String linkCondition = null;

  public EAHasLinksEJB() {
    System.out.println("New EAHasLinksEJB Session Bean created by EJB container ...");
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
  private Vector<EAHasLinksNode> getCoEmergList(String envAspectID, String enterpriseID) throws UserException {
    Vector<EAHasLinksNode> coEmergList = new Vector<EAHasLinksNode>();

    Vector binds = new Vector();
    binds.add(new TplString(enterpriseID));
    binds.add(new TplString(envAspectID));

    try {
      Vector<Vector<TplObject>> result = gQuery.doQuery(
	  "select a.fkEnvAspID, a.emergSitID, a.emergSitDescription from EmergencySituation a, EnvironmentalAspect b " +
	  "where a.fkEnterpriseID = ? AND a.fkEnvAspID = ? AND a.fkEnvAspID = b.envAspID", binds);

      for (Vector<TplObject> row : result) {
	  EAHasLinksNode node = new EAHasLinksNode();
	  node.setEnvAspID((String)row.elementAt(0).getValueAsObject());
	  node.setModuleKey((String)row.elementAt(1).getValueAsObject());
	  node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
	  node.setModuleName("CoEmerg");
	  node.setRefKey((String)row.elementAt(1).getValueAsObject());
	  coEmergList.add(node);
      }
    }
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro remoto");
    }

    return coEmergList;
  }

  private Vector<EAHasLinksNode> getMonitorList(String envAspectID, String enterpriseID) throws UserException {
    Vector<EAHasLinksNode> monitorList = new Vector<EAHasLinksNode>();

    Vector binds = new Vector();
    binds.add(new TplString(enterpriseID));
    binds.add(new TplString(envAspectID));

    try {
      Vector<Vector<TplObject>> result = gQuery.doQuery(
	  "select fkenvaspectid, monitorPlanID, monitorplandescription from MonitorPlan " +
	  "where fkEnterpriseID = ? AND fkenvaspectid = ?", binds);

      for (Vector<TplObject> row : result) {
	  EAHasLinksNode node = new EAHasLinksNode();
	  node.setEnvAspID((String)row.elementAt(0).getValueAsObject());
	  node.setModuleKey((String)row.elementAt(1).getValueAsObject());
	  node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
	  node.setModuleName("Monitor");
	  node.setRefKey((String)row.elementAt(1).getValueAsObject());
	  monitorList.add(node);
      }
    }
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro remoto");
    }

    return monitorList;
  }

  private Vector<EAHasLinksNode> getGoalsList(String envAspectID, String enterpriseID) throws UserException {
    Vector<EAHasLinksNode> goalsList = new Vector<EAHasLinksNode>();

    Vector binds = new Vector();
    binds.add(new TplString(envAspectID));
    binds.add(new TplString(enterpriseID));

    try {
      Vector<Vector<TplObject>> result = gQuery.doQuery(
      "select b.environmentalaspectid, a.goalid, a.goaldescription  from goals a, objectivehasenvaspect b " +
      "where a.goalid = b.goalid AND eatype = 'SINGLE' AND environmentalaspectid = ? AND a.fkEnterpriseID = ? group by 1, 2, 3", binds);

      for (Vector<TplObject> row : result) {
	EAHasLinksNode node = new EAHasLinksNode();
	node.setEnvAspID((String)row.elementAt(0).getValueAsObject());
	node.setModuleKey((String)row.elementAt(1).getValueAsObject());
	node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
	node.setModuleName("PGA");
	node.setRefKey((String)row.elementAt(1).getValueAsObject());
	goalsList.add(node);
      }
    }
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro remoto");
    }

    return goalsList;
  }

  /**
   * Public methods
   */
  public EAHasLinksDao getLinks(String envAspID, String enterpriseID) throws UserException {
    EAHasLinksDao dao = new EAHasLinksDao();
    dao.coeList = getCoEmergList(envAspID, enterpriseID);
    dao.monitorList = getMonitorList(envAspID, enterpriseID);
    dao.pgaList = getGoalsList(envAspID, enterpriseID);
    return dao;
  }
}
