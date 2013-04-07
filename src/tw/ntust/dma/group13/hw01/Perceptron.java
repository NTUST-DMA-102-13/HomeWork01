package tw.ntust.dma.group13.hw01;

import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author firman
 */
public class Perceptron implements MachineLearningInterface {

    double threshold;
    double learning_rate;
    double[] weigths;
    int error_count;

    //weights =
    /**
     *
     * @param threshold
     */
    public Perceptron(int Ni, double threshold, double learning_rate) {
        this.threshold = 0.5;
        this.learning_rate = 0.1;
        this.weigths = new double[Ni];
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

    public void Train(double[][][] set) {
        int inter = 0;
        for (;;) {
            error_count = 0;
            int countData = 0;
            for (double[][] data : set) {
                System.out.println("countData = " + countData);
                System.out.println("weight " + inter + " - " + countData + " - " + error_count + " " + Arrays.toString(weigths));
                double[] input_data = data[0];
//                System.out.println("Arrays.toString(data[0]) = " + Arrays.toString(data[0]));
//                System.out.println("input_data.length = " + input_data.length);
                int result = dot_product(input_data) > this.threshold ? 1 : 0;
                double error = data[1][0] - result;
                if (error != 0) {
                    error_count++;
                    int n = input_data.length;//ni
                    for (int i = 0; i < n; i++) {
                        this.weigths[i] += learning_rate * error * input_data[i];
                    }
                }
                countData++;
            }
            if (error_count == 0) {
                break;
            }
            inter++;
        }
    }

    public void Test(double[][][] set) {

        return;
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
            {{0, 0}, {0}},
            {{0, 1}, {1}},
            {{1, 0}, {1}},
            {{1, 1}, {1}},};
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
}
