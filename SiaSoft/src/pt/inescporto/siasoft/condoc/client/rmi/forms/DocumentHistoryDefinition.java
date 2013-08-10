package pt.inescporto.siasoft.condoc.client.rmi.forms;

import javax.swing.JPanel;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import java.util.ResourceBundle;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJComboBox;
import pt.inescporto.template.client.rmi.MenuSingleton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.TmplJDateField;
import pt.inescporto.template.client.design.TmplJTextArea;
import javax.swing.JScrollPane;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.client.event.TemplateEvent;
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

public class DocumentHistoryDefinition extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.condoc.client.rmi.forms.FormResources");

  TmplLookupButton jlbtnDocumentID = new TmplLookupButton();
  TmplJTextField jtfldDocumentID = new TmplJTextField() {
    public void tmplLink(TemplateEvent e) {
      super.tmplLink(e);
      if (this.link != null)
	this.link.resetLinkKey();
    }
  };
  TmplLookupField jlfldDocumentDescription = new TmplLookupField();

  TmplLookupButton jlbtnUserResp = new TmplLookupButton();
  TmplJTextField jtfldUserRespID = new TmplJTextField();
  TmplLookupField jlfldUserRespDescription = new TmplLookupField();

  TmplLookupButton jlbtnActionUser = new TmplLookupButton();
  TmplJTextField jtfldActionUserID = new TmplJTextField();
  TmplLookupField jlfldActionUserDescription = new TmplLookupField();

  TmplJLabel jlblActionType = new TmplJLabel();
  TmplJComboBox jcmbActionType = new TmplJComboBox();

  TmplJLabel jlblActionDate = new TmplJLabel();
  TmplJDatePicker jtfldActionDate = new TmplJDatePicker();

  TmplJLabel jlblActionObs = new TmplJLabel();
  TmplJTextArea jtfldActionObs = new TmplJTextArea();

  public DocumentHistoryDefinition(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jlbtnDocumentID);
    datasource.addDatasourceListener(jtfldDocumentID);
    datasource.addDatasourceListener(jlfldDocumentDescription);
    datasource.addDatasourceListener(jlbtnUserResp);
    datasource.addDatasourceListener(jtfldUserRespID);
    datasource.addDatasourceListener(jlfldUserRespDescription);
    datasource.addDatasourceListener(jtfldActionDate);
    datasource.addDatasourceListener(jcmbActionType);
    datasource.addDatasourceListener(jlbtnActionUser);
    datasource.addDatasourceListener(jtfldActionUserID);
    datasource.addDatasourceListener(jlfldActionUserDescription);
    datasource.addDatasourceListener(jtfldActionObs);

    fwCListener.addFWComponentListener(jlbtnDocumentID);
    fwCListener.addFWComponentListener(jtfldDocumentID);
    fwCListener.addFWComponentListener(jlfldDocumentDescription);
    fwCListener.addFWComponentListener(jlbtnUserResp);
    fwCListener.addFWComponentListener(jtfldUserRespID);
    fwCListener.addFWComponentListener(jlfldUserRespDescription);
    fwCListener.addFWComponentListener(jtfldActionDate);
    fwCListener.addFWComponentListener(jcmbActionType);
    fwCListener.addFWComponentListener(jlbtnActionUser);
    fwCListener.addFWComponentListener(jtfldActionUserID);
    fwCListener.addFWComponentListener(jlfldActionUserDescription);
    fwCListener.addFWComponentListener(jtfldActionObs);

  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);
    jtfldDocumentID.setField("documentID");

    jlbtnDocumentID.setText(res.getString("docHistory.label.doc"));
    jlbtnDocumentID.setUrl("pt.inescporto.siasoft.condoc.ejb.session.Document");
    jlbtnDocumentID.setTitle(res.getString("docHistory.label.doclist"));
    jlbtnDocumentID.setDefaultFill(jtfldDocumentID);

    jlfldDocumentDescription.setUrl("pt.inescporto.siasoft.condoc.ejb.session.Document");
    jlfldDocumentDescription.setDefaultRefField(jtfldDocumentID);

    jtfldUserRespID.setField("fkUserID");

    jlbtnUserResp.setText(res.getString("documentdef.label.resp"));
    jlbtnUserResp.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    if (!MenuSingleton.isSupplier())
      jlbtnUserResp.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnUserResp.setTitle(res.getString("documentdef.label.resplist"));
    jlbtnUserResp.setDefaultFill(jtfldUserRespID);

    jlfldUserRespDescription.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    jlfldUserRespDescription.setDefaultRefField(jtfldUserRespID);

    jlblActionDate.setText(res.getString("docHistory.label.actDate"));
    jtfldActionDate.setField("actionDate");
    jtfldActionDate.setHolder(jlblActionDate);

    jlblActionType.setText(res.getString("docHistory.label.actType"));
    jcmbActionType.setField("actionType");
    jcmbActionType.setDataItems(new Object[] {"", res.getString("docHistory.label.actType.sub"), res.getString("docHistory.label.actType.rev"), res.getString("docHistory.label.actType.app")});
    jcmbActionType.setDataValues(new Object[] {null, "S", "R", "A"});
    jcmbActionType.setHolder(jlblActionType);

    jtfldActionUserID.setField("fkActionUserID");

    jlbtnActionUser.setText(res.getString("docHistory.label.actUser"));
    jlbtnActionUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    if (!MenuSingleton.isSupplier())
      jlbtnActionUser.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnActionUser.setTitle(res.getString("documentdef.label.resplist"));
    jlbtnActionUser.setDefaultFill(jtfldActionUserID);

    jlfldActionUserDescription.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    jlfldActionUserDescription.setDefaultRefField(jtfldActionUserID);

    jlblActionObs.setText(res.getString("docHistory.label.obs"));
    jtfldActionObs.setField("obs");
    jtfldActionObs.setHolder(jlblActionObs);
    jtfldActionObs.setWrapStyleWord(true);
    jtfldActionObs.setLineWrap(true);
    JScrollPane jspObs = new JScrollPane(jtfldActionObs);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu,pref,2dlu,pref,2dlu,50dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] { {2, 4, 6, 8, 10}
    });

    CellConstraints cc = new CellConstraints();

    content.add(jlbtnDocumentID, cc.xy(2, 2));
    content.add(jtfldDocumentID, cc.xy(4, 2));
    content.add(jlfldDocumentDescription, cc.xyw(6, 2, 4));

    content.add(jlblActionDate, cc.xy(2, 4));
    content.add(jtfldActionDate, cc.xy(4, 4));

    content.add(jlbtnUserResp, cc.xy(2, 6));
    content.add(jtfldUserRespID, cc.xy(4, 6));
    content.add(jlfldUserRespDescription, cc.xyw(6, 6, 4));

    content.add(jlblActionType, cc.xy(2, 8));
    content.add(jcmbActionType, cc.xy(4, 8));

    content.add(jlbtnActionUser, cc.xy(2, 10));
    content.add(jtfldActionUserID, cc.xy(4, 10));
    content.add(jlfldActionUserDescription, cc.xyw(6, 10, 4));

    content.add(jlblActionObs, cc.xy(2, 12));
    content.add(jspObs, cc.xyw(4, 12, 6, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);

  }

}
