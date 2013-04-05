/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.ntust.dma.group13.hw01;

/**
 *
 * @author vania
 */
class NeuronLayer {

    private Neuron[] neurons;
    private double bias;
    private double[] Input, Outputs;
    // jumlah neurons
    private int Size;

    NeuronLayer(int neurons, int numAttributes, TransferFunction tf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the neurons
     */
    public Neuron[] getNeurons() {
        return neurons;
    }

    /**
     * @param neurons the neurons to set
     */
    public void setNeurons(Neuron[] neurons) {
        this.neurons = neurons;
    }

    /**
     * @return the bias
     */
    public double getBias() {
        return bias;
    }

    /**
     * @param bias the bias to set
     */
    public void setBias(double bias) {
        this.bias = bias;
    }

    /**
     * @return the Input
     */
    public double[] getInput() {
        return Input;
    }

    /**
     * @param Input the Input to set
     */
    public void setInput(double[] Input) {
        this.Input = Input;
    }

    /**
     * @param Outputs the Outputs to set
     */
    public void setOutputs(double[] Outputs) {
        this.setOutputs(Outputs);
    }

    /**
     * @return the Outputs
     */
    public double[] getOutputs() {
        return Outputs;
    }

    /**
     * @return the Size
     */
    public int getSize() {
        return Size;
    }

    /**
     * @param Size the Size to set
     */
    public void setSize(int Size) {
        this.Size = Size;
    }
}
