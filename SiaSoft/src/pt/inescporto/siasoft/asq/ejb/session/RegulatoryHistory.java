package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryHistoryDao;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.ConstraintViolatedException;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public interface RegulatoryHistory extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(RegulatoryHistoryDao attrs) throws RemoteException;

  public RegulatoryHistoryDao getData() throws RemoteException;

  public void insert(RegulatoryHistoryDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(RegulatoryHistoryDao attrs) throws RemoteException, UserException;

  public void delete(RegulatoryHistoryDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(RegulatoryHistoryDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(RegulatoryHistoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(RegulatoryHistoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(RegulatoryHistoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(RegulatoryHistoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;
}
