package pt.inescporto.siasoft.comun.ejb.session;


import java.rmi.RemoteException;
import java.util.Vector;
import java.util.Collection;
import javax.ejb.EJBObject;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.comun.ejb.dao.EnterpriseDao;
import pt.inescporto.template.dao.UserException;
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
public interface Enterprise extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EnterpriseDao attrs) throws RemoteException;

  public EnterpriseDao getData() throws RemoteException;

  public void insert(EnterpriseDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EnterpriseDao attrs) throws RemoteException, UserException;

  public void delete(EnterpriseDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EnterpriseDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EnterpriseDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EnterpriseDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EnterpriseDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EnterpriseDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
