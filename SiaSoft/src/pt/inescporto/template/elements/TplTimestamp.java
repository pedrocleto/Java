package pt.inescporto.template.elements;

import java.sql.Timestamp;
import java.util.Calendar;

public class TplTimestamp extends TplObject implements java.io.Serializable {
  private Timestamp value;

  public TplTimestamp() {
  }

  public TplTimestamp(String field, int keyType, boolean required) {
    this.field = field;
    this.keyType = keyType;
    this.required = required;
  }

  public TplTimestamp(int index, String field, int keyType, boolean required) {
    this.index = index;
    this.field = field;
    this.keyType = keyType;
    this.required = required;
  }

  public TplTimestamp(Timestamp value) {
    this.field = "";
    this.value = value;
    this.keyType = TmplKeyTypes.NOKEY;
    this.required = false;
  }

  public Timestamp getValue() {
    if (value == null)
      return null;
    else
      return this.value;
  }

  public void setValue(Timestamp value) {
    if (this.value == null)
      this.value = new Timestamp(0);
    this.value = value;
  }

  public Object getValueAsObject() {
    return value;
  }

  public void setValueAsObject(Object value) {
    if (value == null) {
      this.value = null;
    }
    try {
      if (value instanceof Integer) {
        this.value = new Timestamp(((Integer)value).intValue());
        return;
      }
      if (value instanceof Float) {
        this.value = new Timestamp(((Float)value).longValue());
        return;
      }
      if (value instanceof String) {
        this.value = new Timestamp((new java.util.Date((String)value)).getTime());
        return;
      }
      if (value instanceof java.sql.Date) {
        this.value = new Timestamp(((java.sql.Date)value).getTime());
        return;
      }
      if (value instanceof java.util.Date) {
        this.value = new Timestamp(((java.util.Date)value).getTime());
        return;
      }
      if (value instanceof Calendar) {
        this.value = new Timestamp(((Calendar)value).getTimeInMillis());
        return;
      }
      this.value = (Timestamp)value;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void resetValue() {
    this.value = null;
  }
}
