package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.Font;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

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
public class ActivityCell extends DefaultGraphCell{
  public ActivityCell() {
     this(null, null, null, null,null);
   }
   public ActivityCell(Object userObject, Point2D point) {
     this(userObject, point, null, null, null);
   }
   public ActivityCell(Object userObject, Point2D point, Integer w, Integer h, ImageIcon icon) {
    super(userObject);
    getAttributes().applyMap(createCellAttributes(point, w, h, icon));
  }
   public Map createCellAttributes(Point2D point, Integer w, Integer h, ImageIcon icon) {
     Map map = new Hashtable();
     GraphConstants.setResize(map,false);
     GraphConstants.setAutoSize(map,false);
     // Add a Bounds Attribute to the Map
     GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(), point.getY(),w == null ? 0 : w, h == null ? 0 : h));
     GraphConstants.setIcon(map,icon);
     GraphConstants.setVerticalTextPosition(map, SwingConstants.TOP);
     GraphConstants.setFont(map, new Font("Dialog", Font.PLAIN, 10));
     GraphConstants.setLineWidth(map, 2.0f);
     GraphConstants.setOpaque(map,false);

     return map;
    }
    public String getToolTipString() {
      return toString();
    }

}
