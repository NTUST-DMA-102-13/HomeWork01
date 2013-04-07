/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.ntust.dma.group13.hw01;

/**
 *
 * @author vania
 */
public class SigmoidFunction implements TransferFunction {

    @Override
    public double getDerivativeValue(double weightedInput) {
      //  double a = Math.exp(weightedInput);
        double b = Math.exp(-weightedInput);
        return 1 / (1 + b);
    }
}
