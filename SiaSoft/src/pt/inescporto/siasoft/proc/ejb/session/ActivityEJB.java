package pt.inescporto.siasoft.proc.ejb.session;

import javax.ejb.SessionContext;
import java.sql.SQLException;
import pt.inescporto.template.dao.DupKeyException;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityDao;
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
 * @author jap
 * @version 0.1
 */
public class ActivityEJB implements SessionBean {
  protected transient Key key;
  protected transient ActivityConnection actConn;
  protected SessionContext ctx;
  protected ActivityDao attrs = new ActivityDao();
  protected DAO dao = null;
  protected String linkCondition = null;

  public ActivityEJB() {
    System.out.println("New Activity Session Bean created by EJB container ...");
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

    // get ActivityConnecion Component
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/ActivityConnection");
      ActivityConnectionHome actConnHome = (ActivityConnectionHome)PortableRemoteObject.narrow(objref, ActivityConnectionHome.class);
      actConn = actConnHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate ActivityConnection Home");
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Activity");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    this.linkCondition = linkCondition;
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Activity", linkCondition, binds);
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Activity");
    }
    else {
      if (binds == null)
        binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Activity", linkCondition, binds);
      this.linkCondition = linkCondition;
    }
  }

  public void setData(ActivityDao attrs) {
    this.attrs = attrs;
  }

  public ActivityDao getData() {
    return this.attrs;
  }

  public void insert(ActivityDao attrs) throws DupKeyException, UserException {
    try {
      // generate primary key
      try {
        attrs.activityID.setValue(key.GenerateKey("KM_Activity", "activityID"));
      }
      catch (RemoteException ex) {
        ctx.setRollbackOnly();
        throw new UserException("Generating key!", ex);
      }

      // generate the primary key sequence based on father sequence
      if (attrs.activityFatherID.getValue() != null) {
        ActivityDao father = new ActivityDao();
        father.activityID.setValue(attrs.activityFatherID.getValue());
        dao.findByPrimaryKey(father);
        attrs.activityPkSequence.setValue(father.activityPkSequence.getValue() + "." + attrs.activityID.getValue());
      }
      else
        attrs.activityPkSequence.setValue(attrs.activityID.getValue());

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
    catch (RowNotFoundException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Activity Father ID doesn't exists!", ex);
    }
  }

  public void update(ActivityDao attrs) throws RemoteException, UserException {
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

  public void delete(Vector<String> actlistID) throws ConstraintViolatedException, UserException {
    try {
      // for every activity
      for (String activityID : actlistID) {
	// find is tree
	Collection<ActivityDao> actList = null;
	try {
	  actList = getActivityTree(activityID);
	}
        catch (RowNotFoundException ex1) {
          // someone as deleted first
          continue;
        }

	// order the compute list by leaf's to parent
	Vector<ActivityDao> actlistVector = new Vector<ActivityDao>(actList);
	boolean listAsChanged = false;
	do {
	  listAsChanged = false;
	  for (int i = 0; i < actList.size() - 1; i++) {
	    if (actlistVector.elementAt(i + 1).activityPkSequence.getValue().length() > actlistVector.elementAt(i).activityPkSequence.getValue().length()) {
	      actlistVector.add(i, actlistVector.remove(i + 1));
	      listAsChanged = true;
	    }
	  }
	}
	while (listAsChanged);

	// remove the tree
	for (ActivityDao act : actlistVector) {
          // but first one must remove every connection's that references this activity
	  try {
	    actConn.deleteForActivity(act.activityID.getValue());
	  }
	  catch (RemoteException ex2) {
            ctx.setRollbackOnly();
            throw new UserException("Erro Remoto ao eliminar ligações.", ex2);
	  }
          dao.remove(act);
	}
      }
    }
    catch (UserException ex1) {
      ctx.setRollbackOnly();
      throw ex1;
    }
    catch (NamingException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no servidor de nomes.", ex);
    }
    catch (SQLException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no SQL!", ex);
    }
    catch (ConstraintViolatedException ex) {
      ctx.setRollbackOnly();
      throw ex;
    }
  }

  public void findByPrimaryKey(ActivityDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (ActivityDao)dao.load(attrs);
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

  public void findNext(ActivityDao attrs) throws FinderException, RowNotFoundException {
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

  public void findPrev(ActivityDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection find(ActivityDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection findExact(ActivityDao attrs) throws FinderException, RowNotFoundException {
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
      findByPrimaryKey(attrs);

      String[] MenuDesc = {attrs.activityDescription.getValue()};
      return MenuDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }

  public Collection<ActivityDao> getActivityTree(String startActivityNode) throws RowNotFoundException, UserException{
    DAO daoList = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Activity", "activityPkSequence LIKE '%" + startActivityNode + "%'", new Vector());

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
      throw new UserException("Erro no SQL.", ex);
    }
  }
}
