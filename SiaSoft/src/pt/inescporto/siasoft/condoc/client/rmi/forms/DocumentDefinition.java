package pt.inescporto.siasoft.condoc.client.rmi.forms;

import javax.swing.*;

import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.util.*;
import pt.inescporto.siasoft.condoc.client.rmi.forms.RevisorsApprovalTabbedPane;
import java.awt.BorderLayout;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_ComponentListener;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.rmi.MenuSingleton;
import java.util.ResourceBundle;


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

public class DocumentDefinition extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.condoc.client.rmi.forms.FormResources");

  TmplJLabel jlblDocumentDefID = new TmplJLabel();
  TmplJTextField jtfldDocumentDefID = new TmplJTextField();

  TmplJLabel jlblDocumentDescription = new TmplJLabel();
  TmplJTextField jtfldDocumentDescription = new TmplJTextField();

  TmplLookupButton jlbtnDocumentTypeID = new TmplLookupButton();

  TmplJTextField jtfldDocumentTypeID = new TmplJTextField();
  TmplLookupField jlfldDocumentTypeDescription = new TmplLookupField();

  TmplJLabel jlblApprovalDate = new TmplJLabel();
  TmplJDatePicker jtfldApprovalDate = new TmplJDatePicker();

  TmplJLabel jlblDocState = new TmplJLabel();
  TmplJComboBox jcmbDocState = new TmplJComboBox();

  TmplLookupButton jlbtnUserResp = new TmplLookupButton();
  TmplJTextField jtfldUserRespID = new TmplJTextField();
  TmplLookupField jlfldUserRespDescription = new TmplLookupField();

  TmplJLabel jlblDocumentUrl = new TmplJLabel();
  TmplJTextField4ActiveLink jtfldDocumentUrl = new TmplJTextField4ActiveLink();

  public DocumentDefinition(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jtfldDocumentDefID);
    datasource.addDatasourceListener(jtfldDocumentDescription);
    datasource.addDatasourceListener(jlbtnDocumentTypeID);
    datasource.addDatasourceListener(jtfldDocumentTypeID);
    datasource.addDatasourceListener(jlfldDocumentTypeDescription);
    datasource.addDatasourceListener(jcmbDocState);
    datasource.addDatasourceListener(jlbtnUserResp);
    datasource.addDatasourceListener(jtfldUserRespID);
    datasource.addDatasourceListener(jlfldUserRespDescription);
    datasource.addDatasourceListener(jtfldApprovalDate);
    datasource.addDatasourceListener(jtfldDocumentUrl);

    fwCListener.addFWComponentListener(jtfldDocumentDefID);
    fwCListener.addFWComponentListener(jtfldDocumentDescription);
    fwCListener.addFWComponentListener(jlbtnDocumentTypeID);
    fwCListener.addFWComponentListener(jtfldDocumentTypeID);
    fwCListener.addFWComponentListener(jlfldDocumentTypeDescription);
    fwCListener.addFWComponentListener(jcmbDocState);
    fwCListener.addFWComponentListener(jlbtnUserResp);
    fwCListener.addFWComponentListener(jtfldUserRespID);
    fwCListener.addFWComponentListener(jlfldUserRespDescription);
    fwCListener.addFWComponentListener(jtfldApprovalDate);
    fwCListener.addFWComponentListener(jtfldDocumentUrl);
  }


  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

   jlblDocumentDefID.setText(res.getString("documentdef.label.code"));
   jtfldDocumentDefID.setField("documentID");
   jtfldDocumentDefID.setHolder(jlblDocumentDefID);

   jlblDocumentDescription.setText(res.getString("documentdef.label.description"));
   jtfldDocumentDescription.setField("documentDescription");
   jtfldDocumentDescription.setHolder(jlblDocumentDescription);

   jlblApprovalDate.setText(res.getString("documentdef.label.approvaldate"));
   jtfldApprovalDate.setField("approvalDate");
   jtfldApprovalDate.setHolder(jlblApprovalDate);

   jtfldDocumentTypeID.setField("fkDocumentTypeID");

   jlbtnDocumentTypeID.setText(res.getString("documentdef.buttonlabel.type"));
   jlbtnDocumentTypeID.setUrl("pt.inescporto.siasoft.condoc.ejb.session.DocumentType");
   jlbtnDocumentTypeID.setTitle(res.getString("documentdef.label.doctype"));
   jlbtnDocumentTypeID.setDefaultFill(jtfldDocumentTypeID);

   jlfldDocumentTypeDescription.setUrl("pt.inescporto.siasoft.condoc.ejb.session.DocumentType");
   jlfldDocumentTypeDescription.setDefaultRefField(jtfldDocumentTypeID);

   jlblDocState.setText(res.getString("documentdef.state.cstate"));
   jcmbDocState.setField("documentState");
   jcmbDocState.setDataItems(new Object[] {"", res.getString("documentdef.state.wait"), res.getString("documentdef.state.evaluating"), res.getString("documentdef.state.delayed"), res.getString("documentdef.state.finished")});
   jcmbDocState.setDataValues(new Object[] {null, "E", "X", "A", "C"});
   jcmbDocState.setHolder(jlblDocState);

   jtfldUserRespID.setField("fkUserID");

   jlbtnUserResp.setText(res.getString("documentdef.label.resp"));
   jlbtnUserResp.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
   if (!MenuSingleton.isSupplier())
     jlbtnUserResp.setLinkCondition("enterpriseID = '"+ MenuSingleton.getEnterprise() +"'");
   jlbtnUserResp.setTitle(res.getString("documentdef.label.resplist"));
   jlbtnUserResp.setDefaultFill(jtfldUserRespID);


   jlfldUserRespDescription.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
   jlfldUserRespDescription.setDefaultRefField(jtfldUserRespID);

   jlblDocumentUrl.setText(res.getString("documentdef.label.url"));
   jtfldDocumentUrl.setField("documentURL");
   jtfldDocumentUrl.setHolder(jlblDocumentDefID);


  FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu,pref,4dlu, 70dlu,100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu,pref,2dlu,pref, 2dlu,pref, 5px");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] { {2, 4, 6, 8, 10}});

    CellConstraints cc = new CellConstraints();

    content.add(jlblDocumentDefID, cc.xy(2, 2));
    content.add(jtfldDocumentDefID, cc.xy(4, 2));

    content.add(jlblDocumentDescription, cc.xy(2, 4));
    content.add(jtfldDocumentDescription, cc.xyw(4, 4, 6));

    content.add(jlbtnDocumentTypeID, cc.xy(2, 6));
    content.add(jtfldDocumentTypeID, cc.xy(4, 6));
    content.add(jlfldDocumentTypeDescription, cc.xyw(6, 6, 4));

    content.add(jlbtnUserResp, cc.xy(2, 8));
    content.add(jtfldUserRespID, cc.xy(4, 8));
    content.add(jlfldUserRespDescription, cc.xyw(6, 8, 4));

    content.add(jlblDocState, cc.xy(2,10));
    content.add(jcmbDocState, cc.xy(4,10));

    content.add(jlblApprovalDate, cc.xy(6, 10));
    content.add(jtfldApprovalDate, cc.xy(8, 10));

    content.add(jlblDocumentUrl, cc.xy(2, 12));
    content.add(jtfldDocumentUrl, cc.xyw(4, 12,6));

    add(content, BorderLayout.NORTH);
    add(new RevisorsApprovalTabbedPane(datasource, fwCListener), BorderLayout.CENTER);
  }
}
