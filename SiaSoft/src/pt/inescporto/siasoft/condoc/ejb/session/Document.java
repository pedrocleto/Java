package pt.inescporto.siasoft.condoc.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentDao;
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
public interface Document extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(DocumentDao attrs) throws RemoteException;

  public DocumentDao getData() throws RemoteException;

  public void insert(DocumentDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(DocumentDao attrs) throws RemoteException, UserException;

  public void delete(DocumentDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(DocumentDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(DocumentDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(DocumentDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(DocumentDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(DocumentDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
