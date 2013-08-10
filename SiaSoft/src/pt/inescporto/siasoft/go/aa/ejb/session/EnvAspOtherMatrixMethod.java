package pt.inescporto.siasoft.go.aa.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvAspOtherMatrixMethodDao;
import pt.inescporto.template.dao.RowNotFoundException;
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
public interface EnvAspOtherMatrixMethod extends EJBObject {

  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EnvAspOtherMatrixMethodDao attrs) throws RemoteException;

  public EnvAspOtherMatrixMethodDao getData() throws RemoteException;

  public void insert(EnvAspOtherMatrixMethodDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EnvAspOtherMatrixMethodDao attrs) throws RemoteException, UserException;

  public void delete(EnvAspOtherMatrixMethodDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EnvAspOtherMatrixMethodDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EnvAspOtherMatrixMethodDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EnvAspOtherMatrixMethodDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EnvAspOtherMatrixMethodDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EnvAspOtherMatrixMethodDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
