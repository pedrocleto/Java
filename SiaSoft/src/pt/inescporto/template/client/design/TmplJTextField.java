package pt.inescporto.template.client.design;

import javax.swing.*;
import java.awt.Font;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.event.*;
import pt.inescporto.template.client.design.TmplGetter;
import javax.swing.text.AbstractDocument;

public class TmplJTextField extends JTextField implements DataSourceListener, TemplateComponentListener, TmplGetter {
  protected JLabel jlblHolder = null;
  protected String label = null;
  protected String field;
  protected TplObject link = new TplObject();
  protected int fieldPerm = 15;
  protected String fieldPermName = null;

  public TmplJTextField() {
    super();
    this.setFont(new Font("Dialog", 0, 10));
  }

  public TmplJTextField(JLabel jlblHolder, String field) {
    this.jlblHolder = jlblHolder;
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));
  }

  public TmplJTextField(String label, String field) {
    this.label = label;
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));
  }

  public void setLink(TplObject link) {
    this.link = link;

    if (this.jlblHolder != null && this.link.isRequired()) {
      Font f = this.jlblHolder.getFont();
      this.jlblHolder.setFont(new Font(f.getName(), f.getStyle() | Font.BOLD, f.getSize()));
    }
  }

  public TplObject getLink() {
    return this.link;
  }

  public String getField() {
    return this.field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public void setHolder(JLabel holder) {
    jlblHolder = holder;
  }

  public JLabel getHolder() {
    return jlblHolder;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  //
  // Methods from TmplGetter interface
  //

  public String getLabelName() {
    if (this.jlblHolder != null)
      return this.jlblHolder.getText();
    else
      return label;
  }

  //
  // Methods from TemplateListener interface
  //

  public void tmplInitialize(TemplateEvent e) {
  }

  public void tmplMode(TemplateEvent e) {
    switch (e.getMode()) {
      case TmplFormModes.MODE_SELECT:
        setEnabled(false);
        break;
      case TmplFormModes.MODE_INSERT:
        if (link != null && (int)(fieldPerm & TmplPerms.PERM_INSERT) != 0) {
          if (link.isLinkKey() || link.isGenKey()) {
            setEnabled(false);
            // clear for generated keys
            if (link.isGenKey()) {
              setText("");
              fireActionPerformed();
              link.resetValue();
            }
          }
          else {
            setEnabled(true);
            setText("");
            fireActionPerformed();
            link.resetValue();
          }
        }
        break;
      case TmplFormModes.MODE_UPDATE:
        if (link != null && !link.isPrimaryKey() && (int)(fieldPerm & TmplPerms.PERM_CHANGE) != 0)
          setEnabled(true);
        break;
      case TmplFormModes.MODE_FIND:
        if (link == null || !(link.isLinkKey())) {
          setEnabled(true);
          setText("");
          if (link != null)
            link.resetValue();
        }
        break;
    }
  }

  public void tmplEnable(TemplateEvent e) {
    setEnabled(e.getEnabled());
  }

  public void tmplRefresh(TemplateEvent e) {
    try {
      if (link != null) {
        setText(link.getValueAsObject() == null ? "" : link.getValueAsObject().toString());
      }
    }
    catch (Exception ex) {
      setText("");
    }
    fireActionPerformed();
  }

  public void tmplSave(TemplateEvent e) {
    if (link != null) {
      if (getText().equals(""))
        link.resetValue();
      else {
        String value = getText();
        if (value == null || value == "" || value.compareTo("") == 0)
          link.resetValue();
        else {
          link.setValueAsObject(value);
        }
      }
    }
  }

  public boolean tmplRequired(TemplateEvent e) {
    String value = getText();
    if (link != null && link.isRequired() && !link.isGenKey() && value.equals("")) {
      requestFocus();
      e.setCompFailed(this);
      return false;
    }
    else
      return true;
  }

  public boolean tmplValidate(TemplateEvent e) {
    return true;
  }

  public void tmplPermissions(TemplateEvent e) {
    // field permissions
    if (fieldPermName != null && e.getPermissionForField(fieldPermName) != -1)
      fieldPerm = e.getPermissionForField(fieldPermName);
  }

  public void tmplLink(TemplateEvent e) {
    if (e.getLink().getField().equals(field)) {
      link = e.getLink();
      if (link instanceof pt.inescporto.template.elements.TplString && ((TplString)link).getLength() != -1) {
        ((AbstractDocument) getDocument()).setDocumentFilter(new FixedSizeFilter(((TplString)link).getLength()));
      }
    }
  }

  public JComponent tmplGetComponent() {
    return this;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
