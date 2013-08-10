package pt.inescporto.siasoft.go.aa.ejb.dao;

import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
import java.io.Serializable;
import pt.inescporto.template.elements.TplObjRef;

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
public class EnvAspClassDao implements Serializable {
  public TplString envAspClassID = new TplString(0, "envAspClassID", 20, TmplKeyTypes.PKKEY, true);
  public TplString envAspClassDescription = new TplString(1, "envAspClassDescription", 100, TmplKeyTypes.NOKEY, true);
  public TplObjRef palletGraph = new TplObjRef(2, "palletIcon", TmplKeyTypes.NOKEY, true);
  public TplObjRef graphGraph = new TplObjRef(3, "graphIcon", TmplKeyTypes.NOKEY, true);

  public EnvAspClassDao() {
  }
}
