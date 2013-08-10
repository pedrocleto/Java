package pt.inescporto.siasoft.proc.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import pt.inescporto.siasoft.proc.ejb.dao.PartDao;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import javax.swing.tree.DefaultMutableTreeNode;
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
public interface Part extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(PartDao attrs) throws RemoteException;

  public PartDao getData() throws RemoteException;

  public void insert(PartDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(PartDao attrs) throws RemoteException, UserException;

  public void delete(PartDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(PartDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(PartDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(PartDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection listAllOrderBy(String orderByStmt) throws RemoteException;

  public Collection find(PartDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(PartDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;

  public DefaultMutableTreeNode getPartTree() throws UserException, RemoteException;
}
