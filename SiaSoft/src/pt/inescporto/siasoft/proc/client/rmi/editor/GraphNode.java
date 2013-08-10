package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.io.*;

import javax.swing.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Rute
 * @version 1.0
 */

public class GraphNode implements Serializable{
  private String id = null;
  private String name = null;
  private String idSequence = null;
  private String type = null;
  private ImageIcon icon = null;
  private String elementType = null;
  private String linkActivityID = null;
  private boolean neadsParent;
  private boolean noChildren;
  private boolean hasProperties;
  private boolean allLevels;


  public GraphNode() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public GraphNode(String name) {
    this.name = name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setIdSequence(String idSequence) {
    this.idSequence = idSequence;
  }

  public void setIcon(ImageIcon icon) {
    this.icon = icon;
  }

  public void setElementType(String elementType) {
    this.elementType = elementType;
  }

  public void setNeadsParent(boolean neadsParent) {
    this.neadsParent = neadsParent;
  }

  public void setNoChildren(boolean noChildren) {
    this.noChildren = noChildren;
  }

  public void setHasProperties(boolean hasProperties) {
    this.hasProperties = hasProperties;
  }

  public void setAllLevels(boolean allLevels) {
    this.allLevels = allLevels;
  }

  public void setLinkActivityID(String linkActivityID) {
    this.linkActivityID = linkActivityID;
  }

  public String getType() {
    return type;
  }

  public String getId() {
    return id;
  }

  public String getIdSequence() {
    return idSequence;
  }

  public ImageIcon getIcon() {
    return icon;
  }

  public String getElementType() {
    return elementType;
  }

  public boolean isNeadsParent() {
    return neadsParent;
  }

  public boolean isNoChildren() {
    return noChildren;
  }

  public boolean isHasProperties() {
    return hasProperties;
  }

  public boolean isAllLevels() {
    return allLevels;
  }

  public String getLinkActivityID() {
    return linkActivityID;
  }

  public String toString() {
    return name;
  }

  private void jbInit() throws Exception {
  }
}
