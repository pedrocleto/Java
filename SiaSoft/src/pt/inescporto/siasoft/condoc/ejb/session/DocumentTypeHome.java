package pt.inescporto.siasoft.condoc.ejb.session;

import javax.ejb.EJBHome;
import java.util.Vector;
import pt.inescporto.siasoft.condoc.ejb.session.DocumentType;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

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
public interface DocumentTypeHome extends EJBHome {
  DocumentType create() throws CreateException, RemoteException;

  DocumentType create(String linkCondition, Vector binds) throws CreateException, RemoteException;
}
