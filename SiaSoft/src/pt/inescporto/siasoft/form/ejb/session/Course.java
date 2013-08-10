package pt.inescporto.siasoft.form.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.form.ejb.dao.CourseDao;
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
public interface Course extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(CourseDao attrs) throws RemoteException;

  public CourseDao getData() throws RemoteException;

  public void insert(CourseDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(CourseDao attrs) throws RemoteException, UserException;

  public void delete(CourseDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(CourseDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(CourseDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(CourseDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(CourseDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(CourseDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
