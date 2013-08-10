package pt.inescporto.siasoft.asq.client.rmi.forms;

import pt.inescporto.template.client.rmi.TmplBasicInternalFrame;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.design.thirdparty.DocumentRenderer;
import javax.swing.JScrollPane;

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
public class PrintableRegulatoryListFrame extends TmplBasicInternalFrame implements ActionListener {
  private JEditorPane jepList = new JEditorPane();
  private TmplJButton jbtnPrint = new TmplJButton();

  public PrintableRegulatoryListFrame() {
    initialize();
    setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
  }

  public void setHTMLText(String htmlText) {
    jepList.setText(htmlText);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jepList.setAutoscrolls(true);
    jepList.setContentType("text/html");
    jepList.setEditable(false);

    jbtnPrint.setText("Imprimir");
    jbtnPrint.addActionListener(this);

    JScrollPane jspList = new JScrollPane();
    jspList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    jspList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    jspList.getViewport().add(jepList);

    add(jspList, BorderLayout.CENTER);
    add(jbtnPrint, BorderLayout.SOUTH);
  }

  public void actionPerformed(ActionEvent e) {
    DocumentRenderer dReader = new DocumentRenderer();
    dReader.print(jepList);
  }
}
