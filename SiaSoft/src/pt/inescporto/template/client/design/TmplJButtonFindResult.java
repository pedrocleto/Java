package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonFindResult extends TmplJButton {
  public TmplJButtonFindResult() {
    super();

    setText("result");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/result.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.findRes"));
    setActionCommand("FINDRES");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonFindResult(Action a) {
    super(a);
    setActionCommand("FINDRES");
  }

  public TmplJButtonFindResult(Icon icon) {
    super(icon);

    setText(TmplResourceSingleton.getString("button.label.findRes"));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.findRes"));
    setActionCommand("FINDRES");
  }

  public TmplJButtonFindResult(String text) {
    super(text);
    setActionCommand("FINDRES");
  }

  public TmplJButtonFindResult(String text, Icon icon) {
    super(text, icon);
    setActionCommand("FINDRES");
  }

  public void tmplMode(TemplateEvent e) {
    if (e.getMode() == TmplFormModes.MODE_SELECT)
      setEnabled(true);
    else
      setEnabled(false);
  }
}
