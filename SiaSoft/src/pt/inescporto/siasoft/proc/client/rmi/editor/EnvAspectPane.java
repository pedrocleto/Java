package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Collection;

import javax.ejb.FinderException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.naming.NamingException;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultEdge;

import pt.inescporto.siasoft.go.aa.ejb.dao.EnvAspClassDao;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvironmentAspectDao;
import pt.inescporto.siasoft.proc.ejb.session.Activity;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityDao;
import pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.event.EventSynchronizerWatcher;
import pt.inescporto.template.client.event.EventSynchronizerWatcherRemover;
import pt.inescporto.template.client.event.EventSynchronizer;
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
public class EnvAspectPane extends JPanel implements EventSynchronizerWatcher, EventSynchronizerWatcherRemover {
  protected DataSourceRMI datasource = null;
  protected FW_JTable fwEASList = null;
  protected Hashtable<String, EnvAspLayerNode> layers = new Hashtable<String, EnvAspLayerNode>();
  private GraphPanel graphPanel = null;
  private Graph graph = null;
  private JPanel jpnlLayer = null;
  private String subject = null;
  JLayeredPane jlayeredPane;

  public EnvAspectPane(GraphPanel graphPanel) {
    this.graphPanel = graphPanel;
    datasource = new DataSourceRMI("EnvAspClass");
    datasource.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvAspClass");
    try {
      datasource.initialize();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       "Icon",
					       "palletIcon",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(1,
					       "Description",
					       "envAspClassDescription",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    fwEASList = new FW_JTable(datasource, null, colManager) {
      public void valueChanged(ListSelectionEvent e) {
	super.valueChanged(e);
	if (mode == TmplFormModes.MODE_SELECT && !e.getValueIsAdjusting() && getSelectedRow() != -1) {
	  JLayeredPane jlayeredPane = graphPanel.getJlayeredPane();
	  // clear all components from every layer
	  for (EnvAspLayerNode node : layers.values()) {
	    node.getJpnlLayer().removeAll();
	    node.getJpnlLayer().validate();
	    node.getJpnlLayer().repaint();
	  }
	  EnvAspClassDao attrs = (EnvAspClassDao)tm.getAttrsAt(getSelectedRow());
	  try {
//	    System.out.println("Row selected changed!");
	    graph = graphPanel.getGraph();
	    Object[] cellList = graph.getRoots();
	    for (int i = 0; i < cellList.length; i++) {

	      if (cellList[i] instanceof DefaultGraphCell && !(cellList[i] instanceof DefaultEdge)) {
		DefaultGraphCell cell = (DefaultGraphCell)cellList[i];
		String activityID = ((GraphNode)cell.getUserObject()).getId();
		String envAspClassID = attrs.envAspClassID.getValue();

		ActivityDao actDao = null;
		try {
		  EnvironmentAspect envAspect = (EnvironmentAspect)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
		  for (EnvironmentAspectDao envDao : envAspect.getEnvAspTree(activityID, envAspClassID)) {
//		    System.out.println("Environmental Aspect <" + envDao.envAspectName.getValue() + "> is in sub-tree");

		    Activity activities = null;
		    try {
		      activities = (Activity)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.Activity");
		    }
		    catch (NamingException ex) {
		      JOptionPane.showMessageDialog(graph, "Não encontro Activity EJB!!!", "ERRO", JOptionPane.ERROR_MESSAGE);
		    }

		    ActivityDao aDao = new ActivityDao();
		    aDao.activityID.setValue(((GraphNode)cell.getUserObject()).getId());

		    Iterator<ActivityDao> actList = null;
		    try {
		      actList = (Iterator<ActivityDao>)activities.findExact(aDao).iterator();
		    }
		    catch (RowNotFoundException ex) {
		    }
		    catch (FinderException ex) {
		      JOptionPane.showMessageDialog(graph, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		    }
		    catch (RemoteException ex) {
		      JOptionPane.showMessageDialog(graph, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		    }
		    actDao = actList.next();

		    // get parameters for layer given the class id key
		    EnvAspLayerNode node = layers.get(envDao.envAspectClassID.getValue());

                    jlayeredPane.moveToFront(graph);
                    jlayeredPane.moveToFront(node.getJpnlLayer());

		    ImageIcon icon = node.getIcIcon();
		    int x = actDao.posX.getValue() + (actDao.dx.getValue() / 3);
		    int y = actDao.posY.getValue() - 35;

                    JLabel jlblIcon = new JLabel(icon);
		    jlblIcon.setOpaque(false);
                    jlblIcon.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
		    node.getJpnlLayer().add(jlblIcon);
		  }
		}
		catch (NamingException ex) {
		  ex.printStackTrace();
		}
		catch (RemoteException ex) {
		  ex.printStackTrace();
		}
		catch (RowNotFoundException ex) {
		}
		catch (UserException ex) {
		  ex.printStackTrace();
		}
	      }
	    }
	    fwEASList.clearSelection();
	    datasource.setAttrs(attrs);
	    datasource.refresh(getModel());
	  }
	  catch (DataSourceException ex) {
	    ex.printStackTrace();
	  }
	}
      }
    };
    fwEASList.setRowHeight(55);
    fwEASList.setFont(new Font("Dialog", Font.PLAIN, 10));
    fwEASList.setTableHeader(null);
    fwEASList.setShowGrid(false);
    fwEASList.setShowHorizontalLines(false);
    fwEASList.setShowVerticalLines(false);
    fwEASList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane jsp = new JScrollPane(fwEASList);
    jsp.setPreferredSize(new Dimension((int)jsp.getPreferredSize().getWidth() / 3, (int)jsp.getPreferredSize().getHeight()));
    add(jsp, BorderLayout.CENTER);
    fwEASList.start();
    JLayeredPane jlayeredPane = graphPanel.getJlayeredPane();
    Collection<EnvAspClassDao> classList = datasource.listAll();
    for (EnvAspClassDao classID : classList) {
      jpnlLayer = new JPanel(null);
      jpnlLayer.setSize(900, 900);
      jpnlLayer.setOpaque(false);
      layers.put(classID.envAspClassID.getValue(), new EnvAspLayerNode(jpnlLayer, (ImageIcon)classID.graphGraph.getValue()));
      jlayeredPane.add(jpnlLayer, JLayeredPane.PALETTE_LAYER);
    }
  }

  public void setWatcherSubject(String subject) {
    eventSynchronizerTriggered(this.subject);
    this.subject = subject;
    EventSynchronizer.getInstance().addEventSynchronizerWatcher(this, subject);
  }

  public FW_JTable getFwEASList() {
    return fwEASList;
  }

  public Hashtable getLayers() {
    return layers;
  }

  /**
   * ***************************************************************************
   *                  Implementation of the EventSynchronizerWatcher
   * ***************************************************************************
   */
  public void eventSynchronizerTriggered(String subject) {
    if (this.subject != null && this.subject.equals(subject)) {
      try {
	datasource.refresh();
      }
      catch (DataSourceException ex1) {
      }
    }
  }

  /**
   * ***************************************************************************
   *               Implementation of the EventSynchronizerWatcherRemover
   * ***************************************************************************
   */
  public void removeEventSynchronizer() {
    if (subject != null) {
      EventSynchronizer.getInstance().removeEventSynchronizerWatcher(this, subject);
    }
  }

}
