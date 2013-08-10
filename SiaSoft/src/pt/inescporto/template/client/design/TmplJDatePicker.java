package pt.inescporto.template.client.design;

import java.awt.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import javax.swing.*;

import com.standbysoft.component.date.swing.*;
import pt.inescporto.template.client.event.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.elements.*;
import com.standbysoft.component.date.*;

public class TmplJDatePicker extends JDatePicker implements DataSourceListener, TemplateComponentListener, TmplGetter {
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  protected JLabel jlblHolder = null;
  protected String label = null;
  protected String field;
  protected TplObject link = new TplObject();
  protected int fieldPerm = 7;
  protected String fieldPermName = null;

  public TmplJDatePicker() {
    super();
    this.setFont(new Font("Dialog", 0, 10));
    setCustomDateFormat("yyyy-MM-dd");
    try {
      setEmptySelectionAllowed(true);
      setEmptySelectionText(" ");
      setEditable(true);
    }
    catch (DateSelectionException ex) {
      ex.printStackTrace();
    }
  }

  public TmplJDatePicker(JLabel jlblHolder, String field) {
    this.jlblHolder = jlblHolder;
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));
    setCustomDateFormat("yyyy-MM-dd");
    try {
      setEmptySelectionAllowed(true);
      setEmptySelectionText(" ");
      setEditable(true);
    }
    catch (DateSelectionException ex) {
      ex.printStackTrace();
    }
  }

  public TmplJDatePicker(String label, String field) {
    this.label = label;
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));
    setCustomDateFormat("yyyy-MM-dd");
    try {
      setEmptySelectionAllowed(true);
      setEmptySelectionText(" ");
      setEditable(true);
    }
    catch (DateSelectionException ex) {
      ex.printStackTrace();
    }
  }

  public void setLink(TplObject link) {
    this.link = link;
//    this.setText(this.link.getField());
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
	      setSelectedDate(null);
	      fireActionEvent();
	      link.resetValue();
	    }
	  }
	  else {
	    setEnabled(true);
	    setSelectedDate(null);
	    fireActionEvent();
	    link.resetValue();
	  }
	}
	break;
      case TmplFormModes.MODE_UPDATE:
	if (link != null && !link.isPrimaryKey() && (int)(fieldPerm & TmplPerms.PERM_CHANGE) != 0)
	  setEnabled(true);
	if (link.isLinkKey() || link.isGenKey())
	  setEnabled(false);
	break;
      case TmplFormModes.MODE_FIND:
	if (link == null || !(link.isLinkKey())) {
	  setEnabled(true);
	  setSelectedDate(null);
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
      if (link != null && link instanceof pt.inescporto.template.elements.TplTimestamp) {
	setSelectedDate(new Date(((TplTimestamp)link).getValue().getTime()));
	this.fireActionEvent();
      }
    }
    catch (Exception ex) {
      setSelectedDate(null);
    }
  }

  public void tmplSave(TemplateEvent e) {
    try {
      if (link != null && link instanceof pt.inescporto.template.elements.TplTimestamp) {
	//Date dt = this.getSelectedDate();
	//Date dt = new Date(dateFormat.parse(getText()).getTime());
	if (this.getSelectedDate() == null) {
	  ((TplTimestamp)link).setValue(null);
	}
	else {
	  ((TplTimestamp)link).setValue(new Timestamp(this.getSelectedDate().getTime()));
	}
      }
    }
    catch (Exception ex) {
    }
  }

  public boolean tmplRequired(TemplateEvent e) {
    Date value = this.getSelectedDate();
    if (link != null && link.isRequired() && !link.isGenKey() && value == null) {
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
      link = e.getLink();
  }

  public JComponent tmplGetComponent() {
    return this;
  }

  public void tmplDispose(TemplateEvent e) {
  }

  public Date getSelectedDate(boolean ignoreTime) {
    if (!ignoreTime) {
      return getSelectedDate();
    }
    else {
      GregorianCalendar cal = new GregorianCalendar();
      if (getSelectedDate() == null)
	return null;
      cal.setTime(getSelectedDate());
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
      Date dt = new Date(cal.getTimeInMillis());
      return dt;
    }
  }

}
