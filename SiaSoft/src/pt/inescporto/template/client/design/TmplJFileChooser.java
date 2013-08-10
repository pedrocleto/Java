package pt.inescporto.template.client.design;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author not attributable
 * @version 1.0
 */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.util.TmplFormModes;
import java.net.*;
import java.awt.Component;
import java.security.AccessControlException;
import javax.swing.JOptionPane;

public class TmplJFileChooser extends TmplJButton {
  private int formPerms;
  private int fieldPerms;
  private String title;
  private Component fill = null;
  private JFileChooser chooser = null;

  public TmplJFileChooser() {
    super();

    try {
      chooser = new JFileChooser();
    }
    catch (AccessControlException ex) {
      showInformationMessage("Property 'user.home' access denied");
    }

    this.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doActionPerformed(e);
      }
    }); try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public TmplJFileChooser(String title, Component fill) {
    this();
    this.title = title;
    this.fill = fill;
  }

  public void doActionPerformed( ActionEvent e ) {
    int returnVal = chooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      ImageIcon ic = null;
      try {
        ic = new ImageIcon(chooser.getSelectedFile().toURL());
      }
      catch (MalformedURLException ex) {
      }
      if (fill instanceof pt.inescporto.template.client.design.TmplJLabel)
        ((TmplJLabel)fill).setIcon(ic);
      if (fill instanceof pt.inescporto.template.client.design.TmplImage)
        ((TmplImage)fill).setImage(ic);
    }
    if (returnVal == JFileChooser.CANCEL_OPTION) {
      if (fill instanceof pt.inescporto.template.client.design.TmplJLabel)
        ((TmplJLabel)fill).setIcon(null);
      if (fill instanceof pt.inescporto.template.client.design.TmplImage)
        ((TmplImage)fill).setImage(null);
    }
  }

  public void setTitle( String title ) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setFileFilter(TmplFileFilter ff) {
    chooser.setFileFilter(ff);
  }

  //
  // Methods from TemplateListener interface
  //

  public void tmplMode( TemplateEvent e ) {
    switch( e.getMode() ) {
      case TmplFormModes.MODE_SELECT:
        this.setEnabled(false);
        break;
      case TmplFormModes.MODE_INSERT:
        if( !(fill == null) && (fill) instanceof pt.inescporto.template.client.design.TmplJLabel) {
          if( ((TmplJLabel)(fill)).getLink() != null && ((TmplJLabel)(fill)).getLink().isLinkKey() )
            setEnabled(false);
          else
            setEnabled(true);
        }
        else
          setEnabled(true);
        break;
      case TmplFormModes.MODE_UPDATE:
        if( !(fill == null) && (fill) instanceof pt.inescporto.template.client.design.TmplJLabel ) {
          if( ((TmplJLabel)(fill)).getLink() != null && ((TmplJLabel)(fill)).getLink().isPrimaryKey() )
            setEnabled(false);
          else
            setEnabled(true);
        }
        else
          this.setEnabled(true);
        break;
      case TmplFormModes.MODE_FIND:
        if( !(fill == null) && (fill) instanceof pt.inescporto.template.client.design.TmplJLabel ) {
          if( ((TmplJLabel)(fill)).getLink() != null && ((TmplJLabel)(fill)).getLink().isLinkKey() )
            this.setEnabled(false);
          else
            this.setEnabled(true);
        }
        else
          this.setEnabled(true);
        break;
    }
  }

  public void showInformationMessage(String msg) {
    JOptionPane.showMessageDialog(this,
                                  msg, TmplResourceSingleton.getString("info.dialog.header"),
                                  JOptionPane.INFORMATION_MESSAGE);
  }

  private void jbInit() throws Exception {
  }
}
