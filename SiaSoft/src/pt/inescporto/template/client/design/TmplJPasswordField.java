package pt.inescporto.template.client.design;

import javax.swing.*;
import java.awt.Font;
import java.sql.Timestamp;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.event.*;

public class TmplJPasswordField extends JPasswordField implements DataSourceListener, TemplateComponentListener, TmplGetter  {
  protected JLabel jlblHolder = null;
  protected String label = null;
  protected String field;
  protected TplObject link = null;
  protected int fieldPerm = 15;
  protected String fieldPermName = null;

  public TmplJPasswordField() {
    this.setFont(new Font("Dialog", 0, 10));
  }

  public TmplJPasswordField( JLabel jlblHolder, String field ) {
    this.jlblHolder = jlblHolder;
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));
  }

  public TmplJPasswordField( String label, String field ) {
    this.label = label;
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));
  }

  public void setLink( TplObject link ) {
    this.link = link;
//    this.setText(this.link.getField());
    if( this.jlblHolder != null && this.link.isRequired() ) {
      Font f = this.jlblHolder.getFont();
      this.jlblHolder.setFont( new Font(f.getName(), f.getStyle() | Font.BOLD, f.getSize()));
    }
  }

  public TplObject getLink() {
    return this.link;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getField() {
    return this.field;
  }

  public void setHolder(JLabel jlblHolder) {
    this.jlblHolder = jlblHolder;
  }

  //
  // Methods from TmplGetter interface
  //

  public String getLabelName() {
    if( this.jlblHolder != null )
      return this.jlblHolder.getText();
    else
      return label;
  }

  //
  // Methods from TemplateListener interface
  //

  public void tmplInitialize( TemplateEvent e ) {
  }

  public void tmplMode( TemplateEvent e ) {
    switch( e.getMode() ) {
      case TmplFormModes.MODE_SELECT:
        setEnabled(false);
        setVisible(false);
        if (jlblHolder != null)
          jlblHolder.setVisible(false);
        break;
      case TmplFormModes.MODE_INSERT:
        if( link != null && (int)(fieldPerm & TmplPerms.PERM_INSERT) != 0 ) {
          if( (link.isLinkKey() && this.link.isPrimaryKey()) || link.isGenKey() ) {
            setEnabled(false);
            // clear for generated keys
            if( link.isGenKey() ) {
              setText("");
              fireActionPerformed();
              link.resetValue();
            }
          }
          else {
            setEnabled(true);
            setVisible(true);
            if (jlblHolder != null)
              jlblHolder.setVisible(true);
            setText("");
            fireActionPerformed();
            link.resetValue();
          }
        }
        break;
      case TmplFormModes.MODE_UPDATE:
        if( link != null && !link.isPrimaryKey() && (int)(fieldPerm & TmplPerms.PERM_CHANGE) != 0 ) {
          setEnabled(false);
          setVisible(false);
          if (jlblHolder != null)
            jlblHolder.setVisible(false);
        }
        break;
      case TmplFormModes.MODE_FIND:
        if( link == null || !(link.isLinkKey()) ) {
          setEnabled(false);
          if (jlblHolder != null)
            jlblHolder.setVisible(false);
          setText("");
          if( link != null )
            link.resetValue();
        }
        break;
    }
  }

  public void tmplEnable( TemplateEvent e ) {
    setEnabled( e.getEnabled() );
  }

  public void tmplRefresh( TemplateEvent e ) {
    if( this.link != null && this.link instanceof pt.inescporto.template.elements.TplString )
      this.setText(((TplString)this.link).getValue());
    if( this.link != null && this.link instanceof pt.inescporto.template.elements.TplInt )
      this.setText(((TplInt)this.link).getValue().toString());
    if( this.link != null && this.link instanceof pt.inescporto.template.elements.TplLong )
      this.setText(((TplLong)this.link).getValue().toString());
    if( this.link != null && this.link instanceof pt.inescporto.template.elements.TplFloat )
      this.setText(((TplFloat)this.link).getValue().toString());
    if( this.link != null && this.link instanceof pt.inescporto.template.elements.TplTimestamp )
      this.setText(((TplTimestamp)this.link).getValue().toString());
    this.fireActionPerformed();
  }

  public void tmplSave( TemplateEvent e ) {
    if( this.link != null ) {
      String newValue = new String( this.getPassword() );
      if( newValue.equals("") )
        this.link.resetValue();
      else {
        if( this.link instanceof pt.inescporto.template.elements.TplString )
          ((TplString)this.link).setValue(newValue);
      }
    }
  }

  public boolean tmplRequired( TemplateEvent e ) {
    String value = getPassword().toString();
    if (link != null && link.isRequired() && !link.isGenKey() && getPassword().length == 0) {
      requestFocus();
      e.setCompFailed(this);
      return false;
    }
    else
      return true;
  }

  public boolean tmplValidate( TemplateEvent e ) {
    return true;
  }

  public void tmplPermissions( TemplateEvent e ) {
    // field permissions
    if (fieldPermName != null && e.getPermissionForField(fieldPermName) != -1)
      fieldPerm = e.getPermissionForField(fieldPermName);
  }

  public void tmplLink( TemplateEvent e ) {
    if( e.getLink().getField().equals(field) )
      link = e.getLink();
  }

  public JComponent tmplGetComponent() {
    return null;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
