package pt.inescporto.siasoft.form.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.form.ejb.dao.CourseModuleUsersDao;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;

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
public interface CourseModuleUsers extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(CourseModuleUsersDao attrs) throws RemoteException;

  public CourseModuleUsersDao getData() throws RemoteException;

  public void insert(CourseModuleUsersDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(CourseModuleUsersDao attrs) throws RemoteException, UserException;

  public void delete(CourseModuleUsersDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(CourseModuleUsersDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(CourseModuleUsersDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(CourseModuleUsersDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(CourseModuleUsersDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(CourseModuleUsersDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
