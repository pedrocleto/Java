package pt.inescporto.siasoft.comun.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplInt;

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
public class UserProfileDao implements java.io.Serializable {
  // applicability
  public static final int DIRECT_APP   = 1 << 0;
  public static final int INDIRECT_APP = 1 << 1;
  public static final int INFORM_APP   = 1 << 2;

  // permissions
  public static final int PERM_READ    = 1 << 0;
  public static final int PERM_COPY    = 1 << 1;

  public TplString userID = new TplString(0, "userID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString profileID = new TplString(1, "profileID", 20, TmplKeyTypes.PKKEY, true);
  public TplString profileDesc = new TplString(2, "profileDesc", 100, TmplKeyTypes.NOKEY, true);
  public TplInt permissions = new TplInt(3, "permissions", TmplKeyTypes.NOKEY, true);

  public UserProfileDao() {
  }

  public String toString() {
    return profileDesc.getValue();
  }
}
