package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;

import java.sql.*;
import javax.sql.*;

import pt.inescporto.permissions.ejb.dao.GlbProfFieldDao;
import pt.inescporto.template.dao.*;

public class GlbProfFieldEJB implements SessionBean {
  protected SessionContext ctx;
  protected GlbProfFieldDao attrs = new GlbProfFieldDao();
  protected DAO dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft",
                                "glb_prof_field");

  public GlbProfFieldEJB() {
    System.out.println( "New GlbProfField Session Bean created by EJB container ..." );
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

  public void setLinkCondition( String linkCondition, Vector binds ) throws RemoteException {
    this.dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft",
                                "glb_prof_field", linkCondition, binds );
  }

  public void setData( GlbProfFieldDao attrs ) throws RemoteException {
    this.attrs = attrs;
  }

  public GlbProfFieldDao getData() throws RemoteException {
    return this.attrs;
  }

  public void insert( GlbProfFieldDao attrs ) throws RemoteException, DupKeyException {
    try {
      this.attrs = attrs;
      dao.create(this.attrs);
      dao.update(this.attrs);
    }
    catch( DupKeyException dkex ) {
      throw dkex;
    }
    catch( Exception ex ) {
      ex.toString();
    }
  }

  public void update( GlbProfFieldDao attrs ) throws RemoteException {
    try {
      this.attrs = attrs;
      dao.update(this.attrs);
    }
    catch( Exception ex ) {
      ex.toString();
    }
  }

  public void delete( GlbProfFieldDao attrs ) throws RemoteException {
    try {
      this.attrs = attrs;
      dao.remove(attrs);
    }
    catch( Exception ex ) {
      ex.toString();
    }
  }

  public void findByPrimaryKey( GlbProfFieldDao attrs ) throws RemoteException, FinderException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (GlbProfFieldDao)dao.load((Object)attrs);
    }
    catch( Exception ex ) {
      throw new FinderException (ex.toString());
    }
  }

  public void findNext( GlbProfFieldDao attrs ) throws RemoteException, FinderException, RowNotFoundException {
    try {
      dao.findNextKey((Object)attrs);
      this.attrs = attrs;
    }
    catch( RowNotFoundException rnfex ) {
      throw rnfex;
    }
    catch( Exception ex ) {
      throw new FinderException (ex.toString());
    }
  }

  public void findPrev( GlbProfFieldDao attrs ) throws RemoteException, FinderException, RowNotFoundException {
    try {
      dao.findPrevKey((Object)attrs);
      this.attrs = attrs;
    }
    catch( RowNotFoundException rnfex ) {
      throw rnfex;
    }
    catch( Exception ex ) {
      throw new FinderException (ex.toString());
    }
  }

  public Collection find( GlbProfFieldDao attrs ) throws RemoteException, FinderException, RowNotFoundException {
    try {
      return dao.find((Object)attrs);
    }
    catch( RowNotFoundException rnfex ) {
      throw rnfex;
    }
    catch( Exception ex ) {
      throw new FinderException (ex.toString());
    }
  }

  public Collection listAll() throws RemoteException {
    try {
      return dao.listAll();
    }
    catch( Exception ex ) {
      throw new RemoteException (ex.toString());
    }
  }
}
