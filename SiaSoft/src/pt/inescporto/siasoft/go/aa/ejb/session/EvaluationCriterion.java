package pt.inescporto.siasoft.go.aa.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.siasoft.go.aa.ejb.dao.EvaluationCriterionDao;
import java.util.Vector;
import java.rmi.RemoteException;
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
public interface EvaluationCriterion extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EvaluationCriterionDao attrs) throws RemoteException;

  public EvaluationCriterionDao getData() throws RemoteException;

  public void insert(EvaluationCriterionDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EvaluationCriterionDao attrs) throws RemoteException, UserException;

  public void delete(EvaluationCriterionDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EvaluationCriterionDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EvaluationCriterionDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EvaluationCriterionDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EvaluationCriterionDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EvaluationCriterionDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
