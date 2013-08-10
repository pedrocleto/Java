package pt.inescporto.siasoft.comun.ejb.dao;

import java.io.Serializable;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplObjRef;
import pt.inescporto.template.elements.TplBoolean;
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
public class WarningMessageDao implements Serializable {
  public static final String docModuleHistory = "DOC_HISTORY";
  public static final String regModuleRevogated = "REG_REVOGATED";

  public TplString messageSequence = new TplString(0, "messageSequence", 30, TmplKeyTypes.PKKEY | TmplKeyTypes.GENKEY, true);
  public TplString message = new TplString(1, "message", 128, TmplKeyTypes.NOKEY, true);
  public TplString fkUserID = new TplString(2, "fkUserID", 20, TmplKeyTypes.FKKEY, true);
  public TplString moduleID = new TplString(3, "moduleID", 20, TmplKeyTypes.FKKEY, false);
  public TplObjRef keyRef = new TplObjRef(4, "keyRef", TmplKeyTypes.NOKEY, false);
  public TplTimestamp dateRef = new TplTimestamp(4, "dateRef", TmplKeyTypes.NOKEY, false);
  public TplBoolean accepted = new TplBoolean(6, "accepted", TmplKeyTypes.NOKEY, false);

  public WarningMessageDao() {
  }
}
