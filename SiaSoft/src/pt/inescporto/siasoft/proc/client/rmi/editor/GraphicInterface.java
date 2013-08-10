package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.geom.Point2D;
import org.jgraph.graph.Port;
import javax.swing.ImageIcon;

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

public interface GraphicInterface {

  public void addNode(Point2D point, GraphNode userObject, Integer width, Integer height, ImageIcon icon);

  public Edge addEdge(Port source, Port target);

  public void doDelete();

  public void undo();

  public void redo();

  public InterlevelEdge addInterlevelEdge(Port source, Port target);
}
