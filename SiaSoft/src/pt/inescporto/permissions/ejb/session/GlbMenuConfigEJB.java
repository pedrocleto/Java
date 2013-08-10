package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.Collection;

import java.sql.*;
import javax.sql.*;

import pt.inescporto.permissions.ejb.dao.GlbMenuConfigDao;
import pt.inescporto.template.dao.*;

public class GlbMenuConfigEJB implements SessionBean {
  protected SessionContext ctx;
  protected GlbMenuConfigDao attrs = new GlbMenuConfigDao();
  protected DAO dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft",
                                "glb_menu_config");

  public GlbMenuConfigEJB() {
    System.out.println( "New GlbMenuConfig Session Bean created by EJB container ..." );
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_menu_config");
    }
    else {
      if (binds == null)
        binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_menu_config", linkCondition, binds);
    }
  }

  public void setData( GlbMenuConfigDao attrs ) throws RemoteException {
    this.attrs = attrs;
  }

  public GlbMenuConfigDao getData() throws RemoteException {
    return this.attrs;
  }

  public void insert( GlbMenuConfigDao attrs ) throws RemoteException, DupKeyException {
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

  public void update( GlbMenuConfigDao attrs ) throws RemoteException {
    try {
      this.attrs = attrs;
      dao.update(this.attrs);
    }
    catch( Exception ex ) {
      ex.toString();
    }
  }

  public void delete( GlbMenuConfigDao attrs ) throws RemoteException {
    try {
      this.attrs = attrs;
      dao.remove(attrs);
    }
    catch( Exception ex ) {
      ex.toString();
    }
  }

  public void findByPrimaryKey( GlbMenuConfigDao attrs ) throws RemoteException, FinderException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (GlbMenuConfigDao)dao.load((Object)attrs);
    }
    catch( Exception ex ) {
      throw new FinderException (ex.toString());
    }
  }

  public void findNext( GlbMenuConfigDao attrs ) throws RemoteException, FinderException, RowNotFoundException {
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

  public void findPrev( GlbMenuConfigDao attrs ) throws RemoteException, FinderException, RowNotFoundException {
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

  public Collection find( GlbMenuConfigDao attrs ) throws RemoteException, FinderException, RowNotFoundException {
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

  public String[] lookupDesc( Object[] pkKeys ) throws RemoteException, RowNotFoundException {
    try {
      attrs.configId.setValue( (String) pkKeys[0]);
      findByPrimaryKey(attrs);

      String[] configDesc = { attrs.configName.getValue() };
      return configDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
