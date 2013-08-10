package pt.inescporto.siasoft.condoc.client.rmi.forms;

import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplLookupField;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.TmplLookupButton;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.FW_ComponentListener;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.rmi.MenuSingleton;

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

public class DocumentHistoryLink extends JPanel {

  TmplLookupButton jlbtnEnterprise = new TmplLookupButton();
  TmplJTextField jtfldEnterprise = new TmplJTextField();
  TmplLookupField jlfldEnterprise = new TmplLookupField();
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.condoc.client.rmi.forms.FormResources");

  public DocumentHistoryLink(DataSource datasource, FW_ComponentListener fwCListener) {
    initialize();

    datasource.addDatasourceListener(jlbtnEnterprise);
    datasource.addDatasourceListener(jtfldEnterprise);
    datasource.addDatasourceListener(jlfldEnterprise);


    fwCListener.addFWComponentListener(jlbtnEnterprise);
    fwCListener.addFWComponentListener(jtfldEnterprise);
    fwCListener.addFWComponentListener(jlfldEnterprise);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jtfldEnterprise.setLabel(res.getString("document.label.enterprise"));
    jtfldEnterprise.setField("fkEnterpriseID");
    if (!MenuSingleton.isSupplier())
       jlbtnEnterprise.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlbtnEnterprise.setText(res.getString("document.label.enterprise"));
    jlbtnEnterprise.setTitle(res.getString("document.label.enterpriselist"));
    jlbtnEnterprise.setDefaultFill(jtfldEnterprise);
    jlfldEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlfldEnterprise.setDefaultRefField(jtfldEnterprise);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 100dlu:grow, 5px",
                                           "5px, pref, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}
    });
    CellConstraints cc = new CellConstraints();

    content.add(jlbtnEnterprise, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldEnterprise, cc.xy(4, 2));
    content.add(jlfldEnterprise, cc.xy(6, 2));

    add(content, BorderLayout.NORTH);

  }
}
