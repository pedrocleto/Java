package pt.inescporto.siasoft.comun.ejb.dao;

import java.io.*;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplTimestamp;

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
public class ProfileGroupDao implements Serializable {
  public TplString profileGroupID = new TplString(0, "profileGroupID", 20, TmplKeyTypes.PKKEY, true);
  public TplString profileGroupDesc = new TplString(1, "profileDesc", 100, TmplKeyTypes.NOKEY, true);
  public TplString profileCreator = new TplString(2, "profileCreator", 20, TmplKeyTypes.NOKEY, true);
  public TplTimestamp profileDate = new TplTimestamp(3, "profileDate", TmplKeyTypes.NOKEY, false);
  public TplTimestamp profileRevisonDate = new TplTimestamp(4, "profileRevisonDate", TmplKeyTypes.NOKEY, false);
  public TplString profileObs = new TplString(5, "profileObs", 512, TmplKeyTypes.NOKEY, false);

  public ProfileGroupDao() {
  }
}
