package pt.inescporto.siasoft.go.aa.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvAspSIAMethodDao;
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
public interface EnvAspSIAMethod extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EnvAspSIAMethodDao attrs) throws RemoteException;

  public EnvAspSIAMethodDao getData() throws RemoteException;

  public void insert(EnvAspSIAMethodDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EnvAspSIAMethodDao attrs) throws RemoteException, UserException;

  public void delete(EnvAspSIAMethodDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EnvAspSIAMethodDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EnvAspSIAMethodDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EnvAspSIAMethodDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EnvAspSIAMethodDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EnvAspSIAMethodDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
