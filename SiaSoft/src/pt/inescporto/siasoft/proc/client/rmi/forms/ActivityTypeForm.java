package pt.inescporto.siasoft.proc.client.rmi.forms;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.design.TmplJTextField;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import pt.inescporto.template.client.design.TmplJFileChooser;
import pt.inescporto.template.client.design.TmplFileFilter;
import java.awt.Dimension;
import pt.inescporto.template.client.design.TmplJCheckBox;
import pt.inescporto.template.client.design.TmplJComboBox;
import pt.inescporto.siasoft.events.SyncronizerSubjects;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */public class ActivityTypeForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");
  JPanel jpnlContent = new JPanel();

  DataSourceRMI detail = null;
  BorderLayout borderLayout1 = new BorderLayout();

  TmplJLabel jlblActivityTypeID = new TmplJLabel();
  TmplJTextField jtfldActivityTypeID = new TmplJTextField();

  TmplJLabel jlblActivityTypeDescription = new TmplJLabel();
  TmplJTextField jtfldActivityTypeDescription = new TmplJTextField();

  TmplJFileChooser jbtnFCPallet = null;
  TmplJLabel jlblPalletIcon = null;

  TmplJFileChooser jbtnFCGraph = null;
  TmplJLabel jlblGraphIcon = null;

  TmplJLabel jlblElementType = new TmplJLabel();
  TmplJComboBox jcbElementType = new TmplJComboBox();

  TmplJCheckBox jchbNeadsParent = new TmplJCheckBox();
  TmplJCheckBox jchbNoChildren = new TmplJCheckBox();
  TmplJCheckBox jchbAllLevels = new TmplJCheckBox();
  TmplJCheckBox jchbHasLinks = new TmplJCheckBox();

  public ActivityTypeForm() {
    super();

    // define the datasource tree
    DataSourceRMI master = new DataSourceRMI("ActivityType");
    master.setUrl("pt.inescporto.siasoft.proc.ejb.session.ActivityType");

    // register the master datasource the this form
    setDataSource(master);

    //Activity Event
    setPublisherEvent(SyncronizerSubjects.activityTypeFORM);

    // set the id from permission control
    setAccessPermitionIdentifier("ACTIVITY_TYPE");

    // headers for the table headers
    allHeaders = new String[] {res.getString("activityType.label.code"), res.getString("activityType.label.desc"),res.getString("activityType.label.palletIcon"),res.getString("activityType.label.graphIcon"),res.getString("activityType.label.type"),res.getString("activityType.label.neadsParent"),res.getString("activityType.label.noChildren"),res.getString("activityType.label.allLevels"),res.getString("activityType.label.hasProperties")};

    init();
    start();
  }

  public void init() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    dataSource.addDatasourceListener(jtfldActivityTypeID);
    dataSource.addDatasourceListener(jtfldActivityTypeDescription);
    dataSource.addDatasourceListener(jbtnFCPallet);
    dataSource.addDatasourceListener(jlblPalletIcon);
    dataSource.addDatasourceListener(jbtnFCGraph);
    dataSource.addDatasourceListener(jlblGraphIcon);
    dataSource.addDatasourceListener(jcbElementType);
    dataSource.addDatasourceListener(jchbNeadsParent);
    dataSource.addDatasourceListener(jchbNoChildren);
    dataSource.addDatasourceListener(jchbAllLevels);
    dataSource.addDatasourceListener(jchbHasLinks);

    addFWComponentListener(jtfldActivityTypeID);
    addFWComponentListener(jtfldActivityTypeDescription);
    addFWComponentListener(jbtnFCPallet);
    addFWComponentListener(jlblPalletIcon);
    addFWComponentListener(jbtnFCGraph);
    addFWComponentListener(jlblGraphIcon);
    addFWComponentListener(jcbElementType);
    addFWComponentListener(jchbNeadsParent);
    addFWComponentListener(jchbNoChildren);
    addFWComponentListener(jchbAllLevels);
    addFWComponentListener(jchbHasLinks);
  }

  private void jbInit() throws Exception {
    jlblActivityTypeID.setText(res.getString("activityType.label.code"));
    jtfldActivityTypeID.setField("activityTypeID");
    jtfldActivityTypeID.setHolder(jlblActivityTypeID);

    jlblActivityTypeDescription.setText(res.getString("activityType.label.desc"));
    jtfldActivityTypeDescription.setField("activityTypeDescription");
    jtfldActivityTypeDescription.setHolder(jlblActivityTypeDescription);

    jlblPalletIcon = new TmplJLabel("", "palletIcon");
    jlblPalletIcon.setMinimumSize(new Dimension(48, 48));
    jlblPalletIcon.setPreferredSize(new Dimension(48, 48));
    jlblPalletIcon.setMaximumSize(new Dimension(48, 48));
    jbtnFCPallet = new TmplJFileChooser("Icon", jlblPalletIcon);
    TmplFileFilter filter = new TmplFileFilter();
    filter.addExtension("png");
    filter.addExtension("ico");
    filter.setDescription("PNG & ICO Images");
    jbtnFCPallet.setText("Pallet Icon");
    jbtnFCPallet.setFileFilter(filter);

    jlblGraphIcon = new TmplJLabel("", "graphIcon");
    jlblGraphIcon.setMinimumSize(new Dimension(48, 48));
    jlblGraphIcon.setPreferredSize(new Dimension(48, 48));
    jlblGraphIcon.setMaximumSize(new Dimension(48, 48));
    jbtnFCGraph = new TmplJFileChooser("Icon", jlblGraphIcon);
    jbtnFCGraph.setText("Graph Icon");
    jbtnFCGraph.setFileFilter(filter);

    jlblElementType.setText("Tipo");
    jcbElementType.setHolder(jlblElementType);
    jcbElementType.setField("elementType");
    jcbElementType.setDataItems(new Object[] {"", "Componente", "Componente Internível", "Ligação", "Ligação Internível"});
    jcbElementType.setDataValues(new Object[] {"", "C", "I", "A", "L"});

    jchbNeadsParent.setText("Neads Parent");
    jchbNeadsParent.setLabel("Neads Parent");
    jchbNeadsParent.setField("neadsParent");
    jchbNeadsParent.setDataValues(new Object[] {Boolean.FALSE, Boolean.TRUE});

    jchbNoChildren.setText("No Children");
    jchbNoChildren.setLabel("No Children");
    jchbNoChildren.setField("noChildren");
    jchbNoChildren.setDataValues(new Object[] {Boolean.FALSE, Boolean.TRUE});

    jchbAllLevels.setText("All Levels");
    jchbAllLevels.setLabel("All Levels");
    jchbAllLevels.setField("allLevels");
    jchbAllLevels.setDataValues(new Object[] {Boolean.FALSE, Boolean.TRUE});

    jchbHasLinks.setText("Has Properties");
    jchbHasLinks.setLabel("Has Properties");
    jchbHasLinks.setField("hasLinks");
    jchbHasLinks.setDataValues(new Object[] {Boolean.FALSE, Boolean.TRUE});

    setOpaque(false);
    jpnlContent.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 50dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    jpnlContent.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] {{2, 4, 10, 12}});
    CellConstraints cc = new CellConstraints();

    jpnlContent.add(jlblActivityTypeID, cc.xy(2,2));
    jpnlContent.add(jtfldActivityTypeID, cc.xy(4,2));

    jpnlContent.add(jlblActivityTypeDescription, cc.xy(2,4));
    jpnlContent.add(jtfldActivityTypeDescription, cc.xyw(4,4,2));

    jpnlContent.add(jbtnFCPallet, cc.xy(2,6));
    jpnlContent.add(jlblPalletIcon, cc.xy(4,6));

    jpnlContent.add(jbtnFCGraph, cc.xy(2,8));
    jpnlContent.add(jlblGraphIcon, cc.xy(4,8));

    jpnlContent.add(jlblElementType, cc.xy(2, 10));
    jpnlContent.add(jcbElementType, cc.xy(4, 10));

    FormLayout flCh = new FormLayout("70dlu, 4dlu:grow, 70dlu, 4dlu:grow, 70dlu, 4dlu:grow, 70dlu",
				     "pref");
    JPanel jpnlCh = new JPanel(flCh);
    jpnlCh.setOpaque(false);
    jpnlCh.add(jchbNeadsParent, cc.xy(1, 1));
    jpnlCh.add(jchbNoChildren, cc.xy(3, 1));
    jpnlCh.add(jchbAllLevels, cc.xy(5, 1));
    jpnlCh.add(jchbHasLinks, cc.xy(7, 1));

    jpnlContent.add(jpnlCh, cc.xyw(2, 12, 4, CellConstraints.FILL, CellConstraints.FILL));

    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    add(jpnlContent, BorderLayout.CENTER);
  }
}
