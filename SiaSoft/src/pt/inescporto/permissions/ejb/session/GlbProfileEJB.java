package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;

import java.sql.*;
import javax.sql.*;

import pt.inescporto.permissions.ejb.dao.GlbProfileDao;
import pt.inescporto.template.dao.*;

public class GlbProfileEJB implements SessionBean {
  protected SessionContext ctx;
  protected GlbProfileDao attrs = new GlbProfileDao();
  protected DAO dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft",
			      "glb_profile");

  public GlbProfileEJB() {
    System.out.println("New GlbProfile Session Bean created by EJB container ...");
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_profile");
    }
    else {
      if (binds == null)
	binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_profile", linkCondition, binds);
    }
  }

  public void setData(GlbProfileDao attrs) throws RemoteException {
    this.attrs = attrs;
  }

  public GlbProfileDao getData() throws RemoteException {
    return this.attrs;
  }

  public void insert(GlbProfileDao attrs) throws RemoteException, DupKeyException {
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

  public void update(GlbProfileDao attrs) throws RemoteException {
    try {
      this.attrs = attrs;
      dao.update(this.attrs);
    }
    catch (Exception ex) {
      ex.toString();
    }
  }

  public void delete(GlbProfileDao attrs) throws RemoteException {
    try {
      this.attrs = attrs;
      dao.remove(attrs);
    }
    catch (Exception ex) {
      ex.toString();
    }
  }

  public void findByPrimaryKey(GlbProfileDao attrs) throws RemoteException, FinderException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (GlbProfileDao)dao.load((Object)attrs);
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public void findNext(GlbProfileDao attrs) throws RemoteException, FinderException, RowNotFoundException {
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

  public void findPrev(GlbProfileDao attrs) throws RemoteException, FinderException, RowNotFoundException {
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

  public Collection find(GlbProfileDao attrs) throws RemoteException, FinderException, RowNotFoundException {
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

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException,
      RowNotFoundException {
    try {
      attrs.profileId.setValue((String)pkKeys[0]);
      findByPrimaryKey(attrs);

      String[] roleDesc = {
	  attrs.profileName.getValue()};
      return roleDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
