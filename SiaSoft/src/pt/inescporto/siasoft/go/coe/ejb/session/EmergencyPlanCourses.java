package pt.inescporto.siasoft.go.coe.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.ConstraintViolatedException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DupKeyException;
import java.util.Vector;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencyPlanCoursesDao;
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
public interface EmergencyPlanCourses extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(EmergencyPlanCoursesDao attrs) throws RemoteException;

  public EmergencyPlanCoursesDao getData() throws RemoteException;

  public void insert(EmergencyPlanCoursesDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(EmergencyPlanCoursesDao attrs) throws RemoteException, UserException;

  public void delete(EmergencyPlanCoursesDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(EmergencyPlanCoursesDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(EmergencyPlanCoursesDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(EmergencyPlanCoursesDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(EmergencyPlanCoursesDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(EmergencyPlanCoursesDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;
}
