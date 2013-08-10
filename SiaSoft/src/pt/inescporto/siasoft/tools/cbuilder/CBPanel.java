package pt.inescporto.siasoft.tools.cbuilder;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.awt.Toolkit;
import java.awt.Insets;
import java.awt.Cursor;
import java.awt.event.ActionListener;
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
public class CBPanel extends JPanel {//implements ActionListener, MsgPublisher {
  /*
  private CatalogBuilderInterface cbInterface = null;
  private JProgressBar progressBar;
  private JButton startButton;
  private JButton endButton;
  private CBTask task;
  private JTextArea taskOutput;
  private String newline = "\n";

  public CBPanel(CatalogBuilderInterface cbInterface) {
    super(new BorderLayout());

    this.cbInterface = cbInterface;

    task = new CBTask(this);

    //Create UI.
    startButton = new JButton("Início");
    startButton.setActionCommand("start");
    startButton.addActionListener(this);

    endButton = new JButton("Sair");
    endButton.setActionCommand("exit");
    endButton.addActionListener(this);

    progressBar = new JProgressBar();
    progressBar.setMinimum(0);
    progressBar.setMaximum(1);

    taskOutput = new JTextArea(5, 20);
    taskOutput.setMargin(new Insets(5, 5, 5, 5));
    taskOutput.setEditable(false);
    taskOutput.setCursor(null);

    JPanel panel = new JPanel();
    panel.add(startButton);
    panel.add(endButton);

    add(progressBar, BorderLayout.NORTH);
    add(new JScrollPane(taskOutput), BorderLayout.CENTER);
    add(panel, BorderLayout.SOUTH);
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
      progressBar.setIndeterminate(false);
      startButton.setEnabled(true);
    }
  }

  /**
   * Called when the user presses the start button.

  public void actionPerformed(ActionEvent evt) {
    if (evt.getActionCommand().equals("start")) {
      task.setBaseDir(cbInterface.getBaseDirectory());
      task.setCatalogName(cbInterface.getCatalogName());
      progressBar.setValue(progressBar.getMinimum());
      startButton.setEnabled(false);
      setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      progressBar.setIndeterminate(true);
      task.go();
    }
    if (evt.getActionCommand().equals("exit")) {
      System.exit(0);
    }
  }

  public boolean isDone() {
    return task.isDone();
  }*/
}
