package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.geom.Point2D;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.VertexView;

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
public class ActivityCellView extends VertexView {

  public static ActivityCellRenderer renderer = new ActivityCellRenderer();

  public ActivityCellView() {
    super();
  }

  public ActivityCellView(Object object) {
    super(object);
  }

  public CellViewRenderer getRenderer() {
    return renderer;
  }

  public Point2D getPerimeterPoint(EdgeView edge, Point2D source, Point2D p) {
    if (getRenderer() instanceof ActivityCellRenderer)
      return ((ActivityCellRenderer)getRenderer()).getPerimeterPoint(this, source, p);
    return super.getPerimeterPoint(edge, source, p);
  }
}
