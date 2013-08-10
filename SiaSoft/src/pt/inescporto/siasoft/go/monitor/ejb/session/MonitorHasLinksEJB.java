package pt.inescporto.siasoft.go.monitor.ejb.session;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import pt.inescporto.template.dao.DAO;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.ejb.session.GenericQuery;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorHasLinksDao;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorHasLinksNode;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorPlanDao;

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
public class MonitorHasLinksEJB implements SessionBean {
  protected transient GenericQuery gQuery;
  protected transient MonitorPlan monitorPlan;
  protected SessionContext ctx;
  protected MonitorPlanDao attrs = new MonitorPlanDao();
  protected DAO dao = null;
  protected String linkCondition = null;

  public MonitorHasLinksEJB() {
    System.out.println("New MonitorHasLinksEJB Session Bean created by EJB container ...");
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
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/MonitorPlan");
      MonitorPlanHome monitorPlanHome = (MonitorPlanHome)PortableRemoteObject.narrow(objref, MonitorPlanHome.class);
      monitorPlan = monitorPlanHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate MonitorPlan Home");
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
  private Hashtable<String, Vector> getEnvironmentalAspectList(String monitorPlanID, String enterpriseID) throws UserException {
    Hashtable<String, Vector> envAspList = new Hashtable<String, Vector>();

    MonitorPlanDao pattern = new MonitorPlanDao();
    pattern.monitorPlanID.setValue(monitorPlanID);
    pattern.fkEnterpriseID.setValue(enterpriseID);

    try {
      if (monitorPlanID == null)
	monitorPlan.setLinkCondition(pattern.monitorPlanID.getField() + " IS NULL", new Vector());

      if (monitorPlanID == null)
	monitorPlan.setLinkCondition(null, null);
    }
    catch (RemoteException ex) {
    }
    Vector binds = new Vector();
    binds.add(new TplString(enterpriseID));
    binds.add(new TplString(monitorPlanID));

    try {
      Vector<Vector<TplObject>> result = gQuery.doQuery(
	  "select m.monitorPlanID, m.fkenvaspectid, e.envAspName from MonitorPlan m, EnvironmentalAspect e " +
	  "where m.fkenterpriseID=? AND m.fkenvaspectid=e.envaspid AND m.monitorPlanID= ?", binds);

      for (Vector<TplObject> row : result) {
	if (envAspList.containsKey(monitorPlanID)) {
	  Vector v = envAspList.get(monitorPlanID);
	  MonitorHasLinksNode node = new MonitorHasLinksNode();
	  node.setMonitorPlanID((String)row.elementAt(0).getValueAsObject());
	  node.setModuleKey((String)row.elementAt(1).getValueAsObject());
	  node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
	  node.setModuleName("AA");
	  node.setRefKey((String)row.elementAt(1).getValueAsObject());
	  v.add(node);
	  envAspList.remove(monitorPlanID);
	  envAspList.put(monitorPlanID, v);
	}
	else {
	  Vector v = new Vector();
	  MonitorHasLinksNode node = new MonitorHasLinksNode();
	  node.setMonitorPlanID((String)row.elementAt(0).getValueAsObject());
	  node.setModuleKey((String)row.elementAt(1).getValueAsObject());
	  node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
	  node.setModuleName("AA");
	  node.setRefKey((String)row.elementAt(1).getValueAsObject());
	  v.add(node);
	  envAspList.put(monitorPlanID, v);
	}
      }
    }
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro remoto");
    }

    return envAspList;
  }

  /**
   * Public methods
   */
  public MonitorHasLinksDao getLinks(String monitorPlanID, String enterpriseID) throws UserException {
    MonitorHasLinksDao dao = new MonitorHasLinksDao();
    dao.eaList = getEnvironmentalAspectList(monitorPlanID, enterpriseID);
    return dao;
  }
}
