package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import pt.inescporto.permissions.ejb.dao.GlbProfFieldDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;

public interface GlbProfField extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbProfFieldDao attrs) throws RemoteException;

  public GlbProfFieldDao getData() throws RemoteException;

  public void insert(GlbProfFieldDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbProfFieldDao attrs) throws RemoteException;

  public void delete(GlbProfFieldDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbProfFieldDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbProfFieldDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbProfFieldDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbProfFieldDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;
}
