package pt.inescporto.siasoft.proc.ejb.session;

import java.util.Collection;
import java.util.Vector;
import java.rmi.RemoteException;

import javax.ejb.EJBObject;
import javax.ejb.FinderException;

import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.ConstraintViolatedException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityRegulatoryDao;

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
public interface ActivityRegulatory extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ActivityRegulatoryDao attrs) throws RemoteException;

  public ActivityRegulatoryDao getData() throws RemoteException;

  public void insert(ActivityRegulatoryDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ActivityRegulatoryDao attrs) throws RemoteException, UserException;

  public void delete(ActivityRegulatoryDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void deleteForActivity(String activityID) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(ActivityRegulatoryDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ActivityRegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ActivityRegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(ActivityRegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ActivityRegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
