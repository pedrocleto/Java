package pt.inescporto.siasoft.go.aa.ejb.session;

import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvAspOtherMethodDao;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import javax.ejb.EJBObject;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public interface EnvAspOtherMethod extends EJBObject{
 public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EnvAspOtherMethodDao attrs) throws RemoteException;

  public EnvAspOtherMethodDao getData() throws RemoteException;

  public void insert(EnvAspOtherMethodDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EnvAspOtherMethodDao attrs) throws RemoteException, UserException;

  public void delete(EnvAspOtherMethodDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EnvAspOtherMethodDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EnvAspOtherMethodDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EnvAspOtherMethodDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EnvAspOtherMethodDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EnvAspOtherMethodDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
