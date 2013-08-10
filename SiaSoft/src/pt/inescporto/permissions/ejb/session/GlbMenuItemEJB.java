package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.Collection;

import pt.inescporto.permissions.ejb.dao.GlbMenuItemDao;
import pt.inescporto.template.dao.*;

public class GlbMenuItemEJB implements SessionBean {
  protected SessionContext ctx;
  protected GlbMenuItemDao attrs = new GlbMenuItemDao();
  protected DAO dao = null;

  public GlbMenuItemEJB() {
    System.out.println("New GlbMenuItem Session Bean created by EJB container ...");
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

  public void ejbCreate() {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_menu_items");
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_menu_items");
    }
    else {
      if (binds == null)
        binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_menu_items", linkCondition, binds);
    }
  }

  public void setData(GlbMenuItemDao attrs) throws RemoteException {
    this.attrs = attrs;
  }

  public GlbMenuItemDao getData() throws RemoteException {
    return this.attrs;
  }

  public void insert(GlbMenuItemDao attrs) throws RemoteException, DupKeyException {
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

  public void update(GlbMenuItemDao attrs) throws RemoteException {
    try {
      this.attrs = attrs;
      dao.update(this.attrs);
    }
    catch (Exception ex) {
      ex.toString();
    }
  }

  public void delete(GlbMenuItemDao attrs) throws RemoteException {
    try {
      this.attrs = attrs;
      dao.remove(attrs);
    }
    catch (Exception ex) {
      ex.toString();
    }
  }

  public void findByPrimaryKey(GlbMenuItemDao attrs) throws RemoteException, FinderException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (GlbMenuItemDao)dao.load((Object)attrs);
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public void findNext(GlbMenuItemDao attrs) throws RemoteException, FinderException, RowNotFoundException {
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

  public void findPrev(GlbMenuItemDao attrs) throws RemoteException, FinderException, RowNotFoundException {
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

  public Collection find(GlbMenuItemDao attrs) throws RemoteException, FinderException, RowNotFoundException {
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
    return new String[] {};
  }
}
