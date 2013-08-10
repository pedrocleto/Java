/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Jasper.java,v 1.1 2006/09/26 10:08:10 jap Exp $
 */

package pt.inescporto.template.reports.parser.objects;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class Jasper.
 *
 * @version $Revision: 1.1 $ $Date: 2006/09/26 10:08:10 $
 */
public class Jasper implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _parameterList
     */
    private java.util.Vector _parameterList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Jasper() {
        super();
        _parameterList = new Vector();
    } //-- pt.inescporto.template.reports.parser.objects.Jasper()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addParameter
     *
     * @param vParameter
     */
    public void addParameter(pt.inescporto.template.reports.parser.objects.Parameter vParameter)
        throws java.lang.IndexOutOfBoundsException
    {
        _parameterList.addElement(vParameter);
    } //-- void addParameter(pt.inescporto.template.reports.parser.objects.Parameter)

    /**
     * Method addParameter
     *
     * @param index
     * @param vParameter
     */
    public void addParameter(int index, pt.inescporto.template.reports.parser.objects.Parameter vParameter)
        throws java.lang.IndexOutOfBoundsException
    {
        _parameterList.insertElementAt(vParameter, index);
    } //-- void addParameter(int, pt.inescporto.template.reports.parser.objects.Parameter)

    /**
     * Method enumerateParameter
     */
    public java.util.Enumeration enumerateParameter()
    {
        return _parameterList.elements();
    } //-- java.util.Enumeration enumerateParameter()

    /**
     * Method getParameter
     *
     * @param index
     */
    public pt.inescporto.template.reports.parser.objects.Parameter getParameter(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _parameterList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (pt.inescporto.template.reports.parser.objects.Parameter) _parameterList.elementAt(index);
    } //-- pt.inescporto.template.reports.parser.objects.Parameter getParameter(int)

    /**
     * Method getParameter
     */
    public pt.inescporto.template.reports.parser.objects.Parameter[] getParameter()
    {
        int size = _parameterList.size();
        pt.inescporto.template.reports.parser.objects.Parameter[] mArray = new pt.inescporto.template.reports.parser.objects.Parameter[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (pt.inescporto.template.reports.parser.objects.Parameter) _parameterList.elementAt(index);
        }
        return mArray;
    } //-- pt.inescporto.template.reports.parser.objects.Parameter[] getParameter()

    /**
     * Method getParameterCount
     */
    public int getParameterCount()
    {
        return _parameterList.size();
    } //-- int getParameterCount()

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid()

    /**
     * Method marshal
     *
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {

        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer)

    /**
     * Method marshal
     *
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {

        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler)

    /**
     * Method removeAllParameter
     */
    public void removeAllParameter()
    {
        _parameterList.removeAllElements();
    } //-- void removeAllParameter()

    /**
     * Method removeParameter
     *
     * @param index
     */
    public pt.inescporto.template.reports.parser.objects.Parameter removeParameter(int index)
    {
        java.lang.Object obj = _parameterList.elementAt(index);
        _parameterList.removeElementAt(index);
        return (pt.inescporto.template.reports.parser.objects.Parameter) obj;
    } //-- pt.inescporto.template.reports.parser.objects.Parameter removeParameter(int)

    /**
     * Method setParameter
     *
     * @param index
     * @param vParameter
     */
    public void setParameter(int index, pt.inescporto.template.reports.parser.objects.Parameter vParameter)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _parameterList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _parameterList.setElementAt(vParameter, index);
    } //-- void setParameter(int, pt.inescporto.template.reports.parser.objects.Parameter)

    /**
     * Method setParameter
     *
     * @param parameterArray
     */
    public void setParameter(pt.inescporto.template.reports.parser.objects.Parameter[] parameterArray)
    {
        //-- copy array
        _parameterList.removeAllElements();
        for (int i = 0; i < parameterArray.length; i++) {
            _parameterList.addElement(parameterArray[i]);
        }
    } //-- void setParameter(pt.inescporto.template.reports.parser.objects.Parameter)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (pt.inescporto.template.reports.parser.objects.Jasper) Unmarshaller.unmarshal(pt.inescporto.template.reports.parser.objects.Jasper.class, reader);
    } //-- java.lang.Object unmarshal(java.io.Reader)

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate()

}
