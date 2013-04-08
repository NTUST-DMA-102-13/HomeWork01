/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.ntust.dma.group13.hw01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author firman
 */
public class BackPropagation implements MachineLearningInterface, Runnable {

    int Ni, Nh, No;//number of input, hidden and output node
    double[] Ai, Ah, Ao;//activation for nodes
    double[][] Wi, Wo;//weight
    double[][] Ci, Co;
    Random rand;
    private int typeRun;
    private double[][][] setInput;
    private double error, learningRate;
    private int iteration;
    private boolean isIteration;

    public BackPropagation(int ni, int nh, int no) {
        rand = new Random(0);

        this.Ni = ni + 1;
        this.Nh = nh;
        this.No = no;

        this.Ai = new double[Ni];
        this.Ah = new double[Nh];
        this.Ao = new double[No];

        for (int i = 0; i < Ni; i++) {
            Ai[i] = 1.0;
        }
        for (int i = 0; i < Nh; i++) {
            Ah[i] = 1.0;
        }
        for (int i = 0; i < No; i++) {
            Ao[i] = 1.0;
        }

        this.Wi = new double[Ni][Nh];
        this.Wo = new double[Nh][No];

        for (int i = 0; i < Ni; i++) {
            for (int j = 0; j < Nh; j++) {
                Wi[i][j] = random(-0.2, 0.2);
            }
        }
        for (int i = 0; i < Nh; i++) {
            for (int j = 0; j < No; j++) {
                Wo[i][j] = random(-2.0, 2.0);
            }
        }

        this.Ci = new double[Ni][Nh];
        this.Co = new double[Nh][No];

    }

    public BackPropagation(int ni, int nh, int no, double learningRate) {
        rand = new Random(0);

        this.Ni = ni + 1;
        this.Nh = nh;
        this.No = no;

        this.Ai = new double[Ni];
        this.Ah = new double[Nh];
        this.Ao = new double[No];

        for (int i = 0; i < Ni; i++) {
            Ai[i] = 1.0;
        }
        for (int i = 0; i < Nh; i++) {
            Ah[i] = 1.0;
        }
        for (int i = 0; i < No; i++) {
            Ao[i] = 1.0;
        }

        this.Wi = new double[Ni][Nh];
        this.Wo = new double[Nh][No];

        for (int i = 0; i < Ni; i++) {
            for (int j = 0; j < Nh; j++) {
                Wi[i][j] = random(-0.2, 0.2);
            }
        }
        for (int i = 0; i < Nh; i++) {
            for (int j = 0; j < No; j++) {
                Wo[i][j] = random(-2.0, 2.0);
            }
        }

        this.Ci = new double[Ni][Nh];
        this.Co = new double[Nh][No];
        this.learningRate = learningRate;
    }

    private double sigmoid(double x) {
        return Math.tanh(x);
    }

    private double dsigmoid(double y) {
        return 1.0 - y * y;
    }

    private double random(double min, double max) {
        return (max - min) * rand.nextDouble() + min;
    }

    private double[] update(double[] input) {
        if (input.length != this.Ni - 1) {
            return null;//throw new Exception("input length not equal with number input nodes");
        }        //System.arraycopy(input, 0, Ai, 0, Ni-1);

        for (int i = 0; i < Ni - 1; i++) {
            Ai[i] = input[i];
        }

        for (int j = 0; j < Nh; j++) {
            double sum = 0.0;
            for (int i = 0; i < Ni; i++) {
                sum += Ai[i] * Wi[i][j];
            }
            this.Ah[j] = sigmoid(sum);

        }

        for (int k = 0; k < No; k++) {
            double sum = 0.0;
            for (int j = 0; j < Nh; j++) {
                sum += Ah[j] * Wo[j][k];
            }
            this.Ao[k] = sigmoid(sum);

        }

        return this.Ao.clone();

    }
    ArrayList<Double[]> weightList;

    private double backPropagate(double[] target, double learning_rate, double M) {
        double[] output_deltas = new double[No];
        weightList = new ArrayList<>();
        for (int k = 0; k < No; k++) {
            double error = target[k] - Ao[k];
            output_deltas[k] = dsigmoid(Ao[k]) * error;
        }

        double[] hidden_deltas = new double[Nh];
        for (int j = 0; j < Nh; j++) {
            double error = 0.0;
            for (int k = 0; k < No; k++) {
                error += output_deltas[k] * Wo[j][k];
            }
            hidden_deltas[j] = dsigmoid(Ah[j]) * error;

        }

        for (int j = 0; j < Nh; j++) {

            for (int k = 0; k < No; k++) {
                double change = output_deltas[k] * Ah[j];
                Wo[j][k] += (learning_rate * change + M * Co[j][k]);
                Double[] data = new Double[4];
                data[0] = (double) -1.0;
                data[1] = (double) j + 1;
                data[2] = (double) k + 1;
                data[3] = Wo[j][k];
                weightList.add(data);
//                System.out.print(Wo[j][k] + "\t");
                this.Co[j][k] = change;
            }
//            System.out.println("");
        }
//        System.out.println("#############");
        for (int i = 0; i < Ni; i++) {
            for (int j = 0; j < Nh; j++) {
                double change = hidden_deltas[j] * Ai[i];
                Wi[i][j] += (learning_rate * change + M * Ci[i][j]);
                this.Ci[i][j] = change;
                Double[] data = new Double[4];
                data[0] = (double) i + 1;
                data[1] = (double) j + 1;
                data[2] = (double) -1;
                data[3] = Wi[i][j];
                weightList.add(data);
            }
//            System.out.println("");
        }
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
        double error = 0.0;
        for (int k = 0; k < No; k++) {
            double d = target[k] - Ao[k];

            error += 0.5 * d * d;
        }

        return error;
    }

