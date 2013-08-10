package pt.inescporto.template.client.design;

import javax.swing.*;
import java.text.DecimalFormat;
import pt.inescporto.template.elements.*;
import pt.inescporto.template.client.event.*;

public class TmplJMoneyField extends TmplJTextField implements DataSourceListener, TemplateComponentListener, TmplGetter {
  protected JLabel jlblHolder = null;
  protected String label = null;
  protected String field;
  protected TplObject link = null;
  protected int fieldPerm = 7;
  protected DecimalFormat df = new DecimalFormat("#,##0.00;-#,##0.00");

  public TmplJMoneyField(JLabel jlblHolder, String field) {
    super(jlblHolder, field);
    setHorizontalAlignment(JTextField.RIGHT);
  }

  public TmplJMoneyField(String label, String field) {
    super(label, field);
    setHorizontalAlignment(JTextField.RIGHT);
  }

  public void setTextMoney(float value) {
    try {
      setText(df.parse(df.format(value)).toString());
    }
    catch (java.text.ParseException pex) {
    }
  }

  //
  // Methods from TemplateListener interface
  //

  public void tmplRefresh(TemplateEvent e) {
    try {
      if (link != null && link instanceof pt.inescporto.template.elements.TplFloat)
        setTextMoney((((TplFloat)link).getValue()).floatValue());
    }
    catch (Exception ex) {
      setText("");
    }
    fireActionPerformed();
  }

  public void tmplSave(TemplateEvent e) {
    if (link != null) {
      if (getText().equals(""))
        link.resetValue();
      else {
        if (link instanceof pt.inescporto.template.elements.TplFloat)
          ((TplFloat)link).setValue(new Float(getText()));
      }
    }
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
