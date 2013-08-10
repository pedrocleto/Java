package pt.inescporto.template.client.util;

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
public class RelationKey {
  String masterKey = null;
  String detailKey = null;

  public RelationKey() {
  }

  public RelationKey(String masterKey, String detailKey) {
    this.masterKey = masterKey;
    this.detailKey = detailKey;
  }

  public String getDatailKey() {
    return detailKey;
  }

  public String getMasterKey() {
    return masterKey;
  }

  public void setMasterKey(String masterKey) {
    this.masterKey = masterKey;
  }

  public void setDatailKey(String datailKey) {
    this.detailKey = datailKey;
  }

  public String toString() {
    return "Key pairing is (" + masterKey + ", " + detailKey + ")";
  }
}
