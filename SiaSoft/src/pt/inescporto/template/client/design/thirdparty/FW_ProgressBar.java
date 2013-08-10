package pt.inescporto.template.client.design.thirdparty;

import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

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
public class FW_ProgressBar extends BasicProgressBarUI implements ActionListener {
  private Color startColor = new Color(10, 36, 106);
  private Color endColor = new Color(192, 192, 192);

  private int x = 0, y = 0, delta = +1;
  private Timer timer = null;

  protected void startAnimationTimer() {
    if (timer == null)
      timer = new Timer(10, this);
    x = y = 0;
    delta = +1;
    timer.start();
  }

  protected void stopAnimationTimer() {
    timer.stop();
  }

  public void actionPerformed(ActionEvent ae) {
    // style1
    if (x == 0)
      delta = +1;
    else
      if (x == progressBar.getWidth())
	delta = -1;
    x += delta;

    progressBar.repaint();
  }

  public void paintIndeterminate(Graphics g, JComponent c) {
    if (delta > 0) {
      GradientPaint redtowhite = new GradientPaint(0, 0, endColor, x, 0, startColor, false);
      ((Graphics2D)g).setPaint(redtowhite);
      ((Graphics2D)g).fill(new RoundRectangle2D.Double(0, 0, x, progressBar.getHeight() - 1, 0, 0));
    }
    else {
      GradientPaint redtowhite = new GradientPaint(x, 0, startColor, progressBar.getWidth(), 0, endColor, false);
      ((Graphics2D)g).setPaint(redtowhite);
      ((Graphics2D)g).fill(new RoundRectangle2D.Double(x, 0, progressBar.getWidth() - x, progressBar.getHeight() - 1, 0, 0));
    }
  }
}
