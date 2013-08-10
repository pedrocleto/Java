package pt.inescporto.siasoft.comun.client;

import pt.inescporto.siasoft.comun.client.rmi.helper.ApplicableProfileData;

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
public class UserData {
  private static UserData instance;

  // User data
  private ApplicableProfileData apd = null;

  public static UserData getInstance() {
    if (instance == null)
      instance = new UserData();
    return instance;
  }

  private UserData() {
    apd = new ApplicableProfileData();
  }

  public ApplicableProfileData getApplicableProfileData() {
    return apd;
  }
}
