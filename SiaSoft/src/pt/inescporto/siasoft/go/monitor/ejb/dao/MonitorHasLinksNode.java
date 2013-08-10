package pt.inescporto.siasoft.go.monitor.ejb.dao;

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
public class MonitorHasLinksNode implements Serializable {
  private String monitorPlanID = null;
  private String moduleKey = null;
  private String moduleDescription = null;
  private String moduleName = null;
  private String refKey = null;

  public MonitorHasLinksNode() {
  }

  public String getMonitorPlanID() {
    return monitorPlanID;
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

  public void setMonitorPlanID(String monitorPlanID) {
    this.monitorPlanID = monitorPlanID;
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
