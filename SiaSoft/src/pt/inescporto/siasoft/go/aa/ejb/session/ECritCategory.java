package pt.inescporto.siasoft.go.aa.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.go.aa.ejb.dao.ECritCategoryDao;
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
public interface ECritCategory extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ECritCategoryDao attrs) throws RemoteException;

  public ECritCategoryDao getData() throws RemoteException;

  public void insert(ECritCategoryDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ECritCategoryDao attrs) throws RemoteException, UserException;

  public void delete(ECritCategoryDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(ECritCategoryDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ECritCategoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ECritCategoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(ECritCategoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ECritCategoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
