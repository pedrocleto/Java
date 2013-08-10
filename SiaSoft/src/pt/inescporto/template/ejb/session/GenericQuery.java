package pt.inescporto.template.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Vector;

import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;

public interface GenericQuery extends EJBObject {
  public Vector doQuery( String queryStmt, Vector binds ) throws RemoteException;
  public int doExecuteQuery( String queryStmt, Vector binds ) throws RemoteException;
}
