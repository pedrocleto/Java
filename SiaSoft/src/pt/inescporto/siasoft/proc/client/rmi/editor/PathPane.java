package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJLabel;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author Rute
 * @version 0.1
 */
public class PathPane extends JPanel {
  private TmplJLabel lblName = new TmplJLabel();
  public PathPane() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    setOpaque(true);
    add(lblName, BorderLayout.CENTER);
    lblName.setFont(new Font("Dialog", Font.PLAIN, 10));
  }

  public TmplJLabel getLblName() {
    return lblName;
  }

  public void setLblName(TmplJLabel lblName) {
    this.lblName = lblName;
  }
}
