package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.TmplJLabel;
import com.jgoodies.forms.layout.FormLayout;
import pt.inescporto.template.client.design.TmplLookupField;
import java.util.ResourceBundle;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.TmplJComboBox;
import javax.swing.JTextField;

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

public class EAEvaluationOtherMatrixMethod extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.aa.client.rmi.forms.FormResources");

  TmplLookupButton jlbtnEnvAsp = new TmplLookupButton();
  TmplJTextField jtfldEnvAspID = new TmplJTextField();
  TmplLookupField jlfldEnvAspDescription = new TmplLookupField();

  TmplLookupButton jlbtnCategory1 = new TmplLookupButton();
  TmplJTextField jtfldCategory1 = new TmplJTextField();
  TmplJTextField jtfldCategory1ID = new TmplJTextField();
  TmplLookupField jlfldCategory1Description = new TmplLookupField();

  TmplLookupButton jlbtnCategory2 = new TmplLookupButton();
  TmplJTextField jtfldCategory2 = new TmplJTextField();
  TmplJTextField jtfldCategory2ID = new TmplJTextField();
  TmplLookupField jlfldCategory2Description = new TmplLookupField();

  TmplLookupButton jlbtnCategory3 = new TmplLookupButton();
  TmplJTextField jtfldCategory3 = new TmplJTextField();
  TmplJTextField jtfldCategory3ID = new TmplJTextField();
  TmplLookupField jlfldCategory3Description = new TmplLookupField();

  TmplLookupButton jlbtnCategory4 = new TmplLookupButton();
  TmplJTextField jtfldCategory4 = new TmplJTextField();
  TmplJTextField jtfldCategory4ID = new TmplJTextField();
  TmplLookupField jlfldCategory4Description = new TmplLookupField();

  TmplJLabel jlblSignificance = new TmplJLabel();
  TmplJComboBox jcmbSignificanceDescription = new TmplJComboBox();

  TmplJTextField jtfldSignificance = new TmplJTextField();

  public EAEvaluationOtherMatrixMethod(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jlbtnEnvAsp);
    datasource.addDatasourceListener(jtfldEnvAspID);
    datasource.addDatasourceListener(jlfldEnvAspDescription);
    datasource.addDatasourceListener(jlbtnCategory1);
    datasource.addDatasourceListener(jtfldCategory1ID);
    datasource.addDatasourceListener(jtfldCategory1);
    datasource.addDatasourceListener(jlfldCategory1Description);
    datasource.addDatasourceListener(jlbtnCategory2);
    datasource.addDatasourceListener(jtfldCategory2ID);
    datasource.addDatasourceListener(jtfldCategory2);
    datasource.addDatasourceListener(jlfldCategory2Description);
    datasource.addDatasourceListener(jlbtnCategory3);
    datasource.addDatasourceListener(jtfldCategory3ID);
    datasource.addDatasourceListener(jtfldCategory3);
    datasource.addDatasourceListener(jlfldCategory3Description);
    datasource.addDatasourceListener(jlbtnCategory4);
    datasource.addDatasourceListener(jtfldCategory4ID);
    datasource.addDatasourceListener(jtfldCategory4);
    datasource.addDatasourceListener(jlfldCategory4Description);
    datasource.addDatasourceListener(jcmbSignificanceDescription);
    datasource.addDatasourceListener(jtfldSignificance);

    fwCListener.addFWComponentListener(jlbtnEnvAsp);
    fwCListener.addFWComponentListener(jtfldEnvAspID);
    fwCListener.addFWComponentListener(jlfldEnvAspDescription);
    fwCListener.addFWComponentListener(jlbtnCategory1);
    fwCListener.addFWComponentListener(jtfldCategory1ID);
    fwCListener.addFWComponentListener(jtfldCategory1);
    fwCListener.addFWComponentListener(jlfldCategory1Description);
    fwCListener.addFWComponentListener(jlbtnCategory2);
    fwCListener.addFWComponentListener(jtfldCategory2ID);
    fwCListener.addFWComponentListener(jtfldCategory2);
    fwCListener.addFWComponentListener(jlfldCategory2Description);
    fwCListener.addFWComponentListener(jlbtnCategory3);
    fwCListener.addFWComponentListener(jtfldCategory3ID);
    fwCListener.addFWComponentListener(jtfldCategory3);
    fwCListener.addFWComponentListener(jlfldCategory3Description);
    fwCListener.addFWComponentListener(jlbtnCategory4);
    fwCListener.addFWComponentListener(jtfldCategory4ID);
    fwCListener.addFWComponentListener(jtfldCategory4);
    fwCListener.addFWComponentListener(jlfldCategory4Description);
    fwCListener.addFWComponentListener(jcmbSignificanceDescription);
    fwCListener.addFWComponentListener(jtfldSignificance);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jtfldEnvAspID.setField("envAspID");

    jlbtnEnvAsp.setText(res.getString("label.envAsp"));
    jlbtnEnvAsp.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
    jlbtnEnvAsp.setTitle(res.getString("label.envAspList"));
    jlbtnEnvAsp.setDefaultFill(jtfldEnvAspID);

    jlfldEnvAspDescription.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
    jlfldEnvAspDescription.setDefaultRefField(jtfldEnvAspID);

    jtfldCategory1ID.setField("cat1ID");
    jtfldCategory1.setField("cat1");

    jlbtnCategory1.setText(res.getString("eaevaluationOtherMethod.label.cat1"));
    jlbtnCategory1.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlbtnCategory1.setTitle(res.getString("eaevaluationOtherMethod.label.cat1"));
    jlbtnCategory1.setDefaultFill(jtfldCategory1);
    JTextField[] txt = new JTextField[2];
    txt[0] = jtfldCategory1ID;
    txt[1] = jtfldCategory1;
    jlbtnCategory1.setFillList(txt);

    jlfldCategory1Description.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldCategory1Description.setRefFieldList(txt);

    jtfldCategory2ID.setField("cat2ID");
    jtfldCategory2.setField("cat2");

    jlbtnCategory2.setText(res.getString("eaevaluationOtherMethod.label.cat2"));
    jlbtnCategory2.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlbtnCategory2.setTitle(res.getString("eaevaluationOtherMethod.label.cat2"));
    JTextField[] txt2 = new JTextField[2];
    txt2[0] = jtfldCategory2ID;
    txt2[1] = jtfldCategory2;
    jlbtnCategory2.setFillList(txt2);

    jlfldCategory2Description.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldCategory2Description.setRefFieldList(txt2);

    jtfldCategory3ID.setField("cat3ID");
    jtfldCategory3.setField("cat3");

    jlbtnCategory3.setText(res.getString("eaevaluationOtherMethod.label.cat3"));
    jlbtnCategory3.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlbtnCategory3.setTitle(res.getString("eaevaluationOtherMethod.label.cat3"));
    JTextField[] txt3 = new JTextField[2];
    txt3[0] = jtfldCategory3ID;
    txt3[1] = jtfldCategory3;
    jlbtnCategory3.setFillList(txt3);

    jlfldCategory3Description.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldCategory3Description.setRefFieldList(txt3);

    jtfldCategory4ID.setField("cat4ID");
    jtfldCategory4.setField("cat4");

    jlbtnCategory4.setText(res.getString("eaevaluationOtherMethod.label.cat3"));
    jlbtnCategory4.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlbtnCategory4.setTitle(res.getString("eaevaluationOtherMethod.label.cat3"));
    JTextField[] txt4 = new JTextField[2];
    txt4[0] = jtfldCategory4ID;
    txt4[1] = jtfldCategory4;
    jlbtnCategory4.setFillList(txt4);

    jlfldCategory4Description.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");
    jlfldCategory4Description.setRefFieldList(txt4);

    jlblSignificance.setText(res.getString("label.significance"));
    jtfldSignificance.setField("significance");
    jtfldSignificance.setHolder(jlblSignificance);

    jcmbSignificanceDescription.setField("significanceDescription");
    jcmbSignificanceDescription.setDataItems(new Object[] {"", res.getString("label.Significant"), res.getString("label.NonSignificant")});
    jcmbSignificanceDescription.setDataValues(new Object[] {null, res.getString("label.Significant"), res.getString("label.NonSignificant")});

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,50dlu,4dlu, 70dlu,4dlu, 50dlu,40dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu,pref,2dlu,pref,2dlu,pref,5px");

    FormLayout formLayout2 = new FormLayout("5px, left:pref, 4dlu, 70dlu, 10dlu,pref,4dlu, 70dlu, 5px",
                                            "5px, pref, 5px");

    JPanel content = new JPanel();
    JPanel significantInterval = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    significantInterval.setOpaque(false);
    significantInterval.setLayout(formLayout2);

    CellConstraints cc = new CellConstraints();
    content.add(jlbtnEnvAsp, cc.xy(2, 2));
    content.add(jtfldEnvAspID, cc.xy(4, 2));
    content.add(jlfldEnvAspDescription, cc.xyw(6, 2, 6));

    content.add(jlbtnCategory1, cc.xy(2, 4));
    content.add(jtfldCategory1, cc.xy(4, 4));
    content.add(jlfldCategory1Description, cc.xyw(6, 4, 6));

    content.add(jlbtnCategory2, cc.xy(2, 6));
    content.add(jtfldCategory2, cc.xy(4, 6));
    content.add(jlfldCategory2Description, cc.xyw(6, 6, 6));

    content.add(jlbtnCategory3, cc.xy(2, 8));
    content.add(jtfldCategory3, cc.xy(4, 8));
    content.add(jlfldCategory3Description, cc.xyw(6, 8, 6));

    content.add(jlbtnCategory4, cc.xy(2, 10));
    content.add(jtfldCategory4, cc.xy(4, 10));
    content.add(jlfldCategory4Description, cc.xyw(6, 10, 6));

    content.add(jlblSignificance, cc.xy(6, 12));
    content.add(jcmbSignificanceDescription, cc.xy(8, 12));
    content.add(jtfldSignificance, cc.xy(10, 12));

    add(content, BorderLayout.NORTH);
  }
}
