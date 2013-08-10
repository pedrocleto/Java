package pt.inescporto.template.client.util;

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
 * @author jap
 * @version 0.1
 */
public class DSRelation {
  private String name = null;
  private DataSource master = null;
  private DataSource detail = null;
  Vector<RelationKey> relationKeys = null;

  public DSRelation(String name) {
    this.name = name;
    relationKeys = new Vector<RelationKey>();
  }

  public DataSource getDetail() {
    return detail;
  }

  public DataSource getMaster() {
    return master;
  }

  public String getName() {
    return name;
  }

  public void setMaster(DataSource master) {
    this.master = master;
  }

  public void setDetail(DataSource detail) {
    this.detail = detail;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addKey(RelationKey relationKey) {
    relationKeys.add(relationKey);
  }

  public ListIterator<RelationKey> getKeys() {
    return relationKeys.listIterator();
  }

/*  public String toString() {
    StringBuffer buffer = new StringBuffer();

    buffer.append("Relation " + name + " as master " + master.getName() + " and detail " + detail.getName() + " with the following key pairs:\r\n");
    for (RelationKey e:relationKeys)
      buffer.append(e);

    return buffer.toString();
  }*/
}
