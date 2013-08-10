package pt.inescporto.siasoft.condoc.client.rmi.forms;

import javax.swing.*;

import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.util.*;
import java.awt.BorderLayout;
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

public class DocumentTypeDefinition extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.condoc.client.rmi.forms.FormResources");

  TmplLookupButton jlbtnEnterprise = new TmplLookupButton();
  TmplJTextField jtfldEnterprise = new TmplJTextField();
  TmplLookupField jlfldEnterprise = new TmplLookupField();

  TmplJLabel jlblDocumentTypeID = new TmplJLabel();
  TmplJTextField jtfldDocumentTypeID = new TmplJTextField();

  TmplJLabel jlblDocumentTypeDescription = new TmplJLabel();
  TmplJTextField jtfldDocumentTypeDescription = new TmplJTextField();

  TmplLookupButton jlbtnDocumentTypeFatherID = new TmplLookupButton();
  TmplJTextField jtfldDocumentTypeFatherID = new TmplJTextField();
  TmplLookupField jlfldDocumentTypeFatherDescription = new TmplLookupField();

  public DocumentTypeDefinition(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jtfldDocumentTypeID);
    datasource.addDatasourceListener(jtfldDocumentTypeDescription);
    datasource.addDatasourceListener(jlbtnDocumentTypeFatherID);
    datasource.addDatasourceListener(jtfldDocumentTypeFatherID);
    datasource.addDatasourceListener(jlfldDocumentTypeFatherDescription);
    datasource.addDatasourceListener(jlbtnEnterprise);
    datasource.addDatasourceListener(jtfldEnterprise);
    datasource.addDatasourceListener(jlfldEnterprise);

    fwCListener.addFWComponentListener(jtfldDocumentTypeID);
    fwCListener.addFWComponentListener(jtfldDocumentTypeDescription);
    fwCListener.addFWComponentListener(jlbtnDocumentTypeFatherID);
    fwCListener.addFWComponentListener(jtfldDocumentTypeFatherID);
    fwCListener.addFWComponentListener(jlfldDocumentTypeFatherDescription);
    fwCListener.addFWComponentListener(jlbtnEnterprise);
    fwCListener.addFWComponentListener(jtfldEnterprise);
    fwCListener.addFWComponentListener(jlfldEnterprise);

  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jtfldEnterprise.setText(res.getString("document.label.enterprise"));
    jtfldEnterprise.setField("fkEnterpriseID");

    jlbtnEnterprise.setText(res.getString("document.label.enterprise"));
    jlbtnEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlbtnEnterprise.setTitle(res.getString("document.label.enterpriselist"));
    jlbtnEnterprise.setDefaultFill(jtfldEnterprise);

    jlfldEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlfldEnterprise.setDefaultRefField(jtfldEnterprise);

    jlblDocumentTypeID.setText(res.getString("documenttypedef.label.code"));
    jtfldDocumentTypeID.setField("documentTypeID");
    jtfldDocumentTypeID.setHolder(jlblDocumentTypeID);

    jlblDocumentTypeDescription.setText(res.getString("documenttypedef.label.description"));
    jtfldDocumentTypeDescription.setField("documentTypeDescription");
    jtfldDocumentTypeID.setHolder(jlblDocumentTypeDescription);

    jtfldDocumentTypeFatherID.setField("fkDocumentTypeID");

    jlbtnDocumentTypeFatherID.setText(res.getString("documenttypedef.label.father"));
    jlbtnDocumentTypeFatherID.setTitle(res.getString("documenttypedef.label.fatherlist"));
    jlbtnDocumentTypeFatherID.setUrl("pt.inescporto.siasoft.condoc.ejb.session.DocumentType");
    jlbtnDocumentTypeFatherID.setDefaultFill(jtfldDocumentTypeFatherID);

    jlfldDocumentTypeFatherDescription.setUrl("pt.inescporto.siasoft.condoc.ejb.session.DocumentType");
    jlfldDocumentTypeFatherDescription.setDefaultRefField(jtfldDocumentTypeFatherID);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);

    if (MenuSingleton.isSupplier())
      formLayout.setRowGroups(new int[][] { {2, 4, 6, 8}
      });
    else
      formLayout.setRowGroups(new int[][] { {4, 6, 8}
      });

    CellConstraints cc = new CellConstraints();
    if (MenuSingleton.isSupplier()) {
      content.add(jlbtnEnterprise, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));
      content.add(jtfldEnterprise, cc.xy(4, 2));
      content.add(jlfldEnterprise, cc.xy(6, 2));
    }

    content.add(jlblDocumentTypeID, cc.xy(2, 4));
    content.add(jtfldDocumentTypeID, cc.xy(4, 4));

    content.add(jlblDocumentTypeDescription, cc.xy(2, 6));
    content.add(jtfldDocumentTypeDescription, cc.xyw(4, 6, 3));

    content.add(jlbtnDocumentTypeFatherID, cc.xy(2, 8, CellConstraints.FILL, CellConstraints.FILL));
    content.add(jtfldDocumentTypeFatherID, cc.xy(4, 8));
    content.add(jlfldDocumentTypeFatherDescription, cc.xy(6, 8));

    add(content, BorderLayout.CENTER);
  }

}
