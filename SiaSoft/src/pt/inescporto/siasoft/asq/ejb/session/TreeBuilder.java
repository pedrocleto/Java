package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.EJBObject;
import java.util.Vector;
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
 * @author jap
 * @version 0.1
 */
public interface TreeBuilder extends EJBObject {
  public Vector getRegulatoryByTheme(int order) throws RemoteException;

  public Vector getRegulatoryList(String scope, String legislation, String majorTheme, String minorTheme, int order) throws RemoteException;

  public Vector getRegulatoryByThemeApplicable(String enterpriseID) throws RemoteException;

  public Vector getRegulatoryListApplicable(String enterpriseID, String userID, String profileID, String scope, String legislation, String majorTheme, String minorTheme) throws RemoteException;

  public Vector getRegulatoryByThemeApplicable(String enterpriseID, String userID, String profileID) throws RemoteException;
}
