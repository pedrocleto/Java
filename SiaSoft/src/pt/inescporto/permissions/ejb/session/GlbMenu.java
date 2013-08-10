package pt.inescporto.permissions.ejb.session;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.*;

import pt.inescporto.permissions.ejb.dao.GlbMenuDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;

public interface GlbMenu extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbMenuDao attrs) throws RemoteException;

  public GlbMenuDao getData() throws RemoteException;

  public void insert(GlbMenuDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbMenuDao attrs) throws RemoteException;

  public void delete(GlbMenuDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbMenuDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbMenuDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbMenuDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbMenuDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
