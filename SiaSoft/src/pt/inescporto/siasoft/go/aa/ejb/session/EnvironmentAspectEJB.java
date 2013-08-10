package pt.inescporto.siasoft.go.aa.ejb.session;

import javax.ejb.SessionContext;
import java.sql.SQLException;
import pt.inescporto.template.dao.DupKeyException;
import javax.naming.NamingException;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvironmentAspectDao;
import java.rmi.RemoteException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DAO;
import java.util.Vector;
import pt.inescporto.template.dao.RowNotFoundException;
import javax.ejb.SessionBean;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.template.ejb.util.KeyHome;
import javax.naming.InitialContext;
import pt.inescporto.template.ejb.util.Key;
import pt.inescporto.template.dao.ConstraintViolatedException;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.proc.ejb.session.Activity;
import pt.inescporto.siasoft.proc.ejb.session.ActivityHome;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityDao;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class EnvironmentAspectEJB implements SessionBean {
  protected transient Activity activity = null;
  protected transient Key key;
  protected SessionContext ctx;
  protected EnvironmentAspectDao attrs = new EnvironmentAspectDao();
  protected DAO dao = null;

  public EnvironmentAspectEJB() {
    System.out.println("New EnvironmentAspect Session Bean created by EJB container ...");
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

    // get Activity
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/Activity");
      ActivityHome activityHome = (ActivityHome)PortableRemoteObject.narrow(objref, ActivityHome.class);
      activity = activityHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Key Home");
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "EnvironmentalAspect");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "EnvironmentalAspect", linkCondition, binds);
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "EnvironmentalAspect");
    }
    else {
      if (binds == null)
        binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "EnvironmentalAspect", linkCondition, binds);
    }
  }

  public void setData(EnvironmentAspectDao attrs) {
    this.attrs = attrs;
  }

  public EnvironmentAspectDao getData() {
    return this.attrs;
  }

  public void insert(EnvironmentAspectDao attrs) throws DupKeyException, UserException {
    try {
      // generate key
/*      if (attrs.envAspectID.getValue() == null)
        attrs.envAspectID.setValue(key.GenerateKey("KM_EnvironmentalAspect", "envAspID"));*/

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
/*    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro ao gerar chave primária!", ex);
    }*/
  }

  public void update(EnvironmentAspectDao attrs) throws UserException {
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

  public void delete(EnvironmentAspectDao attrs) throws ConstraintViolatedException, UserException {
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

  public void findByPrimaryKey(EnvironmentAspectDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (EnvironmentAspectDao)dao.load(attrs);
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

  public void findNext(EnvironmentAspectDao attrs) throws FinderException, RowNotFoundException {
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

  public void findPrev(EnvironmentAspectDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection find(EnvironmentAspectDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection findExact(EnvironmentAspectDao attrs) throws FinderException, RowNotFoundException {
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
      attrs.envAspectID.setValue((String)pkKeys[0]);
      findByPrimaryKey(attrs);

      String[] MenuDesc = {attrs.envAspectName.getValue()};
      return MenuDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }

  public Collection<EnvironmentAspectDao> getEnvAspTree(String activityID, String classID) throws UserException, RowNotFoundException {
    String lc = "";
    Vector b = new Vector();

    try {
      for (ActivityDao actDao : activity.getActivityTree(activityID)) {
        lc += actDao.activityID.getField() + " = ? OR ";
        b.add(new TplString(actDao.activityID.getValue()));
      }

      if (!lc.equals("")) {
        lc = "(" + lc.substring(0, lc.length() - 4) + ")" + (classID == null ? "" : " AND envAspClassID = ?");
      }
    }
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro remoto!", ex);
    }

    if (classID != null)
      b.add(new TplString(classID));

    DAO daoList = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "EnvironmentalAspect", lc, b);
    try {
      return daoList.listAll();
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
      throw new UserException("Erro no SLQ.", ex);
    }
  }

}
