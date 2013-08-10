package pt.inescporto.template.client.design.thirdparty;

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
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ActionMapUIResource;
import java.awt.event.*;
import java.awt.GridLayout;

/**
 * Maintenance tip - There were some tricks to getting this code
 * working:
 *
 * 1. You have to overwite addMouseListener() to do nothing
 * 2. You have to add a mouse event on mousePressed by calling
 * super.addMouseListener()
 * 3. You have to replace the UIActionMap for the keyboard event
 * "pressed" with your own one.
 * 4. You have to remove the UIActionMap for the keyboard event
 * "released".
 * 5. You have to grab focus when the next state is entered,
 * otherwise clicking on the component won't get the focus.
 * 6. You have to make a TristateDecorator as a button model that
 * wraps the original button model and does state management.
 */
public class TristateCheckBox extends JCheckBox {
  private final TristateDecorator model;

  public TristateCheckBox(String text, Icon icon, State initial) {
    super(text, icon);
    // Add a listener for when the mouse is pressed
    super.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
	grabFocus();
	model.nextState();
      }
    });
    // Reset the keyboard action map
    ActionMap map = new ActionMapUIResource();
    map.put("pressed", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
	grabFocus();
	model.nextState();
      }
    });
    map.put("released", null);
    SwingUtilities.replaceUIActionMap(this, map);
    // set the model to the adapted model
    model = new TristateDecorator(getModel());
    setModel(model);
    setState(initial);
  }

  public TristateCheckBox(String text, State initial) {
    this(text, null, initial);
  }

  public TristateCheckBox(String text) {
    this(text, State.DONT_CARE);
  }

  public TristateCheckBox() {
    this(null);
  }

  /** No one may add mouse listeners, not even Swing! */
  public void addMouseListener(MouseListener l) {}

  /**
   * Set the new state to either SELECTED, NOT_SELECTED or
   * DONT_CARE.  If state == null, it is treated as DONT_CARE.
   */
  public void setState(State state) {
    model.setState(state);
  }

  /** Return the current state, which is determined by the
   * selection status of the model. */
  public State getState() {
    return model.getState();
  }

  public void setSelected(boolean b) {
    if (b) {
      setState(State.SELECTED);
    }
    else {
      setState(State.NOT_SELECTED);
    }
  }

  public static void main(String args[]) throws Exception {
    JFrame frame = new JFrame("TristateCheckBoxTest");
    frame.getContentPane().setLayout(new GridLayout(0, 1, 5, 5));
    final TristateCheckBox swingBox = new TristateCheckBox(
	"Testing the tristate checkbox");
    swingBox.setMnemonic('T');
    frame.getContentPane().add(swingBox);
    frame.getContentPane().add(new JCheckBox(
	"The normal checkbox"));

/*    UIManager.setLookAndFeel(
	UIManager.getSystemLookAndFeelClassName());*/
    final TristateCheckBox winBox = new TristateCheckBox(
	"Testing the tristate checkbox",
	State.SELECTED);
    frame.getContentPane().add(winBox);
    final JCheckBox winNormal = new JCheckBox(
	"The normal checkbox");
    frame.getContentPane().add(winNormal);
    // wait for 3 seconds, then enable all check boxes
    new Thread() {
      {
	start();
      }

      public void run() {
	try {
	  winBox.setEnabled(false);
	  winNormal.setEnabled(false);
	  Thread.sleep(3000);
	  winBox.setEnabled(true);
	  winNormal.setEnabled(true);
	}
	catch (InterruptedException ex) {}
      }
    };
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
