package pt.inescporto.siasoft.condoc.ejb.dao;

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
public class DocumentTypeDao implements Serializable {
  public TplString documentTypeID = new TplString(0, "documentTypeID", 20, TmplKeyTypes.PKKEY, true);
  public TplString documentTypeDescription = new TplString(1, "documentTypeDescription", 128, TmplKeyTypes.NOKEY, true);
  public TplString fkDocumentTypeFatherID = new TplString(2, "fkDocumentTypeID", 20, TmplKeyTypes.FKKEY, false);
  public TplString fkEnterpriseID = new TplString(3, "fkEnterpriseID", 20, TmplKeyTypes.FKKEY, true);

  public DocumentTypeDao() {
  }
}
