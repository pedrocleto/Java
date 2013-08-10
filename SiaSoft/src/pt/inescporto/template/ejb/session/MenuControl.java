package pt.inescporto.template.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Vector;

import pt.inescporto.template.utils.beans.FormData;

public interface MenuControl extends EJBObject {
  // construction of menus
  public Vector getRootNodes( String menuConfigId ) throws RemoteException;
  public Vector getChildrenForNode( String menuConfigId, String menuId ) throws RemoteException;
  // menu handle
  public FormData getFormData( String menuConfigId, String menuId )throws RemoteException;
  // menu shortcuts
  public Vector getShortcutsData(String menuConfigId) throws RemoteException;
  public Vector getShortcutsForUserData(String menuConfigId, String userId) throws RemoteException;
  public void saveShortcutForUser( String menuConfigId, String usrId, String menuId ) throws RemoteException;
  public int removeShortcutForUser(String menuConfigId, String usrId, String menuId) throws RemoteException;
}
