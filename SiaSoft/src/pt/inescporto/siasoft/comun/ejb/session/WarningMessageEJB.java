package pt.inescporto.siasoft.comun.ejb.session;

import javax.ejb.SessionContext;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.sql.SQLException;
import pt.inescporto.template.dao.DupKeyException;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DAO;
import java.util.Vector;
import pt.inescporto.siasoft.comun.ejb.dao.WarningMessageDao;
import pt.inescporto.template.dao.RowNotFoundException;
import javax.ejb.SessionBean;
import pt.inescporto.template.ejb.util.Key;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.template.ejb.util.KeyHome;
import javax.naming.InitialContext;
import pt.inescporto.siasoft.comun.ejb.dao.UserDao;

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
public class WarningMessageEJB implements SessionBean {
  protected transient Key key;
  protected transient User user;
  protected SessionContext ctx;
  protected WarningMessageDao attrs = new WarningMessageDao();
  protected DAO dao = null;

  public WarningMessageEJB() {
    System.out.println("New WarningMessage Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
    // get Key Generation
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/KeyGen");
      KeyHome keyHome = (KeyHome)PortableRemoteObject.narrow(objref, KeyHome.class);
      key = keyHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Key Home");
      re.printStackTrace();
    }

    // get User
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/User");
      UserHome userHome = (UserHome)PortableRemoteObject.narrow(objref, UserHome.class);
      user = userHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate User Home");
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "WarningMessages");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "WarningMessages", linkCondition, binds);
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "WarningMessages");
    }
    else {
      if (binds == null)
	binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "WarningMessages", linkCondition, binds);
    }
  }

  public void setData(WarningMessageDao attrs) {
    this.attrs = attrs;
  }

  public WarningMessageDao getData() {
    return this.attrs;
  }

  public void insert(WarningMessageDao attrs) throws DupKeyException, UserException {
    try {
      // generate primary key
      try {
	attrs.messageSequence.setValue(key.GenerateKey("KM_WarningMessages", "messageSequence"));
      }
      catch (RemoteException ex) {
	ctx.setRollbackOnly();
	throw new UserException("Generating key!", ex);
      }

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

  public void insertAllUsers(WarningMessageDao attrs, String enterpriseID) throws DupKeyException, UserException {
    Collection<UserDao> userList = new Vector<UserDao>();
    try {
      if (enterpriseID != null) {
	UserDao userPattern = new UserDao();
	userPattern.enterprise.setValue(enterpriseID);
	userList = user.findExact(userPattern);
      }
      else {
	userList = user.listAll();
      }
    }
    catch (UserException ex1) {
      ctx.setRollbackOnly();
      throw new UserException("Finding user list", ex1);
    }
    catch (RowNotFoundException ex1) {
    }
    catch (FinderException ex1) {
      ctx.setRollbackOnly();
      throw new UserException("Finding user list", ex1);
    }
    catch (RemoteException ex1) {
      ctx.setRollbackOnly();
      getEJBReferences();
      throw new UserException("Finding user list", ex1);
    }

    try {
      for (UserDao user : userList) {
	attrs.fkUserID.setValue(user.userID.getValue());
	insert(attrs);
      }
    }
    catch (DupKeyException ex) {
      ctx.setRollbackOnly();
      throw ex;
    }
  }

  public void update(WarningMessageDao attrs) throws UserException {
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

  public void delete(WarningMessageDao attrs) throws ConstraintViolatedException, UserException {
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

  public void findByPrimaryKey(WarningMessageDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (WarningMessageDao)dao.load(attrs);
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

  public void findNext(WarningMessageDao attrs) throws FinderException, RowNotFoundException {
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

  public void findPrev(WarningMessageDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection find(WarningMessageDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection findExact(WarningMessageDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection findByUser(String userID) throws FinderException, RowNotFoundException {
    WarningMessageDao patternAttrs = new WarningMessageDao();
    patternAttrs.fkUserID.setValue(userID);

    try {
      return dao.findExact(patternAttrs);
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
      attrs.messageSequence.setValue((String)pkKeys[0]);
      findByPrimaryKey(attrs);

      String[] MenuDesc = {attrs.message.getValue()};
      return MenuDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
