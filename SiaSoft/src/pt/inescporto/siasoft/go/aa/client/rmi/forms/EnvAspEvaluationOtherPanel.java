package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.DataSourceRMI;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.util.ResourceBundle;
import java.awt.Cursor;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvironmentAspectDao;
import pt.inescporto.template.client.rmi.FW_JPanel;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;

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
public class EnvAspEvaluationOtherPanel extends FW_JPanel {
  JTabbedPane jTabbedPane = new JTabbedPane();
  JPanel jPanel = new JPanel();
  DataSourceRMI master = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.aa.client.rmi.forms.FormResources");

  public EnvAspEvaluationOtherPanel(TplString envAspID) {
    master = new DataSourceRMI("EnvAspOtherMethod");
    master.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvAspOtherMethod");
    Vector binds = new Vector();
       binds.add(envAspID);
       try {
         master.setLinkCondition(envAspID.getField() + " = ?", binds);
         master.refresh();
       }
       catch (DataSourceException ex1) {
         ex1.printStackTrace();
    }
    setDataSource(master);
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
     allHeaders = new String[] {res.getString("label.envAsp"), res.getString("label.significance"),res.getString("label.significanceDescription"),res.getString("eaevaluationOtherMethod.label.catID"),res.getString("eaevaluationOtherMethod.label.cat1"),res.getString("eaevaluationOtherMethod.label.op"),res.getString("eaevaluationOtherMethod.label.catID"),res.getString("eaevaluationOtherMethod.label.cat2"),res.getString("eaevaluationOtherMethod.label.op"),res.getString("eaevaluationOtherMethod.label.catID"),res.getString("eaevaluationOtherMethod.label.cat3"),res.getString("label.max"),res.getString("label.min")};
    start();

  }

  private void jbInit() throws Exception {
    jPanel = new JPanel();
    add(jPanel, java.awt.BorderLayout.CENTER);
    jPanel.add(new EAEvaluationOtherMethod(dataSource, this));
  }

 /* public boolean setPrimaryKey(Object keyValue) {
    try {
      setCursor(new Cursor(Cursor.WAIT_CURSOR));
      EnvironmentAspectDao attrs = (EnvironmentAspectDao)dataSource.getAttrs();
      attrs.envAspectID.setValue((String)keyValue);
      dataSource.findByPK(attrs);
    }
    catch (DataSourceException ex) {
      return false;
    }
    finally {
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    return true;
  }*/
}
