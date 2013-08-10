package pt.inescporto.siasoft.proc.client.rmi.forms;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.jgraph.graph.*;
import com.jgoodies.forms.layout.*;
import pt.inescporto.siasoft.proc.client.rmi.editor.*;
import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.util.*;

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
public class InterLevelActivityPane extends JPanel implements ActionListener{

  private JToolBar jtbarNav = new JToolBar();

  private TmplJButtonSave jbtnSave = new TmplJButtonSave();
  private TmplJButtonCancel jbtnCancel = new TmplJButtonCancel();
  private TmplJLabel jlblActivityID = new TmplJLabel();
  private TmplJTextField jtfldActivityID = new TmplJTextField();

  private TmplJButton jlbtnLinkActivity = new TmplJButton();
  private TmplJTextField jtfldActivityLinkId = new TmplJTextField();
  private TmplLookupField jlfldActivityLinkDesc = new TmplLookupField();

  TmplJLabel jlblActivityDescription = new TmplJLabel();
  TmplJTextField jtfldActivityDescription = new TmplJTextField();

  private JFrame frame;
  private DefaultGraphCell cell = null;
  private Graph graph = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");
  private ProcessEditor pEditor;

  DataSource datasource = null;

  public InterLevelActivityPane(DefaultGraphCell cell, Graph graph, ProcessEditor pEditor) {
    this.cell = cell;
    this.graph = graph;
    this.pEditor = pEditor;

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    jbtnSave.addActionListener(this);
    jbtnCancel.addActionListener(this);
  }
  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createTitledBorder(res.getString("interLevel.label.title")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));

    jlblActivityID.setText(res.getString("interLevel.label.code"));
    jlblActivityID.setEnabled(false);

    jtfldActivityID.setText(((GraphNode)cell.getUserObject()).getId());
    jtfldActivityID.setEnabled(false);

    jlbtnLinkActivity.addActionListener(this);
    jlbtnLinkActivity.setText(res.getString("interLevel.label.activityList"));
    jlbtnLinkActivity.setActionCommand("LIST");

    String linkActivityId = ((GraphNode)cell.getUserObject()).getLinkActivityID();
    jtfldActivityLinkId.setText(linkActivityId);
    jtfldActivityLinkId.setEnabled(false);
    jlblActivityDescription.setText(res.getString("interLevel.label.desc"));
    jtfldActivityDescription.setLabel(res.getString("interLevel.label.desc"));
    jtfldActivityDescription.setText(((GraphNode)cell.getUserObject()).getName());

    String activityDesc = pEditor.loadActivityDesc((GraphNode)cell.getUserObject());
    jlfldActivityLinkDesc.setText(activityDesc);

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

    panel.add(jlbtnLinkActivity, cc.xy(2,6));
    panel.add(jtfldActivityLinkId, cc.xy(4,6));
    panel.add(jlfldActivityLinkDesc, cc.xy(6,6));

    panel.setOpaque(false);
    add(panel, BorderLayout.CENTER);
  }
  public void actionPerformed(ActionEvent e){
    Container parent = getParent();
    if (e.getActionCommand().equals("SAVE")) {
      String name = jtfldActivityDescription.getText();
      String linkActivityId = jtfldActivityLinkId.getText();
      if (linkActivityId != null && !(parent instanceof JDialog)) {
        Map nested = new Hashtable();
        Map attributeMap = new Hashtable();
        ((GraphNode)cell.getUserObject()).setLinkActivityID(linkActivityId);
        ((GraphNode)cell.getUserObject()).setName(name);
       GraphConstants.setValue(attributeMap,((GraphNode)cell.getUserObject()));
       nested.put(cell,attributeMap);
       graph.getGraphLayoutCache().edit(nested,null,null,null);

       while (parent != null && !(parent instanceof JDialog))
          parent = parent.getParent();
        ((JDialog)parent).dispose();

     }
  }
  if(e.getActionCommand().equals("LIST")){
    ActivityDialog activityDialog = new ActivityDialog(frame, graph, this);
    activityDialog.pack();
    activityDialog.setModal(true);
    activityDialog.setVisible(true);
  }

    if(e.getActionCommand().equals("CANCEL")){
      disposeWindow();
    }
  }

  public void disposeWindow(){
     Container parent = getParent();
     while (parent != null && !(parent instanceof JDialog))
     parent = parent.getParent();
   ((JDialog)parent).dispose();
  }

  public TmplLookupField getJlfldActivityLinkDesc() {
    return jlfldActivityLinkDesc;
  }

  public TmplJTextField getJtfldActivityLinkId() {
    return jtfldActivityLinkId;
  }

  public void setJlfldActivityLinkDesc(TmplLookupField jlfldActivityLinkDesc) {
    this.jlfldActivityLinkDesc = jlfldActivityLinkDesc;
  }

  public void setJtfldActivityLinkId(TmplJTextField jtfldActivityLinkId) {
    this.jtfldActivityLinkId = jtfldActivityLinkId;
  }
}
