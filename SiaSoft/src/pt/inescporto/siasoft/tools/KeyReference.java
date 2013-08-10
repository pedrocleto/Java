package pt.inescporto.siasoft.tools;

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
public class KeyReference implements Serializable {
  private String pkField = null;
  private String fkField = null;
  private Integer keySequence = null;

  public KeyReference() {
  }

  public KeyReference(String pkField, String fkField, int keyReference) {
    this.pkField = pkField;
    this.fkField = fkField;
    this.keySequence = new Integer(keyReference);
  }

  public String getFkField() {
    return fkField;
  }

  public Integer getKeySequence() {
    return keySequence;
  }

  public String getPkField() {
    return pkField;
  }

  public void setPkField(String pkField) {
    this.pkField = pkField;
  }

  public void setKeySequence(Integer keySequence) {
    this.keySequence = keySequence;
  }

  public void setFkField(String fkField) {
    this.fkField = fkField;
  }

  public String toString() {
    return pkField + " [" + fkField + ", " + keySequence + "]";
  }

  public boolean equals(Object o) {
    boolean match = false;

    if (o.getClass().getName().equals(getClass().getName())) {
      KeyReference myObj = (KeyReference)o;

      if (myObj.pkField != null && pkField != null && myObj.pkField.equals(pkField) &&
          myObj.fkField != null && fkField != null && myObj.fkField.equals(fkField) &&
          myObj.keySequence != null && keySequence != null && myObj.getKeySequence().equals(keySequence))
        match = true;
    }

    return match;
  }
}
