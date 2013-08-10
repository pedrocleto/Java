package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Collection;

import pt.inescporto.permissions.ejb.dao.GlbOperatorIsInRoleDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;

public interface GlbOperatorIsInRole extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbOperatorIsInRoleDao attrs) throws RemoteException;

  public GlbOperatorIsInRoleDao getData() throws RemoteException;

  public void insert(GlbOperatorIsInRoleDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbOperatorIsInRoleDao attrs) throws RemoteException;

  public void delete(GlbOperatorIsInRoleDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbOperatorIsInRoleDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbOperatorIsInRoleDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbOperatorIsInRoleDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbOperatorIsInRoleDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;
}
