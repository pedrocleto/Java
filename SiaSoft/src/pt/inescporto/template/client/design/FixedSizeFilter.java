package pt.inescporto.template.client.design;

import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

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
class FixedSizeFilter extends DocumentFilter {
  int maxSize;

  // limit is the maximum number of characters allowed.
  public FixedSizeFilter(int limit) {
    maxSize = limit;
  }

  // This method is called when characters are inserted into the document
  public void insertString(DocumentFilter.FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {
    replace(fb, offset, 0, str, attr);
  }

  // This method is called when characters in the document are replace with other characters
  /**
   * @todo FIXED Description Problem with null values - Pedro
   */
  public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String str, AttributeSet attrs) throws BadLocationException {
    int newLength = 0;
    try {
      newLength = fb.getDocument().getLength() - length + str.length();
    }
    catch (Exception ex1) {
    }
    if (newLength <= maxSize) {
      fb.replace(offset, length, str, attrs);
    }
    else {
      throw new BadLocationException("New characters exceeds max size of document", offset);
    }
  }
}
