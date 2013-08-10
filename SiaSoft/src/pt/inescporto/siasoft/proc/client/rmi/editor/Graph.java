package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.geom.Point2D;
import java.awt.Point;

import java.awt.Color;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.Port;
import org.jgraph.graph.VertexView;

import pt.inescporto.siasoft.proc.client.rmi.forms.ActivityHasPartDialog;
import pt.inescporto.siasoft.proc.ejb.dao.PartDao;
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
 *
 * */
public class Graph extends JGraph implements GraphicInterface, DropTargetListener {
  private ProcessEventInterface peListener;
  private DropTarget dropTarget = null;
  private JFrame frame;
  GraphicInterface wndGraphics;

  public Graph(DefaultGraphModel model, GraphLayoutCache view, ProcessEventInterface peListener) {
    super(model, view);
    this.peListener = peListener;
    // Register Graph with ToolTipManager
    ToolTipManager.sharedInstance().registerComponent(this);
    // Make Ports Visible by Default
    setPortsVisible(false);
    // Use the Grid (but don't make it Visible)
    setGridEnabled(true);
    // Set the Grid Size to 10 Pixel
    setGridSize(6);
    // Set the Tolerance to 2 Pixel
    setTolerance(2);
    // Accept edits if click on background
    setInvokesStopCellEditing(true);
    // Allows control-drag
    setCloneable(true);
    // Jump to default port on connect
    setJumpToDefaultPort(true);
    // Handle color for sizeable cells
    setHandleColor(Color.gray);
    //Set the Handle Size to 2 Pixel
    setHandleSize(2);
    // Set selection highlight color
    setHighlightColor(Color.lightGray);
    // Remove editing option on vertex
    setEditable(false);
    setIgnoreRepaint(true);
    //Allow drop operations
    setDropEnabled(true);
    dropTarget = new DropTarget(getGraph(), DnDConstants.ACTION_COPY_OR_MOVE, this);
    getGraphLayoutCache().setFactory(new DefaultCellViewFactory() {
      // CellViews for each type of cell
      protected VertexView createVertexView(Object cell) {
	if (cell instanceof ActivityCell)
	  return new ActivityCellView(cell);
	return new VertexView(cell);
      }
      protected EdgeView createEdgeView(Object cell) {
	if (cell instanceof Edge)
	  return new EdgeView(cell);
	return new EdgeView(cell);
      }
    });
  }
  public void addNode(Point2D point, GraphNode userObject, Integer w, Integer h, ImageIcon icIcon) {
    // Construct Vertex with no Label
    DefaultGraphCell vertex = (w == null && h == null) ? new ActivityCell(userObject, point) : new ActivityCell(userObject, point, w, h, icIcon);
    //Add defaultPort to vertex
    vertex.add(new DefaultPort());
    getGraphLayoutCache().insert(vertex);
  }

  // Insert a new Edge between source and target
  public Edge addEdge(Port source, Port target) {
    Edge e = new Edge();
    if (getModel().acceptsSource(e, source) && getModel().acceptsTarget(e, target)) {
      getGraphLayoutCache().insertEdge(e, source, target);
    }
    return e;
  }
  public InterlevelEdge addInterlevelEdge(Port source, Port target) {
    InterlevelEdge levelEdge = new InterlevelEdge();
    if (getModel().acceptsSource(levelEdge, source) && getModel().acceptsTarget(levelEdge, target)) {
      getGraphLayoutCache().insertEdge(levelEdge, source, target);
    }
    return levelEdge;
  }


  public Graph getGraph() {
    return this;
  }

  public void clearGraph() {
    DefaultGraphModel model = (DefaultGraphModel)getModel();
    model.remove(model.getAll(model));
  }

  public String getToolTipText(MouseEvent e) {
    if (e != null) {
      Object obj = getFirstCellForLocation(e.getX(), e.getY());
      if (obj instanceof ActivityCell)
	return ((ActivityCell)obj).getToolTipString();
    }
    return null;
  }

  public void dragEnter(DropTargetDragEvent e) {
    if (e.isDataFlavorSupported(TransferableComponent.userObjectFlavor)) {
      GraphNode object = null;
      try {
	object = (GraphNode)e.getTransferable().getTransferData(TransferableComponent.userObjectFlavor);

	if (peListener.getProcessID() == null) {

	  if (!object.isNeadsParent() || object.isAllLevels())
	    e.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
	  else
	    e.rejectDrag();
	}
	else {

	  if (object.isNeadsParent() || object.isAllLevels())
	    e.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
	  else
	    e.rejectDrag();
	}
      }
      catch (IOException ex) {
	e.rejectDrag();
      }
      catch (UnsupportedFlavorException ex) {
	e.rejectDrag();
      }
    }
    else
      if (e.isDataFlavorSupported(TransferableTreeNode.treeNodeFlavor)) {
	e.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
      }
      else {
	e.rejectDrag();
      }
  }

  public void drop(DropTargetDropEvent dtde) {
    if (dtde.isDataFlavorSupported(TransferableComponent.userObjectFlavor)) {
      dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
      GraphNode object = null;
      try {
	object = (GraphNode)dtde.getTransferable().getTransferData(TransferableComponent.userObjectFlavor);
      }
      catch (IOException ex) {
	ex.printStackTrace();
	dtde.rejectDrop();
      }
      catch (UnsupportedFlavorException ex) {
	ex.printStackTrace();
	dtde.rejectDrop();
      }
      if (object.getElementType().equals("C") || object.getElementType().equals("I")) {
	addNode(new Point((int)dtde.getLocation().getX(), (int)dtde.getLocation().getY()), object, (int)(ActivityCellView.renderer.getPreferredSize().getHeight()),
		(int)(ActivityCellView.renderer.getPreferredSize().getHeight()), object.getIcon());
      }
      if(object.getElementType().equals("L")){
        addInterlevelEdge(null, null);
      }

      if (object.getElementType().equals("A")) {
	addEdge(null, null);
      }

      dtde.getDropTargetContext().dropComplete(true);
    }
    else
      if (dtde.isDataFlavorSupported(TransferableTreeNode.treeNodeFlavor)) {
	dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
	DefaultMutableTreeNode treeNode = null;
	try {
	  treeNode = (DefaultMutableTreeNode)dtde.getTransferable().getTransferData(TransferableTreeNode.treeNodeFlavor);
	  Object object = getFirstCellForLocation(dtde.getLocation().getX(), dtde.getLocation().getY());
          DefaultGraphCell cell = (DefaultGraphCell) object;
          if (object != null && !(object instanceof Edge) && ((GraphNode)cell.getUserObject()).isHasProperties()) {

	    ActivityHasPartDialog ahpDialog = new ActivityHasPartDialog(frame, ((GraphNode)cell.getUserObject()).getId(), ((PartDao)treeNode.getUserObject()).partClassID.getValue(),
		((PartDao)treeNode.getUserObject()).partID.getValue());
	    ahpDialog.pack();
	    ahpDialog.setModal(true);
	    ahpDialog.setVisible(true);
	  }
	}
	catch (IOException ex) {
	}
	catch (UnsupportedFlavorException ex) {
	}
	dtde.getDropTargetContext().dropComplete(true);
      }
      else {
	dtde.rejectDrop();
      }
  }

  public void doDelete() {}

  public void dragOver(DropTargetDragEvent e) {}

  public void dropActionChanged(DropTargetDragEvent e) {}

  public void dragExit(DropTargetEvent e) {}

  public void undo() {}

  public void redo() {}

}
