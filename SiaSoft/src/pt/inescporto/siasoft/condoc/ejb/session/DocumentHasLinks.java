package pt.inescporto.siasoft.condoc.ejb.session;

import javax.ejb.EJBObject;

import pt.inescporto.template.dao.UserException;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentHasLinksDao;

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
public interface DocumentHasLinks extends EJBObject {
  public DocumentHasLinksDao getLinks(String documentID, String enterpriseID) throws RemoteException, UserException;
}
