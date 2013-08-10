package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryHasClassDao;
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
public interface RegulatoryHasClass extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(RegulatoryHasClassDao attrs) throws RemoteException;

  public RegulatoryHasClassDao getData() throws RemoteException;

  public void insert(RegulatoryHasClassDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(RegulatoryHasClassDao attrs) throws RemoteException, UserException;

  public void delete(RegulatoryHasClassDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(RegulatoryHasClassDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(RegulatoryHasClassDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(RegulatoryHasClassDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public Collection find(RegulatoryHasClassDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(RegulatoryHasClassDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
