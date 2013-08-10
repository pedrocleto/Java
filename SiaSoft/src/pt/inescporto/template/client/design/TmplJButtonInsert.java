package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplPerms;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonInsert extends TmplJButton {
  public TmplJButtonInsert() {
    super();

    setText("insert");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/insert.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.insert"));
    setActionCommand("INSERT");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonInsert(Action a) {
    super(a);
    setActionCommand("INSERT");
  }

  public TmplJButtonInsert(Icon icon) {
    super(icon);

    setText(TmplResourceSingleton.getString("button.label.insert"));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.insert"));
    setActionCommand("INSERT");
  }

  public TmplJButtonInsert(String text) {
    super(text);
    setActionCommand("INSERT");
  }

  public TmplJButtonInsert(String text, Icon icon) {
    super(text, icon);
    setActionCommand("INSERT");
  }

  public void tmplMode(TemplateEvent e) {
    if (e.getMode() == TmplFormModes.MODE_SELECT && (int)(formPerms & TmplPerms.PERM_INSERT) != 0)
      setEnabled(true);
    else
      setEnabled(false);
  }
}
