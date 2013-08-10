package pt.inescporto.template.client.design.thirdparty;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.JLabel;

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
public class ProgressPanel extends JPanel implements ChangeListener {
  JLabel statusLabel = new JLabel();
  JProgressBar progressBar;
  ProgressMonitor monitor;

  public ProgressPanel(ProgressMonitor monitor) {
    init(monitor);
  }

  private void init(ProgressMonitor monitor) {
    this.monitor = monitor;

    progressBar = new JProgressBar(0, monitor.getTotal());
    if (monitor.isIndeterminate())
      progressBar.setIndeterminate(true);
    else
      progressBar.setValue(monitor.getCurrent());
    statusLabel.setText(monitor.getStatus());

    JPanel contents = (JPanel)this;
    contents.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    contents.add(statusLabel, BorderLayout.NORTH);
    contents.add(progressBar);

    monitor.addChangeListener(this);
  }

  public void stateChanged(final ChangeEvent ce) {
    // to ensure EDT thread
    if (!SwingUtilities.isEventDispatchThread()) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          stateChanged(ce);
        }
      });
      return;
    }

    if (monitor.getCurrent() != monitor.getTotal()) {
      statusLabel.setText(monitor.getStatus());
      if (!monitor.isIndeterminate())
        progressBar.setValue(monitor.getCurrent());
    }
  }
}
