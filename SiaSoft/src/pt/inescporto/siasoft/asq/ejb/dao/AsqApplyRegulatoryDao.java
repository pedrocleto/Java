package pt.inescporto.siasoft.asq.ejb.dao;

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
public class AsqApplyRegulatoryDao implements Serializable {
  public String profileGroup = null;
  public String enterprise = null;
  public String user = null;
  public String profileUser = null;
  public String regulatory = null;
  public String legalRequirement = null;
  public Integer app = null;
  public Integer state = null;
  public String obs = null;

  public AsqApplyRegulatoryDao() {
  }
}
