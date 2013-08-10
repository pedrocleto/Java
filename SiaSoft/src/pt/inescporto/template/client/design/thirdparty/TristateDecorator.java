package pt.inescporto.template.client.design.thirdparty;

import javax.swing.ButtonGroup;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.ButtonModel;

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
public class TristateDecorator implements ButtonModel {
  private final ButtonModel other;

  public TristateDecorator(ButtonModel other) {
    this.other = other;
  }

  public void setState(State state) {
    if (state == State.NOT_SELECTED) {
      other.setArmed(false);
      setPressed(false);
      setSelected(false);
    }
    else
      if (state == State.SELECTED) {
        other.setArmed(false);
        setPressed(false);
        setSelected(true);
      }
      else { // either "null" or DONT_CARE
        other.setArmed(true);
        setPressed(true);
        setSelected(true);
      }
  }

  /**
   * The current state is embedded in the selection / armed
   * state of the model.
   *
   * We return the SELECTED state when the checkbox is selected
   * but not armed, DONT_CARE state when the checkbox is
   * selected and armed (grey) and NOT_SELECTED when the
   * checkbox is deselected.
   */
  public State getState() {
    if (isSelected() && !isArmed()) {
      // normal black tick
      return State.SELECTED;
    }
    else
      if (isSelected() && isArmed()) {
        // don't care grey tick
        return State.DONT_CARE;
      }
      else {
        // normal deselected
        return State.NOT_SELECTED;
      }
  }

  /** We rotate between NOT_SELECTED, SELECTED and DONT_CARE.*/
  public void nextState() {
    State current = getState();
    if (current == State.NOT_SELECTED) {
      setState(State.SELECTED);
    }
    else
      if (current == State.SELECTED) {
        setState(State.DONT_CARE);
      }
      else
        if (current == State.DONT_CARE) {
          setState(State.NOT_SELECTED);
        }
  }

  /** Filter: No one may change the armed status except us. */
  public void setArmed(boolean b) {
  }

  /** We disable focusing on the component when it is not
   * enabled. */
  public void setEnabled(boolean b) {
//    setFocusable(b);
    other.setEnabled(b);
  }

  /** All these methods simply delegate to the "other" model
   * that is being decorated. */
  public boolean isArmed() {
    return other.isArmed();
  }

  public boolean isSelected() {
    return other.isSelected();
  }

  public boolean isEnabled() {
    return other.isEnabled();
  }

  public boolean isPressed() {
    return other.isPressed();
  }

  public boolean isRollover() {
    return other.isRollover();
  }

  public void setSelected(boolean b) {
    other.setSelected(b);
  }

  public void setPressed(boolean b) {
    other.setPressed(b);
  }

  public void setRollover(boolean b) {
    other.setRollover(b);
  }

  public void setMnemonic(int key) {
    other.setMnemonic(key);
  }

  public int getMnemonic() {
    return other.getMnemonic();
  }

  public void setActionCommand(String s) {
    other.setActionCommand(s);
  }

  public String getActionCommand() {
    return other.getActionCommand();
  }

  public void setGroup(ButtonGroup group) {
    other.setGroup(group);
  }

  public void addActionListener(ActionListener l) {
    other.addActionListener(l);
  }

  public void removeActionListener(ActionListener l) {
    other.removeActionListener(l);
  }

  public void addItemListener(ItemListener l) {
    other.addItemListener(l);
  }

  public void removeItemListener(ItemListener l) {
    other.removeItemListener(l);
  }

  public void addChangeListener(ChangeListener l) {
    other.addChangeListener(l);
  }

  public void removeChangeListener(ChangeListener l) {
    other.removeChangeListener(l);
  }

  public Object[] getSelectedObjects() {
    return other.getSelectedObjects();
  }

  }
