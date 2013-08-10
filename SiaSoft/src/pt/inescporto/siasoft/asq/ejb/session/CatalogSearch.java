package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Vector;
import pt.inescporto.template.dao.UserException;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.asq.ejb.dao.StatHits;

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
public interface CatalogSearch extends EJBObject {
  public Vector<StatHits> queryCatalog(String queryStmt) throws RemoteException, UserException;
}
