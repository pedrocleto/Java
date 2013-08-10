package pt.inescporto.siasoft.form.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.form.ejb.dao.TeachingEntityDao;
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
public interface TeachingEntity extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(TeachingEntityDao attrs) throws RemoteException;

  public TeachingEntityDao getData() throws RemoteException;

  public void insert(TeachingEntityDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(TeachingEntityDao attrs) throws RemoteException, UserException;

  public void delete(TeachingEntityDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(TeachingEntityDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(TeachingEntityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(TeachingEntityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(TeachingEntityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(TeachingEntityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
