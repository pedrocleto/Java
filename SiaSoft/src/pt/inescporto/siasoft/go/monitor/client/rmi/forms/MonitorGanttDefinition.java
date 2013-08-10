package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import java.awt.event.ActionEvent;
import pt.inescporto.template.client.design.TmplJComboBox;
import pt.inescporto.template.client.rmi.MenuSingleton;
import java.awt.BorderLayout;
import java.awt.SystemColor;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplLookupButton;
import javax.swing.JFrame;
import pt.inescporto.template.client.design.TmplJTextField;
import java.awt.event.ActionListener;
import java.awt.Component;
import pt.inescporto.template.client.design.thirdparty.ComponentTitledBorder;
import pt.inescporto.template.client.design.TmplJDateField;
import pt.inescporto.template.client.design.TmplJLabel;
import com.jgoodies.forms.layout.FormLayout;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.event.TemplateEvent;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.TmplJCheckBox;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.TmplJButtonUpdate;
import java.awt.Color;
import pt.inescporto.template.client.design.TmplJDatePicker;
import java.util.Date;
import java.sql.Timestamp;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class MonitorGanttDefinition extends JPanel {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  MonitorGanttDatasource monGanttDS = new MonitorGanttDatasource();
  JFrame frameChart =  new JFrame();

  TmplJLabel jlblMonitorDate = new TmplJLabel();
  //TmplJDateField jtfldMonitorDate = new TmplJDateField();
  TmplJDatePicker jtfldMonitorDate = new TmplJDatePicker();

  TmplJLabel jlblMonitorEndDate = new TmplJLabel();
  //TmplJDateField jtfldMonitorEndDate = new TmplJDateField();
  TmplJDatePicker jtfldMonitorEndDate = new TmplJDatePicker();

  TmplJCheckBox jcheckDate = new TmplJCheckBox();

  TmplJLabel jlblState = new TmplJLabel();
  TmplJComboBox jcmbState = new TmplJComboBox();
  TmplJCheckBox jcheckState = new TmplJCheckBox();

  TmplLookupButton jlbtnUser = new TmplLookupButton();
  TmplJTextField jtfldUserID = new TmplJTextField();
  TmplLookupField jlfldUserDescription = new TmplLookupField();
  TmplJCheckBox jcheckUser = new TmplJCheckBox();

  TmplLookupButton jlbtnMPlan = new TmplLookupButton();
  TmplJTextField jtfldMPlanID = new TmplJTextField();
  TmplLookupField jlfldMPlanDescription = new TmplLookupField();
  TmplJCheckBox jcheckMPlan = new TmplJCheckBox();

  TmplLookupButton jlbtnEA = new TmplLookupButton();
  TmplJTextField jtfldEAID = new TmplJTextField();
  TmplLookupField jlfldEADescription = new TmplLookupField();
  TmplJCheckBox jcheckEA = new TmplJCheckBox();

  TmplLookupButton jlbtnActivity = new TmplLookupButton();
  TmplJTextField jtfldActivityID = new TmplJTextField();
  TmplLookupField jlfldActivityDescription = new TmplLookupField();
  TmplJCheckBox jcheckActivity = new TmplJCheckBox();

  TmplJButtonUpdate jbtnUpdate = new  TmplJButtonUpdate();


  public MonitorGanttDefinition() {
    initialize();
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jlblMonitorDate.setText(res.getString("monitorDef.label.monitorDate"));
    jtfldMonitorDate.setField("monitorDate");
    jtfldMonitorDate.setHolder(jlblMonitorDate);

    jlblMonitorEndDate.setText(res.getString("monitorDef.label.monitorEndDate"));
    jtfldMonitorEndDate.setField("monitorEndDate");
    jtfldMonitorEndDate.setHolder(jlblMonitorEndDate);

    jlblState.setText(res.getString("monitorSearch.label.state"));
    jcmbState.setField("status");
    jcmbState.setDataItems(new Object[] {"",res.getString("monitorSearch.label.state.wait"), res.getString("monitorSearch.label.state.running"), res.getString("monitorSearch.label.state.delayed"), res.getString("monitorSearch.label.state.ended"), res.getString("monitorSearch.label.state.schedule")});
    jcmbState.setDataValues(new Object[] {null,"W", "R", "D","E","S"});

    jtfldUserID.setField("fkUserID");

    jlbtnUser.setText(res.getString("monitorDef.label.monitorUser"));
    jlbtnUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    if (!MenuSingleton.isSupplier())
      jlbtnUser.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnUser.setTitle(res.getString("monitorDef.label.monitorUserList"));
    jlbtnUser.setDefaultFill(jtfldUserID);

    jlfldUserDescription.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    jlfldUserDescription.setDefaultRefField(jtfldUserID);
    jlfldUserDescription.tmplInitialize(new TemplateEvent(this));

    jtfldMPlanID.setField("monitorPlanID");

    jlbtnMPlan.setText(res.getString("monitorSearch.label.plan"));
    jlbtnMPlan.setUrl("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorPlan");
    if (!MenuSingleton.isSupplier())
      jlbtnMPlan.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnMPlan.setTitle(res.getString("monitorSearch.label.planList"));
    jlbtnMPlan.setDefaultFill(jtfldMPlanID);

    jlfldMPlanDescription.setUrl("pt.inescporto.siasoft.go.monitor.ejb.session.MonitorPlan");
    jlfldMPlanDescription.setDefaultRefField(jtfldMPlanID);
    jlfldMPlanDescription.tmplInitialize(new TemplateEvent(this));

    jtfldEAID.setField("fkEnvAspectID");

    jlbtnEA.setText(res.getString("monitorSearch.label.EnvAsp"));
    jlbtnEA.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
    jlbtnEA.setTitle(res.getString("monitorSearch.label.EnvAspList"));
    jlbtnEA.setDefaultFill(jtfldEAID);

    jlfldEADescription.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
    jlfldEADescription.setDefaultRefField(jtfldEAID);
    jlfldEADescription.tmplInitialize(new TemplateEvent(this));

    jtfldActivityID.setField("fkActivityID");

    jlbtnActivity.setText(res.getString("monitorSearch.label.Act"));
    jlbtnActivity.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    if (!MenuSingleton.isSupplier())
      jlbtnActivity.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnActivity.setTitle(res.getString("monitorSearch.label.ActList"));
    jlbtnActivity.setDefaultFill(jtfldActivityID);

    jlfldActivityDescription.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    jlfldActivityDescription.setDefaultRefField(jtfldActivityID);
    jlfldActivityDescription.tmplInitialize(new TemplateEvent(this));

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,170dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");

    FormLayout formLayout2 = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,70dlu:grow, 5px",
                                            "5px, pref, 5px");

    final JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);

    JPanel jpnlDateSelector = new JPanel();
    JPanel jpnlStateSelector = new JPanel();
    JPanel jpnlUserSelector = new JPanel();
    JPanel jpnlPlanSelector = new JPanel();
    JPanel jpnlEnvAspectSelector = new JPanel();
    JPanel jpnlActivitySelector = new JPanel();

    jpnlDateSelector.setOpaque(false);
    jpnlDateSelector.setLayout(new BorderLayout());
    final JPanel jpnlDummyDS = new JPanel(formLayout2);
    jpnlDummyDS.setOpaque(false);
    jpnlDummyDS.setVisible(false);

    jpnlStateSelector.setOpaque(false);
    jpnlStateSelector.setLayout(new BorderLayout());
    final JPanel jpnlDummyST = new JPanel(formLayout2);
    jpnlDummyST.setOpaque(false);
    jpnlDummyST.setVisible(false);

    jpnlUserSelector.setOpaque(false);
    jpnlUserSelector.setLayout(new BorderLayout());
    final JPanel jpnlDummyUser = new JPanel(formLayout2);
    jpnlDummyUser.setOpaque(false);
    jpnlDummyUser.setVisible(false);

    jpnlPlanSelector.setOpaque(false);
    jpnlPlanSelector.setLayout(new BorderLayout());
    final JPanel jpnlDummyPlan = new JPanel(formLayout2);
    jpnlDummyPlan.setOpaque(false);
    jpnlDummyPlan.setVisible(false);

    jpnlEnvAspectSelector.setOpaque(false);
    jpnlEnvAspectSelector.setLayout(new BorderLayout());
    final JPanel jpnlDummyEnvAsp = new JPanel(formLayout2);
    jpnlDummyEnvAsp.setOpaque(false);
    jpnlDummyEnvAsp.setVisible(false);

    jpnlActivitySelector.setOpaque(false);
    jpnlActivitySelector.setLayout(new BorderLayout());
    final JPanel jpnlDummyActiv = new JPanel(formLayout2);
    jpnlDummyActiv.setOpaque(false);
    jpnlDummyActiv.setVisible(false);

    /*formLayout.setRowGroups(new int[][] { {2, 4, 6, 8, 10}
      });*/

    CellConstraints cc = new CellConstraints();

    jpnlDummyDS.add(jlblMonitorDate, cc.xy(2, 2));
    jlblMonitorDate.setEnabled(false);
    jpnlDummyDS.add(jtfldMonitorDate, cc.xy(4, 2));
    jtfldMonitorDate.setEnabled(false);
    jpnlDummyDS.add(jlblMonitorEndDate, cc.xy(6, 2));
    jlblMonitorEndDate.setEnabled(false);
    jpnlDummyDS.add(jtfldMonitorEndDate, cc.xy(8, 2));
    jtfldMonitorEndDate.setEnabled(false);

    jpnlDummyST.add(jlblState, cc.xy(2, 2));
    jlblState.setEnabled(false);
    jpnlDummyST.add(jcmbState, cc.xy(4, 2));
    jcmbState.setEnabled(false);

    jpnlDummyUser.add(jlbtnUser, cc.xy(2, 2));
    jlbtnUser.setEnabled(false);
    jpnlDummyUser.add(jtfldUserID, cc.xy(4, 2));
    jtfldUserID.setEnabled(false);
    jpnlDummyUser.add(jlfldUserDescription, cc.xyw(6, 2, 4));
    jlfldUserDescription.setEnabled(false);

    jpnlDummyPlan.add(jlbtnMPlan, cc.xy(2, 2));
    jlbtnMPlan.setEnabled(false);
    jpnlDummyPlan.add(jtfldMPlanID, cc.xy(4, 2));
    jtfldMPlanID.setEnabled(false);
    jpnlDummyPlan.add(jlfldMPlanDescription, cc.xyw(6, 2, 4));
    jlfldMPlanDescription.setEnabled(false);

    jpnlDummyEnvAsp.add(jlbtnEA, cc.xy(2, 2));
    jlbtnEA.setEnabled(false);
    jpnlDummyEnvAsp.add(jtfldEAID, cc.xy(4, 2));
    jtfldEAID.setEnabled(false);
    jpnlDummyEnvAsp.add(jlfldEADescription, cc.xyw(6, 2, 4));
    jlfldEADescription.setEnabled(false);

    jpnlDummyActiv.add(jlbtnActivity, cc.xy(2, 2));
    jlbtnActivity.setEnabled(false);
    jpnlDummyActiv.add(jtfldActivityID, cc.xy(4, 2));
    jtfldActivityID.setEnabled(false);
    jpnlDummyActiv.add(jlfldActivityDescription, cc.xyw(6, 2, 4));
    jlfldActivityDescription.setEnabled(false);

    jcheckDate.setBackground(new Color(200,221,244));
    jcheckDate.setText(res.getString("monitorSearch.label.date"));
    jcheckDate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (((TmplJCheckBox)e.getSource()).isSelected()) {
          Component[] con = jpnlDummyDS.getComponents();
          monGanttDS.setUP(false);
          for (int i = 0; i < (con.length); i++)
            con[i].setEnabled(true);
          jpnlDummyDS.setVisible(true);
          content.invalidate();
          content.repaint();
        }
        else {
          jtfldMonitorDate.setSelectedDate(null);
    jtfldMonitorEndDate.setSelectedDate(null);
          Component[] con = jpnlDummyDS.getComponents();
          monGanttDS.setUP(false);
          for (int i = 0; i < (con.length); i++)
            con[i].setEnabled(false);
          jpnlDummyDS.setVisible(false);
          content.invalidate();
          content.repaint();
        }
      }
    });
    jtfldMonitorDate.setSelectedDate(null);
    jtfldMonitorEndDate.setSelectedDate(null);

    ComponentTitledBorder borderDate = new ComponentTitledBorder(jcheckDate, jpnlDateSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlDateSelector.setBorder(borderDate);
    jpnlDateSelector.add(jpnlDummyDS, BorderLayout.NORTH);

    jcheckState.setBackground(new Color(200,221,244));
    jcheckState.setText(res.getString("monitorSearch.label.state"));
    jcheckState.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (((TmplJCheckBox)e.getSource()).isSelected()) {
          Component[] con = jpnlDummyST.getComponents();
          monGanttDS.setUP(false);
          for (int i = 0; i < (con.length); i++)
            con[i].setEnabled(true);
          jpnlDummyST.setVisible(true);
          content.invalidate();
          content.repaint();
        }
        else {
          jcmbState.setSelectedIndex(0);
          Component[] con = jpnlDummyST.getComponents();
          monGanttDS.setUP(false);
          for (int i = 0; i < (con.length); i++)
            con[i].setEnabled(false);
          jpnlDummyST.setVisible(false);
          content.invalidate();
          content.repaint();
        }

      }
    });

    ComponentTitledBorder borderState = new ComponentTitledBorder(jcheckState, jpnlStateSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlStateSelector.setBorder(borderState);
    jpnlStateSelector.add(jpnlDummyST, BorderLayout.NORTH);

    jcheckUser.setBackground(new Color(200,221,244));
    jcheckUser.setText(res.getString("monitorDef.label.monitorUser"));
    jcheckUser.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (((TmplJCheckBox)e.getSource()).isSelected()) {
          Component[] con = jpnlDummyUser.getComponents();
          monGanttDS.setUP(false);
          for (int i = 0; i < (con.length) - 1; i++)
            con[i].setEnabled(true);
          jpnlDummyUser.setVisible(true);
          content.invalidate();
          content.repaint();
        }
        else {
          jtfldUserID.setText("");
          jlfldUserDescription.setText("");
          Component[] con = jpnlDummyUser.getComponents();
          monGanttDS.setUP(false);
          for (int i = 0; i < (con.length) - 1; i++)
            con[i].setEnabled(false);

          jpnlDummyUser.setVisible(false);
          content.invalidate();
          content.repaint();
        }
      }
    });
    ComponentTitledBorder borderUser = new ComponentTitledBorder(jcheckUser, jpnlUserSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlUserSelector.setBorder(borderUser);
    jpnlUserSelector.add(jpnlDummyUser, BorderLayout.NORTH);

    jcheckMPlan.setBackground(new Color(200,221,244));
    jcheckMPlan.setText(res.getString("monitorSearch.label.plan"));
    jcheckMPlan.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (((TmplJCheckBox)e.getSource()).isSelected()) {
          Component[] con = jpnlDummyPlan.getComponents();
          monGanttDS.setUP(false);
          for (int i = 0; i < (con.length) - 1; i++)
            con[i].setEnabled(true);
          jpnlDummyPlan.setVisible(true);
          content.invalidate();
          content.repaint();
        }
        else {
          jtfldMPlanID.setText("");
          jlfldMPlanDescription.setText("");
          monGanttDS.setUP(false);
          Component[] con = jpnlDummyPlan.getComponents();
          for (int i = 0; i < (con.length) - 1; i++)
            con[i].setEnabled(false);
          jpnlDummyPlan.setVisible(false);
          content.invalidate();
          content.repaint();
        }

      }
    });
    ComponentTitledBorder borderMPlan = new ComponentTitledBorder(jcheckMPlan, jpnlPlanSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlPlanSelector.setBorder(borderMPlan);
    jpnlPlanSelector.add(jpnlDummyPlan, BorderLayout.NORTH);

    jcheckEA.setBackground(new Color(200,221,244));
    jcheckEA.setText(res.getString("monitorSearch.label.EnvAsp"));
    jcheckEA.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (((TmplJCheckBox)e.getSource()).isSelected()) {
          Component[] con = jpnlDummyEnvAsp.getComponents();
          monGanttDS.setUP(false);
          for (int i = 0; i < (con.length) - 1; i++)
            con[i].setEnabled(true);
         jpnlDummyEnvAsp.setVisible(true);
          content.invalidate();
          content.repaint();
        }
        else {
          jtfldEAID.setText("");
          jlfldEADescription.setText("");
          monGanttDS.setUP(false);
          Component[] con = jpnlDummyEnvAsp.getComponents();
          for (int i = 0; i < (con.length) - 1; i++)
            con[i].setEnabled(false);
          jpnlDummyEnvAsp.setVisible(false);
          content.invalidate();
          content.repaint();

        }
      }
    });
    ComponentTitledBorder borderEA = new ComponentTitledBorder(jcheckEA, jpnlEnvAspectSelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlEnvAspectSelector.setBorder(borderEA);
    jpnlEnvAspectSelector.add(jpnlDummyEnvAsp, BorderLayout.NORTH);

    jcheckActivity.setBackground(new Color(200,221,244));
    jcheckActivity.setText(res.getString("monitorSearch.label.Act"));
    jcheckActivity.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (((TmplJCheckBox)e.getSource()).isSelected()) {
          monGanttDS.setUP(false);
          Component[] con = jpnlDummyActiv.getComponents();
          for (int i = 0; i < (con.length) - 1; i++)
            con[i].setEnabled(true);
          jpnlDummyActiv.setVisible(true);
          content.invalidate();
          content.repaint();
        }
        else {
          jtfldActivityID.setText("");
          jlfldActivityDescription.setText("");
          Component[] con = jpnlDummyActiv.getComponents();
          for (int i = 0; i < (con.length) - 1; i++)
            con[i].setEnabled(false);
          monGanttDS.setUP(false);
          jpnlDummyActiv.setVisible(false);
          content.invalidate();
          content.repaint();
        }
      }
    });
    ComponentTitledBorder borderActivity = new ComponentTitledBorder(jcheckActivity, jpnlActivitySelector, BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
    jpnlActivitySelector.setBorder(borderActivity);
    jpnlActivitySelector.add(jpnlDummyActiv, BorderLayout.NORTH);

    content.add(jpnlDateSelector, cc.xyw(2, 2, 8));
    content.add(jpnlStateSelector, cc.xyw(2, 4, 8));
    content.add(jpnlUserSelector, cc.xyw(2, 6, 8));
    content.add(jpnlPlanSelector, cc.xyw(2, 8, 8));
    content.add(jpnlEnvAspectSelector, cc.xyw(2, 10, 8));
    content.add(jpnlActivitySelector, cc.xyw(2, 12, 8));

    jbtnUpdate.setText(res.getString("label.update"));
    jbtnUpdate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        monGanttDS.setNullValues(false);
        if ((!jtfldMonitorDate.getText().equals(" - - ") && !jtfldMonitorEndDate.getText().equals(" - - "))) {
          if ((jtfldMonitorEndDate.getSelectedDate(true).before(jtfldMonitorDate.getSelectedDate(true))) || (jtfldMonitorEndDate.getSelectedDate(true).equals(jtfldMonitorDate.getSelectedDate(true)))) {
            jtfldMonitorDate.setSelectedDate(null);
            jtfldMonitorEndDate.setSelectedDate(null);
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,
                                          res.getString("label.invalidDate"),
                                          res.getString("label.warning"),
                                          JOptionPane.WARNING_MESSAGE);

            jtfldMonitorDate.requestFocus();
            monGanttDS.setUP(false);

          }
          else{
            try{
            monGanttDS.setParameters(new String(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()),
                                       jtfldMonitorDate.getText().equals(" - - ") ? null : new Timestamp(jtfldMonitorDate.getSelectedDate(true).getTime()),
                                       jtfldMonitorEndDate.getText().equals(" - - ") ? null : new Timestamp(jtfldMonitorEndDate.getSelectedDate(true).getTime()),
				       jcmbState.getTrueSelectItem() == null ? null : (String)jcmbState.getTrueSelectItem(),
				       jtfldUserID.getText().equals("") ? null : jtfldUserID.getText(),
				       jtfldMPlanID.getText().equals("") ? null : jtfldMPlanID.getText(),
				       jtfldEAID.getText().equals("") ? null : jtfldEAID.getText(),
				       jtfldActivityID.getText().equals("") ? null : jtfldActivityID.getText());
	    }
            catch(NullPointerException ex){
	      JFrame frame = new JFrame();
	      JOptionPane.showMessageDialog(frame,
                                            res.getString("label.chooseEnterprise"),
					    res.getString("label.warning"),
					    JOptionPane.WARNING_MESSAGE);
            }
          }
        }
        else {
          try{
            monGanttDS.setParameters(new String(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()),
                                     jtfldMonitorDate.getText().equals(" - - ") ? null : new Timestamp(jtfldMonitorDate.getSelectedDate(true).getTime()),
                                     jtfldMonitorEndDate.getText().equals(" - - ") ? null : new Timestamp(jtfldMonitorEndDate.getSelectedDate(true).getTime()),
				     jcmbState.getTrueSelectItem() == null ? null : (String)jcmbState.getTrueSelectItem(),
				     jtfldUserID.getText().equals("") ? null : jtfldUserID.getText(),
				     jtfldMPlanID.getText().equals("") ? null : jtfldMPlanID.getText(),
				     jtfldEAID.getText().equals("") ? null : jtfldEAID.getText(),
				     jtfldActivityID.getText().equals("") ? null : jtfldActivityID.getText());
	  }
          catch(NullPointerException ex){
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,
                                          res.getString("label.chooseEnterprise"),
                                          res.getString("label.warning"),
                                          JOptionPane.WARNING_MESSAGE);
          }
        }
      }

    });

    add(content, BorderLayout.NORTH);
    add(new MonitorNewGanttChart(monGanttDS,jbtnUpdate), BorderLayout.CENTER);
  }
}
