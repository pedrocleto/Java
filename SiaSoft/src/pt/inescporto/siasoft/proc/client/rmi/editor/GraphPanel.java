package pt.inescporto.siasoft.proc.client.rmi.editor;

import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Component;

import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
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
public class GraphPanel extends JPanel implements ComponentListener{
  private JLayeredPane jlayeredPane = null;
  private Graph graph = null;
  private ToolBarGraph toolBarGraph = null;
  private PathPane pathPane = null;
  private JPanel panel = null;
  private JScrollPane jsp = null;
  private JScrollPane jspLayered = null;

  public GraphPanel(ProcessEventInterface peListener, ModuleListener mListener) {
    super(new BorderLayout());

    panel = new JPanel();
    panel.setLayout(new BorderLayout());
    add(panel, BorderLayout.NORTH);

    graph = new Graph(new DefaultGraphModel(), new GraphLayoutCache(), peListener);

    // JLayeredPane
    jlayeredPane = new JLayeredPane();
    jlayeredPane.addComponentListener(this);
    jlayeredPane.setPreferredSize(new Dimension(1500, 1500));
    jlayeredPane.add(graph, JLayeredPane.DEFAULT_LAYER);
    jspLayered = new JScrollPane(jlayeredPane);
    jspLayered = new JScrollPane();
    jspLayered.getViewport().add(jlayeredPane);
    jspLayered.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    jspLayered.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    add(jspLayered, BorderLayout.CENTER);
  }

  public void componentResized(ComponentEvent e) {
    Component c = e.getComponent();
    graph.setSize(c.getSize());
    graph.validate();
    c.repaint();
  }
  public void componentMoved(ComponentEvent e) {}

  public void componentShown(ComponentEvent e) {}

  public void componentHidden(ComponentEvent e) {}

  public JLayeredPane getLayerPane() {
    return jlayeredPane;
  }

  public Graph getGraph() {
    return graph;
  }

  public ToolBarGraph getToolBarGraph() {
    return toolBarGraph;
  }

  public JLayeredPane getJlayeredPane() {
    return jlayeredPane;
  }

  public PathPane getPathPane() {
    return pathPane;
  }

  public void setGraph(Graph graph) {
    this.graph = graph;
  }

  public void setToolBarGraph(ToolBarGraph toolBarGraph) {
    this.toolBarGraph = toolBarGraph;
  }

  public void setJlayeredPane(JLayeredPane jlayeredPane) {
    this.jlayeredPane = jlayeredPane;
  }
}
