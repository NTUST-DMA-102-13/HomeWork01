package tw.ntust.dma.group13.hw01;

import java.util.Iterator;

/**
 *
 * @author firman
 */
public class Perceptron implements MachineLearningInterface{
    double threshold ;
    double learning_rate ;
    double[] weigths;
    int error_count;

    //weights =
    /**
     *
     * @param threshold
     */
    public Perceptron(double threshold,double learning_rate) {
        this.threshold = 0.5;
        this.learning_rate = 0.1;
    }

    public Perceptron() {
        this(0.5,0.1);
    }
    
    private double dot_product(double [] values){
        int n = values.length;//ni
        double sum=0;
        for (int i = 0;i<n;i++)
            sum+=this.weigths[i]*values[i];
        
        return sum;
    }
    
    public void Train(double [][][] set) {
        for(;;){
            error_count=0;
            for(double[][] data: set){
                double[] input_data=data[0];
                int result = dot_product(input_data)>this.threshold?1:0;
                double error = data[1][0] - result;
                if (error != 0) {
                    error_count++;
                    int n = input_data.length;//ni
                    for(int i=0;i<n;n++ ) {
                         this.weigths[i] +=  learning_rate * error * input_data[i];
                    }
                }
            }
            
        }
    }
    public void Test(double [][][] set) {
        
        return ;
    }
    
    public static void main(String[] args) {
        double[][][] training_set;
        training_set = new double [][][]{{{1, 0, 0}, {1,}}, {{1, 0, 1}, {1,}}, {{1, 1, 0}, {1}}, {{1, 1, 1}, {0}}};
        Perceptron p = new Perceptron(); 
        
        p.Train(training_set);
        
        p.Test (training_set);
    }
    
}
