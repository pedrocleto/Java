package pt.inescporto.siasoft.go.aa.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvAspOtherSignificantDao;

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
public interface EnvAspOtherSignificant extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EnvAspOtherSignificantDao attrs) throws RemoteException;

  public EnvAspOtherSignificantDao getData() throws RemoteException;

  public void insert(EnvAspOtherSignificantDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EnvAspOtherSignificantDao attrs) throws RemoteException, UserException;

  public void delete(EnvAspOtherSignificantDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EnvAspOtherSignificantDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EnvAspOtherSignificantDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EnvAspOtherSignificantDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EnvAspOtherSignificantDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EnvAspOtherSignificantDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
