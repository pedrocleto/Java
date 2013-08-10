package pt.inescporto.siasoft.condoc.ejb.dao;

import java.io.Serializable;

import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TplTimestamp;

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
public class DocumentDao implements Serializable {
  public TplString documentID = new TplString(0, "documentID", 20, TmplKeyTypes.PKKEY, true);
  public TplString documentDescription = new TplString(1, "documentDescription", 128, TmplKeyTypes.NOKEY, true);
  public TplString fkDocumentTypeID = new TplString(2, "fkDocumentTypeID", 20, TmplKeyTypes.FKKEY, true);
  public TplString fkUserID = new TplString(3, "fkUserID", 20, TmplKeyTypes.FKKEY, true);
  public TplString documentState = new TplString(4, "documentState", 1, TmplKeyTypes.NOKEY, true);
  public TplTimestamp approvalDate = new TplTimestamp(5, "approvalDate", TmplKeyTypes.NOKEY, false);
  public TplString fkRevisorGroup = new TplString(6, "fkRevisorGroup", 20, TmplKeyTypes.FKKEY, false);
  public TplString fkApprovalGroup = new TplString(7, "fkApprovalGroup", 20, TmplKeyTypes.FKKEY, false);
  public TplString fkApprovalUserID = new TplString(8, "fkApprovalUserID", 20, TmplKeyTypes.FKKEY, false);
  public TplString fkEnterpriseID = new TplString(9, "fkEnterpriseID", 20, TmplKeyTypes.FKKEY, true);
  public TplString documentURL = new TplString(10, "documentURL", 200, TmplKeyTypes.NOKEY, false);

  public DocumentDao() {
  }
}
