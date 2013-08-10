package pt.inescporto.template.client.design;

import java.awt.event.ActionListener;
import javax.swing.JToolBar;
import pt.inescporto.template.client.util.DataSource;

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
public class FW_NavBarDetail extends JToolBar {
  protected TmplJButtonDetailInsert jbtnInsert = new TmplJButtonDetailInsert();
  protected TmplJButtonDetailUpdate jbtnUpdate = new TmplJButtonDetailUpdate();
  protected TmplJButtonDetailDelete jbtnDelete = new TmplJButtonDetailDelete();
  protected TmplJButtonDetailSave jbtnSave = new TmplJButtonDetailSave();
  protected TmplJButtonDetailCancel jbtnCancel = new TmplJButtonDetailCancel();

  public FW_NavBarDetail() {
    setFloatable(false);
    add(jbtnInsert);
    add(jbtnUpdate);
    add(jbtnDelete);
    add(jbtnSave);
    add(jbtnCancel);
  }

  public void setFW_ComponentListener(FW_ComponentListener fwCL) {
    // register Framework component listeners
    if (fwCL != null) {
      fwCL.addFWComponentListener(jbtnInsert);
      fwCL.addFWComponentListener(jbtnUpdate);
      fwCL.addFWComponentListener(jbtnDelete);
      fwCL.addFWComponentListener(jbtnSave);
      fwCL.addFWComponentListener(jbtnCancel);
    }
  }

  public void setDatasourceListener(DataSource dsl) {
    // register datasource listeners
    if (dsl != null) {
      dsl.addDatasourceListener(jbtnInsert);
      dsl.addDatasourceListener(jbtnUpdate);
      dsl.addDatasourceListener(jbtnDelete);
      dsl.addDatasourceListener(jbtnSave);
      dsl.addDatasourceListener(jbtnCancel);
    }
  }

  public void setActionListener(ActionListener al) {
    jbtnInsert.addActionListener(al);
    jbtnUpdate.addActionListener(al);
    jbtnDelete.addActionListener(al);
    jbtnSave.addActionListener(al);
    jbtnCancel.addActionListener(al);
  }
}
