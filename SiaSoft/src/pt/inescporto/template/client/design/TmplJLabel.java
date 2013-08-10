package pt.inescporto.template.client.design;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.ImageIcon;
import pt.inescporto.template.elements.TplObjRef;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.event.*;
import pt.inescporto.template.client.design.TmplGetter;

public class TmplJLabel extends JLabel implements DataSourceListener, TemplateComponentListener, TmplGetter {
  protected JLabel jlblHolder = null;
  protected String field;
  protected TplObjRef link = null;
  protected String fieldPermName = null;
  protected int fieldPerm = 15;

  public TmplJLabel() {
    super();
    this.setFont(new Font("Dialog", 0, 10));
  }

  public TmplJLabel(String text) {
    super(text);
    this.setFont(new Font("Dialog", 0, 10));
  }

  public TmplJLabel(JLabel jlblHolder, String field) {
    this.jlblHolder = jlblHolder;
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));
  }

  public TmplJLabel(String text, String field) {
    super(text);
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));
  }

  public void setLink(TplObjRef link) {
    this.link = link;
//    this.setText(this.link.getField());
    if (this.jlblHolder != null && this.link.isRequired()) {
      Font f = this.jlblHolder.getFont();
      this.jlblHolder.setFont(new Font(f.getName(), f.getStyle() | Font.BOLD, f.getSize()));
    }
  }

  public TplObjRef getLink() {
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

  //
  // Methods from TmplGetter interface
  //

  public String getLabelName() {
    if (this.jlblHolder != null)
      return this.jlblHolder.getText();
    else
      return getText();
  }

  //
  // Methods from TemplateListener interface
  //

  public void tmplInitialize(TemplateEvent e) {
  }

  public void tmplMode(TemplateEvent e) {
    switch (e.getMode()) {
      case TmplFormModes.MODE_SELECT:
	setEnabled(true);
	break;
      case TmplFormModes.MODE_INSERT:
	if (link != null && (int)(fieldPerm & TmplPerms.PERM_INSERT) != 0) {
	  if (link.isLinkKey()) {
	    setEnabled(true);
	  }
	  else {
	    setEnabled(true);
	    setText("");
	    setIcon(null);
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
	  setIcon(null);
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
	setIcon((ImageIcon)link.getValue());
      }
    }
    catch (Exception ex) {
      setText("");
    }
  }

  public void tmplSave(TemplateEvent e) {
    if (link != null) {
      if (getIcon() == null)
	link.resetValue();
      else
	link.setValue(getIcon());
    }
  }

  public boolean tmplRequired(TemplateEvent e) {
    String value = getText();
    if (link != null && link.isRequired() && !link.isGenKey() && getIcon() == null) {
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
    if (e.getLink().getField().equals(field))
      link = (TplObjRef)e.getLink();
  }

  public JComponent tmplGetComponent() {
    return this;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
