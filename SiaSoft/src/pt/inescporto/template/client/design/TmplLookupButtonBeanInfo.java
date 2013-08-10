package pt.inescporto.template.client.design;

import java.beans.PropertyDescriptor;
import javax.swing.JTextFieldBeanInfo;
import java.util.*;

public class TmplLookupButtonBeanInfo extends TmplJButtonBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    List l = Arrays.asList( super.getPropertyDescriptors() );
    try {
      l.add(new PropertyDescriptor( "title", TmplLookupButton.class));
      l.add(new PropertyDescriptor( "headers", TmplLookupButton.class));
      l.add(new PropertyDescriptor( "url", TmplLookupButton.class));
      l.add(new PropertyDescriptor( "defaultFill", TmplLookupButton.class));
      l.add(new PropertyDescriptor( "fillList", TmplLookupButton.class));

      return (PropertyDescriptor[])l.toArray();
    }
    catch( Exception ex ) {
      System.err.println( "TmplJButtonBeanInfo: unexpected exception: " + ex);
      return null;
    }
  }
}