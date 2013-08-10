package pt.inescporto.siasoft.go.pga.client.rmi.forms;

import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJLabel;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import pt.inescporto.template.client.util.DataSource;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.TmplJTextField;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.TmplJDateField;
import pt.inescporto.template.client.design.TmplJTextArea;
import javax.swing.JScrollPane;
import pt.inescporto.template.client.design.TmplJDatePicker;

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
public class GoalsDefinitionPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.pga.client.rmi.forms.FormResources");

  //
  TmplJLabel jlblGoalID = new TmplJLabel();
  TmplJTextField jtfldGoalID = new TmplJTextField();

  TmplJLabel jlblGoalDescription = new TmplJLabel();
  TmplJTextField jtfldGoalDescription = new TmplJTextField();

  TmplJLabel jlblDeadEndDate = new TmplJLabel();
  TmplJDatePicker jtfldDeadEndDate = new TmplJDatePicker();

  TmplJLabel jlblResume = new TmplJLabel();
  TmplJTextArea jtfldResume = new TmplJTextArea();

  public GoalsDefinitionPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jtfldGoalID);
    datasource.addDatasourceListener(jtfldGoalDescription);
    datasource.addDatasourceListener(jtfldDeadEndDate);
    datasource.addDatasourceListener(jtfldResume);

    fwCListener.addFWComponentListener(jtfldGoalID);
    fwCListener.addFWComponentListener(jtfldGoalDescription);
    fwCListener.addFWComponentListener(jtfldDeadEndDate);
    fwCListener.addFWComponentListener(jtfldResume);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jlblGoalID.setText(res.getString("goal.label.code"));
    jtfldGoalID.setField("goalID");
    jtfldGoalID.setHolder(jlblGoalID);

    jlblGoalDescription.setText(res.getString("goal.label.desc"));
    jtfldGoalDescription.setField("goalDescription");
    jtfldGoalDescription.setHolder(jlblGoalDescription);

    jlblDeadEndDate.setText(res.getString("goal.label.deadEndDate"));
    jtfldDeadEndDate.setField("deadEndDate");
    jtfldDeadEndDate.setHolder(jlblDeadEndDate);

    jlblResume.setText(res.getString("goal.label.resume"));
    jtfldResume.setField("resume");
    jtfldResume.setHolder(jlblResume);
    jtfldResume.setWrapStyleWord(true);
    jtfldResume.setLineWrap(true);
    JScrollPane jspResume = new JScrollPane(jtfldResume);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, 40dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2, 4, 6, 8}
    });
    CellConstraints cc = new CellConstraints();

    content.add(jlblGoalID, cc.xy(2, 2));
    content.add(jtfldGoalID, cc.xy(4, 2));

    content.add(jlblGoalDescription, cc.xy(2, 4));
    content.add(jtfldGoalDescription, cc.xyw(4, 4, 3));

    content.add(jlblDeadEndDate, cc.xy(2, 6));
    content.add(jtfldDeadEndDate, cc.xy(4, 6));

    content.add(jlblResume, cc.xy(2, 8));
    content.add(jspResume, cc.xyw(2, 10, 5, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.NORTH);
    add(new ObjectiveTabbedPane(datasource, fwCListener), BorderLayout.CENTER);
  }
}
