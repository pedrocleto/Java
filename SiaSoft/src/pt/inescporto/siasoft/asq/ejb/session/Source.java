package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.asq.ejb.dao.SourceDao;
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
public interface Source extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(SourceDao attrs) throws RemoteException;

  public SourceDao getData() throws RemoteException;

  public void insert(SourceDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(SourceDao attrs) throws RemoteException, UserException;

  public void delete(SourceDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(SourceDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(SourceDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(SourceDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(SourceDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(SourceDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
