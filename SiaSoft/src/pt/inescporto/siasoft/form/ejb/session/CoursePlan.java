package pt.inescporto.siasoft.form.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.form.ejb.dao.CoursePlanDao;
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
public interface CoursePlan extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(CoursePlanDao attrs) throws RemoteException;

  public CoursePlanDao getData() throws RemoteException;

  public void insert(CoursePlanDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(CoursePlanDao attrs) throws RemoteException, UserException;

  public void delete(CoursePlanDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(CoursePlanDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(CoursePlanDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(CoursePlanDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(CoursePlanDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(CoursePlanDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
