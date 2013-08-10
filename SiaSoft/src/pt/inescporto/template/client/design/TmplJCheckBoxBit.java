package pt.inescporto.template.client.design;

import javax.swing.JCheckBox;
import java.awt.Font;

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
public class TmplJCheckBoxBit extends JCheckBox {
  protected int bit;

  public TmplJCheckBoxBit() {
    setFont(new Font("Dialog", 0, 10));
  }

  public TmplJCheckBoxBit(String label, int bit) {
    setText(label);
    this.bit = bit;
    setFont(new Font("Dialog", 0, 10));
  }

  public void setLabel(String label) {
    setText(label);
  }

  public String getLabel() {
    return getText();
  }

  public void setBit(int bit) {
    this.bit = bit;
  }

  public int getBit() {
    return bit;
  }
}
