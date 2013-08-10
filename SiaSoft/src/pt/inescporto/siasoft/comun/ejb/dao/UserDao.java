package pt.inescporto.siasoft.comun.ejb.dao;

import pt.inescporto.template.elements.*;

public class UserDao implements java.io.Serializable {
  public TplString userID = new TplString(0, "userID", 20, TmplKeyTypes.PKKEY, true);
  public TplString userName = new TplString(1, "userName", 100, TmplKeyTypes.NOKEY, true);
  public TplString passwd = new TplString(2, "passwd", 100, TmplKeyTypes.NOKEY, true);
  public TplString eMail = new TplString(3, "eMail", 100, TmplKeyTypes.NOKEY, false);
  public TplString enterprise = new TplString(4, "enterpriseID", 20, TmplKeyTypes.FKKEY, true);

  public UserDao() {
  }

  public UserDao(String userId) {
    userID.setValue(userId);
  }
}
