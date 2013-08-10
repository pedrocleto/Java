package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.BorderLayout;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.table.FW_TableModel;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityTypeDao;
import pt.inescporto.template.client.event.EventSynchronizerWatcher;
import pt.inescporto.template.client.event.EventSynchronizerWatcherRemover;
import pt.inescporto.template.client.event.EventSynchronizer;
import javax.swing.ToolTipManager;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;

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
class ItemTypeList extends JPanel implements DragGestureListener, DragSourceListener, EventSynchronizerWatcher, EventSynchronizerWatcherRemover{
  private DragSource dragSource = null;
  protected DataSourceRMI datasource = null;
  protected FW_JTable fwActList = null;
  private String subject = null;
  private Map<Integer, String> tooltipMap = new HashMap<Integer, String>();

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");

  public ItemTypeList() {
    datasource = new DataSourceRMI("ActivityTypeClass");
    datasource.setUrl("pt.inescporto.siasoft.proc.ejb.session.ActivityType");
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
    loadActivityTypes();

    setBorder(BorderFactory.createTitledBorder(res.getString("itemTypeList.label.title")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
    setLayout(new BorderLayout());
    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("itemTypeList.label.icon"),
					       "palletIcon",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    fwActList = new FW_JTable(datasource, null, colManager){
      public String getToolTipText(MouseEvent e) {
	String result = null;
	for (int i = 0; i < fwActList.getRowCount(); i++) {
	  if (fwActList.contains(e.getX(), e.getY())) {
	    int pos = fwActList.rowAtPoint(e.getPoint());
	    result = tooltipMap.get(pos);
	    break;
	  }
	}
	return result;
      }
    };
    fwActList.setRowHeight(55);
    fwActList.setTableHeader(null);
    fwActList.setShowHorizontalLines(false);
    fwActList.setShowVerticalLines(false);
    fwActList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    fwActList.clearSelection();
    add(fwActList, BorderLayout.CENTER);
    fwActList.start();
    fwActList.setDragEnabled(true);
    fwActList.setTransferHandler(new TransferHandler(null));
    ToolTipManager.sharedInstance().registerComponent(this);
    dragSource = new DragSource();
    dragSource.createDefaultDragGestureRecognizer(fwActList, DnDConstants.ACTION_COPY_OR_MOVE, this);
  }

  public void setWatcherSubject(String subject) {
    eventSynchronizerTriggered(this.subject);
    this.subject = subject;
    EventSynchronizer.getInstance().addEventSynchronizerWatcher(this, subject);
  }


  public void dragGestureRecognized(DragGestureEvent e) {
    if (fwActList.getSelectedRow() != -1) {
      ActivityTypeDao element = (ActivityTypeDao)((FW_TableModel)fwActList.getModel()).getAttrsAt(fwActList.getSelectedRow());
      if (element != null) {
	GraphNode uo = new GraphNode(null);
	uo.setIcon((ImageIcon)element.graphGraph.getValueAsObject());
	uo.setElementType(element.elementType.getValue());
	uo.setType(element.activityTypeID.getValue());
	uo.setName(element.activityTypeDescription.getValue());
	uo.setNeadsParent(element.neadsParent.getValue());
	uo.setNoChildren(element.noChildren.getValue());
	uo.setAllLevels(element.allLevels.getValue());
	uo.setHasProperties(element.hasLinks.getValue() == null ? false : element.hasLinks.getValue());
	TransferableComponent tc = new TransferableComponent(uo);
	dragSource.startDrag(e, DragSource.DefaultMoveDrop, tc, this);
      }
    }
  }
  private void loadActivityTypes() {
   DataSourceRMI datasource = new DataSourceRMI("ActivityTypeClass");
   datasource.setUrl("pt.inescporto.siasoft.proc.ejb.session.ActivityType");
   int i=0;
   try {
     datasource.initialize();
     for (ActivityTypeDao atDao : (Collection<ActivityTypeDao>)datasource.listAll()) {
        String toolTip = atDao.activityTypeDescription.getValue();
       tooltipMap.put(i, toolTip);
       i++;
     }
   }
   catch (DataSourceException ex) {
     ex.printStackTrace();
   }
 }

  public void dragOver(DragSourceDragEvent e) {
    DragSourceContext context = e.getDragSourceContext();
    int dropAction = e.getDropAction();
    if ((dropAction & DnDConstants.ACTION_COPY_OR_MOVE) != 0)
      context.setCursor(DragSource.DefaultMoveDrop);
    else
      context.setCursor(DragSource.DefaultMoveNoDrop);
  }

  public void dragExit(DragSourceEvent e) {
    DragSourceContext context = e.getDragSourceContext();
    context.setCursor(DragSource.DefaultMoveNoDrop);
  }

  public void dragEnter(DragSourceDragEvent e) {}
  public void dragDropEnd(DragSourceDropEvent e) {}
  public void dropActionChanged(DragSourceDragEvent e) {}

  public FW_JTable getFwActList() {
    return fwActList;
  }

  public void setFwActList(FW_JTable fwActList) {
    this.fwActList = fwActList;
  }
  /**
   * ***************************************************************************
   *                  Implementation of the EventSynchronizerWatcher
   * ***************************************************************************
   */
  public void eventSynchronizerTriggered(String subject) {
    if (this.subject != null && this.subject.equals(subject)){
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
