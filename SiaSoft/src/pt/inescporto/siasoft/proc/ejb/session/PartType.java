package pt.inescporto.siasoft.proc.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.proc.ejb.dao.PartTypeDao;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
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
 * @author jap
 * @version 0.1
 */
public interface PartType extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(PartTypeDao attrs) throws RemoteException;

  public PartTypeDao getData() throws RemoteException;

  public void insert(PartTypeDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(PartTypeDao attrs) throws RemoteException, UserException;

  public void delete(PartTypeDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(PartTypeDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(PartTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(PartTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(PartTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(PartTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
