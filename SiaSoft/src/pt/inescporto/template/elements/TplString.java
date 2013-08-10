package pt.inescporto.template.elements;

public class TplString extends TplObject implements java.io.Serializable {
  private String value;
  protected int length = -1;

  public TplString() {
    super();
  }

  public TplString(String field, int length, int keyType, boolean required) {
    this(0, field, length, keyType, required);
  }

  public TplString(int index, String field, int length, int keyType, boolean required) {
    this.index = index;
    this.field = field;
    this.length = length;
    this.keyType = keyType;
    this.required = required;
  }

  public TplString(String field, int keyType, boolean required) {
    this(0, field, -1, keyType, required);
  }

  public TplString(String value) {
    this.field = "";
    this.value = value;
    this.keyType = TmplKeyTypes.NOKEY;
    this.required = false;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Object getValueAsObject() {
    return value;
  }

  public void setValueAsObject(Object value) {
    this.value = value == null ? null : value.toString();
  }

  public void resetValue() {
    this.value = null;
  }

  public int getLength() {
    return length;
  }
}
