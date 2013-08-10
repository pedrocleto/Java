package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.asq.ejb.dao.HistoricTypeDao;
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
public interface HistoricType extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(HistoricTypeDao attrs) throws RemoteException;

  public HistoricTypeDao getData() throws RemoteException;

  public void insert(HistoricTypeDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(HistoricTypeDao attrs) throws RemoteException, UserException;

  public void delete(HistoricTypeDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(HistoricTypeDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(HistoricTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(HistoricTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(HistoricTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(HistoricTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
