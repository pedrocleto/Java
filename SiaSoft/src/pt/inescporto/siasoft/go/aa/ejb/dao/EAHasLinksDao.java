package pt.inescporto.siasoft.go.aa.ejb.dao;

import java.util.Vector;
import java.io.Serializable;
import java.util.Hashtable;

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
public class EAHasLinksDao implements Serializable {
  public Vector<EAHasLinksNode> coeList = null;
  public Vector<EAHasLinksNode> monitorList = null;
  public Vector<EAHasLinksNode> auditList = null;
  public Vector<EAHasLinksNode> pgaList = null;

  public EAHasLinksDao() {
  }
}
