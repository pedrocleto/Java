package pt.inescporto.template.client.design;

import javax.swing.JCheckBoxBeanInfo;
import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class TmplJCheckBoxBeanInfo extends JCheckBoxBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    List l = Arrays.asList( super.getPropertyDescriptors() );
    try {
      l.add(new PropertyDescriptor( "label", TmplJCheckBox.class));
      l.add(new PropertyDescriptor( "field", TmplJCheckBox.class));
      l.add(new PropertyDescriptor( "dataValues", TmplJCheckBox.class));

      return (PropertyDescriptor[])l.toArray();
    }
    catch( Exception ex ) {
      System.err.println( "TmplJCheckBoxBeanInfo: unexpected exception: " + ex);
      return null;
    }
  }
}