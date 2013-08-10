package pt.inescporto.siasoft.go.pga.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.go.pga.ejb.dao.ObjectiveActionsDao;
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
public interface ObjectiveActions extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ObjectiveActionsDao attrs) throws RemoteException;

  public ObjectiveActionsDao getData() throws RemoteException;

  public void insert(ObjectiveActionsDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ObjectiveActionsDao attrs) throws RemoteException, UserException;

  public void delete(ObjectiveActionsDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(ObjectiveActionsDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ObjectiveActionsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ObjectiveActionsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(ObjectiveActionsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ObjectiveActionsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
