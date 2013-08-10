package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Collection;

import pt.inescporto.permissions.ejb.dao.GlbProfFormDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;

public interface GlbProfForm extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbProfFormDao attrs) throws RemoteException;

  public GlbProfFormDao getData() throws RemoteException;

  public void insert(GlbProfFormDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbProfFormDao attrs) throws RemoteException;

  public void delete(GlbProfFormDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbProfFormDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbProfFormDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbProfFormDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbProfFormDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;
}
