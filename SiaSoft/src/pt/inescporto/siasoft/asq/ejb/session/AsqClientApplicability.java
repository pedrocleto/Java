package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.siasoft.asq.ejb.dao.AsqClientApplicabilityDao;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.siasoft.asq.ejb.dao.AsqApplyRegulatoryDao;
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
public interface AsqClientApplicability extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(AsqClientApplicabilityDao attrs) throws RemoteException;

  public AsqClientApplicabilityDao getData() throws RemoteException;

  public void insert(AsqClientApplicabilityDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(AsqClientApplicabilityDao attrs) throws RemoteException, UserException;

  public void delete(AsqClientApplicabilityDao attrs) throws ConstraintViolatedException, RemoteException, UserException;

  public void findByPrimaryKey(AsqClientApplicabilityDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(AsqClientApplicabilityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(AsqClientApplicabilityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public Collection find(AsqClientApplicabilityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(AsqClientApplicabilityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Vector<AsqClientApplicabilityDao> findExactWithNulls(AsqClientApplicabilityDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void applyRegulatoryList(Vector<AsqApplyRegulatoryDao> appRegList) throws RemoteException, UserException;
}
