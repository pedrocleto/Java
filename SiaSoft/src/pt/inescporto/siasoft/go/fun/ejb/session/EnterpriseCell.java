package pt.inescporto.siasoft.go.fun.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.go.fun.ejb.dao.EnterpriseCellDao;
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
 * @author not attributable
 * @version 0.1
 */
public interface EnterpriseCell extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EnterpriseCellDao attrs) throws RemoteException;

  public EnterpriseCellDao getData() throws RemoteException;

  public void insert(EnterpriseCellDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EnterpriseCellDao attrs) throws RemoteException, UserException;

  public void delete(EnterpriseCellDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EnterpriseCellDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EnterpriseCellDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EnterpriseCellDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EnterpriseCellDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EnterpriseCellDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
