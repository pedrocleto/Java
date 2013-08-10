package pt.inescporto.siasoft.asq.client.rmi.forms;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class TreeBuilderNode {
  public static final int SCOPE_TYPE = 1;
  public static final int LEGISLATION_TYPE = 2;
  public static final int MAJOR_THEME_TYPE = 3;
  public static final int MINOR_THEME_TYPE = 4;
  public static final int REGULATORY = 5;
  public static final int LEGAL_REQUIREMENT = 6;

  private String nodeID;
  private String nodeDescription;
  private int nodeType;
  public TreeBuilderNode() {
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
