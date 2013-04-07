/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.ntust.dma.group13.hw01;

/**
 *
 * @author firman
 */
public interface MachineLearningInterface {
    public abstract void Train(double [][][] set) ;
    public abstract void Test (double [][][] set) ;
           
    public static int TrainFunction = 0;
    public static int TestingFunction = 1;
    
}
