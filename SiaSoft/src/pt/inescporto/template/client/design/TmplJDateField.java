package pt.inescporto.template.client.design;

import javax.swing.*;
import java.sql.Timestamp;
import java.sql.Date;
import java.text.*;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.client.event.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TmplJDateField extends TmplJTextField implements DataSourceListener, TemplateComponentListener {
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public TmplJDateField() {
    super();
    registerFocusListener();
  }

  public TmplJDateField(JLabel jlblHolder, String field) {
    super(jlblHolder, field);
    registerFocusListener();
  }

  public TmplJDateField(String label, String field) {
    super(label, field);
    registerFocusListener();
  }

  private void registerFocusListener() {
    addFocusListener(new FocusAdapter() {
      public void focusLost(FocusEvent e) {
        JTextField textField = (JTextField)e.getSource();
        String content = textField.getText();
        if (content.compareTo("") != 0) {
          try {
            Date dt = new Date(dateFormat.parse(content).getTime());
            Timestamp.valueOf(content + " 00:00:00");
          }
          catch (Exception ex) {
            JOptionPane.showMessageDialog(textField, TmplResourceSingleton.getString("error.dateVerifier.msg"), TmplResourceSingleton.getString("error.dialog.header"), JOptionPane.ERROR_MESSAGE);
            textField.requestFocus();
          }
        }
      }
    });
  }

  public void tmplRefresh(TemplateEvent e) {
    try {
      if (link != null && link instanceof pt.inescporto.template.elements.TplTimestamp)
        setText(new Date(((TplTimestamp)link).getValue().getTime()).toString());
      fireActionPerformed();
    }
    catch (Exception ex) {
      setText("");
    }
  }

  public void tmplSave(TemplateEvent e) {
    try {
      if (link != null && link instanceof pt.inescporto.template.elements.TplTimestamp) {
        if (getText().compareTo("") != 0) {
          try {
            Date dt = java.sql.Date.valueOf(getText());
            ((TplTimestamp)link).setValue(new Timestamp(dt.getTime()));
          }
          catch (Exception ex) {
          }
        }
        else
          ((TplTimestamp)link).resetValue();
      }
    }
    catch (Exception ex) {
    }
  }

  public Timestamp getFormatedText() {
    try {
      Date dt = java.sql.Date.valueOf(getText());
      return new Timestamp(dt.getTime());
    }
    catch (Exception ex) {
      return null;
    }
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
