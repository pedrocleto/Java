package pt.inescporto.permissions.ejb.session;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.*;

import pt.inescporto.permissions.ejb.dao.GlbMenuItemDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;

public interface GlbMenuItem extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbMenuItemDao attrs) throws RemoteException;

  public GlbMenuItemDao getData() throws RemoteException;

  public void insert(GlbMenuItemDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbMenuItemDao attrs) throws RemoteException;

  public void delete(GlbMenuItemDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbMenuItemDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbMenuItemDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbMenuItemDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbMenuItemDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
