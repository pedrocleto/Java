package pt.inescporto.permissions.ejb.session;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.*;

import pt.inescporto.permissions.ejb.dao.GlbRoleDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;

public interface GlbRole extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbRoleDao attrs) throws RemoteException;

  public GlbRoleDao getData() throws RemoteException;

  public void insert(GlbRoleDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbRoleDao attrs) throws RemoteException;

  public void delete(GlbRoleDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbRoleDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbRoleDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbRoleDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbRoleDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
