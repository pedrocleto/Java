package pt.inescporto.siasoft.go.pga.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.UserException;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.go.pga.ejb.dao.GoalHasLinksDao;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public interface GoalHasLinks extends EJBObject {
  public GoalHasLinksDao getLinks(String goalID, String enterpriseID) throws RemoteException, UserException;
}
