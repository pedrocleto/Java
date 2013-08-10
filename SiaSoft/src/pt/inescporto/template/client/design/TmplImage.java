package pt.inescporto.template.client.design;

import pt.inescporto.template.client.event.DataSourceListener;
import javax.swing.JLabel;
import pt.inescporto.template.elements.TplObjRef;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.util.TmplPerms;
import javax.swing.JComponent;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import pt.inescporto.template.client.event.TemplateComponentListener;

/**
 * <p>Title: TeleMaint</p>
 * <p>Description: Sistema de Telemanutenção Remota</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class TmplImage extends JLabel implements DataSourceListener, TemplateComponentListener, TmplGetter {
  protected ImageIcon image = null;
  protected JLabel jlblHolder = null;
  protected String field;
  protected TplObjRef link = null;
  protected int fieldPerm = 15;
  protected String fieldPermName = null;

  public TmplImage() {
    super();
  }

  public TmplImage(String text, String field) {
    super(text);
    this.field = field;
    this.setFont(new Font("Dialog", 0, 10));
  }

  public void paint(Graphics g) {
    super.paint(g);
    if (image != null) {
      Graphics2D g2d = (Graphics2D)g;
      AffineTransform tx = new AffineTransform();

      double dx = getSize().getWidth() - getBorder().getBorderInsets(this).left - getBorder().getBorderInsets(this).right;
      double dy = getSize().getHeight() - getBorder().getBorderInsets(this).top - getBorder().getBorderInsets(this).bottom;

      double dxImage = image.getIconWidth();
      double dyImage = image.getIconHeight();

      // calculate scale and center the image
      double scale = 1;
      if (dxImage >= dyImage) {
        scale = dx / dxImage;
        tx.translate(getBorder().getBorderInsets(this).left, (dy - scale * dyImage) / 2 + ((dxImage == dyImage) ? getBorder().getBorderInsets(this).top : 0));
      }
      else {
        scale = dy / dyImage;
        tx.translate((dx - scale * dxImage) / 2, getBorder().getBorderInsets(this).top);
      }

      tx.scale(scale, scale);

      g2d.drawImage(image.getImage(), tx, this);
    }
  }

  public void setImage(ImageIcon image) {
    this.image = image != null ? new ImageIcon(image.getImage()) : null;
    this.repaint();
  }

  public void setLink(TplObjRef link) {
    this.link = link;
//    this.setText(this.link.getField());
    if (this.jlblHolder != null && this.link.isRequired()) {
      Font f = this.jlblHolder.getFont();
      this.jlblHolder.setFont(new Font(f.getName(), f.getStyle() | Font.BOLD, f.getSize()));
    }
  }

  public TplObjRef getLink() {
    return this.link;
  }

  public String getField() {
    return this.field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public void setHolder(JLabel holder) {
    jlblHolder = holder;
  }

  public JLabel getHolder() {
    return jlblHolder;
  }

  //
  // Methods from TmplGetter interface
  //

  public String getLabelName() {
    if (this.jlblHolder != null)
      return this.jlblHolder.getText();
    else
      return getText();
  }

  //
  // Methods from TemplateListener interface
  //

  public void tmplInitialize(TemplateEvent e) {
  }

  public void tmplMode(TemplateEvent e) {
    switch (e.getMode()) {
      case TmplFormModes.MODE_SELECT:
        setEnabled(true);
        break;
      case TmplFormModes.MODE_INSERT:
        if (link != null && (int)(fieldPerm & TmplPerms.PERM_INSERT) != 0) {
          if (link.isLinkKey()) {
            setEnabled(true);
          }
          else {
            setEnabled(true);
            setText("");
            image = null;
            link.resetValue();
          }
        }
        break;
      case TmplFormModes.MODE_UPDATE:
        if (link != null && !link.isPrimaryKey() && (int)(fieldPerm & TmplPerms.PERM_CHANGE) != 0)
          setEnabled(true);
        break;
      case TmplFormModes.MODE_FIND:
        if (link == null || !(link.isLinkKey())) {
          setEnabled(true);
          setText("");
          image = null;
          if (link != null)
            link.resetValue();
        }
        break;
    }
  }

  public void tmplEnable(TemplateEvent e) {
    setEnabled(e.getEnabled());
  }

  public void tmplRefresh(TemplateEvent e) {
    try {
      if (link != null) {
        setImage((ImageIcon)link.getValue());
      }
    }
    catch (Exception ex) {
      setText("");
    }
  }

  public void tmplSave(TemplateEvent e) {
    if (link != null) {
      if (image == null)
        link.resetValue();
      else
        link.setValue(image);
    }
  }

  public boolean tmplRequired(TemplateEvent e) {
    String value = getText();
    if (link != null && link.isRequired() && !link.isGenKey() && value.equals("")) {
      requestFocus();
      e.setCompFailed(this);
      return false;
    }
    else
      return true;
  }

  public boolean tmplValidate(TemplateEvent e) {
    return true;
  }

  public void tmplPermissions(TemplateEvent e) {
    // field permissions
    if (fieldPermName != null && e.getPermissionForField(fieldPermName) != -1)
      fieldPerm = e.getPermissionForField(fieldPermName);
  }

  public void tmplLink(TemplateEvent e) {
    if (e.getLink().getField().equals(field))
      link = (TplObjRef)e.getLink();
  }

  public JComponent tmplGetComponent() {
    return this;
  }

  public void tmplDispose(TemplateEvent e) {
  }
}
