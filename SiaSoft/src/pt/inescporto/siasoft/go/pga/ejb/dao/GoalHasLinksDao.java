package pt.inescporto.siasoft.go.pga.ejb.dao;

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
public class GoalHasLinksDao implements Serializable {
  public Hashtable<String, Vector> regulatoryList = null;

  public GoalHasLinksDao() {
  }
}
