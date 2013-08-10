package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Collection;

import pt.inescporto.permissions.ejb.dao.GlbFieldDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;

public interface GlbField extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbFieldDao attrs) throws RemoteException;

  public GlbFieldDao getData() throws RemoteException;

  public void insert(GlbFieldDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbFieldDao attrs) throws RemoteException;

  public void delete(GlbFieldDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbFieldDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbFieldDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbFieldDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbFieldDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
