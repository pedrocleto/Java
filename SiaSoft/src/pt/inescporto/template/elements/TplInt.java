package pt.inescporto.template.elements;

public class TplInt extends TplObject implements java.io.Serializable {
  private Integer value;

  public TplInt() {
  }

  public TplInt(String field, int keyType, boolean required) {
    this.field = field;
    this.keyType = keyType;
    this.required = required;
  }

  public TplInt(int index, String field, int keyType, boolean required) {
    this.index = index;
    this.field = field;
    this.keyType = keyType;
    this.required = required;
  }

  public TplInt(int value) {
    this.field = "";
    this.value = new Integer(value);
    this.keyType = TmplKeyTypes.NOKEY;
    this.required = false;
  }

  public Integer getValue() {
    if (this.value == null)
      return null;
    else
      return this.value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public void resetValue() {
    this.value = null;
  }

  public Object getValueAsObject() {
    return value;
  }

  public void setValueAsObject(Object value) {
    if (value == null) {
      this.value = null;
    }
    try {
      if (value instanceof Long) {
        this.value = new Integer(((Long)value).intValue());
        return;
      }
      if (value instanceof Float) {
        this.value = new Integer(((Float)value).intValue());
        return;
      }
      if (value instanceof Boolean) {
        this.value = new Integer(((Boolean)value).toString());
        return;
      }
      if (value instanceof String) {
        this.value = new Integer((String)value);
        return;
      }
      this.value = (Integer)value;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
