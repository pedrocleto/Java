package pt.inescporto.template.client.design;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author not attributable
 * @version 1.0
 */
import javax.swing.*;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.TmplResourceSingleton;

public class TmplJButtonExit extends TmplJButton {
  public TmplJButtonExit() {
    super();

    setText("exit");
    setIcon(new ImageIcon(TmplJButtonInsert.class.getResource("icons/exit.png")));
    setToolTipText(TmplResourceSingleton.getString("button.tooltip.exit"));
    setActionCommand("EXIT");
    setBorderPainted(false);
    setOpaque(false);
    setFocusable(false);
  }

  public TmplJButtonExit( Action a ) {
    super( a );
    this.setActionCommand("EXIT");
  }

  public TmplJButtonExit( Icon icon ) {
    super( icon );

    this.setText(TmplResourceSingleton.getString("button.label.exit"));
    this.setToolTipText(TmplResourceSingleton.getString("button.tooltip.exit"));
    this.setActionCommand("EXIT");
  }

  public TmplJButtonExit( String text ) {
    super( text );
    this.setActionCommand("EXIT");
  }

  public TmplJButtonExit( String text, Icon icon ) {
    super( text, icon );
    this.setActionCommand("EXIT");
  }

  public void tmplMode( TemplateEvent e ) {
    if( e.getMode() == TmplFormModes.MODE_SELECT )
      this.setEnabled(true);
    else
      this.setEnabled(false);
  }
}
