package pt.inescporto.siasoft.go.coe.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationDao;
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
public interface EmergencySituation extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EmergencySituationDao attrs) throws RemoteException;

  public EmergencySituationDao getData() throws RemoteException;

  public void insert(EmergencySituationDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EmergencySituationDao attrs) throws RemoteException, UserException;

  public void delete(EmergencySituationDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EmergencySituationDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EmergencySituationDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EmergencySituationDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EmergencySituationDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EmergencySituationDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
