/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.ntust.dma.group13.hw01;

/**
 *
 * @author vania
 */
class Dataset {

    private int NumAttributes;
    private int NumClasses;
    private int numEntries;
   
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

    
}
