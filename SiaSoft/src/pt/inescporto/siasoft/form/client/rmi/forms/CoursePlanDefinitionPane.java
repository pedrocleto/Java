package pt.inescporto.siasoft.form.client.rmi.forms;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.ResourceBundle;

import pt.inescporto.template.client.design.TmplJDateField;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplJTextArea;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.TmplJDatePicker;

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

public class CoursePlanDefinitionPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.form.client.rmi.forms.FormResources");

  TmplLookupButton jlbtnEnterprise = new TmplLookupButton();
  TmplJTextField jtfldEnterprise = new TmplJTextField();
  TmplLookupField jlfldEnterprise = new TmplLookupField();

  TmplJLabel jlblCoursePlanID = new TmplJLabel();
  TmplJTextField jtfldCoursePlanID = new TmplJTextField();

  TmplJLabel jlblCoursePlanName = new TmplJLabel();
  TmplJTextField jtfldCoursePlanName = new TmplJTextField();

  TmplJLabel jlblCoursePlanResume = new TmplJLabel();
  TmplJTextArea jtfldCoursePlanResume = new TmplJTextArea();

  TmplJLabel jlblCoursePlanStartDate = new TmplJLabel();
  TmplJDatePicker jtfldCoursePlanStartDate = new TmplJDatePicker();

  TmplJLabel jlblCoursePlanEndDate = new TmplJLabel();
  TmplJDatePicker jtfldCoursePlanEndDate = new TmplJDatePicker();

  TmplLookupButton jlbtnUser = new TmplLookupButton();
  TmplJTextField jtfldUser = new TmplJTextField();
  TmplLookupField jlfldUser = new TmplLookupField();

  public CoursePlanDefinitionPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jtfldCoursePlanID);
    datasource.addDatasourceListener(jtfldCoursePlanName);
    datasource.addDatasourceListener(jtfldCoursePlanResume);
    datasource.addDatasourceListener(jlbtnEnterprise);
    datasource.addDatasourceListener(jtfldEnterprise);
    datasource.addDatasourceListener(jlfldEnterprise);
    datasource.addDatasourceListener(jtfldCoursePlanStartDate);
    datasource.addDatasourceListener(jtfldCoursePlanEndDate);
    datasource.addDatasourceListener(jlbtnUser);
    datasource.addDatasourceListener(jtfldUser);
    datasource.addDatasourceListener(jlfldUser);

    fwCListener.addFWComponentListener(jtfldCoursePlanID);
    fwCListener.addFWComponentListener(jtfldCoursePlanName);
    fwCListener.addFWComponentListener(jtfldCoursePlanResume);
    fwCListener.addFWComponentListener(jlbtnEnterprise);
    fwCListener.addFWComponentListener(jtfldEnterprise);
    fwCListener.addFWComponentListener(jlfldEnterprise);
    fwCListener.addFWComponentListener(jtfldCoursePlanStartDate);
    fwCListener.addFWComponentListener(jtfldCoursePlanEndDate);
    fwCListener.addFWComponentListener(jlbtnUser);
    fwCListener.addFWComponentListener(jtfldUser);
    fwCListener.addFWComponentListener(jlfldUser);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jtfldEnterprise.setText(res.getString("course.label.enterprise"));
    jtfldEnterprise.setField("fkEnterpriseID");

    jlbtnEnterprise.setText(res.getString("course.label.enterprise"));
    jlbtnEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlbtnEnterprise.setTitle(res.getString("course.label.enterpriselist"));
    jlbtnEnterprise.setDefaultFill(jtfldEnterprise);

    jlfldEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlfldEnterprise.setDefaultRefField(jtfldEnterprise);

    jlblCoursePlanID.setText(res.getString("course.label.code"));
    jtfldCoursePlanID.setField("coursePlanID");
    jtfldCoursePlanID.setHolder(jlblCoursePlanID);

    jlblCoursePlanName.setText(res.getString("course.label.name"));
    jtfldCoursePlanName.setField("coursePlanName");
    jtfldCoursePlanName.setHolder(jlblCoursePlanName);

    jlblCoursePlanResume.setText(res.getString("course.label.desc"));
    jtfldCoursePlanResume.setField("coursePlanResume");
    jtfldCoursePlanResume.setHolder(jlblCoursePlanResume);
    jtfldCoursePlanResume.setWrapStyleWord(true);
    jtfldCoursePlanResume.setLineWrap(true);
    JScrollPane jspRes = new JScrollPane(jtfldCoursePlanResume);

    jlblCoursePlanStartDate.setText(res.getString("course.label.coursePlanStartDate"));
    jtfldCoursePlanStartDate.setField("coursePlanStartDate");
    jtfldCoursePlanStartDate.setHolder(jlblCoursePlanStartDate);

    jlblCoursePlanEndDate.setText(res.getString("course.label.coursePlanEndDate"));
    jtfldCoursePlanEndDate.setField("coursePlanEndDate");
    jtfldCoursePlanEndDate.setHolder(jlblCoursePlanEndDate);

    jtfldUser.setLabel(res.getString("course.label.user"));
    jtfldUser.setField("fkUserID");
    jlbtnUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    if (!MenuSingleton.isSupplier())
      jlbtnUser.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");

    jlbtnUser.setText(res.getString("course.label.user"));
    jlbtnUser.setTitle(res.getString("course.label.userList"));
    jlbtnUser.setDefaultFill(jtfldUser);
    jlfldUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    jlfldUser.setDefaultRefField(jtfldUser);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, pref,4dlu, 70dlu,50dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref,2dlu,50dlu:grow, 5px, pref, 2dlu, pref, 2dlu");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);

    if (MenuSingleton.isSupplier())
      formLayout.setRowGroups(new int[][] { {2, 4, 6}
      });
    else
      formLayout.setRowGroups(new int[][] { {4, 6}
      });

    CellConstraints cc = new CellConstraints();
    if (MenuSingleton.isSupplier()) {
      content.add(jlbtnEnterprise, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));
      content.add(jtfldEnterprise, cc.xy(4, 2));
      content.add(jlfldEnterprise, cc.xyw(6, 2,4));
    }
    content.add(jlblCoursePlanID, cc.xy(2, 4));
    content.add(jtfldCoursePlanID, cc.xy(4, 4));

    content.add(jlblCoursePlanName, cc.xy(2, 6));
    content.add(jtfldCoursePlanName, cc.xyw(4, 6, 6));

    content.add(jlblCoursePlanResume, cc.xy(2, 8));
    content.add(jspRes, cc.xywh(4, 8, 6, 3, CellConstraints.FILL, CellConstraints.FILL));

    content.add(jlblCoursePlanStartDate, cc.xy(2, 12));
    content.add(jtfldCoursePlanStartDate, cc.xy(4, 12));

    content.add(jlblCoursePlanEndDate, cc.xy(6, 12));
    content.add(jtfldCoursePlanEndDate, cc.xy(8, 12));

    content.add(jlbtnUser, cc.xy(2, 14, CellConstraints.FILL, CellConstraints.FILL));
    content.add(jtfldUser, cc.xy(4, 14));
    content.add(jlfldUser, cc.xyw(6, 14, 4));
    add(content, BorderLayout.NORTH);
    add(new CourseTabbedPane(datasource, fwCListener), BorderLayout.CENTER);
  }
}
