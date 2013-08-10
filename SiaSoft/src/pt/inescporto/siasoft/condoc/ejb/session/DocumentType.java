package pt.inescporto.siasoft.condoc.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentTypeDao;
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
public interface DocumentType extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(DocumentTypeDao attrs) throws RemoteException;

  public DocumentTypeDao getData() throws RemoteException;

  public void insert(DocumentTypeDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(DocumentTypeDao attrs) throws RemoteException, UserException;

  public void delete(DocumentTypeDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(DocumentTypeDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(DocumentTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(DocumentTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(DocumentTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(DocumentTypeDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
