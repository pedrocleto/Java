package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.siasoft.asq.ejb.dao.ThemeDao;
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
public interface Theme extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(ThemeDao attrs) throws RemoteException;

  public ThemeDao getData() throws RemoteException;

  public void insert(ThemeDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(ThemeDao attrs) throws RemoteException, UserException;

  public void delete(ThemeDao attrs) throws RemoteException, UserException, ConstraintViolatedException;

  public void findByPrimaryKey(ThemeDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(ThemeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(ThemeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public Collection find(ThemeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(ThemeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
