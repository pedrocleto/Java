package pt.inescporto.siasoft.condoc.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentUserApprovalDao;
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
public interface DocumentUserApproval extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(DocumentUserApprovalDao attrs) throws RemoteException;

  public DocumentUserApprovalDao getData() throws RemoteException;

  public void insert(DocumentUserApprovalDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(DocumentUserApprovalDao attrs) throws RemoteException, UserException;

  public void delete(DocumentUserApprovalDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(DocumentUserApprovalDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(DocumentUserApprovalDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(DocumentUserApprovalDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(DocumentUserApprovalDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(DocumentUserApprovalDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
