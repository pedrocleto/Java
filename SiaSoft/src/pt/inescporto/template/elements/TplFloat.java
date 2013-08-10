package pt.inescporto.template.elements;

public class TplFloat extends TplObject implements java.io.Serializable {
  private Float value;

  public TplFloat() {
  }

  public TplFloat(String field, int keyType, boolean required) {
    this.field = field;
    this.keyType = keyType;
    this.required = required;
  }

  public TplFloat(int index, String field, int keyType, boolean required) {
    this.index = index;
    this.field = field;
    this.keyType = keyType;
    this.required = required;
  }

  public TplFloat(float value) {
    this.field = "";
    this.value = new Float(value);
    this.keyType = TmplKeyTypes.NOKEY;
    this.required = false;
  }

  public Float getValue() {
    return this.value;
  }

  public void setValue(Float value) {
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
	this.value = new Float(((Integer)value).floatValue());
        return;
      }
      if (value instanceof Long) {
	this.value = new Float(((Long)value).floatValue());
        return;
      }
      if (value instanceof Boolean) {
	this.value = new Float(((Boolean)value).toString());
        return;
      }
      if (value instanceof String) {
	this.value = new Float((String)value);
        return;
      }
      this.value = (Float)value;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void resetValue() {
    this.value = null;
  }
}
