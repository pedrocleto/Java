package pt.inescporto.template.client.design;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.swing.*;

import com.standbysoft.component.date.*;
import pt.inescporto.template.client.event.*;
import pt.inescporto.template.elements.*;

public class TmplJDateTimePicker extends JPanel implements DataSourceListener, TemplateComponentListener, TmplGetter {

  TmplJDatePicker dtPicker = new TmplJDatePicker();
  TmplJDateTimeField timefield = new TmplJDateTimeField();

  protected JLabel jlblHolder = null;
  protected String label = null;
  protected String field;
  protected TplObject link = new TplObject();
  protected int fieldPerm = 7;
  protected String fieldPermName = null;

  public TmplJDateTimePicker() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    timefield.setCustomDateFormat("HH:mm:ss");
    this.add(dtPicker);
    this.add(timefield);
  }

  public Date getSelectedDate() {
    return getSelectedDateAsCalendar().getTime();
  }

  public Date getSelectedDate(boolean ignoreTime) {
    if ( ignoreTime ) {
      return dtPicker.getSelectedDate(true);
    } else {
      return getSelectedDate();
    }
  }

  public void setSelectedDate(Date date) {
    dtPicker.setSelectedDate(date);
    timefield.setSelectedDate(date);
  }

  public void setEmptySelectionAllowed(boolean _boolean) throws DateSelectionException {
    dtPicker.setEmptySelectionAllowed(_boolean);
    timefield.setEmptySelectionAllowed(_boolean);
  }

  public void setEditable(boolean _boolean) {
    dtPicker.setEditable(_boolean);
  }

  public void setLink( TplObject link ) {
    this.link = link;
    if( this.jlblHolder != null && this.link.isRequired() ) {
      Font f = this.jlblHolder.getFont();
      this.jlblHolder.setFont( new Font(f.getName(), f.getStyle() | Font.BOLD, f.getSize()));
    }
  }

  public TplObject getLink() {
    return this.link;
  }

  public String getField() {
    return this.field;
  }

  public void setField( String field ) {
    this.field = field;
  }

  public void setHolder( JLabel holder ) {
    jlblHolder = holder;
  }

  public JLabel getHolder() {
    return jlblHolder;
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
    dtPicker.tmplInitialize(e);
    timefield.tmplInitialize(e);
  }

  public void tmplMode( TemplateEvent e ) {
    dtPicker.tmplMode(e);
    timefield.tmplMode(e);
  }

  public void tmplEnable( TemplateEvent e ) {
    setEnabled( e.getEnabled() );
    dtPicker.tmplEnable(e);
    timefield.tmplEnable(e);
  }

  public void tmplRefresh( TemplateEvent e ) {
    dtPicker.tmplRefresh(e);
    timefield.tmplRefresh(e);

/*
    try {
      if( link != null && link instanceof pt.inescporto.template.elements.TplTimestamp ) {
        dtPicker.setSelectedDate(new Date( ((TplTimestamp)link).getValue().getTime() ));
        timefield.setSelectedDate(new Date( ((TplTimestamp)link).getValue().getTime() ));
        ///this.fireActionEvent();
      }
    }
    catch( Exception ex ) {
      this.dtPicker.setSelectedDate(null);
      this.timefield.setSelectedDate(null);
    }
*/
  }

  private GregorianCalendar getSelectedDateAsCalendar() {
    Date dt = this.dtPicker.getSelectedDate(true);
    Date time = this.timefield.getSelectedDate();
    GregorianCalendar caltime = new GregorianCalendar();
    caltime.setTime(time);

    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(dt);
    cal.set(Calendar.HOUR,caltime.get(Calendar.HOUR));
    cal.set(Calendar.HOUR,caltime.get(Calendar.MINUTE));
    cal.set(Calendar.HOUR,caltime.get(Calendar.SECOND));
    cal.set(Calendar.HOUR,caltime.get(Calendar.MILLISECOND));
    return cal;
  }

  public void tmplSave( TemplateEvent e ) {
    try {
      if( link != null && link instanceof pt.inescporto.template.elements.TplTimestamp ) {
        Date dt = this.dtPicker.getSelectedDate(true);
        Date time = this.timefield.getSelectedDate();
        GregorianCalendar caltime = new GregorianCalendar();
        caltime.setTime(time);

        GregorianCalendar cal = getSelectedDateAsCalendar();
        ((TplTimestamp)link).setValue( new Timestamp(cal.getTime().getTime()) );
      }
    }
    catch( Exception ex ) {}
  }

  public boolean tmplRequired( TemplateEvent e ) {
    return ( dtPicker.tmplRequired(e) && timefield.tmplRequired(e) );
  }

  public boolean tmplValidate( TemplateEvent e ) {
    return ( dtPicker.tmplValidate(e) && timefield.tmplValidate(e) );
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
    return this;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
