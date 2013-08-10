package pt.inescporto.siasoft.asq.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class ScopeDao implements java.io.Serializable {
  public TplString scopeId = new TplString(0, "scopeId", 20, TmplKeyTypes.PKKEY, true);
  public TplString scopeDescription = new TplString(1, "scopeDescription", 200, TmplKeyTypes.NOKEY, true);

  public ScopeDao() {
  }
}
