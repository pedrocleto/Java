package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;

import pt.inescporto.permissions.ejb.dao.GlbFieldPermDao;
import pt.inescporto.template.dao.*;

public class GlbFieldPermEJB implements SessionBean {
  protected SessionContext ctx;
  protected GlbFieldPermDao attrs = new GlbFieldPermDao();
  protected DAO dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_field_perm");

  public GlbFieldPermEJB() {
    System.out.println("New GlbFieldPerm Session Bean created by EJB container ...");
  }

  /**
   * EJB Required methods
   */

  public void ejbActivate() throws RemoteException {}

  public void ejbPassivate() throws RemoteException {}

  public void ejbRemove() throws RemoteException {
    dao.close();
    dao = null;
  }

  public void ejbCreate() {}

  public void setSessionContext(SessionContext context) throws RemoteException {
    ctx = context;
  }

  public void unsetSessionContext() {
    this.ctx = null;
  }

  /**
   * Public methods
   */

  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException {
    if (linkCondition == null) {
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_field_perm");
    }
    else {
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_field_perm", linkCondition, binds == null ? new Vector() : binds);
    }
  }

  public void setData(GlbFieldPermDao attrs) throws RemoteException {
    this.attrs = attrs;
  }

  public GlbFieldPermDao getData() throws RemoteException {
    return this.attrs;
  }

  public void insert(GlbFieldPermDao attrs) throws RemoteException, DupKeyException {
    try {
      this.attrs = attrs;
      dao.create(this.attrs);
      dao.update(this.attrs);
    }
    catch (DupKeyException dkex) {
      throw dkex;
    }
    catch (Exception ex) {
      ex.toString();
    }
  }

  public void update(GlbFieldPermDao attrs) throws RemoteException {
    try {
      this.attrs = attrs;
      dao.update(this.attrs);
    }
    catch (Exception ex) {
      ex.toString();
    }
  }

  public void delete(GlbFieldPermDao attrs) throws RemoteException {
    try {
      this.attrs = attrs;
      dao.remove(attrs);
    }
    catch (Exception ex) {
      ex.toString();
    }
  }

  public void findByPrimaryKey(GlbFieldPermDao attrs) throws RemoteException, FinderException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (GlbFieldPermDao)dao.load((Object)attrs);
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public void findNext(GlbFieldPermDao attrs) throws RemoteException, FinderException, RowNotFoundException {
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

  public void findPrev(GlbFieldPermDao attrs) throws RemoteException, FinderException, RowNotFoundException {
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

  public Collection find(GlbFieldPermDao attrs) throws RemoteException, FinderException, RowNotFoundException {
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

  public Collection listAll() throws RemoteException {
    try {
      return dao.listAll();
    }
    catch (Exception ex) {
      throw new RemoteException(ex.toString());
    }
  }
}
