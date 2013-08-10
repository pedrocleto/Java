package pt.inescporto.template.client.design;

import pt.inescporto.template.client.util.TmplFormModes;
import java.awt.event.ActionEvent;
import pt.inescporto.template.client.util.TmplPerms;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.elements.TplInt;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.event.TemplateEvent;
import java.util.Vector;
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
 * @author not attributable
 * @version 0.1
 */
public class TmplButtonGroupBit implements DataSourceListener, TemplateComponentListener, TmplGetter {
  protected String label = null;
  protected String field;
  protected TplInt link = null;
  protected int fieldPerm = 15;
  protected String fieldPermName = null;
  private Integer value = null;
  private Vector<TmplJCheckBoxBit> btn = new Vector<TmplJCheckBoxBit>();

  public TmplButtonGroupBit() {
  }

  public TmplButtonGroupBit(String label, String field) {
    this.label = label;
    this.field = field;
  }

  public void add(TmplJCheckBoxBit b) {
    btn.add(b);
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	selectedValueChanged(e);
      }
    });
  }

  public void selectedValueChanged(ActionEvent e) {
    if (((TmplJCheckBoxBit)e.getSource()).isSelected())
      value |= ((TmplJCheckBoxBit)e.getSource()).getBit();
    else
      value &= ~((TmplJCheckBoxBit)e.getSource()).getBit();
  }

  protected void setEnabled(boolean enable) {
    for (TmplJCheckBoxBit bb : btn) {
      bb.setEnabled(enable);
    }
  }

  protected void setSelected() {
    for (TmplJCheckBoxBit bb : btn) {
      bb.setSelected((bb.getBit() & value.intValue()) > 0);
    }
  }

  //link
  public void setLink(TplInt link) {
    this.link = link;
    value = link.getValue();
  }

  public TplInt getLink() {
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
    value = new Integer(0);
  }

  public void tmplMode(TemplateEvent e) {
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
            value = new Integer(0);
            setSelected();
	  }
	}
	break;
      case TmplFormModes.MODE_UPDATE:
	if (link != null && !link.isPrimaryKey() && (int)(fieldPerm & TmplPerms.PERM_CHANGE) != 0)
	  setEnabled(true);
	break;
      case TmplFormModes.MODE_FIND:
	setEnabled(true);
	if (link != null)
	  link.resetValue();
	break;
    }
  }

  public void tmplEnable(TemplateEvent e) {
    this.setEnabled(e.getEnabled());
  }

  public void tmplRefresh(TemplateEvent e) {
    try {
      if (link != null) {
	value = link.getValue();
        if (value == null)
          value = new Integer(0);
      }
      setSelected();
    }
    catch (Exception ex) {
    }
  }

  public void tmplSave(TemplateEvent e) {
    try {
      if (link != null)
	link.setValue(value);
    }
    catch (Exception ex) {
    }
  }

  public boolean tmplRequired(TemplateEvent e) {
    if (link.isRequired() && !link.isGenKey() && value == null) {
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
      link = (TplInt)e.getLink();
  }

  public JComponent tmplGetComponent() {
    return null;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
