package pt.inescporto.template.client.design.table;

import java.awt.Component;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import com.sun.java.swing.SwingUtilities2;
import java.awt.geom.AffineTransform;
import java.awt.FontMetrics;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.*;

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
public class TmplProgressBarRender extends JProgressBar implements TableCellRenderer {
  DecimalFormat df = new DecimalFormat("#00.00");
  boolean isBordered = true;
  float currentValue = 0;

  public TmplProgressBarRender() {
    super();
    setFont(new Font("Dialog", Font.PLAIN, 10));
    setBorder(BorderFactory.createEmptyBorder());
    setOpaque(true);
    setMaximum(100);
    setMinimum(0);
    setStringPainted(false);
  }

  public void paint(Graphics g) {
    super.paint(g);

    Insets b = getInsets(); // area for border
    int barRectWidth = getWidth() - (b.right + b.left);
    int barRectHeight = getHeight() - (b.top + b.bottom);
    // amount of progress to draw
    int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

    paintString(g, b.left, b.top, barRectWidth, barRectHeight, amountFull, b);
  }

  private void paintString(Graphics g, int x, int y,
			   int width, int height,
			   int amountFull, Insets b) {
    if (getOrientation() == JProgressBar.HORIZONTAL) {
      if (getComponentOrientation().isLeftToRight()) {
	paintString(g, x, y, width, height, x, amountFull, b);
      }
      else {
	paintString(g, x, y, width, height, x + width - amountFull, amountFull, b);
      }
    }
    else {
      paintString(g, x, y, width, height, y + height - amountFull, amountFull, b);
    }
  }

  private void paintString(Graphics g, int x, int y, int width, int height, int fillStart, int amountFull, Insets b) {
    if (!(g instanceof Graphics2D)) {
      return;
    }

    Graphics2D g2 = (Graphics2D)g;
    String progressString = null;
    progressString = df.format(currentValue) + " %";
    g2.setFont(getFont());
    Point renderLocation = getStringPlacement(g2, progressString,
					      x, y, width, height);
    Rectangle oldClip = g2.getClipBounds();

    if (getOrientation() == JProgressBar.HORIZONTAL) {
      g2.setColor(getSelectionBackground());
      SwingUtilities2.drawString(this, g2, progressString, renderLocation.x, renderLocation.y);
      g2.setColor(getSelectionForeground());
      g2.clipRect(fillStart, y, amountFull, height);
      SwingUtilities2.drawString(this, g2, progressString, renderLocation.x, renderLocation.y);
    }
    else { // VERTICAL
      g2.setColor(getSelectionBackground());
      AffineTransform rotate = AffineTransform.getRotateInstance(Math.PI / 2);
      g2.setFont(getFont().deriveFont(rotate));
      renderLocation = getStringPlacement(g2, progressString, x, y, width, height);
      SwingUtilities2.drawString(this, g2, progressString, renderLocation.x, renderLocation.y);
      g2.setColor(getSelectionForeground());
      g2.clipRect(x, fillStart, width, amountFull);
      SwingUtilities2.drawString(this, g2, progressString, renderLocation.x, renderLocation.y);
    }
    g2.setClip(oldClip);
  }

  protected Point getStringPlacement(Graphics g, String progressString, int x, int y, int width, int height) {
    FontMetrics fontSizer = SwingUtilities2.getFontMetrics(this, g, getFont());
    int stringWidth = SwingUtilities2.stringWidth(this, fontSizer, progressString);

    if (getOrientation() == JProgressBar.HORIZONTAL) {
      return new Point(x + Math.round(width / 2 - stringWidth / 2), y + ((height + fontSizer.getAscent() - fontSizer.getLeading() - fontSizer.getDescent()) / 2));
    }
    else { // VERTICAL
      return new Point(x + ((width - fontSizer.getAscent() + fontSizer.getLeading() + fontSizer.getDescent()) / 2), y + Math.round(height / 2 - stringWidth / 2));
    }
  }

  private Color getSelectionForeground() {
    return UIManager.getColor("ProgressBar.selectionForeground");
  }

  private Color getSelectionBackground() {
    return UIManager.getColor("ProgressBar.selectionBackground");
  }

  protected int getAmountFull(Insets b, int width, int height) {
    int amountFull = 0;
    BoundedRangeModel model = getModel();

    if ((model.getMaximum() - model.getMinimum()) != 0) {
      if (getOrientation() == JProgressBar.HORIZONTAL) {
	amountFull = (int)Math.round(width * getPercentComplete());
      }
      else {
	amountFull = (int)Math.round(height * getPercentComplete());
      }
    }
    return amountFull;
  }

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    if (value == null) {
      setValue(0);
      return this;
    }
    if (value instanceof Float) {
      Float f = (Float)value;
      setValue(f.intValue());
      currentValue = f;
    }

    return this;
  }
}
