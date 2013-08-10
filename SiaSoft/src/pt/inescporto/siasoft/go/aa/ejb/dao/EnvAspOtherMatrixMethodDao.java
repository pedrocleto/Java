package pt.inescporto.siasoft.go.aa.ejb.dao;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplInt;
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
public class EnvAspOtherMatrixMethodDao implements Serializable {
  public TplString envAspectID = new TplString(0, "envAspID", 20, TmplKeyTypes.PKKEY | TmplKeyTypes.LNKKEY | TmplKeyTypes.FKKEY, true);
  public TplString envSignificance = new TplString(1, "significance",10, TmplKeyTypes.NOKEY, true);
  public TplString envSignificanceDesc = new TplString(2, "significanceDescription",20, TmplKeyTypes.NOKEY, true);
  public TplString envCat1ID = new TplString(3, "cat1ID",20, TmplKeyTypes.NOKEY, true);
  public TplString envCat1 = new TplString(4, "cat1",20, TmplKeyTypes.NOKEY, true);
  public TplString envCat2ID = new TplString(5, "cat2ID",20, TmplKeyTypes.NOKEY, true);
  public TplString envCat2 = new TplString(6, "cat2",20, TmplKeyTypes.NOKEY, true);
  public TplString envCat3ID = new TplString(7, "cat3ID",20, TmplKeyTypes.NOKEY, false);
  public TplString envCat3 = new TplString(8, "cat3",20, TmplKeyTypes.NOKEY, false);
  public TplString envCat4ID = new TplString(9, "cat4ID",20, TmplKeyTypes.NOKEY, false);
  public TplString envCat4 = new TplString(10, "cat4",20, TmplKeyTypes.NOKEY, false);

  public EnvAspOtherMatrixMethodDao() {
  }
}
