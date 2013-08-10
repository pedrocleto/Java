package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Collection;

import pt.inescporto.permissions.ejb.dao.GlbFormDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;

public interface GlbForm extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbFormDao attrs) throws RemoteException;

  public GlbFormDao getData() throws RemoteException;

  public void insert(GlbFormDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbFormDao attrs) throws RemoteException;

  public void delete(GlbFormDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbFormDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbFormDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbFormDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbFormDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
