package pt.inescporto.siasoft.tools;

import javax.swing.JProgressBar;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.Cursor;
import java.util.Hashtable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import pt.inescporto.siasoft.tools.dbanalizer.DBStructCompare;
import pt.inescporto.siasoft.tools.dbanalizer.sql.TableActions;
import pt.inescporto.siasoft.tools.test.MsgPublisher;

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
public class DBStructComparePanel extends JPanel implements ActionListener, MsgPublisher {
  private JProgressBar progressBar;
  private JButton startButton;
  private DBStructCompare task;
  private JTextArea taskOutput;
  private String newline = "\n";

  public DBStructComparePanel(Hashtable<String, TableNode> tableTreeSource,
      Hashtable<String, TableNode> tableTreeDestiny) {
    super(new BorderLayout());

    task = new DBStructCompare(this, tableTreeSource, tableTreeDestiny);

    //Create the demo's UI.
    startButton = new JButton("Start");
    startButton.setActionCommand("start");
    startButton.addActionListener(this);

    progressBar = new JProgressBar(0, task.getLengthOfTask());
    progressBar.setValue(0);
    progressBar.setStringPainted(true);

    taskOutput = new JTextArea(5, 20);
    taskOutput.setMargin(new Insets(5, 5, 5, 5));
    taskOutput.setEditable(false);
    taskOutput.setCursor(null); //inherit the panel's cursor
    //see bug 4851758

    JPanel panel = new JPanel();
    panel.add(startButton);
    panel.add(progressBar);

    add(panel, BorderLayout.PAGE_START);
    add(new JScrollPane(taskOutput), BorderLayout.CENTER);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
  }

  public void setProgress(String msg, int count) {
    progressBar.setValue(task.getCurrent());
    if (msg != null) {
      taskOutput.append(msg + newline);
      taskOutput.setCaretPosition(
	  taskOutput.getDocument().getLength());
    }
    if (task.isDone()) {
      Toolkit.getDefaultToolkit().beep();
      startButton.setEnabled(false);
      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }

  public void showMessage(String msg) {
    if (msg != null) {
      taskOutput.append(msg + newline);
      taskOutput.setCaretPosition(taskOutput.getDocument().getLength());
    }
  }

  /**
   * Called when the user presses the start button.
   */
  public void actionPerformed(ActionEvent evt) {
    progressBar.setValue(progressBar.getMinimum());
    startButton.setEnabled(false);
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    task.go();
  }

  public Hashtable<String, TableActions> getTableActions() {
    return task.getTableActions();
  }

  public boolean isDone() {
    return task.isDone();
  }
}
