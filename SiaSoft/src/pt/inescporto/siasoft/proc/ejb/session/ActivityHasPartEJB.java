package pt.inescporto.siasoft.proc.ejb.session;

import javax.ejb.SessionContext;
import java.sql.SQLException;
import pt.inescporto.template.dao.DupKeyException;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityHasPartDao;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DAO;
import java.util.Vector;
import pt.inescporto.template.dao.RowNotFoundException;
import javax.ejb.SessionBean;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.naming.InitialContext;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityDao;
import pt.inescporto.template.elements.TplString;
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
public class ActivityHasPartEJB implements SessionBean {
  protected transient Activity activity = null;
  protected SessionContext ctx;
  protected ActivityHasPartDao attrs = new ActivityHasPartDao();
  protected DAO dao = null;

  public ActivityHasPartEJB() {
    System.out.println("New ActivityHasPart Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "ActivityHasPart");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "ActivityHasPart", linkCondition, binds);
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "ActivityHasPart");
    }
    else {
      if (binds == null)
        binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "ActivityHasPart", linkCondition, binds);
    }
  }

  public void setData(ActivityHasPartDao attrs) {
    this.attrs = attrs;
  }

  public ActivityHasPartDao getData() {
    return this.attrs;
  }

  public void insert(ActivityHasPartDao attrs) throws DupKeyException, UserException {
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

  public void update(ActivityHasPartDao attrs) throws UserException {
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

  public void delete(ActivityHasPartDao attrs) throws ConstraintViolatedException, UserException {
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

  public void findByPrimaryKey(ActivityHasPartDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (ActivityHasPartDao)dao.load(attrs);
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

  public void findNext(ActivityHasPartDao attrs) throws FinderException, RowNotFoundException {
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

  public void findPrev(ActivityHasPartDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection find(ActivityHasPartDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection findExact(ActivityHasPartDao attrs) throws FinderException, RowNotFoundException {
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
      attrs.activityID.setValue((String)pkKeys[0]);
      attrs.partClassID.setValue((String)pkKeys[1]);
      attrs.partID.setValue((String)pkKeys[2]);
      findByPrimaryKey(attrs);

      String[] MenuDesc = {attrs.qty.getValue().toString()};
      return MenuDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }

  public Collection<ActivityHasPartDao> getPartTree(String activityID, String type) throws UserException, RowNotFoundException {
    String lc = "";
    Vector b = new Vector();

    try {
      for (ActivityDao actDao : activity.getActivityTree(activityID)) {
	lc += actDao.activityID.getField() + " = ? OR ";
	b.add(new TplString(actDao.activityID.getValue()));
      }

      if (!lc.equals("")) {
        lc = "(" + lc.substring(0, lc.length() - 4) + ") AND flowType = ?";
      }
    }
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro remoto!", ex);
    }

    b.add(new TplString(type));

    DAO daoList = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "ActivityHasPart", lc, b);
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
