package pt.inescporto.siasoft.tools;

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
public class TableConstraint implements Serializable {
  private String pkTableName = null;
  private String constraintName = null;
  private Vector<KeyReference> keyRefs = new Vector<KeyReference>();

  public TableConstraint() {
  }

  public TableConstraint(String pkTableName, String constraintName) {
    this.pkTableName = pkTableName;
    this.constraintName = constraintName;
  }

  public String getConstraintName() {
    return constraintName;
  }

  public String getPkTableName() {
    return pkTableName;
  }

  public void setPkTableName(String pkTableName) {
    this.pkTableName = pkTableName;
  }

  public void setConstraintName(String constraintName) {
    this.constraintName = constraintName;
  }

  public void addKeyReference(KeyReference keyRef) {
    keyRefs.add(keyRef);
  }

  public Iterator<KeyReference> getKeyReferencesList() {
    return keyRefs.iterator();
  }

  public String toString() {
    return constraintName + " [" + pkTableName + "]";
  }
}
