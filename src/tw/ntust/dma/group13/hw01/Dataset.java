/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.ntust.dma.group13.hw01;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vania utami
 */
class Dataset {

    private int NumAttributes;
    private int NumClasses;
    private int numEntries;
    private String[] nameAttributes;
    private Map<String, String> dataReal;
    private List<String>[] listValueAttribute;

    /**
     * @return the NumAttributes
     */
    public int getNumAttributes() {
        return NumAttributes;
    }

    /**
     * @param NumAttributes the NumAttributes to set
     */
    public void setNumAttributes(int NumAttributes) {
        listValueAttribute = (ArrayList<String>[]) new ArrayList[NumAttributes];
        this.NumAttributes = NumAttributes;
    }

    /**
     * @return the NumClasses
     */
    public int getNumClasses() {
        return NumClasses;
    }

    /**
     * @param NumClasses the NumClasses to set
     */
    public void setNumClasses(int NumClasses) {
        this.NumClasses = NumClasses;
    }

    /**
     * @return the numEntries
     */
    public int getNumEntries() {
        return numEntries;
    }

    /**
     * @param numEntries the numEntries to set
     */
    public void setNumEntries(int numEntries) {
        this.numEntries = numEntries;
    }

    double getClass(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    double[] getAttributeSet(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the nameAttributes
     */
    public String[] getNameAttributes() {
        return nameAttributes;
    }

    /**
     * @param nameAttributes the nameAttributes to set
     */
    public void setNameAttributes(String[] nameAttributes) {
        this.nameAttributes = nameAttributes;
    }

    /**
     * @return the dataReal
     */
    public Map<String, String> getDataReal() {
        return dataReal;
    }

    /**
     * @param dataReal the dataReal to set
     */
    public void setDataReal(Map<String, String> dataReal) {
        this.dataReal = dataReal;
    }
}
