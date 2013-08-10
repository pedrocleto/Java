package pt.inescporto.siasoft.comun.ejb.session;

import java.util.Collection;
import java.util.Vector;
import java.rmi.RemoteException;

import javax.ejb.FinderException;
import javax.ejb.EJBObject;

import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.ConstraintViolatedException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.siasoft.comun.ejb.dao.WarningMessageDao;

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
public interface WarningMessage extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(WarningMessageDao attrs) throws RemoteException;

  public WarningMessageDao getData() throws RemoteException;

  public void insert(WarningMessageDao attrs) throws RemoteException, DupKeyException, UserException;

  public void insertAllUsers(WarningMessageDao attrs, String enterpriseID) throws RemoteException, DupKeyException, UserException;

  public void update(WarningMessageDao attrs) throws RemoteException, UserException;

  public void delete(WarningMessageDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(WarningMessageDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(WarningMessageDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(WarningMessageDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(WarningMessageDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(WarningMessageDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;

  public Collection findByUser(String userID) throws RemoteException, FinderException, RowNotFoundException;
}
