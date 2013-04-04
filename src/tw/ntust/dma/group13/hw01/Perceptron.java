package tw.ntust.dma.group13.hw01;

/**
 *
 * @author firman
 */
public class Perceptron implements MachineLearningInterface{
    double threshold ;
    double learning_rate ;

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
    
    public void Train() {
        
        return ;
    }
    
    public void Test() {
        
        return ;
    }
    
    
    
}
