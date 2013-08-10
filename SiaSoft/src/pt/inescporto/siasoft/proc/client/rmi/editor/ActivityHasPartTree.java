package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.BorderLayout;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceContext;
import java.awt.Font;
import java.rmi.RemoteException;

import javax.naming.NamingException;
import javax.swing.TransferHandler;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;

import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.siasoft.proc.ejb.session.Part;
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
public class ActivityHasPartTree extends JTree implements DragSourceListener, DragGestureListener, EventSynchronizerWatcher, EventSynchronizerWatcherRemover {
  private DragSource dragSource = null;
  private String subject = null;

  public ActivityHasPartTree() {
    setLayout(new BorderLayout());
    getSelectionModel().setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
    setFont(new Font("Dialog", Font.PLAIN, 10));
    setDragEnabled(true);
    setTransferHandler(new TransferHandler(null));
    setRowHeight(30);
    setShowsRootHandles(false);
    dragSource = new DragSource();
    dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
    setRootVisible(false);
    DefaultTreeModel tm = (DefaultTreeModel)getModel();
    try {
      tm.setRoot(((Part)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.Part")).getPartTree());
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }
    catch (UserException ex) {
      ex.printStackTrace();
    }
  }

  public void setWatcherSubject(String subject) {
    eventSynchronizerTriggered(this.subject);
    this.subject = subject;
    EventSynchronizer.getInstance().addEventSynchronizerWatcher(this, subject);
  }

  public void dragGestureRecognized(DragGestureEvent e) {
    ActivityHasPartTree tree = (ActivityHasPartTree)e.getComponent();
    TreePath path = tree.getSelectionPath();
    if (path != null) {
      DefaultMutableTreeNode selection = (DefaultMutableTreeNode)path.getLastPathComponent();
      if (selection.isLeaf()) {
	TransferableTreeNode tn = new TransferableTreeNode(selection);
	e.startDrag(DragSource.DefaultMoveDrop, tn, this);
      }
    }
  }

  public void dragEnter(DragSourceDragEvent e) {
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

  public void dragDropEnd(DragSourceDropEvent e) {}

  public void dragOver(DragSourceDragEvent e) {}

  public void dropActionChanged(DragSourceDragEvent e) {}

  /**
   * ***************************************************************************
   *                  Implementation of the EventSynchronizerWatcher
   * ***************************************************************************
   */
  public void eventSynchronizerTriggered(String subject) {
    if (this.subject != null && this.subject.equals(subject)) {

      DefaultTreeModel tm = (DefaultTreeModel)getModel();
      try {
	tm.setRoot(((Part)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.proc.ejb.session.Part")).getPartTree());
      }
      catch (NamingException ex) {
	ex.printStackTrace();
      }
      catch (RemoteException ex) {
	ex.printStackTrace();
      }
      catch (UserException ex) {
	ex.printStackTrace();
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
