package pt.inescporto.siasoft.go.auditor.ejb.session;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;
import javax.ejb.SessionContext;
import javax.ejb.SessionBean;
import javax.naming.NamingException;

import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditActionTypeDao;
import pt.inescporto.siasoft.go.auditor.ejb.dao.CorrectiveActionsDao;
import pt.inescporto.siasoft.go.auditor.ejb.dao.PreventiveActionsDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.LinkFieldNode;
import pt.inescporto.template.dao.LinkTableNode;
import pt.inescporto.template.dao.DAO;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TplTimestamp;
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
public class AuditActionTypeEJB implements SessionBean {
  protected SessionContext ctx;
  protected AuditActionTypeDao attrs = new AuditActionTypeDao();

  public AuditActionTypeEJB() {
    System.out.println("New AuditActionType Session Bean created by EJB container ...");
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
  public Collection<AuditActionTypeDao> listAll(String enterpriseID, Timestamp startDate, Timestamp endDate, String state, String auditPlanID, Timestamp auditDate, String auditActionID, String type, String userID) throws UserException {
    Vector<AuditActionTypeDao> auditActionTypeList = new Vector<AuditActionTypeDao>();
    Timestamp tsToday = new Timestamp(System.currentTimeMillis());

    String linkCondition = null;
    Vector binds = new Vector();

    if (type == null || (type != null && type.equals("C"))) {

      // bind enterprise
      linkCondition = "AuditPlan.fkEnterpriseID = ? ";
      binds.add(new TplString(enterpriseID));

      // filter by Audit Plan
      if (auditPlanID != null) {
	linkCondition += "AND CorrectiveActions.auditPlanID = ? ";
	binds.add(new TplString(auditPlanID));
      }
      // filter by Audit Date
      if (auditDate != null) {
	linkCondition += "AND CorrectiveActions.auditDate >= ? ";
	binds.add(new TplTimestamp(auditDate));
      }

      // filter by Audit Action
      if (auditActionID != null) {
	linkCondition += "AND CorrectiveActions.auditActionID = ? ";
	binds.add(new TplString(auditActionID));
      }

      // filter by Start Date
      if (startDate != null) {
        linkCondition += "AND CorrectiveActions.planStartDate >= ? ";
        binds.add(new TplTimestamp(startDate));
      }

      // filter by End Date
      if (endDate != null) {
        linkCondition += "AND CorrectiveActions.planEndDate <= ? ";
        binds.add(new TplTimestamp(endDate));
      }

      //filter by User
      if (userID != null) {
	linkCondition += "AND CorrectiveActions.fkUserID = ? ";
	binds.add(new TplString(userID));
      }

      // instantiate a new dinamic link condition
      // link to AuditPlan
      LinkTableNode ltAuditPlan = new LinkTableNode();
      ltAuditPlan.setLinkTableName("AuditPlan");
      LinkFieldNode lfAuditPlan = new LinkFieldNode("auditPlanID", "auditPlanID");
      Vector<LinkFieldNode> flAuditPlan = new Vector<LinkFieldNode>();
      flAuditPlan.add(lfAuditPlan);
      ltAuditPlan.setLinkFieldList(flAuditPlan);

      // link to Audit
      LinkTableNode ltAudit = new LinkTableNode();
      ltAudit.setLinkTableName("Audit");
      LinkFieldNode lfAudit1 = new LinkFieldNode("auditPlanID", "auditPlanID");
      LinkFieldNode lfAudit2 = new LinkFieldNode("auditDate", "auditDate");
      Vector<LinkFieldNode> flAudit = new Vector<LinkFieldNode>();
      flAudit.add(lfAudit1);
      flAudit.add(lfAudit2);
      ltAudit.setLinkFieldList(flAudit);

      // link to Audit Actions
      LinkTableNode ltAuditActions = new LinkTableNode();
      ltAuditActions.setLinkTableName("AuditActions");
      LinkFieldNode lfAuditActions1 = new LinkFieldNode("auditPlanID", "auditPlanID");
      LinkFieldNode lfAuditActions2 = new LinkFieldNode("auditDate", "auditDate");
      LinkFieldNode lfAuditActions3 = new LinkFieldNode("auditActionID", "auditActionID");
      Vector<LinkFieldNode> flAuditAction = new Vector<LinkFieldNode>();
      flAuditAction.add(lfAuditActions1);
      flAuditAction.add(lfAuditActions2);
      flAuditAction.add(lfAuditActions3);
      ltAuditActions.setLinkFieldList(flAuditAction);

      // create dao and link it!
      DAO searchDao = new DAO(new CorrectiveActionsDao(), "", "java:comp/env/jdbc/siasoft", "CorrectiveActions", linkCondition, binds);
      searchDao.addLinkTable(ltAuditPlan);
      searchDao.addLinkTable(ltAudit);
      searchDao.addLinkTable(ltAuditActions);

      // get list of Corrective Actions rows that we handle
      Collection<CorrectiveActionsDao> correctiveActionList = null;
      try {
	correctiveActionList = searchDao.listAll();
      }
      catch (RowNotFoundException ex) {
	// nothing to do!
	return null;
      }
      catch (NamingException ex) {
	ctx.setRollbackOnly();
	throw new UserException("Erro no servidor de nomes.", ex);
      }
      catch (SQLException ex) {
	ctx.setRollbackOnly();
	throw new UserException("Erro no SQL!", ex);
      }
      // convert to stated rows!
      for (CorrectiveActionsDao correctiveActionsDao : correctiveActionList) {
	AuditActionTypeDao auditActionTypeDao = new AuditActionTypeDao();
	auditActionTypeDao.copyCorrectiveActions(correctiveActionsDao);
        auditActionTypeDao.type.setValue("C");
	// determine state of corrective actions
	if (correctiveActionsDao.startDate.getValue() == null) {
	  if (correctiveActionsDao.planStartDate.getValue().before(tsToday))
	    auditActionTypeDao.status.setValue("D"); //delayed
	  else
	    auditActionTypeDao.status.setValue("S"); //scheduled
	}
	else {
	  if (correctiveActionsDao.startDate.getValue() == null) {
	    auditActionTypeDao.status.setValue("R"); //running
	  }
	  else
	    auditActionTypeDao.status.setValue("E"); //ended
	}

	// add
	if (state == null || (state != null && auditActionTypeDao.status.getValue().equals(state)))
	  auditActionTypeList.add(auditActionTypeDao);
      }
    }
    if (type == null || (type != null && type.equals("P"))) {
      binds = new Vector();

      // bind enterprise
      linkCondition = "AuditPlan.fkEnterpriseID = ? ";
      binds.add(new TplString(enterpriseID));

      // filter by Audit Plan
      if (auditPlanID != null) {
	linkCondition += "AND PreventiveActions.auditPlanID = ? ";
	binds.add(new TplString(auditPlanID));
      }
      // filter by Audit Date
      if (auditDate != null) {
	linkCondition += "AND PreventiveActions.auditDate = ? ";
	binds.add(new TplTimestamp(auditDate));
      }


      // filter by Audit Action
      if (auditActionID != null) {
	linkCondition += "AND PreventiveActions.auditActionID = ? ";
	binds.add(new TplString(auditActionID));
      }

      // filter by Start Date
     if (startDate != null) {
       linkCondition += "AND PreventiveActions.planStartDate >= ? ";
       binds.add(new TplTimestamp(startDate));
     }

     // filter by End Date
     if (endDate != null) {
       linkCondition += "AND PreventiveActions.planEndDate <= ? ";
       binds.add(new TplTimestamp(endDate));
     }

      //filter by User
      if (userID != null) {
	linkCondition += "AND PreventiveActions.fkUserID = ? ";
	binds.add(new TplString(userID));
      }

      // instantiate a new dinamic link condition based on Audit Plan table
      // link to AuditPlan
      LinkTableNode ltAuditPlan = new LinkTableNode();
      ltAuditPlan.setLinkTableName("AuditPlan");
      LinkFieldNode lfAuditPlan = new LinkFieldNode("auditPlanID", "auditPlanID");
      Vector<LinkFieldNode> flAuditPlan = new Vector<LinkFieldNode>();
      flAuditPlan.add(lfAuditPlan);
      ltAuditPlan.setLinkFieldList(flAuditPlan);

      // link to Audit
      LinkTableNode ltAudit = new LinkTableNode();
      ltAudit.setLinkTableName("Audit");
      LinkFieldNode lfAudit1 = new LinkFieldNode("auditPlanID", "auditPlanID");
      LinkFieldNode lfAudit2 = new LinkFieldNode("auditDate", "auditDate");
      Vector<LinkFieldNode> flAudit = new Vector<LinkFieldNode>();
      flAudit.add(lfAudit1);
      flAudit.add(lfAudit2);
      ltAudit.setLinkFieldList(flAudit);

      // link to Audit Actions
      LinkTableNode ltAuditActions = new LinkTableNode();
      ltAuditActions.setLinkTableName("AuditActions");
      LinkFieldNode lfAuditActions1 = new LinkFieldNode("auditPlanID", "auditPlanID");
      LinkFieldNode lfAuditActions2 = new LinkFieldNode("auditDate", "auditDate");
      LinkFieldNode lfAuditActions3 = new LinkFieldNode("auditActionID", "auditActionID");
      Vector<LinkFieldNode> flAuditAction = new Vector<LinkFieldNode>();
      flAuditAction.add(lfAuditActions1);
      flAuditAction.add(lfAuditActions2);
      flAuditAction.add(lfAuditActions3);
      ltAuditActions.setLinkFieldList(flAuditAction);

      // create dao and link it!
      DAO searchDao = new DAO(new PreventiveActionsDao(), "", "java:comp/env/jdbc/siasoft", "PreventiveActions", linkCondition, binds);
      searchDao.addLinkTable(ltAuditPlan);
      searchDao.addLinkTable(ltAudit);
      searchDao.addLinkTable(ltAuditActions);

      // get list of Preventive Actions rows that we handle
      Collection<PreventiveActionsDao> preventiveActionList = null;
      try {
	preventiveActionList = searchDao.listAll();
      }
      catch (RowNotFoundException ex) {
	// nothing to do!
	return null;
      }
      catch (NamingException ex) {
	ctx.setRollbackOnly();
	throw new UserException("Erro no servidor de nomes.", ex);
      }
      catch (SQLException ex) {
	ctx.setRollbackOnly();
	throw new UserException("Erro no SQL!", ex);
      }

      // convert to stated rows!
      for (PreventiveActionsDao preventiveActionsDao : preventiveActionList) {
	AuditActionTypeDao aaTypeDao = new AuditActionTypeDao();
	aaTypeDao.copyPreventiveActions(preventiveActionsDao);
        aaTypeDao.type.setValue("P");
	// determine state of auditing
	if (preventiveActionsDao.startDate.getValue() == null) {
	  if (preventiveActionsDao.planStartDate.getValue().before(tsToday))
	    aaTypeDao.status.setValue("D"); //delayed

	  else
	    aaTypeDao.status.setValue("S"); //scheduled
	}
	else {
	  if (preventiveActionsDao.startDate.getValue() == null)
	    aaTypeDao.status.setValue("R"); //running

	  else
	    aaTypeDao.status.setValue("E"); //ended
	}
	// add
	if (state == null || (state != null && aaTypeDao.status.getValue().equals(state)))
	  auditActionTypeList.add(aaTypeDao);
      }
    }
    return auditActionTypeList;
  }
}
