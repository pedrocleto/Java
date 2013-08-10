package pt.inescporto.siasoft.comun.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import pt.inescporto.siasoft.comun.ejb.dao.UserDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.ConstraintViolatedException;

public interface User extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(UserDao attrs) throws RemoteException;

  public UserDao getData() throws RemoteException;

  public void insert(UserDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(UserDao attrs) throws RemoteException, UserException;

  public void delete(UserDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(UserDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(UserDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(UserDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(UserDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(UserDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
