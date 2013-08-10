package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import pt.inescporto.permissions.ejb.dao.GlbFieldPermDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;

public interface GlbFieldPerm extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbFieldPermDao attrs) throws RemoteException;

  public GlbFieldPermDao getData() throws RemoteException;

  public void insert(GlbFieldPermDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbFieldPermDao attrs) throws RemoteException;

  public void delete(GlbFieldPermDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbFieldPermDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbFieldPermDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbFieldPermDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbFieldPermDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;
}
