package pt.inescporto.siasoft.go.auditor.ejb.session;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.FinderException;

import pt.inescporto.template.dao.DAO;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.ConstraintViolatedException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditDao;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditPlanActionsDao;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditActionsDao;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditStatedDao;
import pt.inescporto.template.elements.TplTimestamp;

import pt.inescporto.template.dao.LinkFieldNode;
import pt.inescporto.template.dao.LinkTableNode;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditPlanDao;

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
public class AuditEJB implements SessionBean {
  protected transient AuditPlan auditPlan = null;
  protected transient AuditPlanActions auditPlanActions = null;
  protected transient AuditActions auditActions = null;
  protected SessionContext ctx;
  protected AuditDao attrs = new AuditDao();
  protected DAO dao = null;

  public AuditEJB() {
    System.out.println("New Audit Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
    // get AuditPlan
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/AuditPlan");
      AuditPlanHome auditPlanHome = (AuditPlanHome)PortableRemoteObject.narrow(objref, AuditPlanHome.class);
      auditPlan = auditPlanHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Audit Plan Home");
      re.printStackTrace();
    }

    // get AuditPlanActions
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/AuditPlanActions");
      AuditPlanActionsHome auditPlanActionsHome = (AuditPlanActionsHome)PortableRemoteObject.narrow(objref, AuditPlanActionsHome.class);
      auditPlanActions = auditPlanActionsHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Audit Plan Actions Home");
      re.printStackTrace();
    }

