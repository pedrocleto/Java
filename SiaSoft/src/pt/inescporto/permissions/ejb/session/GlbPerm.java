package pt.inescporto.permissions.ejb.session;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface GlbPerm extends EJBObject {
  public FormFieldPermission getFormPerms( String roleId, String formId ) throws RemoteException;
}
