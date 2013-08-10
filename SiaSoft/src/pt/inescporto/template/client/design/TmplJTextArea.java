package pt.inescporto.template.client.design;

import javax.swing.*;
import java.awt.Font;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.event.*;
import javax.swing.text.AbstractDocument;

public class TmplJTextArea extends JTextArea implements DataSourceListener, TemplateComponentListener, TmplGetter {
  protected JLabel jlblHolder = null;
  protected String label = null;
  protected String field;
  protected TplString link = null;
  protected int fieldPerm = 15;
  protected String fieldPermName = null;

  public TmplJTextArea() {
    this.setFont(new Font("Dialog", 0, 10));
  }

  public TmplJTextArea(JLabel jlblHolder, String field) {
    this.jlblHolder = jlblHolder;
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));
  }

  public TmplJTextArea(String label, String field) {
    this.label = label;
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));
  }

  public void setLink(TplObject link) {
    this.link = (TplString)link;
    if (jlblHolder != null && this.link.isRequired()) {
      Font f = jlblHolder.getFont();
      jlblHolder.setFont(new Font(f.getName(), f.getStyle() | Font.BOLD, f.getSize()));
    }
  }

  public TplObject getLink() {
    return link;
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

  public String getLabel() {
    return label;
  }

  //
  // Methods from TmplGetter interface
  //

  public String getLabelName() {
    return jlblHolder != null ? jlblHolder.getText() : label;
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
	  if ((link.isLinkKey() && link.isPrimaryKey()) || link.isGenKey()) {
	    setEnabled(false);
	    // clear for generated keys
	    if (link.isGenKey()) {
	      setText("");
	      link.resetValue();
	    }
	  }
	  else {
	    setEnabled(true);
	    setText("");
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
    if (link != null)
      setText(link.getValue());
  }

  public void tmplSave(TemplateEvent e) {
    if (link != null) {
      if (getText().equals(""))
	link.resetValue();
      else
	link.setValue(getText());
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
      link = (TplString)e.getLink();
      if (link instanceof pt.inescporto.template.elements.TplString && ((TplString)link).getLength() != -1) {
        ((AbstractDocument) getDocument()).setDocumentFilter(new FixedSizeFilter(((TplString)link).getLength()));
      }
    }
  }

  public JComponent tmplGetComponent() {
    return null;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
