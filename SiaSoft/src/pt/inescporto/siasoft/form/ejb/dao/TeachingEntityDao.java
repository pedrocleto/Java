package pt.inescporto.siasoft.form.ejb.dao;

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
 * @author not attributable
 * @version 0.1
 */
public class TeachingEntityDao implements Serializable {
  public TplString teachingEntityID = new TplString(0, "teachingEntityID", 20, TmplKeyTypes.PKKEY, true);
  public TplString teachingEntityName = new TplString(1, "teachingEntityName", 128, TmplKeyTypes.NOKEY, true);

  public TeachingEntityDao() {
  }
}
