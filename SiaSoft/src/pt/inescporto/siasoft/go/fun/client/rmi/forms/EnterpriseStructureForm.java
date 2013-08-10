package pt.inescporto.siasoft.go.fun.client.rmi.forms;

import javax.swing.JInternalFrame;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.DefaultEdge;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.siasoft.go.fun.ejb.session.EnterpriseCell;
import javax.naming.*;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import java.rmi.RemoteException;
import java.util.Collection;
import pt.inescporto.siasoft.go.fun.ejb.dao.EnterpriseCellDao;
import java.util.Hashtable;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.UserException;
import org.jgraph.layout.JGraphLayoutAlgorithm;
import org.jgraph.layout.SugiyamaLayoutAlgorithm;
import java.awt.Point;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.siasoft.MenuFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.ResourceBundle;
import java.beans.*;
import pt.inescporto.template.client.rmi.FW_InternalFrameBasic;

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
public class EnterpriseStructureForm extends FW_InternalFrameBasic {
   static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.fun.client.rmi.forms.FormResources");

  public EnterpriseStructureForm() {
    buildDiagram();
  }

  public void buildDiagram() {
    Hashtable<String, EnterpriseCellDao> hashEcell = new Hashtable<String,EnterpriseCellDao>();
    try {
      hashEcell = readStructure();
    }
    catch (UserException ex) {
    }
    catch (RowNotFoundException ex) {
    }
    catch (RemoteException ex) {
    }
    catch (NamingException ex) {
    }

    GraphModel model = new DefaultGraphModel();
    GraphLayoutCache view = new GraphLayoutCache(model, new DefaultCellViewFactory());
    JGraph graph = new JGraph(model, view);
    DefaultGraphCell[] cells = new DefaultGraphCell[hashEcell.size()];
    Collection<EnterpriseCellDao> col = hashEcell.values();
    int i = 0;
    for (EnterpriseCellDao cell : col) {
      cells[i] = new DefaultGraphCell(cell);
      GraphConstants.setBounds(cells[i].getAttributes(), new Rectangle2D.Double(20, 20, 40, 20));
      GraphConstants.setAutoSize(cells[i].getAttributes(), true);
      GraphConstants.setGradientColor(cells[i].getAttributes(), Color.orange);
      GraphConstants.setOpaque(cells[i].getAttributes(), true);
      cells[i].add(new DefaultPort());
      ++i;
    }
    i = 0;
    Vector<DefaultEdge> edges = new Vector();
    for (EnterpriseCellDao cell : col) {
      if (cell.fatherCellID.getValue() != null) {
        for (i = 0; i < cells.length; i++) {
          if (cells[i].getUserObject().equals(cell)) {
//            System.out.println("Found my cell!");
//            System.out.println("FatherID is <" + cell.fatherCellID.getValue() + ">");
            EnterpriseCellDao fatherCell = hashEcell.get(cell.fatherCellID.getValue());
            for (int j = 0; j < cells.length; j++) {
              if (cells[j].getUserObject().equals(fatherCell)) {
//                System.out.println("Found father cell!");
                DefaultEdge edge = new DefaultEdge();
                GraphConstants.setRouting(edge.getAttributes(), GraphConstants.ROUTING_SIMPLE);
                edge.setSource(cells[j].getChildAt(0));
                edge.setTarget(cells[i].getChildAt(0));
                edges.add(edge);
                break;
              }
            }
            break;
          }
        }
      }
    }
    graph.getGraphLayoutCache().insert(cells);
    graph.getGraphLayoutCache().insert(edges.toArray());

    JGraphLayoutAlgorithm graphLayout = new SugiyamaLayoutAlgorithm();
    ((SugiyamaLayoutAlgorithm)graphLayout).setSpacing(new Point(125, 125));
    ((SugiyamaLayoutAlgorithm)graphLayout).setVertical(true);

    Object[] cellss = DefaultGraphModel.getAll(graph.getModel());
    try {
      if (MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise().equals(null) : MenuSingleton.getEnterprise().equals(null)) {}
      try{
	JGraphLayoutAlgorithm.applyLayout(graph, graphLayout, cellss, null);
	graph.setEditable(false);
	graph.setAntiAliased(true);
	graph.setEnabled(false);
	graph.setSelectionCell(new Object());
	add(new JScrollPane(graph));
	hasVisualPerms = true;
      }
      catch (Exception ex) {
	JFrame frame = new JFrame();
	JOptionPane.showMessageDialog(frame,
				      res.getString("label.doesntExist"),
				      res.getString("label.warning"),
				      JOptionPane.WARNING_MESSAGE);

	hasVisualPerms = false;
      }
    }
    catch (NullPointerException ex) {
     JFrame frame = new JFrame();
     JOptionPane.showMessageDialog(frame,
                                   res.getString("label.chooseEnterprise"),
                                   res.getString("label.warning"),
                                   JOptionPane.WARNING_MESSAGE);

    hasVisualPerms=false;
   }


  }

  private Hashtable<String, EnterpriseCellDao> readStructure() throws NamingException, RemoteException, RowNotFoundException, UserException {
    EnterpriseCell eCell = (EnterpriseCell)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.fun.ejb.session.EnterpriseCell");

    Vector binds = new Vector();

      binds.add(new TplString(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()));
      eCell.setLinkCondition("enterpriseID = ?", binds);

    Collection<EnterpriseCellDao> eCellList = eCell.listAll();

    Hashtable<String, EnterpriseCellDao> hashEcell = new Hashtable<String,EnterpriseCellDao>();
    for (EnterpriseCellDao eCellDao : eCellList) {
      hashEcell.put(eCellDao.enterpriseCellID.getValue(), eCellDao);
    }

    return hashEcell;
  }
}
