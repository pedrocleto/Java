package pt.inescporto.siasoft.go.auditor.ejb.dao;

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
public class AuditEntityDao implements Serializable {
  public TplString auditEntityID = new TplString(0, "auditEntityID", 20, TmplKeyTypes.PKKEY, true);
  public TplString auditName = new TplString(1, "auditEntityName", 128, TmplKeyTypes.NOKEY, true);

  public AuditEntityDao() {
  }
}
