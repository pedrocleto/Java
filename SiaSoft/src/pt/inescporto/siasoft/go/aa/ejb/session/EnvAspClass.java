package pt.inescporto.siasoft.go.aa.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvAspClassDao;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
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
public interface EnvAspClass extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EnvAspClassDao attrs) throws RemoteException;

  public EnvAspClassDao getData() throws RemoteException;

  public void insert(EnvAspClassDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EnvAspClassDao attrs) throws RemoteException, UserException;

  public void delete(EnvAspClassDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EnvAspClassDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EnvAspClassDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EnvAspClassDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EnvAspClassDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EnvAspClassDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
