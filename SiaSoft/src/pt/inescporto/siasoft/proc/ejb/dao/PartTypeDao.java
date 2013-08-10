package pt.inescporto.siasoft.proc.ejb.dao;

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
public class PartTypeDao implements Serializable {
  public TplString partTypeID = new TplString(0, "partTypeID", 20, TmplKeyTypes.PKKEY, true);
  public TplString partTypeDescription = new TplString(1, "partTypeDescription", 100, TmplKeyTypes.NOKEY, true);

  public PartTypeDao() {
  }
}
