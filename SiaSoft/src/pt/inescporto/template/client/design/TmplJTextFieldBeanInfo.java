package pt.inescporto.template.client.design;

import java.beans.*;

import java.awt.*;

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
public class TmplJTextFieldBeanInfo extends SimpleBeanInfo {
  Class beanClass = TmplJTextField.class;
  String iconColor16x16Filename;
  String iconColor32x32Filename;
  String iconMono16x16Filename;
  String iconMono32x32Filename;

  public TmplJTextFieldBeanInfo() {
  }

  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor _field = new PropertyDescriptor(
	  "field", beanClass, "getField", "setField");

      PropertyDescriptor _holder = new PropertyDescriptor(
	  "holder", beanClass, "getHolder", "setHolder");

      PropertyDescriptor _label = new PropertyDescriptor(
	  "label", beanClass, null, "setLabel");

      PropertyDescriptor _labelName = new PropertyDescriptor(
	  "labelName", beanClass, "getLabelName", null);

      PropertyDescriptor _link = new PropertyDescriptor(
	  "link", beanClass, "getLink", "setLink");

      PropertyDescriptor[] pds = new PropertyDescriptor[] {
	  _field,
	  _holder,
	  _label,
	  _labelName,
	  _link
      };
      return pds;
    }
    catch (Exception exception) {
      exception.printStackTrace();
      return null;
    }
  }

  public Image getIcon(int iconKind) {
    switch (iconKind) {
      case BeanInfo.ICON_COLOR_16x16:
	return ((iconColor16x16Filename != null)
		? loadImage(iconColor16x16Filename) : null);

      case BeanInfo.ICON_COLOR_32x32:
	return ((iconColor32x32Filename != null)
		? loadImage(iconColor32x32Filename) : null);

      case BeanInfo.ICON_MONO_16x16:
	return ((iconMono16x16Filename != null)
		? loadImage(iconMono16x16Filename) : null);

      case BeanInfo.ICON_MONO_32x32:
	return ((iconMono32x32Filename != null)
		? loadImage(iconMono32x32Filename) : null);
    }

    return null;
  }
}