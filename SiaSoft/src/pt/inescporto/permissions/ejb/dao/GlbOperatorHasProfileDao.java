package pt.inescporto.permissions.ejb.dao;

import pt.inescporto.template.elements.*;

public class GlbOperatorHasProfileDao implements java.io.Serializable {
  public TplString operatorId = new TplString(0, "IDOperator", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString profileId = new TplString(1, "profile_id", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString defProf = new TplString(2, "def_prof", 1, TmplKeyTypes.NOKEY, true);

  public GlbOperatorHasProfileDao() {
  }

  public GlbOperatorHasProfileDao(String operatorId, String profileId) {
    this.operatorId.setValue(operatorId);
    this.profileId.setValue(profileId);
  }
}
