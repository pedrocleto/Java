/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.6</a>, using an XML
 * Schema.
 * $Id: FormReportConfig.java,v 1.1 2006/09/26 10:07:52 jap Exp $
 */

package pt.inescporto.template.reports.forms.parser.objects;

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
 * Class FormReportConfig.
 *
 * @version $Revision: 1.1 $ $Date: 2006/09/26 10:07:52 $
 */
public class FormReportConfig implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _formList
     */
    private java.util.Vector _formList;


      //----------------/
     //- Constructors -/
    //----------------/

    public FormReportConfig() {
        super();
        _formList = new Vector();
    } //-- pt.inescporto.template.reports.forms.parser.objects.FormReportConfig()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addForm
     *
     *
     *
     * @param vForm
     */
    public void addForm(pt.inescporto.template.reports.forms.parser.objects.Form vForm)
        throws java.lang.IndexOutOfBoundsException
    {
        _formList.addElement(vForm);
    } //-- void addForm(pt.inescporto.template.reports.forms.parser.objects.Form)

    /**
     * Method addForm
     *
     *
     *
     * @param index
     * @param vForm
     */
    public void addForm(int index, pt.inescporto.template.reports.forms.parser.objects.Form vForm)
        throws java.lang.IndexOutOfBoundsException
    {
        _formList.insertElementAt(vForm, index);
    } //-- void addForm(int, pt.inescporto.template.reports.forms.parser.objects.Form)

    /**
     * Method enumerateForm
     *
     *
     *
     * @return Enumeration
     */
    public java.util.Enumeration enumerateForm()
    {
        return _formList.elements();
    } //-- java.util.Enumeration enumerateForm()

    /**
     * Method getForm
     *
     *
     *
     * @param index
     * @return Form
     */
    public pt.inescporto.template.reports.forms.parser.objects.Form getForm(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _formList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (pt.inescporto.template.reports.forms.parser.objects.Form) _formList.elementAt(index);
    } //-- pt.inescporto.template.reports.forms.parser.objects.Form getForm(int)

    /**
     * Method getForm
     *
     *
     *
     * @return Form
     */
    public pt.inescporto.template.reports.forms.parser.objects.Form[] getForm()
    {
        int size = _formList.size();
        pt.inescporto.template.reports.forms.parser.objects.Form[] mArray = new pt.inescporto.template.reports.forms.parser.objects.Form[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (pt.inescporto.template.reports.forms.parser.objects.Form) _formList.elementAt(index);
        }
        return mArray;
    } //-- pt.inescporto.template.reports.forms.parser.objects.Form[] getForm()

    /**
     * Method getFormCount
     *
     *
     *
     * @return int
     */
    public int getFormCount()
    {
        return _formList.size();
    } //-- int getFormCount()

    /**
     * Method isValid
     *
     *
     *
     * @return boolean
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
     *
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
     *
     *
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {

        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler)

    /**
     * Method removeAllForm
     *
     */
    public void removeAllForm()
    {
        _formList.removeAllElements();
    } //-- void removeAllForm()

    /**
     * Method removeForm
     *
     *
     *
     * @param index
     * @return Form
     */
    public pt.inescporto.template.reports.forms.parser.objects.Form removeForm(int index)
    {
        java.lang.Object obj = _formList.elementAt(index);
        _formList.removeElementAt(index);
        return (pt.inescporto.template.reports.forms.parser.objects.Form) obj;
    } //-- pt.inescporto.template.reports.forms.parser.objects.Form removeForm(int)

    /**
     * Method setForm
     *
     *
     *
     * @param index
     * @param vForm
     */
    public void setForm(int index, pt.inescporto.template.reports.forms.parser.objects.Form vForm)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _formList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _formList.setElementAt(vForm, index);
    } //-- void setForm(int, pt.inescporto.template.reports.forms.parser.objects.Form)

    /**
     * Method setForm
     *
     *
     *
     * @param formArray
     */
    public void setForm(pt.inescporto.template.reports.forms.parser.objects.Form[] formArray)
    {
        //-- copy array
        _formList.removeAllElements();
        for (int i = 0; i < formArray.length; i++) {
            _formList.addElement(formArray[i]);
        }
    } //-- void setForm(pt.inescporto.template.reports.forms.parser.objects.Form)

    /**
     * Method unmarshal
     *
     *
     *
     * @param reader
     * @return Object
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (pt.inescporto.template.reports.forms.parser.objects.FormReportConfig) Unmarshaller.unmarshal(pt.inescporto.template.reports.forms.parser.objects.FormReportConfig.class, reader);
    } //-- java.lang.Object unmarshal(java.io.Reader)

    /**
     * Method validate
     *
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate()

}
