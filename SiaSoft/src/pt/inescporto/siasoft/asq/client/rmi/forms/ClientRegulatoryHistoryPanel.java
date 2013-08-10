package pt.inescporto.siasoft.asq.client.rmi.forms;

import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.FW_JTable;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_ComponentListener;
import javax.swing.JScrollPane;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.util.*;
import java.awt.Color;
import pt.inescporto.template.client.rmi.FW_JPanelBasic;
import pt.inescporto.template.client.event.DataSourceListener;
import java.awt.Dimension;
import pt.inescporto.template.client.design.TmplJButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import pt.inescporto.template.client.design.TmplJLabel;
import java.awt.Toolkit;

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
public class ClientRegulatoryHistoryPanel extends FW_JPanelBasic implements DataSourceListener {
  private DataSource datasource = null;
  private FW_ComponentListener fwCListener = null;
  NewClientRegulatoryHistoryPanel image = null;
  double var=0;
 // private JGraph graph = null;

  JPanel jPanel1 = new JPanel();

  TmplJTextField jtfldRegulatoryDescription = new TmplJTextField() {
    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }
    public void tmplSave(TemplateEvent e) {}
  };

  TmplJTextField jtfldRegulatoryID = new TmplJTextField() {
    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }
    public void tmplSave(TemplateEvent e) {}
    public void tmplRefresh(TemplateEvent e) {
      super.tmplRefresh(e);
      // buildGraph();
    }
  };

  JScrollPane jScrollPane1 = new JScrollPane();
  FW_JTable jtblActiveRef = null;

  JScrollPane jScrollPane2 = new JScrollPane();
  FW_JTable jtblPassiveRef = null;
  JScrollPane jsp= new JScrollPane();
  TmplJButton legBtn= new TmplJButton();

  public ClientRegulatoryHistoryPanel(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    fwCListener.addFWComponentListener(this);

    fwCListener.addFWComponentListener(jtfldRegulatoryDescription);

    datasource.addDatasourceListener(jtfldRegulatoryDescription);
    datasource.addDatasourceListener(jtfldRegulatoryID);
    datasource.addDatasourceListener(this);

    setAccessPermitionIdentifier("REG_CLIENT_HISTORY");
  }

  private void jbInit() throws Exception {
    removeAll();
    setLayout(new BorderLayout());
    setOpaque(false);
    FormLayout formLayout = new FormLayout("100dlu:grow, 2dlu 40dlu",
					   "pref");
    CellConstraints cc = new CellConstraints();
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);

    jtfldRegulatoryID.setField("regId");
    jtfldRegulatoryDescription.setField("name");
    legBtn.setText("Legenda");

    content.add(jtfldRegulatoryDescription, cc.xy(1, 1));
    content.add(legBtn, cc.xy(3, 1));

    legBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	FormLayout formLayout = new FormLayout("5px,pref,5px",
					       "5px,pref,2dlu,pref,2dlu,pref,2dlu,pref,2dlu,pref,2dlu,pref,5px");
	CellConstraints cc = new CellConstraints();
	JPanel jpanel = new JPanel();
	jpanel.setOpaque(false);
	jpanel.setLayout(formLayout);
	TmplJLabel jlbAltera = new TmplJLabel("Altera");
	jlbAltera.setForeground(new Color(255, 215, 0));

	TmplJLabel jlbRevoga = new TmplJLabel("Revoga");
	jlbRevoga.setForeground(Color.red);

	TmplJLabel jlbTranspoe = new TmplJLabel("Transpoe");
	jlbTranspoe.setForeground(Color.blue);

	TmplJLabel jlbRevogaParc = new TmplJLabel("Revoga Parcialmente");
	jlbRevogaParc.setForeground(Color.magenta);

	TmplJLabel jlbTranspoeParc = new TmplJLabel("Transpoe Parcialmente");
	jlbTranspoeParc.setForeground(new Color(147, 112, 219));

	TmplJLabel jlbRegulamenta = new TmplJLabel("Regulamenta");
	jlbRegulamenta.setForeground(Color.green);

	jpanel.add(jlbAltera, cc.xy(2, 2));
	jpanel.add(jlbRevoga, cc.xy(2, 4));
	jpanel.add(jlbRevogaParc, cc.xy(2, 6));
	jpanel.add(jlbRegulamenta, cc.xy(2, 8));
	jpanel.add(jlbTranspoe, cc.xy(2, 10));
	jpanel.add(jlbTranspoeParc, cc.xy(2, 12));

	JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(150,140));
        frame.setResizable(false);
        //frame.setUndecorated(true);
	frame.add(jpanel);
	frame.setTitle("Legenda");
	frame.pack();

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension frameSize = getPreferredSize();
	if (frameSize.height > screenSize.height) {
	  frameSize.height = screenSize.height;
	}
	if (frameSize.width > screenSize.width) {
	  frameSize.width = screenSize.width;
	}

	frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	frame.setEnabled(true);
	frame.setVisible(true);
      }
    });
    add(content, BorderLayout.NORTH);

    image = new NewClientRegulatoryHistoryPanel(datasource, fwCListener);
    jsp.getViewport().add(image);
    add(jsp, BorderLayout.CENTER);
  }

  public void tmplInitialize(TemplateEvent e) {
  }

  public void tmplRefresh(TemplateEvent e) {
  jsp.getViewport().remove(image);
  image = new NewClientRegulatoryHistoryPanel(datasource, fwCListener);
  jsp.getViewport().add(image);
  }

  public void tmplSave(TemplateEvent e) {
  }

  public void tmplLink(TemplateEvent e) {
  }

}
