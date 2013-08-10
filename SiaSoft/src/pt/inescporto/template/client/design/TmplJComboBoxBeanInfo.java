package pt.inescporto.template.client.design;

import javax.swing.JComboBoxBeanInfo;
import java.beans.PropertyDescriptor;
import java.util.*;

public class TmplJComboBoxBeanInfo extends JComboBoxBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    List l = Arrays.asList( super.getPropertyDescriptors() );
    try {
      l.add(new PropertyDescriptor( "url", TmplJComboBox.class));
      l.add(new PropertyDescriptor( "holder", TmplJComboBox.class));
      l.add(new PropertyDescriptor( "label", TmplJComboBox.class));
      l.add(new PropertyDescriptor( "field", TmplJComboBox.class));
      l.add(new PropertyDescriptor( "dataItems", TmplJComboBox.class));
      l.add(new PropertyDescriptor( "dataValues", TmplJComboBox.class));
      l.add(new PropertyDescriptor( "showSave", TmplJComboBox.class));

      return (PropertyDescriptor[])l.toArray();
    }
    catch( Exception ex ) {
      System.err.println( "TmplJComboBoxBeanInfo: unexpected exception: " + ex);
      return null;
    }
  }
}