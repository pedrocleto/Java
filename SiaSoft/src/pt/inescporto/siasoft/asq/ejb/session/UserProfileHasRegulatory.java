package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.asq.ejb.dao.UserProfileHasRegulatoryDao;
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
public interface UserProfileHasRegulatory extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(UserProfileHasRegulatoryDao attrs) throws RemoteException;

  public UserProfileHasRegulatoryDao getData() throws RemoteException;

  public void insert(UserProfileHasRegulatoryDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(UserProfileHasRegulatoryDao attrs) throws RemoteException, UserException;

  public void delete(UserProfileHasRegulatoryDao attrs) throws RemoteException, UserException, ConstraintViolatedException;

  public void findByPrimaryKey(UserProfileHasRegulatoryDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(UserProfileHasRegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(UserProfileHasRegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public Collection find(UserProfileHasRegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(UserProfileHasRegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
