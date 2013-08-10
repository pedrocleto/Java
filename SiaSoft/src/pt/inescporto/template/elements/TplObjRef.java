package pt.inescporto.template.elements;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author not attributable
 * @version 1.0
 */

public class TplObjRef extends TplObject implements java.io.Serializable {
  private Object value;

  public TplObjRef() {
  }

  public TplObjRef(String field, int keyType, boolean required) {
    this.field = field;
    this.keyType = keyType;
    this.required = required;
  }

  public TplObjRef(int index, String field, int keyType, boolean required) {
    this.index = index;
    this.field = field;
    this.keyType = keyType;
    this.required = required;
  }

  public TplObjRef(Object value) {
    this.field = "";
    this.value = value;
    this.keyType = TmplKeyTypes.NOKEY;
    this.required = false;
  }

  public Object getValue() {
    return this.value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public Object getValueAsObject() {
    return value;
  }

  public void setValueAsObject(Object value) {
    this.value = value;
  }

  public void resetValue() {
    this.value = null;
  }
}
