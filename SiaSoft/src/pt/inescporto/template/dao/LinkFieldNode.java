package pt.inescporto.template.dao;

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

public class LinkFieldNode implements Serializable {
  private String masterFieldName = null;
  private String detailFieldName = null;

  public LinkFieldNode() {
  }

  public LinkFieldNode(String masterFieldName, String detailFieldName) {
    this.masterFieldName = masterFieldName;
    this.detailFieldName = detailFieldName;
  }

  public String getDetailFieldName() {
    return detailFieldName;
  }

  public String getMasterFieldName() {
    return masterFieldName;
  }

  public void setMasterFieldName(String masterFieldName) {
    this.masterFieldName = masterFieldName;
  }

  public void setDetailFieldName(String detailFieldName) {
    this.detailFieldName = detailFieldName;
  }
}
