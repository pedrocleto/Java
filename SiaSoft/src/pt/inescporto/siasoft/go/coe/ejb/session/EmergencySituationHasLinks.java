package pt.inescporto.siasoft.go.coe.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.UserException;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationHasLinksDao;

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
public interface EmergencySituationHasLinks extends EJBObject {
  public EmergencySituationHasLinksDao getLinks(String emergSitID, String enterpriseID) throws RemoteException, UserException;
}
