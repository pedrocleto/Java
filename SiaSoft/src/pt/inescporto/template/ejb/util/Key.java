package pt.inescporto.template.ejb.util;

import javax.ejb.*;
import java.rmi.RemoteException;
import pt.inescporto.template.dao.UserException;

public interface Key extends EJBObject {
  public String GenerateKey( String keyTable, String keyColumn ) throws RemoteException, UserException;
}
