package pt.inescporto.template.client.design;

import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class TmplJTextAreaBeanInfo extends JTextAreaBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    List l = Arrays.asList(super.getPropertyDescriptors());
    try {
      l.add(new PropertyDescriptor("field", TmplJTextField.class.getMethod("getField", new Class[]{}), TmplJTextField.class.getMethod("setField", new Class[]{String.class})));
      l.add(new PropertyDescriptor("holder", TmplJTextField.class));
      l.add(new PropertyDescriptor("label", TmplJTextField.class));

      return (PropertyDescriptor[]) l.toArray();
    }
    catch (Exception ex) {
      System.err.println("TmplJTextAreaBeanInfo: unexpected exception: " + ex);
      return null;
    }
  }
}
