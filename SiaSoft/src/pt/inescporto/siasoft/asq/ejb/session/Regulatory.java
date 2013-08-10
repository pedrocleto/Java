package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Date;

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
public interface Regulatory extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(RegulatoryDao attrs) throws RemoteException;

  public RegulatoryDao getData() throws RemoteException;

  public void insert(RegulatoryDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(RegulatoryDao attrs) throws RemoteException, UserException;

  public void delete(RegulatoryDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(RegulatoryDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(RegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(RegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException;

  public Collection find(RegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(RegulatoryDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;

  public Collection findByFullCriterion(String name, String resume, Date dateInit, Date dateEnd,
                                        String scopeID, String sourceID, String majorThemeID,
                                        String minorThemeID, boolean state, boolean revocate,
                                        String enterpriseID, String userID, String profileID,
                                        boolean hasLR) throws RemoteException, FinderException, UserException, RowNotFoundException;
}
