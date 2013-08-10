package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;
import java.util.Hashtable;
import java.util.ArrayList;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Rute
 * @version 1.0
 */

public class Edge extends DefaultEdge {

  public Edge() {
    super();
    getAttributes().applyMap(createEdgeAttributes());
  }

  public Map createEdgeAttributes() {
    Map map = new Hashtable();
    GraphConstants.setOpaque(map, true);
    GraphConstants.setResize(map, true);
    //list of ordered points where an edge is connected by two lines
    ArrayList points = new ArrayList();
    points.add(new Point(20, 20));
    points.add(new Point(60, 20));
    GraphConstants.setPoints(map, points);
    // Add a Line End Attribute
    GraphConstants.setLineEnd(map, GraphConstants.ARROW_CLASSIC);
    //setting the color line to gray
    GraphConstants.setLineColor(map, Color.gray);
    // Add a Fill End attribute
    GraphConstants.setEndFill(map, true);
    //restrict the interactive ( using a mouse ) addition or removal of control points
    GraphConstants.setBendable(map, true);
    GraphConstants.setLineStyle(map, GraphConstants.ARROW_SIMPLE);
    return map;
  }
}
