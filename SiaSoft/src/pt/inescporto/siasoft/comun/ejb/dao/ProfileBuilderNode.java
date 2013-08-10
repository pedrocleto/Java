package pt.inescporto.siasoft.comun.ejb.dao;

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
public class ProfileBuilderNode {
  public static final int PROFILE_GROUP = 1;
  public static final int ENTERPRISE = 2;
  public static final int USER = 3;
  public static final int PROFILE = 4;

  public String nodeID;
  public String nodeDescription;
  public int nodeType;

  public ProfileBuilderNode() {
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public void setNodeDescription(String nodeDescription) {
    this.nodeDescription = nodeDescription;
  }

  public void setNodeType(int nodeType) {
    this.nodeType = nodeType;
  }

  public String getNodeID() {
    return nodeID;
  }

  public String getNodeDescription() {
    return nodeDescription;
  }

  public int getNodeType() {
    return nodeType;
  }

  public String toString() {
    return getNodeDescription();
  }
}
