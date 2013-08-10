package pt.inescporto.siasoft.asq.ejb.dao;

import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
import java.io.Serializable;

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
public class SourceDao implements Serializable {
  public TplString sourceId = new TplString(0, "sourceId", 20, TmplKeyTypes.PKKEY, true);
  public TplString sourceDescription = new TplString(1, "sourceDescription", 100, TmplKeyTypes.NOKEY, true);

  public SourceDao() {
  }
}
