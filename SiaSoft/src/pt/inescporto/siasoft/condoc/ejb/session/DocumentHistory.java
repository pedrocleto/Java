package pt.inescporto.siasoft.condoc.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentHistoryDao;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;

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
public interface DocumentHistory extends EJBObject {
  public void setAttrs(DocumentHistoryDao attrs) throws RemoteException;

  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(DocumentHistoryDao attrs) throws RemoteException;

  public DocumentHistoryDao getData() throws RemoteException;

  public void insert(DocumentHistoryDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(DocumentHistoryDao attrs) throws RemoteException, UserException;

  public void delete(DocumentHistoryDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(DocumentHistoryDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(DocumentHistoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(DocumentHistoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(DocumentHistoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(DocumentHistoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
