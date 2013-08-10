package pt.inescporto.template.client.design;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.sql.Timestamp;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.event.*;

public class TmplButtonGroup extends ButtonGroup implements DataSourceListener, TemplateComponentListener, TmplGetter {
  protected String label = null;
  protected String field;
  protected TplObject link = null;
  protected int fieldPerm = 15;
  protected String fieldPermName = null;
  private String value = null;
  private int currentMode = -1;

  public TmplButtonGroup() {
    super();
  }

  public TmplButtonGroup(String label, String field) {
    super();
    this.label = label;
    this.field = field;
  }

  public void add(AbstractButton b) {
    super.add(b);
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	selectedValueChanged(e);
      }
    });
  }

  public void selectedValueChanged(ActionEvent e) {
    value = ((TmplJRadioButton)e.getSource()).getValue();
  }

  protected void setEnabled(boolean enable) {
    Enumeration en = getElements();

    while (en.hasMoreElements())
      ((TmplJRadioButton)en.nextElement()).setEnabled(enable);
  }

  protected void setSelected() {
    Enumeration en = getElements();

    while (en.hasMoreElements()) {
      TmplJRadioButton btn = (TmplJRadioButton)en.nextElement();
      btn.setSelected(btn.getValue().equals(value));
    }
  }

  protected void selectFirst() {
    Enumeration en = getElements();

    if (en.hasMoreElements()) {
      TmplJRadioButton btn = (TmplJRadioButton)en.nextElement();
      btn.setSelected(true);
      selectedValueChanged(new ActionEvent(btn, 0, btn.getActionCommand()));
    }
  }

  //link
  public void setLink(TplObject link) {
    this.link = link;
    value = link.getField();
  }

  public TplObject getLink() {
    return link;
  }

  //label
  public void setLabel(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  //field
  public void setField(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }

  //
  // Methods from TmplGetter interface
  //

  public String getLabelName() {
    return label;
  }

  //
  // Methods from TemplateListener interface
  //

  public void tmplInitialize(TemplateEvent e) {
  }

  public void tmplMode(TemplateEvent e) {
    currentMode = e.getMode();
    switch (e.getMode()) {
      case TmplFormModes.MODE_SELECT:
	setEnabled(false);
	break;
      case TmplFormModes.MODE_INSERT:
	if (link != null && (int)(fieldPerm & TmplPerms.PERM_INSERT) != 0) {
	  if ((link.isLinkKey() && link.isPrimaryKey()) || link.isGenKey())
	    setEnabled(false);
	  else {
	    setEnabled(true);
	  }
	}
	break;
      case TmplFormModes.MODE_UPDATE:
	if (link != null && !link.isPrimaryKey() && (int)(fieldPerm & TmplPerms.PERM_CHANGE) != 0)
	  setEnabled(true);
	break;
      case TmplFormModes.MODE_FIND:
	setEnabled(true);
	if (link != null) {
	  link.resetValue();
          value = null;
	}
	break;
    }
  }

  public void tmplEnable(TemplateEvent e) {
    this.setEnabled(e.getEnabled());
  }

  public void tmplRefresh(TemplateEvent e) {
    try {
      if (link != null && link instanceof pt.inescporto.template.elements.TplString)
	value = (((TplString)link).getValue());
      if (link != null && link instanceof pt.inescporto.template.elements.TplInt)
	value = (((TplInt)link).getValue().toString());
      if (link != null && link instanceof pt.inescporto.template.elements.TplLong)
	value = (((TplLong)link).getValue().toString());
      if (link != null && link instanceof pt.inescporto.template.elements.TplFloat)
	value = (((TplFloat)link).getValue().toString());
      if (link != null && link instanceof pt.inescporto.template.elements.TplTimestamp)
	value = (((TplTimestamp)link).getValue().toString());
      setSelected();

      // correct the insert value
      if (currentMode == TmplFormModes.MODE_INSERT)
        selectFirst();
    }
    catch (Exception ex) {
    }
  }

  public void tmplSave(TemplateEvent e) {
    try {
      if (link != null && link instanceof pt.inescporto.template.elements.TplString)
	((TplString)link).setValue(value);
      if (link != null && link instanceof pt.inescporto.template.elements.TplInt)
	((TplInt)link).setValue(new Integer(value));
      if (link != null && this.link instanceof pt.inescporto.template.elements.TplLong)
	((TplLong)this.link).setValue(new Long(value));
      if (link != null && link instanceof pt.inescporto.template.elements.TplFloat)
	((TplFloat)link).setValue(new Float(value));
      if (link != null && link instanceof pt.inescporto.template.elements.TplTimestamp)
	((TplTimestamp)link).setValue(Timestamp.valueOf(value));
    }
    catch (Exception ex) {
    }
  }

  public boolean tmplRequired(TemplateEvent e) {
    if (link.isRequired() && !link.isGenKey() && (value == null || (value != null && value.equals("")))) {
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
      link = e.getLink();
  }

  public JComponent tmplGetComponent() {
    return null;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
