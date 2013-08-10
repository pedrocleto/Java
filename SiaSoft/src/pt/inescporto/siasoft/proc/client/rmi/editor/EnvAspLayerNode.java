package pt.inescporto.siasoft.proc.client.rmi.editor;

import javax.swing.*;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class EnvAspLayerNode {
  private JPanel jpnlLayer = null;
  private ImageIcon icIcon = null;

  public EnvAspLayerNode() {
  }

  public EnvAspLayerNode(JPanel jpnlLayer, ImageIcon icIcon) {
    this.jpnlLayer = jpnlLayer;
    this.icIcon = icIcon;
  }

  public ImageIcon getIcIcon() {
    return icIcon;
  }

  public JPanel getJpnlLayer() {
    return jpnlLayer;
  }

  public void setJpnlLayer(JPanel jpnlLayer) {
    this.jpnlLayer = jpnlLayer;
  }

  public void setIcIcon(ImageIcon icIcon) {
    this.icIcon = icIcon;
  }
}
