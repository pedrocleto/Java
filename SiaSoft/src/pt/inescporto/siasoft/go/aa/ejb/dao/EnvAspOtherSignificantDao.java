package pt.inescporto.siasoft.go.aa.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import java.io.Serializable;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class EnvAspOtherSignificantDao implements Serializable {
  public TplString envAspectID = new TplString(0, "envAspID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY, true);
  public TplString envtype = new TplString(1, "type", 1, TmplKeyTypes.NOKEY, true);
  public TplString significance = new TplString(2, "significance", 10, TmplKeyTypes.NOKEY, true);
  public TplString regID = new TplString(3, "regID", 20, TmplKeyTypes.NOKEY, true);


  public EnvAspOtherSignificantDao() {
  }
}
