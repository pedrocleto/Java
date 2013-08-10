package pt.inescporto.template.dao;

import java.io.*;
import java.util.*;

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

public class LinkTableNode implements Serializable {
  private String linkTableName = null;
  private Vector<LinkFieldNode> linkFieldList = null;
  private String joinSyntax = null;
  private String afterJoinSyntax = null;

  public LinkTableNode() {
  }

  public LinkTableNode(String linkTableName, Vector<LinkFieldNode> linkFieldList) {
    this.linkTableName = linkTableName;
    this.linkFieldList = linkFieldList;
  }

  public String getLinkTableName() {
    return linkTableName;
  }

  public Vector<LinkFieldNode> getLinkFieldList() {
    return linkFieldList;
  }

  public String getAfterJoinSyntax() {
    return afterJoinSyntax;
  }

  public String getJoinSyntax() {
    return joinSyntax;
  }

  public void setLinkTableName(String linkTableName) {
    this.linkTableName = linkTableName;
  }

  public void setLinkFieldList(Vector<LinkFieldNode> linkFieldList) {
    this.linkFieldList = linkFieldList;
  }

  public void setJoinSyntax(String joinSyntax) {
    this.joinSyntax = joinSyntax;
  }

  public void setAfterJoinSyntax(String afterJoinSyntax) {
    this.afterJoinSyntax = afterJoinSyntax;
  }
}
