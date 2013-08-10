package pt.inescporto.siasoft.go.pga.ejb.session;

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
import pt.inescporto.siasoft.go.pga.ejb.dao.GoalDao;
import pt.inescporto.siasoft.go.pga.ejb.dao.GoalHasLinksDao;
import pt.inescporto.siasoft.go.pga.ejb.dao.GoalHasLinksNode;

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
public class GoalHasLinksEJB implements SessionBean {
  protected transient GenericQuery gQuery;
  protected transient Goals goals;
  protected SessionContext ctx;
  protected GoalDao attrs = new GoalDao();
  protected DAO dao = null;
  protected String linkCondition = null;

  public GoalHasLinksEJB() {
    System.out.println("New GoalHasLinksEJB Session Bean created by EJB container ...");
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

    // get Goals
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/Goals");
      GoalsHome monitorPlanHome = (GoalsHome)PortableRemoteObject.narrow(objref, GoalsHome.class);
      goals = monitorPlanHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Goals Home");
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
  private Hashtable<String, Vector> getRegulatoryList(String goalID, String enterpriseID) throws UserException {
    Hashtable<String, Vector> regulatoryList = new Hashtable<String, Vector>();

    GoalDao pattern = new GoalDao();
    pattern.goalID.setValue(goalID);
    pattern.enterpriseID.setValue(enterpriseID);

    try {
      if (goalID == null)
	goals.setLinkCondition(pattern.goalID.getField() + " IS NULL", new Vector());

      if (goalID == null)
	goals.setLinkCondition(null, null);
    }
    catch (RemoteException ex) {
    }
    Vector binds = new Vector();
    binds.add(new TplString(enterpriseID));
    binds.add(new TplString(goalID));

    try {
      Vector<Vector<TplObject>> result = gQuery.doQuery(
	  "select m.goalID, m.fkregid, e.name from Goals m, Regulatory e " +
	  "where m.fkenterpriseid=? AND m.fkregid=e.regid AND m.goalID= ?", binds);

      for (Vector<TplObject> row : result) {
	if (regulatoryList.containsKey(goalID)) {
	  Vector v = regulatoryList.get(goalID);
	  GoalHasLinksNode node = new GoalHasLinksNode();
	  node.setGoalID((String)row.elementAt(0).getValueAsObject());
	  node.setModuleKey((String)row.elementAt(1).getValueAsObject());
	  node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
	  node.setModuleName("Regulatory");
	  node.setRefKey((String)row.elementAt(1).getValueAsObject());
	  v.add(node);
	  regulatoryList.remove(goalID);
	  regulatoryList.put(goalID, v);
	}
	else {
	  Vector v = new Vector();
	  GoalHasLinksNode node = new GoalHasLinksNode();
	  node.setGoalID((String)row.elementAt(0).getValueAsObject());
	  node.setModuleKey((String)row.elementAt(1).getValueAsObject());
	  node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
	  node.setModuleName("Regulatory");
	  node.setRefKey((String)row.elementAt(1).getValueAsObject());
	  v.add(node);
	  regulatoryList.put(goalID, v);
	}
      }
    }
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro remoto");
    }

    return regulatoryList;
  }

  /**
   * Public methods
   */
  public GoalHasLinksDao getLinks(String goalID, String enterpriseID) throws UserException {
    GoalHasLinksDao dao = new GoalHasLinksDao();
    dao.regulatoryList = getRegulatoryList(goalID, enterpriseID);
    return dao;
  }
}
