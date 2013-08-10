package pt.inescporto.siasoft.proc.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.proc.ejb.dao.PartClassDao;
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
public interface PartClass extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(PartClassDao attrs) throws RemoteException;

  public PartClassDao getData() throws RemoteException;

  public void insert(PartClassDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(PartClassDao attrs) throws RemoteException, UserException;

  public void delete(PartClassDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(PartClassDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(PartClassDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(PartClassDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(PartClassDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(PartClassDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
