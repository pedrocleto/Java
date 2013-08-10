package pt.inescporto.siasoft.go.pga.ejb.session;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.NamingException;

import pt.inescporto.siasoft.go.pga.ejb.dao.ObjActionsDao;
import pt.inescporto.siasoft.go.pga.ejb.dao.ObjectiveActionsDao;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TplTimestamp;
import pt.inescporto.template.dao.LinkFieldNode;
import pt.inescporto.template.dao.LinkTableNode;
import pt.inescporto.template.dao.DAO;
import pt.inescporto.template.dao.RowNotFoundException;

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
public class ObjActionsEJB implements SessionBean {
  protected SessionContext ctx;
  protected ObjActionsDao attrs = new ObjActionsDao();

  public ObjActionsEJB() {
    System.out.println("New ObjActions Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
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
   * PUBLIC METHODS
   */
  public Collection<ObjActionsDao> listAll(String enterpriseID, String goalID, String objectiveID, Timestamp planStartDate, Timestamp planEndDate, String userID, String state) {
    Vector<ObjActionsDao> oaList = new Vector<ObjActionsDao>();
    Timestamp tsToday = new Timestamp(System.currentTimeMillis());

    String linkCondition = null;
    Vector binds = new Vector();

      // bind enterprise
      linkCondition = "Goals.fkEnterpriseID = ? ";
      binds.add(new TplString(enterpriseID));

      // filter by goal
      if (goalID != null) {
	linkCondition += "AND ObjectiveActions.goalID = ?";
	binds.add(new TplString(goalID));
      }

      // filter by objective
      if (objectiveID != null) {
	linkCondition += "AND ObjectiveActions.objectiveID = ?";
	binds.add(new TplString(objectiveID));
      }

      // filter by start Date
      if (planStartDate != null) {
	linkCondition += "AND ObjectiveActions.planStartDate >= ?";
	binds.add(new TplTimestamp(planStartDate));
      }

      // filter by end Date
      if (planEndDate != null) {
	linkCondition += "AND ObjectiveActions.planEndDate <= ?";
	binds.add(new TplTimestamp(planEndDate));
      }

      //filter by user
      if (userID != null) {
	linkCondition += "AND ObjectiveActions.fkUserID <= ?";
	binds.add(new TplString(userID));
      }

      // instantiate a new dinamic link condition
      // link to Goals
      LinkTableNode ltGoals = new LinkTableNode();
      ltGoals.setLinkTableName("Goals");
      LinkFieldNode lfGoals = new LinkFieldNode("goalID", "goalID");
      Vector<LinkFieldNode> flGoals = new Vector<LinkFieldNode>();
      flGoals.add(lfGoals);
      ltGoals.setLinkFieldList(flGoals);

      // link to Objective
      LinkTableNode ltObjective = new LinkTableNode();
      ltObjective.setLinkTableName("Objective");
      LinkFieldNode lfObjective1 = new LinkFieldNode("goalID", "goalID");
      LinkFieldNode lfObjective2 = new LinkFieldNode("objectiveID", "objectiveID");
      Vector<LinkFieldNode> flObjective = new Vector<LinkFieldNode>();
      flObjective.add(lfObjective1);
      flObjective.add(lfObjective2);
      ltObjective.setLinkFieldList(flObjective);

      // create dao and link it!
      DAO searchDao = new DAO(new ObjectiveActionsDao(), "", "java:comp/env/jdbc/siasoft", "ObjectiveActions", linkCondition, binds);
      searchDao.addLinkTable(ltGoals);
      searchDao.addLinkTable(ltObjective);

      // get list of Objective Actions rows that we handle
      Collection<ObjectiveActionsDao> objActionsList = null;
      try {
	objActionsList = searchDao.listAll();
      }
      catch (RowNotFoundException ex) {
	// nothing to do!
	return null;
      }
      catch (NamingException ex) {
	ctx.setRollbackOnly();
	//  throw new UserException("Erro no servidor de nomes.", ex);
      }
      catch (SQLException ex) {
	ctx.setRollbackOnly();
	//throw new UserException("Erro no SQL!", ex);
      }
      // convert to stated rows!
      for (ObjectiveActionsDao objectiveActionsDao : objActionsList) {
	ObjActionsDao oaDao = new ObjActionsDao();
	oaDao.copyObjectiveActions(objectiveActionsDao);

	// determine state of corrective actions
	if (objectiveActionsDao.startDate.getValue() == null) {
	  if (objectiveActionsDao.planStartDate.getValue().before(tsToday))
	    oaDao.status.setValue("D"); //delayed
	  else
	    oaDao.status.setValue("S"); //scheduled
	}
	else {
	  if (objectiveActionsDao.startDate.getValue() == null) {
	    oaDao.status.setValue("R"); //running
	  }
	  else
	    oaDao.status.setValue("E"); //ended
	}

	// add
	if (state == null || (state != null && oaDao.status.getValue().equals(state)))
	  oaList.add(oaDao);
      }
    return oaList;
  }

}
