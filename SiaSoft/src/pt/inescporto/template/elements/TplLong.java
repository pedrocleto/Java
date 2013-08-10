package pt.inescporto.template.elements;

public class TplLong extends TplObject implements java.io.Serializable {
  private Long value;

  public TplLong() {
    super();
  }

  public TplLong(String field, int keyType, boolean required) {
    super();
    this.field = field;
    this.keyType = keyType;
    this.required = required;
  }

  public TplLong(int index, String field, int keyType, boolean required) {
    this(field, keyType, required);
    this.index = index;
  }

  public TplLong(long value) {
    super();
    this.field = "";
    this.value = new Long(value);
    this.keyType = TmplKeyTypes.NOKEY;
    this.required = false;
  }

  public Long getValue() {
    if (this.value == null)
      return null;
    else
      return this.value;
  }

  public void setValue(Long value) {
    if (this.value == null)
      this.value = new Long(0);
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
        this.value = new Long(((Integer)value).intValue());
        return;
      }
      if (value instanceof Float) {
        this.value = new Long(((Float)value).longValue());
        return;
      }
      if (value instanceof Boolean) {
        this.value = new Long(((Boolean)value).toString());
        return;
      }
      if (value instanceof String) {
        this.value = new Long((String)value);
        return;
      }
      this.value = (Long)value;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void resetValue() {
    this.value = null;
  }
}
