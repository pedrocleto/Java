package pt.inescporto.siasoft.go.pga.ejb.session;

import javax.ejb.EJBObject;
import java.util.Collection;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.UserException;
import java.sql.Timestamp;
import pt.inescporto.siasoft.go.pga.ejb.dao.ObjActionsDao;

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
public interface ObjActions extends EJBObject {
  public Collection<ObjActionsDao> listAll(String enterpriseID, String goalID, String objectiveID, Timestamp planStartDate, Timestamp planEndDate, String userID, String state) throws RemoteException, UserException;
}
