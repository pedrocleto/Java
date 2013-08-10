package pt.inescporto.template.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import pt.inescporto.template.rmi.beans.LoggedUser;

public interface SessionControl extends EJBObject {
  public LoggedUser logginUser(String sessionType, String user, String pass) throws RemoteException;
}
