package pt.inescporto.siasoft.proc.ejb.dao;

import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
import java.io.Serializable;
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
 * @author jap
 * @version 0.1
 */
public class ActivityConnectionDao implements Serializable {
  public TplString startActivityID = new TplString(0, "startActivityID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplString endActivityID = new TplString(1, "endActivityID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.FKKEY, true);
  public TplInt x1 = new TplInt(2, "x1", TmplKeyTypes.NOKEY, false);
  public TplInt y1 = new TplInt(3, "y1", TmplKeyTypes.NOKEY, false);
  public TplInt x2 = new TplInt(4, "x2", TmplKeyTypes.NOKEY, false);
  public TplInt y2 = new TplInt(5, "y2", TmplKeyTypes.NOKEY, false);
  public TplString type = new TplString(6, "type", 1, TmplKeyTypes.NOKEY, false);

  public ActivityConnectionDao() {
  }
}
