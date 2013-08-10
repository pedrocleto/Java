package pt.inescporto.siasoft.go.monitor.ejb.session;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.Collection;

import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;

import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorPlanParameterDao;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorParameterDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorDao;
import pt.inescporto.template.dao.DAO;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.dao.ConstraintViolatedException;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.dao.LinkFieldNode;
import pt.inescporto.template.dao.LinkTableNode;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorStatedDao;
import pt.inescporto.template.elements.TplTimestamp;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorPlanDao;
import java.util.GregorianCalendar;

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
public class MonitorEJB implements SessionBean {
  protected transient MonitorPlan monitorPlan = null;
  protected transient MonitorPlanParameter monitorPlanParameter = null;
  protected transient MonitorParameters monitorParameters = null;
  protected SessionContext ctx;
  protected MonitorDao attrs = new MonitorDao();
  protected DAO dao = null;

  public MonitorEJB() {
    System.out.println("New Monitor Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
    // get MonitorPlan
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/MonitorPlan");
      MonitorPlanHome monitorPlanHome = (MonitorPlanHome)PortableRemoteObject.narrow(objref, MonitorPlanHome.class);
      monitorPlan = monitorPlanHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate MonitorPlanParameter Home");
      re.printStackTrace();
    }

    // get MonitorPlanParameter
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/MonitorPlanParameter");
      MonitorPlanParameterHome monitorPlanParameterHome = (MonitorPlanParameterHome)PortableRemoteObject.narrow(objref, MonitorPlanParameterHome.class);
      monitorPlanParameter = monitorPlanParameterHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate MonitorPlanParameter Home");
      re.printStackTrace();
    }

