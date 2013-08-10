package pt.inescporto.template.client.design;

import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.util.TmplPerms;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.elements.TplBoolean;
import java.awt.Insets;
import pt.inescporto.template.client.event.TemplateComponentListener;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class TmplJToggleButton extends JToggleButton implements DataSourceListener, TemplateComponentListener, TmplGetter {
  protected JLabel jlblHolder = null;
  protected String field;
  protected TplBoolean link = null;
  protected int fieldPerm = 15;
  protected String fieldPermName = null;

  public TmplJToggleButton() {
    setMargin(new Insets(2, 2, 2, 2));
    setFont(new java.awt.Font("Dialog", 0, 10));
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
      setSelected(link.getValue().booleanValue());
    }
    catch (Exception ex) {
      setSelected(false);
    }
  }

  public void tmplSave(TemplateEvent e) {
    if (link != null) {
      link.setValue(isSelected());
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
    if (e.getLink().getField().equals(field) && e.getLink() instanceof pt.inescporto.template.elements.TplBoolean)
      link = (TplBoolean)e.getLink();
  }

  public JComponent tmplGetComponent() {
    return this;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