    private void weight() {
        System.out.println("Input weights:");
        for (int i = 0; i < Ni; i++) {
            System.out.println(Arrays.toString(Wi[i]));
        }
        System.out.println("Output weights:");
        for (int j = 0; j < Nh; j++) {
            System.out.println(Arrays.toString(Wo[j]));
        }
    }
    ArrayList<Double> errors;
    ArrayList<double[]> ouput;

    public void Train(double[][][] set, int iteration, double learning_rate, double momentum) {
        System.out.println("iteration = " + iteration);
        this.errors = new ArrayList<>();
        this.ouput = new ArrayList<>();
        int N = set.length;
        for (int i = 0; i < iteration; i++) {
            double error = 0.0;
            ouput.clear();
//            System.out.println("i = " + i);
            for (int n = 0; n < N; n++) {
                double[] input = set[n][0];
                double[] target = set[n][1];
                double[] outs = this.update(input);
                ouput.add(outs);
                error += this.backPropagate(target, learning_rate, momentum);
            }
            if (i % 100 == 0) {
                this.errors.add(error);
                System.out.println("Error: " + error);/* weight();*/
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BackPropagation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void Train(double[][][] set, double error_threshold, double learning_rate, double momentum) {
        System.out.println("Error!!! " + error_threshold);
        this.errors = new ArrayList<>();
        int N = set.length;
        int count = 0;
        for (;;) {
            double error = 0.0;
            ouput.clear();
            for (int n = 0; n < N; n++) {
                double[] input = set[n][0];
                double[] target = set[n][1];
                double[] outs = this.update(input);
                ouput.add(outs);
                error += this.backPropagate(target, learning_rate, momentum);
            }
            if (error < error_threshold) {
                System.out.println("error = " + error);
                System.out.println("error_threshold = " + error_threshold);
                break;
            }
            if (count % 100 == 0) {
                this.errors.add(error);
                System.out.println("Error: " + error);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BackPropagation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }/* weight();*/
            count++;

        }
    }

    @Override
    public void Train(double[][][] set) {
        this.Train(set, 1000, 0.5, 0.1);
    }

    @Override
    public void Test(double[][][] set) {
        ouput.clear();
        for (double[][] s : set) {
            double[] outs = this.update(s[0]);
            ouput.add(outs);
            System.out.format("%s -> %s.\n", Arrays.toString(s[0]), Arrays.toString(outs));
        }
    }

    public static void main(String[] args) {
        double[][][] training_set;

        BackPropagation bpn = new BackPropagation(2, 4, 2);

        /* //AND *
         training_set = new double [][][] {
         {{0,0},{0}},
         {{0,1},{0}},
         {{1,0},{0}},
         {{1,1},{1}},
            
         };
         /*/
        /* //OR *
         training_set = new double [][][] {
         {{0,0},{0}},
         {{0,1},{1}},
         {{1,0},{1}},
         {{1,1},{1}},
            
         };
         //*/

        /* //XOR */
        training_set = new double[][][]{
            {{0, 0}, {0, 1}},
            {{0, 1}, {1, 0}},
            {{1, 0}, {1, 0}},
            {{1, 1}, {0, 1}},};
        double[][][] training_set2 = new double[][][]{
            {{0, 0}, {0}},
            {{0, 1}, {1}},
            {{1, 0}, {1}},
            {{1, 1}, {0}},};
        //*/

        bpn.Train(training_set);

        bpn.Test(training_set);
    }

    @Override
    public void run() {
        switch (this.getTypeRun()) {
            case MachineLearningInterface.TrainFunction:
                System.out.println("############################");
                if (isIteration) {
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + iteration);
                    this.Train(setInput, iteration, learningRate, 0.1);
                } else {
                    System.out.println("***********************");
                    this.Train(setInput, error, learningRate, 0.1);
                }
                JOptionPane.showMessageDialog(null, "Processes Train Succes");
                break;
            case MachineLearningInterface.TestingFunction:
                this.Test(getSetInput());
                JOptionPane.showMessageDialog(null, "Processes Test Succes");
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
     * @return the error
     */
    public double getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(double error) {
        this.error = error;
    }

    /**
     * @return the iteration
     */
    public int getIteration() {
        return iteration;
    }

    /**
     * @param iteration the iteration to set
     */
    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    /**
     * @return the isIteration
     */
    public boolean isIsIteration() {
        return isIteration;
    }

    /**
     * @param isIteration the isIteration to set
     */
    public void setIsIteration(boolean isIteration) {
        this.isIteration = isIteration;
    }
}
