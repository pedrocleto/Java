package pt.inescporto.siasoft.go.aa.ejb.session;

import javax.ejb.EJBObject;
import pt.inescporto.template.dao.UserException;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.go.aa.ejb.dao.EAHasLinksDao;


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
public interface EAHasLinks extends EJBObject {
  public EAHasLinksDao getLinks(String envAspID, String enterpriseID) throws RemoteException, UserException;
}
