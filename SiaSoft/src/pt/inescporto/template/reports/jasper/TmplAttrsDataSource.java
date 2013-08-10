package pt.inescporto.template.reports.jasper;

import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRException;
import java.util.Vector;
import net.sf.jasperreports.engine.JRDataSource;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.util.Iterator;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TmplAttrsDataSource implements JRDataSource {
  protected Vector data;
  private Iterator itrData;
  private Object valorActual;
  private boolean irParaProximo = true;

  public final static String REPORTDETAILPREFIX = "reportDetail_";

  public TmplAttrsDataSource(Vector data) {
    super();
    this.data = data;
    this.itrData = data.iterator();
  }

  /**
   * next
   *
   * @return boolean
   * @throws JRException
   */
  public boolean next() throws JRException {
    valorActual = itrData.hasNext() ? itrData.next() : null;
    irParaProximo = (valorActual != null);
    return irParaProximo;
  }

  /**
   * getFieldValue
   *
   * @param jRField JRField
   * @return Object
   * @throws JRException
   */
  public Object getFieldValue(JRField jRField) throws JRException {
    try {
      if ( jRField.getName().startsWith(REPORTDETAILPREFIX) ) {
        Object retVal = new TmplAttrsDataSource(((JarperReportInterface)valorActual).getDetailData(jRField.getName()));
        return retVal;
      } else {
        String field = jRField.getName();
        Object attrs = valorActual;

        Class c = attrs.getClass();
        Field cfield = c.getField(field);
        Object tplAttrs = cfield.get(attrs);

        Class tc = tplAttrs.getClass();
        Method getValueMethod = tc.getMethod("getValue", null);
        Object retVal = getValueMethod.invoke(tplAttrs, null);

        return retVal;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

}
