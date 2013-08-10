package pt.inescporto.siasoft.comun.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;

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
public class ProfileNotes implements java.io.Serializable {
  // applicability
  public static final String MASQ = "A";

  public TplString userID = new TplString("userID", TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString profileID = new TplString("profileID", TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString module = new TplString("module", TmplKeyTypes.PKKEY, true);
  public TplString pkKey = new TplString("primaryKey", TmplKeyTypes.PKKEY, true);
  public TplString note = new TplString("note", 512, TmplKeyTypes.NOKEY, true);

  public ProfileNotes() {
  }
}
