package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonSave extends TmplJButton {
  public TmplJButtonSave() {
    super();

    setText("save");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/save.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.save"));
    setActionCommand("SAVE");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonSave(Action a) {
    super(a);
    setActionCommand("SAVE");
  }

  public TmplJButtonSave(Icon icon) {
    super(icon);

    setText(TmplResourceSingleton.getString("button.label.save"));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.save"));
    setActionCommand("SAVE");
  }

  public TmplJButtonSave(String text) {
    super(text);
    setActionCommand("SAVE");
  }

  public TmplJButtonSave(String text, Icon icon) {
    super(text, icon);
    setActionCommand("SAVE");
  }

  public void tmplMode(TemplateEvent e) {
    if (e.getMode() != TmplFormModes.MODE_SELECT && e.getMode() != TmplFormModes.MODE_LOCK)
      setEnabled(true);
    else
      setEnabled(false);
  }
}
