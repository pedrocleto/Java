package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.siasoft.asq.ejb.dao.LegislationDao;
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
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public interface Legislation extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(LegislationDao attrs) throws RemoteException;

  public LegislationDao getData() throws RemoteException;

  public void insert(LegislationDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(LegislationDao attrs) throws RemoteException, UserException;

  public void delete(LegislationDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(LegislationDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(LegislationDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(LegislationDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(LegislationDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(LegislationDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