    // get MonitorParameters
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/MonitorParameters");
      MonitorParametersHome monitorParametersHome = (MonitorParametersHome)PortableRemoteObject.narrow(objref, MonitorParametersHome.class);
      monitorParameters = monitorParametersHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate MonitorParameters Home");
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Monitor");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Monitor", linkCondition, binds);
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Monitor");
    }
    else {
      if (binds == null)
        binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Monitor", linkCondition, binds);
    }
  }

  public void setData(MonitorDao attrs) {
    this.attrs = attrs;
  }

  public MonitorDao getData() {
    return this.attrs;
  }

  public void insert(MonitorDao attrs) throws DupKeyException, UserException {
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

  public void update(MonitorDao attrs) throws UserException {
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

  public void delete(MonitorDao attrs) throws ConstraintViolatedException, UserException {
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

  public void findByPrimaryKey(MonitorDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (MonitorDao)dao.load(attrs);
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

  public void findNext(MonitorDao attrs) throws FinderException, RowNotFoundException {
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

  public void findPrev(MonitorDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection find(MonitorDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection findExact(MonitorDao attrs) throws FinderException, RowNotFoundException {
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
      attrs.monitorPlanID.setValue((String)pkKeys[0]);
      attrs.monitorDate.setValue((Timestamp)pkKeys[0]);
      findByPrimaryKey(attrs);

      String[] MenuDesc = {attrs.monitorResult.getValue()};
      return MenuDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }

  public void copyParametersFromMonitorPlan(String monitorPlanID, Timestamp monitorDate) throws UserException {
    Vector binds = new Vector();
    binds.add(new TplString(monitorPlanID));
    try {
      monitorPlanParameter.setLinkCondition("monitorPlanID = ?", binds);
    }
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Remote error setting link condition statement in MonitorPlanParameter Component!");
    }

    Collection<MonitorPlanParameterDao> mppList = null;
    try {
      mppList = monitorPlanParameter.listAll();
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
      throw new UserException("Remote error getting list in MonitorPlanParameters Component!");
    }

    // make copy
    for (MonitorPlanParameterDao mppDao : mppList) {
      MonitorParameterDao mpDao = new MonitorParameterDao();
      mpDao.monitorPlanID.setValue(monitorPlanID);
      mpDao.monitorDate.setValue(monitorDate);
      mpDao.parameterID.setValue(mppDao.parameterID.getValue());
      mpDao.parameterDescription.setValue(mppDao.parameterDescription.getValue());
      mpDao.startPlanDate.setValue(monitorDate);
      mpDao.endPlanDate.setValue(monitorDate);

      try {
        monitorParameters.insert(mpDao);
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
        throw new UserException("Remote error inserting on MonitorParameters Component!");
      }
    }
  }

  public Collection<MonitorStatedDao> getFilteredMonitor(String enterpriseID, Timestamp startDate, Timestamp endDate, String state, String userID, String monPlanID, String envAspID, String activityID) throws UserException {
    Vector<MonitorStatedDao> monStatedList = new Vector<MonitorStatedDao>();
    Timestamp tsToday = new Timestamp(System.currentTimeMillis());

    String linkCondition = null;
    Vector binds = new Vector();

    if (state == null || (state != null && (state.equals("W") || state.equals("R") || state.equals("E")))) {
      // bind enterprise
      linkCondition = "MonitorPlan.fkEnterpriseID = ? ";
      binds.add(new TplString(enterpriseID));

      // filter by environmental aspect
      if (envAspID != null) {
	linkCondition += "AND MonitorPlan.fkEnvAspectID = ? ";
	binds.add(new TplString(envAspID));
      }

      // filter by activity
      if (activityID != null) {
	linkCondition += "AND MonitorPlan.fkActivityID = ? ";
	binds.add(new TplString(activityID));
      }

      // filter by plan
      if (monPlanID != null) {
	linkCondition += "AND MonitorPlan.monitorPlanID = ? ";
	binds.add(new TplString(monPlanID));
      }

      // filter by Start Date
      if (startDate == null)
	startDate = tsToday;
      if (startDate != null) {
	linkCondition += "AND Monitor.monitorDate >= ? ";
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
	linkCondition += "AND Monitor.monitorDate <= ?";
	binds.add(new TplTimestamp(endDate));
      }

      // instantiate a new dinamic link condition based os MonitorPlan table
      LinkTableNode linkTable = new LinkTableNode();
      linkTable.setLinkTableName("MonitorPlan");
      LinkFieldNode linkField = new LinkFieldNode("monitorPlanID", "monitorPlanID");
      Vector<LinkFieldNode> fieldList = new Vector<LinkFieldNode>();
      fieldList.add(linkField);
      linkTable.setLinkFieldList(fieldList);

      // create dao and link it!
      DAO searchDao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Monitor", linkCondition, binds);
      searchDao.addLinkTable(linkTable);

      // get list of monitoring rows that we handle
      Collection<MonitorDao> monList = null;
      try {
	monList = searchDao.listAll();
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
      for (MonitorDao monDao : monList) {
	MonitorStatedDao monStatedDao = new MonitorStatedDao();
	monStatedDao.setMonitor(monDao);

	// determine state of monitoring
	if (monDao.monitorRealStartDate.getValue() == null) {
	  monStatedDao.status.setValue("W"); //wait
	}
	else {
	  if (monDao.monitorRealEndDate.getValue() == null) {
	    monStatedDao.status.setValue("R"); //running
	  }
	  else
	    monStatedDao.status.setValue("E"); //ended
	}

	// add
        if (state == null || (state != null && monStatedDao.status.getValue().equals(state)))
          monStatedList.add(monStatedDao);
      }
    }

    if (state == null || (state != null && (state.equals("D") || state.equals("S")))) {
      // construct list of monitoring rows that are not done (schedule and delayed)
      binds = new Vector();
      // bind enterprise
      linkCondition = "MonitorPlan.fkEnterpriseID = ? ";
      binds.add(new TplString(enterpriseID));

      // filter by environmental aspect
      if (envAspID != null) {
	linkCondition += "AND MonitorPlan.fkEnvAspectID = ? ";
	binds.add(new TplString(envAspID));
      }

      // filter by activity
      if (activityID != null) {
	linkCondition += "AND MonitorPlan.fkActivityID = ? ";
	binds.add(new TplString(activityID));
      }

      // filter by plan
      if (monPlanID != null) {
        linkCondition += "AND MonitorPlan.monitorPlanID = ? ";
        binds.add(new TplString(monPlanID));
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
	linkCondition += "AND MonitorPlan.nextMonitorDate <= ? ";
	binds.add(new TplTimestamp(endDate));
      }

      Collection<MonitorPlanDao> monPlanList = null;
      try {
	monitorPlan.setLinkCondition(linkCondition, binds);
	monPlanList = (Collection<MonitorPlanDao>)monitorPlan.listAll();
      }
      catch (UserException ex1) {
      }
      catch (RowNotFoundException ex1) {
      }
      catch (RemoteException ex1) {
      }

      for (MonitorPlanDao mpDao : monPlanList) {
	GregorianCalendar gc = new GregorianCalendar();
	gc.setTime(mpDao.nextMonitorDate.getValue().before(startDate) ? startDate : mpDao.nextMonitorDate.getValue());
	while ((new Timestamp(gc.getTime().getTime())).before(endDate)) {
	  MonitorStatedDao monStatedDao = new MonitorStatedDao();
	  monStatedDao.monitorPlanID.setValue(mpDao.monitorPlanID.getValue());
	  monStatedDao.monitorDate.setValue(new Timestamp(gc.getTime().getTime()));
	  if ((new Timestamp(gc.getTime().getTime())).before(tsToday))
	    monStatedDao.status.setValue("D"); // delayed
	  else
	    monStatedDao.status.setValue("S"); // scheduled

	  // add
          if (state == null || (state != null && monStatedDao.status.getValue().equals(state)))
            monStatedList.add(monStatedDao);

	  // compute next date
	  String period = mpDao.periodicity.getValue();
	  if (period.equals("d")) //daily
	    gc.add(GregorianCalendar.DAY_OF_YEAR, 1);
	  if (period.equals("s")) //weekly
	    gc.add(GregorianCalendar.WEEK_OF_YEAR, 1);
	  if (period.equals("q")) //15 days
	    gc.add(GregorianCalendar.WEEK_OF_YEAR, 2);
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

    return monStatedList;
  }
}
