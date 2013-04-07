/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.ntust.dma.group13.hw01;

/**
 *
 * @author vania
 */
class Neuron {

    private double[] Weights,input;
    private double Delta;
    private double OldDelta;
    private double Output;

    public Neuron(int numberWeight,double [] input) {
        for(int i=0;i<numberWeight;i++){
            Weights [i]= Math.random();
        }
        this.input = input;
    }

      public Neuron(int numberWeight) {
        for(int i=0;i<numberWeight;i++){
            Weights [i]= Math.random();
        }
    }
    
    
    /**
     * @return the Weights
     */
    public double[] getWeights() {
        return Weights;
    }

    /**
     * @param Weights the Weights to set
     */
    public void setWeights(double[] Weights) {
        this.Weights = Weights;
    }

    /**
     * @return the Delta
     */
    public double getDelta() {
        return Delta;
    }

    /**
     * @param Delta the Delta to set
     */
    public void setDelta(double Delta) {
        this.Delta = Delta;
    }

    /**
     * @return the OldDelta
     */
    public double getOldDelta() {
        return OldDelta;
    }

    /**
     * @param OldDelta the OldDelta to set
     */
    public void setOldDelta(double OldDelta) {
        this.OldDelta = OldDelta;
    }
    // activation function

    public double weightedInput() {
        double sum = 0;
        for(int i=0;i<Weights.length;i++){
            sum = sum + (Weights[i]*input[i]);
        }
        return sum;
    }

    /**
     * @return the Output
     */
    public double getOutput() {
        return Output;
    }

    /**
     * @param Output the Output to set
     */
    public void setOutput(double Output) {
        this.Output = Output;
    }
}
