package pt.inescporto.template.client.design.thirdparty;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import javax.swing.plaf.ActionMapUIResource;
import java.awt.Graphics;
import java.awt.Color;

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
public class TristateToggleButton extends JToggleButton {
  private final TristateDecorator model;

  public TristateToggleButton(String text, Icon icon, State initial) {
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

  public void paint(Graphics g) {
    super.paint(g);
    if (getState() == State.DONT_CARE) {
      g.setColor(new Color(200, 200, 200, 127));
      g.fillRect(0, 0, getSize().width, getSize().height);
    }
  }

  public TristateToggleButton(String text, State initial) {
    this(text, null, initial);
  }

  public TristateToggleButton(String text) {
    this(text, State.DONT_CARE);
  }

  public TristateToggleButton() {
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
}
