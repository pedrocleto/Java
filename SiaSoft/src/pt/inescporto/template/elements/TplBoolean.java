package pt.inescporto.template.elements;

public class TplBoolean extends TplObject implements java.io.Serializable {
  private Boolean value;

  public TplBoolean() {
    super();
  }

  public TplBoolean(boolean value) {
    this(null, TmplKeyTypes.NOKEY, false);
    this.value = new Boolean(value);
  }

  public TplBoolean(String field, int keyType, boolean required) {
    this(0, field, keyType, required);
  }

  public TplBoolean(int index, String field, int keyType, boolean required) {
    super();
    this.index = index;
    this.field = field;
    this.keyType = keyType;
    this.required = required;
  }

  public Boolean getValue() {
    if (this.value == null)
      return null;
    else
      return this.value;
  }

  public void setValue(Boolean value) {
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
	this.value = new Boolean(((Integer)value).toString());
        return;
      }
      if (value instanceof Long) {
	this.value = new Boolean(((Long)value).toString());
        return;
      }
      if (value instanceof Boolean) {
	this.value = new Boolean(((Boolean)value).booleanValue());
        return;
      }
      if (value instanceof String) {
	this.value = new Boolean((String)value);
        return;
      }
      this.value = (Boolean)value;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void resetValue() {
    this.value = null;
  }
}
