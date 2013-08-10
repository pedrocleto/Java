package pt.inescporto.template.elements;

public class TplObject implements java.io.Serializable, Cloneable {
  protected int index;
  protected String field;
  protected int keyType;
  protected boolean required;

  public TplObject() {
    index = 0;
  }

  public String getField() {
    return this.field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public void resetValue() {
  }

  public Object getValueAsObject() {
    return null;
  }

  public void setValueAsObject(Object value) {
  }

  public void setindex(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public void setKeyType(int keyType) {
    this.keyType = keyType;
  }

  public int getKeyType() {
    return keyType;
  }

  public boolean isPrimaryKey() {
    return (this.keyType & TmplKeyTypes.PKKEY) > 0 ? true : false;
  }

  public boolean isForeignKey() {
    return (this.keyType & TmplKeyTypes.FKKEY) > 0 ? true : false;
  }

  public boolean isLinkKey() {
    return (this.keyType & TmplKeyTypes.LNKKEY) > 0 ? true : false;
  }

  public boolean isGenKey() {
    return (this.keyType & TmplKeyTypes.GENKEY) > 0 ? true : false;
  }

  public boolean isRequired() {
    return this.required;
  }

  public void setLinkKey() {
    this.keyType = this.keyType | TmplKeyTypes.LNKKEY;
  }

  public void resetLinkKey() {
    this.keyType = this.keyType & ~TmplKeyTypes.LNKKEY;
  }

  public void setRequired() {
    required = true;
  }

  public void resetRequired() {
    required = false;
  }

  public void setGenKey() {
    this.keyType = this.keyType | TmplKeyTypes.GENKEY;
  }

  public void resetGenKey() {
    this.keyType = this.keyType & ~TmplKeyTypes.GENKEY;
  }

  public void setFkKey() {
    this.keyType = this.keyType | TmplKeyTypes.FKKEY;
  }

  public void resetFkKey() {
    this.keyType = this.keyType & ~TmplKeyTypes.FKKEY;
  }
}
