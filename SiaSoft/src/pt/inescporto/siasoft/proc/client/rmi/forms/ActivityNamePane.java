package pt.inescporto.siasoft.proc.client.rmi.forms;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import pt.inescporto.siasoft.proc.client.rmi.editor.Graph;
import pt.inescporto.siasoft.proc.client.rmi.editor.GraphNode;
import pt.inescporto.template.client.design.TmplJButtonSave;
import pt.inescporto.template.client.design.TmplJButtonCancel;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJTextField;

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
public class ActivityNamePane extends JPanel implements ActionListener {

  JToolBar jtbarNav = new JToolBar();
  TmplJButtonSave jbtnSave = new TmplJButtonSave();
  TmplJButtonCancel jbtnCancel = new TmplJButtonCancel();
  TmplJLabel jlblActivityDescription = new TmplJLabel();
  TmplJTextField jtfldActivityDescription = new TmplJTextField();
  TmplJLabel jlblActivityID = new TmplJLabel();
  TmplJTextField jtfldActivityID = new TmplJTextField();

  private DefaultGraphCell cell = null;
  private Graph graph = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");

  public ActivityNamePane(DefaultGraphCell cell, Graph graph) {
    this.cell = cell;
    this.graph = graph;
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createTitledBorder(res.getString("activityNamePane.label.title")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));

    jlblActivityID.setText(res.getString("activityNamePane.label.code"));
    jlblActivityID.setEnabled(false);
    jtfldActivityID.setText(((GraphNode)cell.getUserObject()).getId());
    jtfldActivityID.setEnabled(false);

    jlblActivityDescription.setText(res.getString("activityNamePane.label.desc"));
    jtfldActivityDescription.setLabel(res.getString("activityNamePane.label.desc"));
    jtfldActivityDescription.setText(((GraphNode)cell.getUserObject()).getName());

    jtbarNav.setFloatable(false);
    jtbarNav.setOrientation(JToolBar.VERTICAL);
    jtbarNav.add(jbtnSave,null);
    jtbarNav.add(jbtnCancel,null);
    add(jtbarNav, BorderLayout.EAST);
    JPanel panel = new JPanel();
    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 150dlu:grow, 5px",
                                          "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    panel.setLayout(formLayout);
    CellConstraints cc = new CellConstraints();
    panel.add(jlblActivityID, cc.xy(2,2));
    panel.add(jtfldActivityID, cc.xy(4,2));

    panel.add(jlblActivityDescription, cc.xy(2,4));
    panel.add(jtfldActivityDescription, cc.xy(4,4));

    panel.setOpaque(false);
    add(panel, BorderLayout.CENTER);
    jbtnSave.addActionListener(this);
    jbtnCancel.addActionListener(this);
  }
  public void actionPerformed(ActionEvent e){
    Container parent = getParent();
    if (e.getActionCommand().equals("SAVE")) {
      String name = jtfldActivityDescription.getText();
      if (name != null && !(parent instanceof JDialog)) {
        Map nested = new Hashtable();
        Map attributeMap = new Hashtable();
        ((GraphNode)cell.getUserObject()).setName(name);
        GraphConstants.setValue(attributeMap,((GraphNode)cell.getUserObject()));
        nested.put(cell,attributeMap);
        graph.getGraphLayoutCache().edit(nested,null,null,null);
        while (parent != null && !(parent instanceof JDialog))
	  parent = parent.getParent();
	((JDialog)parent).dispose();
      }
    }
    if(e.getActionCommand().equals("CANCEL")){
      while (parent != null && !(parent instanceof JDialog))
	parent = parent.getParent();
      ((JDialog)parent).dispose();
    }
  }
}
