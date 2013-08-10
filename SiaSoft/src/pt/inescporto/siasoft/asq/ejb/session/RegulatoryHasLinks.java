package pt.inescporto.siasoft.asq.ejb.session;

import java.util.Vector;
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import pt.inescporto.template.dao.UserException;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryHasLinksNode;

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
public interface RegulatoryHasLinks extends EJBObject {
  public Vector<RegulatoryHasLinksNode> getLinks(String envAspID, String enterpriseID) throws RemoteException, UserException;
}
