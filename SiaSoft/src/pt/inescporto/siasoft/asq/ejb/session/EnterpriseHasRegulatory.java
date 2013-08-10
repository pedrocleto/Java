package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.asq.ejb.dao.EnterpriseHasRegulatoryDao;
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
public interface EnterpriseHasRegulatory extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EnterpriseHasRegulatoryDao attrs) throws RemoteException;

  public EnterpriseHasRegulatoryDao getData() throws RemoteException;

  public void insert(EnterpriseHasRegulatoryDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EnterpriseHasRegulatoryDao attrs) throws RemoteException, UserException;

  public void delete(EnterpriseHasRegulatoryDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EnterpriseHasRegulatoryDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EnterpriseHasRegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EnterpriseHasRegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public Collection find(EnterpriseHasRegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EnterpriseHasRegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
