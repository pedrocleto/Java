package pt.inescporto.siasoft.go.pga.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.siasoft.go.pga.ejb.dao.ObjectiveDao;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
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
public interface Objective extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ObjectiveDao attrs) throws RemoteException;

  public ObjectiveDao getData() throws RemoteException;

  public void insert(ObjectiveDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ObjectiveDao attrs) throws RemoteException, UserException;

  public void delete(ObjectiveDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(ObjectiveDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ObjectiveDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ObjectiveDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(ObjectiveDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ObjectiveDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
