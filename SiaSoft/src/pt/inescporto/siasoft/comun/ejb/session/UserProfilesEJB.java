package pt.inescporto.siasoft.comun.ejb.session;

import javax.ejb.SessionContext;
import java.sql.SQLException;
import pt.inescporto.template.dao.DupKeyException;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DAO;
import pt.inescporto.siasoft.comun.ejb.dao.UserProfileDao;
import java.util.Vector;
import pt.inescporto.template.dao.RowNotFoundException;
import javax.ejb.SessionBean;
import pt.inescporto.template.dao.ConstraintViolatedException;

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
public class UserProfilesEJB implements SessionBean {
  protected SessionContext ctx;
  protected UserProfileDao attrs = new UserProfileDao();
  protected DAO dao = null;

  public UserProfilesEJB() {
    System.out.println("New Operator Session Bean created by EJB container ...");
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "UserProfiles");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "UserProfiles", linkCondition, binds);
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "UserProfiles");
    }
    else {
      if (binds == null)
        binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "UserProfiles", linkCondition, binds);
    }
  }

  public void setData(UserProfileDao attrs) {
    this.attrs = attrs;
  }

  public UserProfileDao getData() {
    return this.attrs;
  }

  public void insert(UserProfileDao attrs) throws DupKeyException, UserException {
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

  public void update(UserProfileDao attrs) throws UserException {
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

  public void delete(UserProfileDao attrs) throws ConstraintViolatedException, UserException {
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

  public void findByPrimaryKey(UserProfileDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (UserProfileDao)dao.load(attrs);
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

  public void findNext(UserProfileDao attrs) throws FinderException, RowNotFoundException {
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

  public void findPrev(UserProfileDao attrs) throws FinderException, RowNotFoundException {
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
      throw new UserException("Erro no SQL !", ex);
    }
  }

  public Collection find(UserProfileDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection findExact(UserProfileDao attrs) throws FinderException, RowNotFoundException {
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
      attrs.userID.setValue((String)pkKeys[0]);
      attrs.profileID.setValue((String)pkKeys[1]);
      findByPrimaryKey(attrs);

      String[] MenuDesc = {attrs.profileDesc.getValue()};
      return MenuDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
