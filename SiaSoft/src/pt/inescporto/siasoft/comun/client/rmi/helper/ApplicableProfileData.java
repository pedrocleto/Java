package pt.inescporto.siasoft.comun.client.rmi.helper;

import pt.inescporto.siasoft.comun.ejb.dao.UserProfileDao;

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
public class ApplicableProfileData {
  private UserProfileDao profile = null;
  private String enterpriseForUser = null;

  public ApplicableProfileData() {
  }

  public UserProfileDao getProfile() {
    return profile;
  }

  public String getEnterpriseForUser(){
    return enterpriseForUser;
  }

  public void setProfile(String enterpriseForUser, UserProfileDao profile) {
    this.enterpriseForUser = enterpriseForUser;
    this.profile = profile;
  }
}
