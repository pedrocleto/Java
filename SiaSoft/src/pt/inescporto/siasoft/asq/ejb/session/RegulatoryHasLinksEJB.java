package pt.inescporto.siasoft.asq.ejb.session;

import java.util.Vector;
import java.rmi.RemoteException;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import pt.inescporto.template.ejb.session.GenericQueryHome;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.DAO;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.ejb.session.GenericQuery;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryHasLinksNode;

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
public class RegulatoryHasLinksEJB implements SessionBean {
  protected transient GenericQuery gQuery;
  protected SessionContext ctx;
  protected DAO dao = null;
  protected String linkCondition = null;

  public RegulatoryHasLinksEJB() {
    System.out.println("New RegulatoryHasLinksEJB Session Bean created by EJB container ...");
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
  private Vector<RegulatoryHasLinksNode> getActivityList(String regID, String enterpriseID) throws UserException {
    Vector<RegulatoryHasLinksNode> activityList = new Vector<RegulatoryHasLinksNode>();

    Vector binds = new Vector();
    binds.add(new TplString(regID));
    binds.add(new TplString(enterpriseID));

    try {
      Vector<Vector<TplObject>> result = gQuery.doQuery(
      "select b.fkRegID, a.activityID, a.activityDescription from activity a, ActivityRegulatory b " +
      "where a.activityID = b.fkActivityID AND b.fkRegID = ? AND a.fkEnterpriseID = ?", binds);

      for (Vector<TplObject> row : result) {
          RegulatoryHasLinksNode node = new RegulatoryHasLinksNode();
          node.setRegID((String)row.elementAt(0).getValueAsObject());
          node.setModuleKey((String)row.elementAt(1).getValueAsObject());
          node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
          node.setModuleName("Actividade");
          node.setRefKey((String)row.elementAt(1).getValueAsObject());
          activityList.add(node);
      }
    }
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro remoto");
    }

    return activityList;
  }

  private Vector<RegulatoryHasLinksNode> getEnvAspList(String regID, String enterpriseID) throws UserException {
    Vector<RegulatoryHasLinksNode> aaList = new Vector<RegulatoryHasLinksNode>();

    Vector binds = new Vector();
    binds.add(new TplString(enterpriseID));
    binds.add(new TplString(regID));

    try {
      Vector<Vector<TplObject>> result = gQuery.doQuery(
      "select b.regID, a.envAspID, a.envAspName from environmentalaspect a, envaspothersignificant b, activity c " +
      "where a.envAspID = b.envAspID AND a.activityID = c.activityID AND c.fkEnterpriseID = ? AND b.regID = ?", binds);

      for (Vector<TplObject> row : result) {
          RegulatoryHasLinksNode node = new RegulatoryHasLinksNode();
          node.setRegID((String)row.elementAt(0).getValueAsObject());
          node.setModuleKey((String)row.elementAt(1).getValueAsObject());
          node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
          node.setModuleName("AA");
          node.setRefKey((String)row.elementAt(1).getValueAsObject());
          aaList.add(node);
      }
    }
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro remoto");
    }

    return aaList;
  }

  /**
   * Public methods
   */
  public Vector<RegulatoryHasLinksNode> getLinks(String envAspID, String enterpriseID) throws UserException {
    Vector<RegulatoryHasLinksNode> all = new Vector<RegulatoryHasLinksNode>();

    // activity's
    Vector<RegulatoryHasLinksNode> activityList = getActivityList(envAspID, enterpriseID);
    all.addAll(activityList.subList(0, activityList.size()));

    // Environmental Aspect's
    Vector<RegulatoryHasLinksNode> envAspList = getEnvAspList(envAspID, enterpriseID);
    all.addAll(envAspList.subList(0, envAspList.size()));

    return all;
  }
}
