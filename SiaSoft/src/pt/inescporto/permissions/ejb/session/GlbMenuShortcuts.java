package pt.inescporto.permissions.ejb.session;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.*;

import pt.inescporto.permissions.ejb.dao.GlbMenuShortcutsDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;

public interface GlbMenuShortcuts extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbMenuShortcutsDao attrs) throws RemoteException;

  public GlbMenuShortcutsDao getData() throws RemoteException;

  public void insert(GlbMenuShortcutsDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbMenuShortcutsDao attrs) throws RemoteException;

  public void delete(GlbMenuShortcutsDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbMenuShortcutsDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbMenuShortcutsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbMenuShortcutsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbMenuShortcutsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
