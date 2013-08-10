package pt.inescporto.template.client.design;

import javax.swing.*;
import java.awt.Font;
import java.sql.Timestamp;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.event.*;

public class TmplJCheckBox extends JCheckBox implements DataSourceListener, TemplateComponentListener, TmplGetter {
  protected JLabel jlblHolder = null;
  protected String field;
  protected TplObject link = null;
  protected int fieldPerm = 15;
  protected String fieldPermName = null;
  protected Object dataValues[];

  public TmplJCheckBox() {
    setFont(new Font("Dialog", 0, 10));
  }

  public TmplJCheckBox(String label, String field, Object dataValues[]) {
    setText(label);
    this.field = field;
    this.dataValues = dataValues;
    setFont(new Font("Dialog", 0, 10));
  }

  public void setLink(TplObject link) {
    this.link = link;
    if (jlblHolder != null && this.link.isRequired()) {
      Font f = this.jlblHolder.getFont();
      jlblHolder.setFont(new Font(f.getName(), f.getStyle() | Font.BOLD, f.getSize()));
    }
  }

  public void setLabel(String label) {
    setText(label);
  }

  public String getLabel() {
    return getText();
  }

  public void setDataValues(Object[] dataValues) {
    this.dataValues = dataValues;
  }

  public Object[] getDataValues() {
    return dataValues;
  }

  /**
   * Set the field name
   * @param field name of field
   */
  public void setField(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }

  public TplObject getLink() {
    return link;
  }

  //
  // Methods from TmplGetter interface
  //

  public String getLabelName() {
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
        setEnabled(false);
        break;
      case TmplFormModes.MODE_INSERT:
        if (link != null && (int)(fieldPerm & TmplPerms.PERM_INSERT) != 0) {
          if (link.isLinkKey() && link.isPrimaryKey()) {
            setEnabled(false);
          }
          else {
            setEnabled(true);
            link.resetValue();
            setSelected(false);
          }
        }
        break;
      case TmplFormModes.MODE_UPDATE:
        if (link != null && !link.isPrimaryKey() && (int)(fieldPerm & TmplPerms.PERM_CHANGE) != 0) {
          setEnabled(true);
        }
        break;
      case TmplFormModes.MODE_FIND:
        if (link == null || !(link.isLinkKey())) {
          setEnabled(true);
          setSelected(false);
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
      if (link != null && link instanceof pt.inescporto.template.elements.TplString)
        setSelected(((TplString)link).getValue().equalsIgnoreCase((String)dataValues[1]));
      if (link != null && link instanceof pt.inescporto.template.elements.TplBoolean)
        setSelected(((TplBoolean)link).getValue().booleanValue() == ((Boolean)dataValues[1]).booleanValue());
      if (link != null && link instanceof pt.inescporto.template.elements.TplInt)
        setSelected(((TplInt)link).getValue().intValue() == ((Integer)dataValues[1]).intValue());
      if (link != null && link instanceof pt.inescporto.template.elements.TplLong)
        setSelected(((TplLong)link).getValue().longValue() == ((Long)dataValues[1]).longValue());
      if (link != null && link instanceof pt.inescporto.template.elements.TplFloat)
        setSelected(((TplFloat)link).getValue().floatValue() == ((Float)dataValues[1]).floatValue());
      if (link != null && link instanceof pt.inescporto.template.elements.TplTimestamp)
        setSelected(((TplTimestamp)link).getValue().equals((Timestamp)dataValues[1]));
    }
    catch (Exception ex) {
      setSelected(false);
    }
  }

  public void tmplSave(TemplateEvent e) {
    if (link != null) {
      int i = isSelected() ? 1 : 0;
      if (link instanceof pt.inescporto.template.elements.TplString)
        ((TplString)link).setValue((String)dataValues[i]);
      if (link instanceof pt.inescporto.template.elements.TplBoolean)
        ((TplBoolean)link).setValue((Boolean)dataValues[i]);
      if (link instanceof pt.inescporto.template.elements.TplInt)
        ((TplInt)link).setValue((Integer)dataValues[i]);
      if (link instanceof pt.inescporto.template.elements.TplLong)
        ((TplLong)link).setValue((Long)dataValues[i]);
      if (link instanceof pt.inescporto.template.elements.TplFloat)
        ((TplFloat)link).setValue((Float)dataValues[i]);
      if (link instanceof pt.inescporto.template.elements.TplTimestamp)
        ((TplTimestamp)link).setValue((Timestamp)dataValues[i]);
    }
  }

  public boolean tmplRequired(TemplateEvent e) {
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
