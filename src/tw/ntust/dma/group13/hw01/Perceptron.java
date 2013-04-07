package tw.ntust.dma.group13.hw01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author firman
 */
public class Perceptron implements MachineLearningInterface, Runnable {

    private double threshold;
    private double learning_rate;
    double[] weigths;
    int error_count;
    boolean iterations = false;
    int numIterations = 0;
    private int typeRun = 0;
    private double[][][] setInput;
    //weights =

    /**
     *
     * @param threshold
     */
    public Perceptron(int Ni, double threshold, double learning_rate) {
        this.threshold = 0.5;
        this.learning_rate = 0.1;
        this.weigths = new double[Ni];
        this.iterations = false;
        this.errors = new ArrayList<>();

    }

    public Perceptron(int Ni, double threshold, double learning_rate, int iteratio) {
        this.threshold = 0.5;
        this.learning_rate = 0.1;
        this.weigths = new double[Ni];
        this.numIterations = iteratio;
        this.iterations = true;
        this.errors = new ArrayList<>();
    }

    public Perceptron(int Ni) {
        this(Ni, 0.5, 0.1);
    }

    private double dot_product(double[] values) {
        int n = values.length;//ni
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += this.weigths[i] * values[i];
        }

        return sum;
    }
    ArrayList<Double> errors;
    ArrayList<double[]> weights;
    ArrayList<Double> outs;

    public void Train(double[][][] set) {
        this.errors = new ArrayList<>();
        this.weights = new ArrayList<>();
        this.outs = new ArrayList<>();
        this.errors.clear();
        int inter = 0;
        for (;;) {
            error_count = 0;
            int countData = 0;
            this.outs.clear();
//   this.weights.clear();
            for (double[][] data : set) {
                this.weights.clear();
                double[] input_data = data[0];
                int result = dot_product(input_data) > this.getThreshold() ? 1 : 0;
                this.outs.add((double)result);
                double error = data[1][0] - result;
                if (error != 0) {
                    error_count++;
                    int n = input_data.length;//ni
                    for (int i = 0; i < n; i++) {
                        this.weigths[i] += getLearning_rate() * error * input_data[i];
                    }
                    System.out.println("weights = " + weights);
                }
                this.weights.add(this.weigths.clone());
                countData++;
            }
            this.errors.add((double) error_count);
            if (error_count == 0) {
                break;
            }
            if (iterations && inter == numIterations - 1) {
                break;
            }
            inter++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Perceptron.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //  return new Object[1][1];
    }

    public void Test(double[][][] set) {
          this.outs = new ArrayList<>();
        for (double[][] s : set) {
            double output = 0.0;
            for (int i = 0; i < s[0].length; i++) {
                output += s[0][i] * weigths[i];
            }
            this.outs.add(output);
            System.out.format("%s -> %.5f.\n", Arrays.toString(s[0]), output);
        }
    }

    public static void main(String[] args) {
        double[][][] training_set;
        //training_set = new double [][][]{{{1, 0, 0}, {1,}}, {{1, 0, 1}, {1,}}, {{1, 1, 0}, {1}}, {{1, 1, 1}, {0}}};
        /* //AND
         training_set = new double [][][] {
         {{0,0},{0}},
         {{0,1},{0}},
         {{1,0},{0}},
         {{1,1},{1}},
            
         };
         */
        /* //OR */
        training_set = new double[][][]{
            {{0, 0}, {0, 1}},
            {{0, 1}, {1, 0}},
            {{1, 0}, {1, 0}},
            {{1, 1}, {1, 0}},};
        //*/

        /* //XOR *
         training_set = new double [][][] {
         {{0,0},{0}},
         {{0,1},{1}},
         {{1,0},{1}},
         {{1,1},{0}},
            
         };
         //*/
        Perceptron p = new Perceptron(2);

        p.Train(training_set);

        p.Test(training_set);
    }

    @Override
    public void run() {

        switch (this.typeRun) {
            case MachineLearningInterface.TrainFunction:
                this.Train(getSetInput());
                break;
            case MachineLearningInterface.TestingFunction:
                this.Test(getSetInput());
                break;
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the typeRun
     */
    public int getTypeRun() {
        return typeRun;
    }

    /**
     * @param typeRun the typeRun to set
     */
    public void setTypeRun(int typeRun) {
        this.typeRun = typeRun;
    }

    /**
     * @return the setInput
     */
    public double[][][] getSetInput() {
        return setInput;
    }

    /**
     * @param setInput the setInput to set
     */
    public void setSetInput(double[][][] setInput) {
        this.setInput = setInput;
    }

    /**
     * @return the threshold
     */
    public double getThreshold() {
        return threshold;
    }

    /**
     * @param threshold the threshold to set
     */
    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    /**
     * @return the learning_rate
     */
    public double getLearning_rate() {
        return learning_rate;
    }

    /**
     * @param learning_rate the learning_rate to set
     */
    public void setLearning_rate(double learning_rate) {
        this.learning_rate = learning_rate;
    }
}