    // get AuditActions
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/AuditActions");
      AuditActionsHome auditActionsHome = (AuditActionsHome)PortableRemoteObject.narrow(objref, AuditActionsHome.class);
      auditActions = auditActionsHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Audit Actions Home");
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Audit");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Audit", linkCondition, binds);
  }

  public void setSessionContext(SessionContext context) throws RemoteException {
    ctx = context;
  }

  public void unsetSessionContext() {
    this.ctx = null;
  }

  /**
   * Public methods
   */

  public void setLinkCondition(String linkCondition, Vector binds) {
    if (linkCondition == null) {
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Audit");
    }
    else {
      if (binds == null)
	binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Audit", linkCondition, binds);
    }
  }

  public void setData(AuditDao attrs) {
    this.attrs = attrs;
  }

  public AuditDao getData() {
    return this.attrs;
  }

  public void insert(AuditDao attrs) throws DupKeyException, UserException {
    try {
      this.attrs = attrs;
      dao.create(this.attrs);
      dao.update(this.attrs);
    }
    catch (DupKeyException ex) {
      ctx.setRollbackOnly();
      throw ex;
    }
    catch (NamingException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no servidor de nomes.", ex);
    }
    catch (SQLException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no SQL!", ex);
    }
  }

  public void update(AuditDao attrs) throws UserException {
    try {
      dao.update(attrs);
    }
    catch (NamingException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no servidor de nomes.", ex);
    }
    catch (SQLException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no SQL!", ex);
    }
    this.attrs = attrs;
  }

  public void delete(AuditDao attrs) throws ConstraintViolatedException, UserException {
    try {
      this.attrs = attrs;
      dao.remove(attrs);
    }
    catch (ConstraintViolatedException ex) {
      ctx.setRollbackOnly();
      throw ex;
    }
    catch (NamingException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no servidor de nomes.", ex);
    }
    catch (SQLException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no SQL!", ex);
    }
  }

  public void findByPrimaryKey(AuditDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (AuditDao)dao.load(attrs);
    }
    catch (NamingException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no servidor de nomes.", ex);
    }
    catch (SQLException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no SQL !", ex);
    }
  }

  public void findNext(AuditDao attrs) throws FinderException, RowNotFoundException {
    try {
      dao.findNextKey((Object)attrs);
      this.attrs = attrs;
    }
    catch (RowNotFoundException rnfex) {
      throw rnfex;
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public void findPrev(AuditDao attrs) throws FinderException, RowNotFoundException {
    try {
      dao.findPrevKey((Object)attrs);
      this.attrs = attrs;
    }
    catch (RowNotFoundException rnfex) {
      throw rnfex;
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public Collection listAll() throws RowNotFoundException, UserException {
    try {
      return dao.listAll();
    }
    catch (RowNotFoundException ex) {
      ctx.setRollbackOnly();
      throw ex;
    }
    catch (NamingException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no servidor de nomes.", ex);
    }
    catch (SQLException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no SQL!", ex);
    }
  }

  public Collection find(AuditDao attrs) throws FinderException, RowNotFoundException {
    try {
      return dao.find((Object)attrs);
    }
    catch (RowNotFoundException rnfex) {
      throw rnfex;
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public Collection findExact(AuditDao attrs) throws FinderException, RowNotFoundException {
    try {
      return dao.findExact((Object)attrs);
    }
    catch (RowNotFoundException rnfex) {
      throw rnfex;
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public String[] lookupDesc(Object[] pkKeys) throws RowNotFoundException {
    try {
      attrs.auditPlanID.setValue((String)pkKeys[0]);
      attrs.auditDate.setValue((Timestamp)pkKeys[1]);
      findByPrimaryKey(attrs);

      String[] MenuDesc = {attrs.auditRealStartDate.getValue().toString()};
      return MenuDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }

  public void copyActionsFromAuditPlan(String auditPlanID, Timestamp auditDate) throws UserException {
    Vector binds = new Vector();
    binds.add(new TplString(auditPlanID));
    try {
      auditPlanActions.setLinkCondition("auditplanID = ?", binds);
    }
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Remote error setting link condition statement in AuditPlanActions Component!");
    }

    Collection<AuditPlanActionsDao> apaList = null;
    try {
      apaList = auditPlanActions.listAll();
    }
    catch (UserException ex1) {
      ctx.setRollbackOnly();
      throw ex1;
    }
    catch (RowNotFoundException ex1) {
      // we're done
      return;
    }
    catch (RemoteException ex1) {
      ctx.setRollbackOnly();
      throw new UserException("Remote error getting list in AuditPlanActions Component!");
    }

    // make copy
    for (AuditPlanActionsDao apaDao : apaList) {
      AuditActionsDao aaDao = new AuditActionsDao();
      aaDao.auditPlanID.setValue(auditPlanID);
      aaDao.auditDate.setValue(auditDate);
      aaDao.auditActionID.setValue(apaDao.auditPlanActionID.getValue());
      aaDao.auditActionDescription.setValue(apaDao.auditPlanActionDescription.getValue());
      Timestamp apStartDate = new Timestamp(auditDate.getTime() + apaDao.startingDay.getValue().intValue() * 24 * 60 * 60 * 1000);
      aaDao.startPlanDate.setValue(apStartDate);
      Timestamp apEndDate = new Timestamp(apStartDate.getTime() + apaDao.durationDays.getValue().intValue() * 24 * 60 * 60 * 1000);
      aaDao.endPlanDate.setValue(apEndDate);

      try {
	auditActions.insert(aaDao);
      }
      catch (UserException ex2) {
	ctx.setRollbackOnly();
	throw ex2;
      }
      catch (DupKeyException ex2) {
	ctx.setRollbackOnly();
	throw new UserException("Duplicated key!", ex2);
      }
      catch (RemoteException ex2) {
	ctx.setRollbackOnly();
	throw new UserException("Remote error inserting on AuditActions Component!");
      }
    }
  }

  public Collection<AuditStatedDao> getFilteredAudit(String enterpriseID, String enterpriseCellID, String activityID, String auditPlanID, String state, Timestamp startDate, Timestamp endDate, String auditType) throws UserException {
    Vector<AuditStatedDao> auditStatedList = new Vector<AuditStatedDao>();
    Timestamp tsToday = new Timestamp(System.currentTimeMillis());

    String linkCondition = null;
    Vector binds = new Vector();
    if (state == null || (state != null && (state.equals("W") || state.equals("R") || state.equals("E")))) {
      // bind enterprise
      linkCondition = "AuditPlan.fkEnterpriseID = ? ";
      binds.add(new TplString(enterpriseID));

      //filter by enterpriseCell
      if (enterpriseCellID != null) {
	linkCondition += "AND AuditPlan.fkEnterpriseCellID = ? ";
	binds.add(new TplString(enterpriseCellID));
      }

      //filter by activity
      if (activityID != null) {
	linkCondition += "AND AuditPlan.fkActivityID = ? ";
	binds.add(new TplString(activityID));
      }

      //filter by auditPlan
      if (auditPlanID != null) {
	linkCondition += "AND AuditPlan.auditPlanID = ? ";
	binds.add(new TplString(auditPlanID));
      }


      //filter by auditType
      if(auditType != null) {
        linkCondition += "AND AuditPlan.auditType = ? ";
        binds.add(new TplString(auditType));
      }

      // filter by Start Date
      if (startDate == null)
	startDate = tsToday;

      if (startDate != null) {
	linkCondition += "AND Audit.auditDate >= ? ";
	binds.add(new TplTimestamp(startDate));
      }

      // filter by End Date
      if (endDate == null) {
	GregorianCalendar gc = new GregorianCalendar();
	gc.setTime(tsToday);
	gc.add(GregorianCalendar.MONTH, 1);
	endDate = new Timestamp(gc.getTime().getTime());
      }
      if (endDate != null) {
	linkCondition += "AND Audit.auditEndDate <= ?";
	binds.add(new TplTimestamp(endDate));
      }

      // instantiate a new dinamic link condition based on AuditPlan table
      LinkTableNode linkTable = new LinkTableNode();
      linkTable.setLinkTableName("AuditPlan");
      LinkFieldNode linkField = new LinkFieldNode("auditPlanID", "auditPlanID");
      Vector<LinkFieldNode> fieldList = new Vector<LinkFieldNode>();
      fieldList.add(linkField);
      linkTable.setLinkFieldList(fieldList);

      // create dao and link it!
      DAO searchDao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Audit", linkCondition, binds);
      searchDao.addLinkTable(linkTable);

      // get list of auditing rows that we handle
      Collection<AuditDao> auditList = null;
      try {
	auditList = searchDao.listAll();
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
      for (AuditDao auditDao : auditList) {
	AuditStatedDao auditStatedDao = new AuditStatedDao();
	auditStatedDao.setAudit(auditDao);

	// determine state of auditing
	if (auditDao.auditRealStartDate.getValue() == null) {
	  auditStatedDao.status.setValue("W"); //wait
	}
	else {
	  if (auditDao.auditRealEndDate.getValue() == null) {
	    auditStatedDao.status.setValue("R"); //running
	  }
	  else
	    auditStatedDao.status.setValue("E"); //ended
	}

	// add
        if (state == null || (state != null && auditStatedDao.status.getValue().equals(state)))
          auditStatedList.add(auditStatedDao);
      }
    }

     if (state == null || (state != null && (state.equals("D") || state.equals("S")))) {
       // construct list of auditing rows that are not done (schedule and delayed)
       binds = new Vector();
       // bind enterprise
       linkCondition = "AuditPlan.fkEnterpriseID = ? ";
       binds.add(new TplString(enterpriseID));

       // filter by enterprise cell
       if (enterpriseCellID != null) {
	 linkCondition += "AND AuditPlan.fkEnterpriseCellID = ? ";
	 binds.add(new TplString(enterpriseCellID));
       }

       // filter by activity
       if (activityID != null) {
	 linkCondition += "AND AuditPlan.fkActivityID = ? ";
	 binds.add(new TplString(activityID));
       }

       //filter by auditPlan
       if (auditPlanID != null) {
	 linkCondition += "AND AuditPlan.auditPlanID = ? ";
	 binds.add(new TplString(auditPlanID));
       }

       //filter by auditType
       if (auditType != null) {
	 linkCondition += "AND AuditPlan.auditType =? ";
	 binds.add(new TplString(auditType));
       }

       // filter by startDate
       if (startDate == null)
	 startDate = tsToday;

       // filter by End Date
        if (endDate == null) {
          GregorianCalendar gc = new GregorianCalendar();
          gc.setTime(tsToday);
          gc.add(GregorianCalendar.MONTH, 1);
          endDate = new Timestamp(gc.getTime().getTime());
        }
        if (endDate != null) {
          linkCondition += "AND AuditPlan.nextAuditDate <= ? ";
          binds.add(new TplTimestamp(endDate));
        }

       Collection<AuditPlanDao> auditPlanList = null;
       try {
	 auditPlan.setLinkCondition(linkCondition, binds);
	 auditPlanList = (Collection<AuditPlanDao>)auditPlan.listAll();
       }
       catch (UserException ex) {
       }
       catch (RowNotFoundException ex) {
       }
       catch (RemoteException ex) {
       }

       for (AuditPlanDao apDao : auditPlanList) {

	 GregorianCalendar gc = new GregorianCalendar();
	 gc.setTime(apDao.nextAuditDate.getValue().before(startDate) ? startDate : apDao.nextAuditDate.getValue());
	 while ((new Timestamp(gc.getTime().getTime())).before(endDate)) {
	   AuditStatedDao auditStatedDao = new AuditStatedDao();
	   auditStatedDao.auditPlanID.setValue(apDao.auditPlanID.getValue());
	   auditStatedDao.auditDate.setValue(new Timestamp(gc.getTime().getTime()));
	   if ((new Timestamp(gc.getTime().getTime())).before(tsToday))
	     auditStatedDao.status.setValue("D"); // delayed
	   else
	     auditStatedDao.status.setValue("S"); // scheduled

	   // add
	    if (state == null || (state != null && auditStatedDao.status.getValue().equals(state)))
	     auditStatedList.add(auditStatedDao);

	   // compute next date
	   String period = apDao.periodicity.getValue();
	   if (period.equals("M")) //monthly
	     gc.add(GregorianCalendar.MONTH, 1);
	   if (period.equals("B")) //2 month's
	     gc.add(GregorianCalendar.MONTH, 2);
	   if (period.equals("T")) //3 month's
	     gc.add(GregorianCalendar.MONTH, 3);
	   if (period.equals("Q")) //4 month's
	     gc.add(GregorianCalendar.MONTH, 4);
	   if (period.equals("S")) //6 month's
	     gc.add(GregorianCalendar.MONTH, 6);
	   if (period.equals("A")) //1 year
	     gc.add(GregorianCalendar.YEAR, 1);

	 }
       }
     }
    return auditStatedList;
  }
}
