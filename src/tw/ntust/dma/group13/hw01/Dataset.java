/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.ntust.dma.group13.hw01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author vania utami
 */
class Dataset {

    private int NumAttributes;
    private int NumClasses;
    private int numEntries;
    //name of attributes in data set, example marriage status, education
    private String[] nameAttributes;
    // all of data, save in table with eash key is name of attributes, and
    // data saved in array list
    // key attribute
    // objectnny list data
    private Hashtable<String, ArrayList<String>> dataReal;
    // list of distinct value with its frequency
    // exmple in marriage status distinct value list are single=17 data, divorce=18, marriage=20
    // array represent of attribute , example listValueAttribute[0] = marriage status
    private Map<String, Integer>[] listValueAttribute;
    // to save categorical attribute, ex marriage status
    private ArrayList<String> categoricalAttribute;
    // to save numerical attibute, age
    private ArrayList<String> numericalAttribute;
    // to save mapping of each dummy variabel and each categoricla
    // example attribute : marriage status. value : 1 -> single, 2-> married, 3 --> divorce
    Map<String, Map<String, Integer>> mapCategorcalAttribute;

    /**
     * @return the NumAttributes
     */
    public int getNumAttributes() {
//       categoricalAttr

        return NumAttributes;
    }

    /**
     * @param NumAttributes the NumAttributes to set
     */
    public void setNumAttributes(int NumAttributes) {
        setListValueAttribute((Map<String, Integer>[]) new HashMap[NumAttributes]);
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
        String clas = nameAttributes[nameAttributes.length - 1];
        ArrayList<String> nameClas = dataReal.get(clas);
        String valueClass = nameClas.get(i);
        try {
            double hsl = Double.parseDouble(valueClass);
            return hsl;
        } catch (Exception ex) {
            System.out.println("valueClass = " + valueClass);
            System.out.println("mapCategorcalAttribute = " + mapCategorcalAttribute);
            System.out.println("mapCategorcalAttribute.get(clas) = " + mapCategorcalAttribute.get(clas));
            System.out.println("mapCategorcalAttribute.get(clas).get(valueClass) = " + mapCategorcalAttribute.get(clas).get(valueClass));
            return mapCategorcalAttribute.get(clas).get(valueClass);
        }

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    double[] getAttributeSet(int i) {
        double[] dataReDs = new double[nameAttributes.length - 1];
        for (int j = 0; j < nameAttributes.length - 1; j++) {
            try {
                dataReDs[j] = Double.parseDouble(dataReal.get(nameAttributes[j]).get(i));
            } catch (NumberFormatException ex) {
                String valueClass = dataReal.get(nameAttributes[i]).get(i);
                dataReDs[j] = mapCategorcalAttribute.get(nameAttributes[i]).get(valueClass);
            }
        }
        return dataReDs;
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public Hashtable<String, ArrayList<String>> getDataReal() {
        return dataReal;
    }

    /**
     * @param dataReal the dataReal to set
     */
    public void setDataReal(Hashtable<String, ArrayList<String>> dataReal) {
        this.dataReal = dataReal;
    }

    /**
     * @return the listValueAttribute
     */
    public Map<String, Integer>[] getListValueAttribute() {
        return listValueAttribute;
    }

    /**
     * @param listValueAttribute the listValueAttribute to set
     */
    public void setListValueAttribute(Map<String, Integer>[] listValueAttribute) {
        this.listValueAttribute = listValueAttribute;
    }

    /**
     * @return the numericalAttribute
     */
    public ArrayList<String> getNumericalAttribute() {
        return numericalAttribute;
    }

    /**
     * @param numericalAttribute the numericalAttribute to set
     */
    public void setNumericalAttribute(ArrayList<String> numericalAttribute) {
        this.numericalAttribute = numericalAttribute;
    }

    /**
     * @return the categoricalAttribute
     */
    public ArrayList<String> getCategoricalAttribute() {
        return categoricalAttribute;
    }

    /**
     * @param categoricalAttribute the categoricalAttribute to set
     */
    public void setCategoricalAttribute(ArrayList<String> categoricalAttribute) {
        this.categoricalAttribute = categoricalAttribute;
    }

    public void processCategoricalInput() {
        mapCategorcalAttribute = new HashMap<>();
        for (String catAttr : categoricalAttribute) {
            if (!mapCategorcalAttribute.containsKey(catAttr)) {
                Map<String, Integer> maps = new HashMap<>();
                mapCategorcalAttribute.put(catAttr, maps);

            }
            // mapCategorcalAttribute.
            Map<String, Integer> maps = mapCategorcalAttribute.get(catAttr);
            int index = getIndexAttribute(catAttr);
            Set<String> attibuteValueList = listValueAttribute[index].keySet();
            int i = 1;
            for (Iterator<String> itAttrVal = attibuteValueList.iterator(); itAttrVal.hasNext();) {
                String valueDat = itAttrVal.next();
                maps.put(valueDat, i);
                i++;
            }
//           for(int i=0;i<attibuteValueList.;i++){
//               attibuteValueList.
//           }
        }
    }

    public int getIndexAttribute(String nameAttribute) {
        for (int i = 0; i < nameAttributes.length; i++) {
            if (nameAttribute.equals(nameAttributes[i])) {
                return i;
            }
        }
        return -1;
    }

    public double[][][] getDataSet() {
        double[][][] dataRet = new double[numEntries][2][];

        for (int i = 0; i < dataRet.length; i++) {
            dataRet[i][0] = getAttributeSet(i);
            dataRet[i][1] = new double[1];
            dataRet[i][1][0] = getClass(i);
        }

        return dataRet;
    }
}
