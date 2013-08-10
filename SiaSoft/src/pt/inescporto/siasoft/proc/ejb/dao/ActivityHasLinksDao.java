package pt.inescporto.siasoft.proc.ejb.dao;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

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
public class ActivityHasLinksDao implements Serializable {
  public Hashtable<String, Vector> funList = null;
  public Hashtable<String, Vector> eaList = null;
  public Hashtable<String, Vector> epmList = null;
  public Hashtable<String, Vector> monitorList = null;
  public Hashtable<String, Vector> ocEmergList = null;
  public Hashtable<String, Vector> auditList = null;
  public Hashtable<String, Vector> regulatoryList = null;
  public Hashtable<String, Vector> docList = null;
  public Hashtable<String, Vector> formList = null;

  public ActivityHasLinksDao() {
  }
}
