package pt.inescporto.template.client.design;

import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;

import pt.inescporto.template.client.design.thirdparty.TristateToggleButton;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.util.TmplPerms;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.client.event.TemplateComponentListener;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.thirdparty.State;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class FW_JToggleButtonThreeState extends TristateToggleButton implements DataSourceListener, TemplateComponentListener, TmplGetter {
  protected JLabel jlblHolder = null;
  protected String field;
  protected TplObject link = null;
  protected int fieldPerm = 15;
  protected String fieldPermName = null;
  protected Object dataValues[];

  public FW_JToggleButtonThreeState() {
    this(null, null, null);
  }

  public FW_JToggleButtonThreeState(String label, String field, Object dataValues[]) {
    setText(label);
    this.field = field;
    this.dataValues = dataValues;
    setFont(new Font("Dialog", Font.PLAIN, 10));
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
            setState(State.DONT_CARE);
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
          setState(State.DONT_CARE);
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
      if (link != null && link instanceof pt.inescporto.template.elements.TplObject) {
        if (link.getValueAsObject() != null)
          setSelected(link.getValueAsObject().equals(dataValues[1]));
        else
          setState(State.DONT_CARE);
      }
    }
    catch (Exception ex) {
      setSelected(false);
    }
  }

  public void tmplSave(TemplateEvent e) {
    if (link != null) {
      if (link instanceof pt.inescporto.template.elements.TplObject) {
        if (getState() != State.DONT_CARE) {
	  int i = isSelected() ? 1 : 0;
	  link.setValueAsObject(dataValues[i]);
	}
        else
          link.setValueAsObject(null);
      }
    }
  }

  public boolean tmplRequired(TemplateEvent e) {
    if (link != null && link.isRequired() && !link.isGenKey() && getState() == State.DONT_CARE) {
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
    return null;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
