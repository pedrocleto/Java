package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Collection;

import pt.inescporto.permissions.ejb.dao.GlbOperatorHasProfileDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;

public interface GlbOperatorHasProfile extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(GlbOperatorHasProfileDao attrs) throws RemoteException;

  public GlbOperatorHasProfileDao getData() throws RemoteException;

  public void insert(GlbOperatorHasProfileDao attrs) throws RemoteException, DupKeyException;

  public void update(GlbOperatorHasProfileDao attrs) throws RemoteException;

  public void delete(GlbOperatorHasProfileDao attrs) throws RemoteException;

  public void findByPrimaryKey(GlbOperatorHasProfileDao attrs) throws RemoteException, FinderException;

  public void findNext(GlbOperatorHasProfileDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(GlbOperatorHasProfileDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection find(GlbOperatorHasProfileDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;
}
