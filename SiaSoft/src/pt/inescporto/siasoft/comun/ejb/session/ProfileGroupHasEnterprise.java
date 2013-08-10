package pt.inescporto.siasoft.comun.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.comun.ejb.dao.ProfileGroupHasEnterpriseDao;
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
public interface ProfileGroupHasEnterprise extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ProfileGroupHasEnterpriseDao attrs) throws RemoteException;

  public ProfileGroupHasEnterpriseDao getData() throws RemoteException;

  public void insert(ProfileGroupHasEnterpriseDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ProfileGroupHasEnterpriseDao attrs) throws RemoteException, UserException;

  public void delete(ProfileGroupHasEnterpriseDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(ProfileGroupHasEnterpriseDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ProfileGroupHasEnterpriseDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ProfileGroupHasEnterpriseDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(ProfileGroupHasEnterpriseDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ProfileGroupHasEnterpriseDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
