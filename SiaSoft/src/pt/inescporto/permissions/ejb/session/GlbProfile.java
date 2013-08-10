package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Collection;

import pt.inescporto.permissions.ejb.dao.GlbProfileDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;

public interface GlbProfile extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbProfileDao attrs) throws RemoteException;

  public GlbProfileDao getData() throws RemoteException;

  public void insert(GlbProfileDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbProfileDao attrs) throws RemoteException;

  public void delete(GlbProfileDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbProfileDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbProfileDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbProfileDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbProfileDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
