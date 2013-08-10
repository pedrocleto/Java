package pt.inescporto.siasoft.go.monitor.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityHasLinksDao;
import pt.inescporto.template.dao.UserException;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorHasLinksDao;

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
public interface MonitorHasLinks extends EJBObject {
  public MonitorHasLinksDao getLinks(String monitorPlanID, String enterpriseID) throws RemoteException, UserException;
}

