/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.6</a>, using an XML
 * Schema.
 * $Id: Report.java,v 1.1 2006/09/26 10:07:56 jap Exp $
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
 * Class Report.
 *
 * @version $Revision: 1.1 $ $Date: 2006/09/26 10:07:56 $
 */
public class Report implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _anchor
     */
    private java.lang.String _anchor;

    /**
     * Field _file
     */
    private java.lang.String _file;

    /**
     * Field _reportList
     */
    private java.util.Vector _reportList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Report() {
        super();
        _reportList = new Vector();
    } //-- pt.inescporto.template.reports.forms.parser.objects.Report()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addReport
     *
     *
     *
     * @param vReport
     */
    public void addReport(pt.inescporto.template.reports.forms.parser.objects.Report vReport)
        throws java.lang.IndexOutOfBoundsException
    {
        _reportList.addElement(vReport);
    } //-- void addReport(pt.inescporto.template.reports.forms.parser.objects.Report)

    /**
     * Method addReport
     *
     *
     *
     * @param index
     * @param vReport
     */
    public void addReport(int index, pt.inescporto.template.reports.forms.parser.objects.Report vReport)
        throws java.lang.IndexOutOfBoundsException
    {
        _reportList.insertElementAt(vReport, index);
    } //-- void addReport(int, pt.inescporto.template.reports.forms.parser.objects.Report)

    /**
     * Method enumerateReport
     *
     *
     *
     * @return Enumeration
     */
    public java.util.Enumeration enumerateReport()
    {
        return _reportList.elements();
    } //-- java.util.Enumeration enumerateReport()

    /**
     * Returns the value of field 'anchor'.
     *
     * @return String
     * @return the value of field 'anchor'.
     */
    public java.lang.String getAnchor()
    {
        return this._anchor;
    } //-- java.lang.String getAnchor()

    /**
     * Returns the value of field 'file'.
     *
     * @return String
     * @return the value of field 'file'.
     */
    public java.lang.String getFile()
    {
        return this._file;
    } //-- java.lang.String getFile()

    /**
     * Returns the value of field 'name'.
     *
     * @return String
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName()

    /**
     * Method getReport
     *
     *
     *
     * @param index
     * @return Report
     */
    public pt.inescporto.template.reports.forms.parser.objects.Report getReport(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reportList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (pt.inescporto.template.reports.forms.parser.objects.Report) _reportList.elementAt(index);
    } //-- pt.inescporto.template.reports.forms.parser.objects.Report getReport(int)

    /**
     * Method getReport
     *
     *
     *
     * @return Report
     */
    public pt.inescporto.template.reports.forms.parser.objects.Report[] getReport()
    {
        int size = _reportList.size();
        pt.inescporto.template.reports.forms.parser.objects.Report[] mArray = new pt.inescporto.template.reports.forms.parser.objects.Report[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (pt.inescporto.template.reports.forms.parser.objects.Report) _reportList.elementAt(index);
        }
        return mArray;
    } //-- pt.inescporto.template.reports.forms.parser.objects.Report[] getReport()

    /**
     * Method getReportCount
     *
     *
     *
     * @return int
     */
    public int getReportCount()
    {
        return _reportList.size();
    } //-- int getReportCount()

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
     * Method removeAllReport
     *
     */
    public void removeAllReport()
    {
        _reportList.removeAllElements();
    } //-- void removeAllReport()

    /**
     * Method removeReport
     *
     *
     *
     * @param index
     * @return Report
     */
    public pt.inescporto.template.reports.forms.parser.objects.Report removeReport(int index)
    {
        java.lang.Object obj = _reportList.elementAt(index);
        _reportList.removeElementAt(index);
        return (pt.inescporto.template.reports.forms.parser.objects.Report) obj;
    } //-- pt.inescporto.template.reports.forms.parser.objects.Report removeReport(int)

    /**
     * Sets the value of field 'anchor'.
     *
     * @param anchor the value of field 'anchor'.
     */
    public void setAnchor(java.lang.String anchor)
    {
        this._anchor = anchor;
    } //-- void setAnchor(java.lang.String)

    /**
     * Sets the value of field 'file'.
     *
     * @param file the value of field 'file'.
     */
    public void setFile(java.lang.String file)
    {
        this._file = file;
    } //-- void setFile(java.lang.String)

    /**
     * Sets the value of field 'name'.
     *
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
    } //-- void setName(java.lang.String)

    /**
     * Method setReport
     *
     *
     *
     * @param index
     * @param vReport
     */
    public void setReport(int index, pt.inescporto.template.reports.forms.parser.objects.Report vReport)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reportList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _reportList.setElementAt(vReport, index);
    } //-- void setReport(int, pt.inescporto.template.reports.forms.parser.objects.Report)

    /**
     * Method setReport
     *
     *
     *
     * @param reportArray
     */
    public void setReport(pt.inescporto.template.reports.forms.parser.objects.Report[] reportArray)
    {
        //-- copy array
        _reportList.removeAllElements();
        for (int i = 0; i < reportArray.length; i++) {
            _reportList.addElement(reportArray[i]);
        }
    } //-- void setReport(pt.inescporto.template.reports.forms.parser.objects.Report)

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
        return (pt.inescporto.template.reports.forms.parser.objects.Report) Unmarshaller.unmarshal(pt.inescporto.template.reports.forms.parser.objects.Report.class, reader);
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
