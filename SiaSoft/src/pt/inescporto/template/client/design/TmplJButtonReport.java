package pt.inescporto.template.client.design;

import javax.swing.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;

public class TmplJButtonReport extends TmplJButton {
  public TmplJButtonReport() {
    super();

//    this.setText(TmplResourceSingleton.getString("button.label.report"));
    this.setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/report.gif")));
    this.setToolTipText(TmplResourceSingleton.getString("button.tooltip.report"));
    this.setActionCommand("REPORT");
  }

  public TmplJButtonReport( Action a ) {
    super( a );
    this.setActionCommand("REPORT");
  }

  public TmplJButtonReport( Icon icon ) {
    super( icon );

    this.setText(TmplResourceSingleton.getString("button.label.report"));
    this.setToolTipText(TmplResourceSingleton.getString("button.tooltip.report"));
    this.setActionCommand("REPORT");
  }

  public TmplJButtonReport( String text ) {
    super( text );
    this.setActionCommand("REPORT");
  }

  public TmplJButtonReport( String text, Icon icon ) {
    super( text, icon );
    this.setActionCommand("REPORT");
  }

  public void tmplMode( TemplateEvent e ) {
    if( e.getMode() == TmplFormModes.MODE_SELECT )
      this.setEnabled(true);
    else
      this.setEnabled(false);
  }
}
