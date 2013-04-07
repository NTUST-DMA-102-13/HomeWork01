/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.ntust.dma.group13.hw01;

import java.util.Arrays;

/**
 *
 * @author vania
 */
public class MLP {
      private Dataset ds, ds2;
    private double rate; // , momentum;
    private NeuronLayer[] layers;
    private TransferFunction tf;

    /**
       Constructs a new MLP network.
    **/ 
    public MLP(Dataset ds, Dataset ds2, int noLayers, 
            int neurons, double rate, TransferFunction tf){
        this.ds = ds;
        this.ds2 = ds2;
        this.rate = rate;
//        this.momentum = momentum;
        this.tf = tf;
        layers = new NeuronLayer[noLayers];
        //Input layer.
        layers[0] = new NeuronLayer(neurons, ds.getNumAttributes(), tf);
        //Hidden layers.
        for(int i=1; i<layers.length-1; i++){
           layers[i] = new NeuronLayer(neurons, layers[i-1].getSize(),tf);
        }
        //Output layer.
        layers[layers.length-1] = new NeuronLayer(ds.getNumClasses(), layers[layers.length-2].getSize(), tf);
    }
    public MLP(Dataset ds, Dataset ds2, int noLayers, 
            int neurons[], double rate,
             TransferFunction tf){
        this.ds = ds;
        this.ds2 = ds2;
        this.rate = rate;
     //   this.momentum = momentum;
        this.tf = tf;
        layers = new NeuronLayer[noLayers];
        //Input layer.
        layers[0] = new NeuronLayer(ds.getNumAttributes(), ds.getNumAttributes(), tf);
        //Hidden layers.
        for(int i=1; i<layers.length-1; i++){
           layers[i] = new NeuronLayer(neurons[i-1], layers[i-1].getSize(),tf);
        }
        //Output layer.
        layers[layers.length-1] = new NeuronLayer(ds.getNumClasses(), layers[layers.length-2].getSize(), tf);
    }
    

    /**
       Tests the network against data it hasn't seen before.
    **/
    public void test(){
        System.out.println("--------TESTING UNSEEN DATA--------");
        for(int i=0; i<ds2.getNumEntries(); i++){
            double[] inputs = ds2.getAttributeSet(i);
            double desired = ds2.getClass(i);
            feedForward(inputs);
            double[] outputs = layers[layers.length-1].getOutputs();
            System.out.println("DESIRED: "+desired+" ACTUAL: "+Arrays.toString(outputs)+"");
        }
    }

    /**
       Runs all of the training data through the network without updating weights.
    **/
    public void visualise(String outFname, String templateFname){
        double[] outs = new double[ds.getNumEntries()];
        double[] desired = new double[ds.getNumEntries()];

        for(int i=0; i<ds.getNumEntries(); i++){
            feedForward(ds.getAttributeSet(i));
            outs[i] = ds.getClass(i);
            desired[i] = getMax(layers[layers.length-1].getOutputs());
        }
//        Grapher.writeScript(desired, outs, outFname, templateFname);
    }

    /**
       Performs the On-Line/Sequential variant of the training algorithm.
    **/
    public void sequential(){
        double error;
        Neuron[] neurons, earlierNeurons;
        double[] weights, outputs;
        int epoch = 0;
        do{
            epoch++;
            error = 0f;
            //For each example in the training set.
            for(int i=0; i<ds.getNumEntries(); i++){
                //Feedforward, calculate error and backwards propagate error across neurons.
                feedForward(ds.getAttributeSet(i));
                outputs = layers[layers.length-1].getOutputs();
                error += backPropagate(outputs, toOutputVector(ds.getClass(i), layers[layers.length-1].getSize()));

                //For each layer, update weights.
                for(int j=layers.length-1; j>0; j--){
                    neurons = layers[j].getNeurons(); 
                    earlierNeurons = layers[j-1].getNeurons();
                    for(int k=0; k<neurons.length; k++){
                        weights = neurons[k].getWeights();
                        for(int l=0; l<earlierNeurons.length; l++){
                            weights[l] += rate*(neurons[k].getDelta()*earlierNeurons[l].getOutput());
                        }
                        neurons[k].setWeights(weights);
                    }
                    layers[j].setNeurons(neurons);
                }
                System.out.println("EPOCH: "+epoch+" EXAMPLE: "+i+" OUTPUT: "+Arrays.toString(outputs)+" DESIRED: "+ds.getClass(i)+" ERROR: "+error+"");
            }
            error /= ds.getNumEntries();
        }while(error > 0.01);//TO DO: Change target error
    }

    /**
       Method to classify a set of features.
    **/
    public double classify(){
        return 0f;
    }

    /**
       Takes a scalar class value, and returns the binary representation, used as a target output vector.
    **/
    private double[] toOutputVector(double val, int length){
        double[] out = new double[length];
        out[(int)val-1] = 1f;
        return out;
    }

    /**
       Gives the Sum Squared Error.
    **/
    private double getSSE(double output, double desired){
        //return 0.5*Math.pow((desired - output), 2);
        return (desired-output);
    }

    /**
       Finds the maximum value in a double array.
    **/
    private double getMax(double[] outputs){
        double out = 0f;
        for(int i=0; i<outputs.length; i++){
            if(outputs[i] > out){
                out = outputs[i];
            }
        }
        return out;
    }

    /**
       Feeds the inputs forward through the network.
    **/
    private void feedForward(double[] input){
        //Update input layer.
        layers[0].setInput(addBias(input, 0));

        //Update hidden layers and output layer.
        for(int i=1; i<layers.length; i++){
            layers[i].setInput(layers[i-1].getOutputs());
        }
    }

    /**
       Adds bias value to inputs.
        Bias value is taken from a layers bias node.
    **/
    private double[] addBias(double[] input, int index){
        double[] tmp = new double[input.length+1];
        for(int i=0; i<input.length; i++){
            tmp[i] = input[i];
        }
        tmp[tmp.length-1] = layers[index].getBias();
        return tmp;
    }

    /**
       Backwards propagate errors.
    **/
    private double backPropagate(double[] outputs, double[] desired){
        Neuron[] neurons, laterNeurons;
        double delta, sum;
        double error = 0f;

        //Calculate delta's of output neurons.
        neurons = layers[layers.length-1].getNeurons();
        for(int i=0; i<neurons.length; i++){
            error = getSSE(outputs[i], desired[i]);
            delta = tf.getDerivativeValue(neurons[i].weightedInput())*error;
            neurons[i].setOldDelta(neurons[i].getDelta());
            neurons[i].setDelta(delta);
        }
        layers[layers.length-1].setNeurons(neurons);

        //For hidden and input layers, update delta's.
        for(int i=layers.length-2; i>=0; i--){
            neurons = layers[i].getNeurons();
            laterNeurons = layers[i+1].getNeurons();
            for(int j=0; j<neurons.length; j++){
                sum = 0f;
                for(int k=0; k<laterNeurons.length; k++){
                    sum += neurons[j].getWeights()[k]*laterNeurons[k].getDelta();
                }
                delta = tf.getDerivativeValue(neurons[j].weightedInput())*sum;
                neurons[i].setDelta(delta);
            }
            layers[i].setNeurons(neurons);
        }
        return error;
    }
}
