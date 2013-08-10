package pt.inescporto.siasoft.proc.ejb.session;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

import pt.inescporto.template.dao.UserException;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityHasLinksDao;

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
public interface ActivityHasLinks extends EJBObject {
  public ActivityHasLinksDao getLinks(String activityFatherID, String enterpriseID) throws RemoteException, UserException;
}
