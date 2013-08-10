package pt.inescporto.siasoft.condoc.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentUserRevisorsDao;
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
public interface DocumentUserRevisors extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(DocumentUserRevisorsDao attrs) throws RemoteException;

  public DocumentUserRevisorsDao getData() throws RemoteException;

  public void insert(DocumentUserRevisorsDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(DocumentUserRevisorsDao attrs) throws RemoteException, UserException;

  public void delete(DocumentUserRevisorsDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(DocumentUserRevisorsDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(DocumentUserRevisorsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(DocumentUserRevisorsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(DocumentUserRevisorsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(DocumentUserRevisorsDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
