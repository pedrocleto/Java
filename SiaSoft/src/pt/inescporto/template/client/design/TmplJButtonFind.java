package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonFind extends TmplJButton {
  public TmplJButtonFind() {
    super();

    setText("search");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/search.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.find"));
    setActionCommand("FIND");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonFind( Action a ) {
    super( a );
    setActionCommand("FIND");
  }

  public TmplJButtonFind( Icon icon ) {
    super( icon );

    setText(TmplResourceSingleton.getString("button.label.find"));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.find"));
    setActionCommand("FIND");
  }

  public TmplJButtonFind( String text ) {
    super( text );
    setActionCommand("FIND");
  }

  public TmplJButtonFind( String text, Icon icon ) {
    super( text, icon );
    setActionCommand("FIND");
  }

  public void tmplMode( TemplateEvent e ) {
    if( e.getMode() == TmplFormModes.MODE_SELECT )
      setEnabled(true);
    else
      setEnabled(false);
  }
}
