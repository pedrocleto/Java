package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Collection;

import pt.inescporto.permissions.ejb.dao.GlbFormIsInRoleDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;

public interface GlbFormIsInRole extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbFormIsInRoleDao attrs) throws RemoteException;

  public GlbFormIsInRoleDao getData() throws RemoteException;

  public void insert(GlbFormIsInRoleDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbFormIsInRoleDao attrs) throws RemoteException;

  public void delete(GlbFormIsInRoleDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbFormIsInRoleDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbFormIsInRoleDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbFormIsInRoleDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbFormIsInRoleDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;
}
