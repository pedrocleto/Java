package pt.inescporto.template.client.design;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.text.SimpleDateFormat;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJTimeField extends TmplJTextField {
  SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  public TmplJTimeField(JLabel jlblHolder, String field) {
    super(jlblHolder, field);
    registerFocusListener();
  }

  public TmplJTimeField(String label, String field) {
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
            Timestamp.valueOf("1970-01-01 " + content);
          }
          catch (Exception ex) {
            JOptionPane.showMessageDialog(textField, TmplResourceSingleton.getString("error.timeVerifier.msg"), TmplResourceSingleton.getString("error.dialog.header"), JOptionPane.ERROR_MESSAGE);
            textField.requestFocus();
          }
        }
      }
    });
  }

  public void tmplRefresh(TemplateEvent e) {
    try {
      if (link != null && link instanceof pt.inescporto.template.elements.TplTimestamp)
        setText(((TplTimestamp)link).getValue().toString().substring(11, 19));
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
            Time tm = Time.valueOf(getText());
            ((TplTimestamp)link).setValue(new Timestamp(tm.getTime()));
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
        }
        else
          ((TplTimestamp)link).resetValue();
      }
    }
    catch (Exception ex) {
    }
  }
}
