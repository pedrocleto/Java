package pt.inescporto.siasoft.asq.ejb.session;

import java.sql.Timestamp;
import java.util.Date;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import javax.ejb.SessionContext;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.naming.InitialContext;

import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DAO;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import pt.inescporto.template.dao.ConstraintViolatedException;
import pt.inescporto.siasoft.comun.ejb.session.WarningMessage;
import pt.inescporto.siasoft.comun.ejb.session.WarningMessageHome;
import pt.inescporto.siasoft.comun.ejb.dao.WarningMessageDao;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TplTimestamp;
import pt.inescporto.template.ejb.session.GenericQuery;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.elements.TplBoolean;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class RegulatoryEJB implements SessionBean {
  protected transient WarningMessage warningMessage = null;
  protected transient GenericQuery genericQuery = null;
  protected SessionContext ctx;
  protected RegulatoryDao attrs = new RegulatoryDao();
  protected DAO dao = null;

  public RegulatoryEJB() {
    System.out.println("New Regulatory Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
    // get Generic Query
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/GenericQuery");
      GenericQueryHome genericQueryHome = (GenericQueryHome)PortableRemoteObject.narrow(objref, GenericQueryHome.class);
      genericQuery = genericQueryHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate WarningMessage Home");
      re.printStackTrace();
    }

    // get WarningMessage Generation
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/WarningMessage");
      WarningMessageHome warningMessageHome = (WarningMessageHome)PortableRemoteObject.narrow(objref, WarningMessageHome.class);
      warningMessage = warningMessageHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate WarningMessage Home");
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "regulatory");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "regulatory", linkCondition, binds);
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "regulatory");
    }
    else {
      if (binds == null)
	binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "regulatory", linkCondition, binds);
    }
  }

  public void setData(RegulatoryDao attrs) {
    this.attrs = attrs;
  }

  public RegulatoryDao getData() {
    return this.attrs;
  }

  public void insert(RegulatoryDao attrs) throws DupKeyException, UserException {
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

  public void update(RegulatoryDao attrs) throws UserException {
    // find previous value
    RegulatoryDao pattern = new RegulatoryDao();
    pattern.regId.setValue(attrs.regId.getValue());
    try {
      findByPrimaryKey(pattern);
      pattern = getData();
      if (pattern.revocate.getValue().equals(Boolean.FALSE) && attrs.revocate.getValue().equals(Boolean.TRUE)) {
	WarningMessageDao wmDao = new WarningMessageDao();
	wmDao.message.setValue("&startMessageRegRevocated& #regID# &endMessageRegRevocated&");
	wmDao.moduleID.setValue(wmDao.regModuleRevogated);
	wmDao.dateRef.setValue(new Timestamp(System.currentTimeMillis()));
	wmDao.keyRef.setValue(new Object[] {this.attrs.regId});
	warningMessage.insertAllUsers(wmDao, null);
      }
    }
    catch (UserException ex1) {
      ctx.setRollbackOnly();
      throw new UserException("Erro.", ex1);
    }
    catch (RowNotFoundException ex1) {
      ctx.setRollbackOnly();
      throw new UserException("Erro o documento não existe.", ex1);
    }
    catch (DupKeyException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Chave duplicada.", ex);
    }
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      getEJBReferences();
      throw new UserException("Erro remoto.", ex);
    }

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

  public void delete(RegulatoryDao attrs) throws ConstraintViolatedException, UserException {
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

  public void findByPrimaryKey(RegulatoryDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (RegulatoryDao)dao.load(attrs);
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

  public void findNext(RegulatoryDao attrs) throws FinderException, RowNotFoundException {
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

  public void findPrev(RegulatoryDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection listAll() throws RemoteException {
    try {
      return dao.listAll();
    }
    catch (Exception ex) {
      throw new RemoteException(ex.toString());
    }
  }

  public Collection find(RegulatoryDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection findExact(RegulatoryDao attrs) throws FinderException, RowNotFoundException {
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
      attrs.regId.setValue((String)pkKeys[0]);
      findByPrimaryKey(attrs);

      String[] DescList = {attrs.name.getValue()};
      return DescList;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }

  public Collection findByFullCriterion(String name, String resume, Date dateInit, Date dateEnd,
                                        String scopeID, String sourceID, String majorThemeID,
                                        String minorThemeID, boolean state, boolean revocate,
                                        String enterpriseID, String userID, String profileID,
                                        boolean hasLR) throws FinderException, UserException, RowNotFoundException {
    String linkCondition = "";
    Vector binds = new Vector();

    String selectStmt = "SELECT a.regID, a.name, a.resume FROM regulatory a, " +
        "RegulatoryHasClass c " +
        "where a.regID = c.regID";

    String orderByStmt = "ORDER BY a.regID ASC";

    // name
    if (name != null) {
      linkCondition += "a.name LIKE ? AND ";
      binds.add(new TplString("%" + name + "%"));
    }

    // resume
    if (resume != null) {
      linkCondition += "a.resume LIKE ? AND ";
      binds.add(new TplString("%" + resume + "%"));
    }

    // Start date
    if (dateInit != null) {
      linkCondition += "a.publishDate >= ? AND ";
      binds.add(new TplTimestamp(new Timestamp(dateInit.getTime())));
    }

    // End date
    if (dateEnd != null) {
      linkCondition += "a.publishDate <= ? AND ";
      binds.add(new TplTimestamp(new Timestamp(dateEnd.getTime())));
    }

    // boolean values on regulatory
    linkCondition += "a.state = ? AND a.revocate = ? AND ";
    binds.add(new TplBoolean(!state));
    binds.add(new TplBoolean(revocate));

    // Scope
    if (scopeID != null) {
      linkCondition += "c.scopeID = ? AND ";
      binds.add(new TplString(scopeID));
    }

    // Source
    if (sourceID != null) {
      linkCondition += "c.sourceID = ? AND ";
      binds.add(new TplString(sourceID));
    }

    // Major theme
    if (majorThemeID != null) {
      linkCondition += "c.themeID = ? AND ";
      binds.add(new TplString(majorThemeID));
    }

    // Minor theme
    if (minorThemeID != null) {
      linkCondition += "c.theme1ID = ? AND ";
      binds.add(new TplString(minorThemeID));
    }

    if (linkCondition.length() > 0)
      linkCondition = linkCondition.substring(0, linkCondition.length() - 5);

    // has Legal Requirement
    if (hasLR) {
      linkCondition += " AND a.regid in (select distinct(regid) from legalrequirement)";
    }

    // by applicability
    if (enterpriseID != null) {
      linkCondition += " AND a.regid in (select distinct(regId) from AsqClientApplicability where enterpriseID = ? ";
      binds.add(new TplString(enterpriseID));
      linkCondition += (userID != null) ? " AND userID = ? " : " AND userID is NULL ";
      linkCondition += (profileID != null) ? " AND profileID = ? " : " AND profileID is NULL ";
      if (userID != null)
        binds.add(new TplString(userID));
      if (profileID != null)
        binds.add(new TplString(profileID));
      linkCondition += ")";
    }

    Vector<Vector<TplObject>> list = null;

    // get list of auditing rows that we handle
    try {
      list = (Vector<Vector<TplObject>>)genericQuery.doQuery(selectStmt + " AND " + linkCondition + " " + orderByStmt, binds);
      return list;
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
      ctx.setRollbackOnly();
      getEJBReferences();
      throw new UserException("Erro no SQL !", ex);
    }
  }
}
