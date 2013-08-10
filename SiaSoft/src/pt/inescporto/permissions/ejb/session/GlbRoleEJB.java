package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.Collection;

import pt.inescporto.permissions.ejb.dao.GlbRoleDao;
import pt.inescporto.template.dao.*;

public class GlbRoleEJB implements SessionBean {
  protected SessionContext ctx;
  protected GlbRoleDao attrs = new GlbRoleDao();
  protected DAO dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_role");

  public GlbRoleEJB() {
    System.out.println("New GlbRole Session Bean created by EJB container ...");
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

  public void setLinkCondition(String linkCondition, Vector binds) {
    if (linkCondition == null) {
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_role");
    }
    else {
      if (binds == null)
        binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_role", linkCondition, binds);
    }
  }

  public void setData(GlbRoleDao attrs) throws RemoteException {
    this.attrs = attrs;
  }

  public GlbRoleDao getData() throws RemoteException {
    return this.attrs;
  }

  public void insert(GlbRoleDao attrs) throws RemoteException, DupKeyException {
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

  public void update(GlbRoleDao attrs) throws RemoteException {
    try {
      this.attrs = attrs;
      dao.update(this.attrs);
    }
    catch (Exception ex) {
      ex.toString();
    }
  }

  public void delete(GlbRoleDao attrs) throws RemoteException {
    try {
      this.attrs = attrs;
      dao.remove(attrs);
    }
    catch (Exception ex) {
      ex.toString();
    }
  }

  public void findByPrimaryKey(GlbRoleDao attrs) throws RemoteException, FinderException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (GlbRoleDao)dao.load((Object)attrs);
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public void findNext(GlbRoleDao attrs) throws RemoteException, FinderException, RowNotFoundException {
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

  public void findPrev(GlbRoleDao attrs) throws RemoteException, FinderException, RowNotFoundException {
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

  public Collection find(GlbRoleDao attrs) throws RemoteException, FinderException, RowNotFoundException {
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

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException {
    try {
      attrs.roleId.setValue((String)pkKeys[0]);
      findByPrimaryKey(attrs);

      String[] roleDesc = {attrs.roleName.getValue()};
      return roleDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
