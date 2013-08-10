package pt.inescporto.siasoft.comun.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.comun.ejb.dao.ProfileGroupDao;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.ConstraintViolatedException;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public interface ProfileGroup extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ProfileGroupDao attrs) throws RemoteException;

  public ProfileGroupDao getData() throws RemoteException;

  public void insert(ProfileGroupDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ProfileGroupDao attrs) throws RemoteException, UserException;

  public void delete(ProfileGroupDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(ProfileGroupDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ProfileGroupDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ProfileGroupDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(ProfileGroupDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ProfileGroupDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
