package pt.inescporto.siasoft.condoc.client.rmi.forms;

import pt.inescporto.template.client.design.FW_JTable;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JScrollPane;
import java.util.ResourceBundle;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplDateRenderer;
import pt.inescporto.template.client.design.table.TmplDateEditor;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import pt.inescporto.template.client.event.EventSynchronizerWatcher;
import pt.inescporto.template.client.event.EventSynchronizerWatcherRemover;
import pt.inescporto.template.client.event.EventSynchronizer;

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

public class DocumentHistoryTab extends JPanel implements EventSynchronizerWatcher, EventSynchronizerWatcherRemover{

  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  private String subject = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.condoc.client.rmi.forms.FormResources");

  JScrollPane jScrollPane1 = new JScrollPane();
  FW_JTable jtblHistory = null;

  public DocumentHistoryTab(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    fwCListener.addFWComponentListener(jtblHistory);

  }

  public void setWatcherSubject(String subject) {
    eventSynchronizerTriggered(this.subject);
    this.subject = subject;
    EventSynchronizer.getInstance().addEventSynchronizerWatcher(this, subject);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);
    setBorder(BorderFactory.createTitledBorder(res.getString("revisorsapp.label.history")));

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("docHistory.label.doc"),
					       "documentID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("docHistory.label.actDate"),
					       "actionDate",
					       new TmplDateRenderer(),
					       new TmplDateEditor()));
    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("documentdef.label.resp"),
					       "fkUserID",
					       new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.User"),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(3,
					       res.getString("docHistory.label.actType"),
					       "actionType",
					       new TmplLookupRenderer(new Object[] {res.getString("docHistory.label.actType.sub"), res.getString("docHistory.label.actType.rev"), res.getString("docHistory.label.actType.app")}, new Object[] {"S", "R", "A"}),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(4,
					       res.getString("docHistory.label.actUser"),
					       "fkActionUserID",
					       new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.User"),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(5,
					       res.getString("docHistory.label.obs"),
					       "obs",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    try {
      jtblHistory = new FW_JTable(datasource.getDataSourceByName("DocumentHistory"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    jtblHistory.setAsMaster(false);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, 50dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}
    });
    CellConstraints cc = new CellConstraints();

    jScrollPane1.getViewport().add(jtblHistory);
    content.add(jScrollPane1, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);

  }

  /**
   * ***************************************************************************
   *                  Implementation of the EventSynchronizerWatcher
   * ***************************************************************************
   */
  public void eventSynchronizerTriggered(String subject) {
    if (this.subject != null && this.subject.equals(subject))
      try {
	datasource.refresh();
      }
      catch (DataSourceException ex) {
      }
  }

  /**
   * ***************************************************************************
   *               Implementation of the EventSynchronizerWatcherRemover
   * ***************************************************************************
   */
  public void removeEventSynchronizer() {
    if (subject != null) {
      EventSynchronizer.getInstance().removeEventSynchronizerWatcher(this, subject);
    }
  }


}
