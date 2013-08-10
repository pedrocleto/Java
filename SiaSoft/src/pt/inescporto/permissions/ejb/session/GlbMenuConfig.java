package pt.inescporto.permissions.ejb.session;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.*;

import pt.inescporto.permissions.ejb.dao.GlbMenuConfigDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;

public interface GlbMenuConfig extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbMenuConfigDao attrs) throws RemoteException;

  public GlbMenuConfigDao getData() throws RemoteException;

  public void insert(GlbMenuConfigDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbMenuConfigDao attrs) throws RemoteException;

  public void delete(GlbMenuConfigDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbMenuConfigDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbMenuConfigDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbMenuConfigDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbMenuConfigDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
