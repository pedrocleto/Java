package pt.inescporto.siasoft.go.auditor.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditActionsDao;
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
public interface AuditActions extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(AuditActionsDao attrs) throws RemoteException;

  public AuditActionsDao getData() throws RemoteException;

  public void insert(AuditActionsDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(AuditActionsDao attrs) throws RemoteException, UserException;

  public void delete(AuditActionsDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(AuditActionsDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(AuditActionsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(AuditActionsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(AuditActionsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(AuditActionsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
