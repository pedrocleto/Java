package pt.inescporto.siasoft.go.auditor.ejb.session;

import pt.inescporto.siasoft.go.auditor.ejb.dao.PreventiveActionsDao;
import javax.ejb.SessionContext;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.sql.SQLException;
import pt.inescporto.template.dao.DupKeyException;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DAO;
import java.util.Vector;
import pt.inescporto.template.dao.RowNotFoundException;
import javax.ejb.SessionBean;

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
public class PreventiveActionsEJB implements SessionBean {
  protected SessionContext ctx;
  protected PreventiveActionsDao attrs = new PreventiveActionsDao();
  protected DAO dao = null;

  public PreventiveActionsEJB() {
    System.out.println("New PreventiveActions Session Bean created by EJB container ...");
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "PreventiveActions");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "PreventiveActions", linkCondition, binds);
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "PreventiveActions");
    }
    else {
      if (binds == null)
        binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "PreventiveActions", linkCondition, binds);
    }
  }

  public void setData(PreventiveActionsDao attrs) {
    this.attrs = attrs;
  }

  public PreventiveActionsDao getData() {
    return this.attrs;
  }

  public void insert(PreventiveActionsDao attrs) throws DupKeyException, UserException {
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

  public void update(PreventiveActionsDao attrs) throws UserException {
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

  public void delete(PreventiveActionsDao attrs) throws ConstraintViolatedException, UserException {
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

  public void findByPrimaryKey(PreventiveActionsDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (PreventiveActionsDao)dao.load(attrs);
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

  public void findNext(PreventiveActionsDao attrs) throws FinderException, RowNotFoundException {
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

  public void findPrev(PreventiveActionsDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection find(PreventiveActionsDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection findExact(PreventiveActionsDao attrs) throws FinderException, RowNotFoundException {
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
      attrs.auditActionID.setValue((String)pkKeys[2]);
      attrs.preventiveActionID.setValue((String)pkKeys[3]);
      findByPrimaryKey(attrs);

      String[] MenuDesc = {attrs.preventiveActionDescription.getValue().toString()};
      return MenuDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
