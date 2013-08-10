package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.util.*;
import java.sql.*;

import pt.inescporto.permissions.ejb.dao.GlbFieldDao;
import pt.inescporto.template.dao.*;
import javax.naming.*;

public class GlbFieldEJB implements SessionBean {
  protected SessionContext ctx;
  protected GlbFieldDao attrs = new GlbFieldDao();
  protected DAO dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_field");

  public GlbFieldEJB() {
    System.out.println("New GlbField Session Bean created by EJB container ...");
  }

  /**
   * EJB Required methods
   */

  public void ejbActivate() {}

  public void ejbPassivate() {}

  public void ejbRemove() {
    dao.close();
    dao = null;
  }

  public void ejbCreate() {}

  public void setSessionContext(SessionContext context) {
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_field");
    }
    else {
      if (binds == null)
        binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "glb_field", linkCondition, binds);
    }
  }

  public void setData(GlbFieldDao attrs) {
    this.attrs = attrs;
  }

  public GlbFieldDao getData() {
    return this.attrs;
  }

  public void insert(GlbFieldDao attrs) throws DupKeyException {
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

  public void update(GlbFieldDao attrs) {
    try {
      this.attrs = attrs;
      dao.update(this.attrs);
    }
    catch (Exception ex) {
      ex.toString();
    }
  }

  public void delete(GlbFieldDao attrs) {
    try {
      this.attrs = attrs;
      dao.remove(attrs);
    }
    catch (Exception ex) {
      ex.toString();
    }
  }

  public void findByPrimaryKey(GlbFieldDao attrs) throws FinderException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (GlbFieldDao)dao.load((Object)attrs);
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public void findNext(GlbFieldDao attrs) throws FinderException, RowNotFoundException {
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

  public void findPrev(GlbFieldDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection find(GlbFieldDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection listAll() {
      try {
	return dao.listAll();
      }
      catch (RowNotFoundException ex) {
	return null;
      }
      catch (NamingException ex) {
	return null;
      }
      catch (SQLException ex) {
	return null;
      }
  }

  public String[] lookupDesc(Object[] pkKeys) throws RowNotFoundException {
    try {
      attrs.fieldId.setValue((String)pkKeys[0]);
      findByPrimaryKey(attrs);

      String[] roleDesc = {
	  attrs.fieldDesc.getValue()};
      return roleDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
