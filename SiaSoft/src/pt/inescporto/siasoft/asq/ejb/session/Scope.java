package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.siasoft.asq.ejb.dao.ScopeDao;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.ConstraintViolatedException;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public interface Scope extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ScopeDao attrs) throws RemoteException;

  public ScopeDao getData() throws RemoteException;

  public void insert(ScopeDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ScopeDao attrs) throws RemoteException, UserException;

  public void delete(ScopeDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(ScopeDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ScopeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ScopeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public Collection find(ScopeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ScopeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
