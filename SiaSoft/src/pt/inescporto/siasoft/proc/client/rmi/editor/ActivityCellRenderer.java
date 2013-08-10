package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.geom.Area;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;

import org.jgraph.graph.VertexRenderer;

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
public class ActivityCellRenderer extends VertexRenderer {
  private static Area areaRec;

  public ActivityCellRenderer() {
    super();
    setPreferredSize(new Dimension(70, 70));
  }

  public void paint(Graphics g) {
    int b = borderWidth;
    Dimension d = getSize();
    int height = d.height - b;
    int width = d.width - b;

    Graphics2D g2 = (Graphics2D)g;

    boolean tmp = selected;
    if (super.isOpaque()) {
      g.setColor(super.getBackground());
      g2.drawRect(b / 2, b / 2, width, height);
    }
    try {
      setBorder(null);
      setOpaque(false);
      selected = false;
      super.paint(g);
    }
    finally {
      selected = tmp;
    }
    if (bordercolor != null) {
      g.setColor(bordercolor);
      g2.setStroke(new BasicStroke(b));
      g2.drawRect(b / 2, b / 2, width, height);
    }
    if (selected) {
      g2.setStroke(new BasicStroke(b));
      g.setColor(highlightColor);
      g2.drawRect(b / 2, b / 2, width, height);
    }
  }
}
