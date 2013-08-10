package pt.inescporto.siasoft.proc.ejb.dao;

import java.io.*;

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
public class ActHasLinksNode implements Serializable {
  private String activityID = null;
  private String moduleKey = null;
  private String moduleDescription = null;
  private String moduleName = null;
  private String refKey = null;

  public ActHasLinksNode() {
  }

  public String getActivityID() {
    return activityID;
  }

  public String getModuleDescription() {
    return moduleDescription;
  }

  public String getModuleKey() {
    return moduleKey;
  }

  public String getModuleName() {
    return moduleName;
  }

  public String getRefKey() {
    return refKey;
  }

  public void setActivityID(String activityID) {
    this.activityID = activityID;
  }

  public void setModuleDescription(String moduleDescription) {
    this.moduleDescription = moduleDescription;
  }

  public void setModuleKey(String moduleKey) {
    this.moduleKey = moduleKey;
  }

  public void setModuleName(String regID) {
    this.moduleName = regID;
  }

  public void setRefKey(String refKey) {
    this.refKey = refKey;
  }
}
