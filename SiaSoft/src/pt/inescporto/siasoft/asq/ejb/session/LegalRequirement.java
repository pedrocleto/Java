package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.asq.ejb.dao.LegalRequirementDao;
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
public interface LegalRequirement extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(LegalRequirementDao attrs) throws RemoteException;

  public LegalRequirementDao getData() throws RemoteException;

  public void insert(LegalRequirementDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(LegalRequirementDao attrs) throws RemoteException, UserException;

  public void delete(LegalRequirementDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(LegalRequirementDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(LegalRequirementDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(LegalRequirementDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(LegalRequirementDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(LegalRequirementDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
