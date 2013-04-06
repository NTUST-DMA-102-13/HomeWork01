/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.ntust.dma.group13.hw01;

/**
 *
 * @author firman
 */
public class BackPropagation implements MachineLearningInterface{
    
    int Ni,Nh,No;//number of input, hidden and output node
    double[] Ai,Ah,Ao;//activation for nodes
    double[][] Wi,Wo;//weight
    double[][] Ci,Co;
    
    public BackPropagation(int ni,int nh, int no ){
        this.Ni = ni + 1;
        this.Nh = nh;
        this.No = no;

        this.Ai = new double[Ni];
        this.Ah = new double[Nh];
        this.Ao = new double[No];

        for(int i = 0;i<Ni;i++ ) Ai[i]=1.0;
        for(int i = 0;i<Nh;i++ ) Ah[i]=1.0;
        for(int i = 0;i<No;i++ ) Ao[i]=1.0;

        this.Wi = new double[ni][nh];
        this.Wo = new double[nh][no];
        
        for(int i = 0;i<Ni;i++)
            for(int j = 0 ;j<Nh;j++)
                Wi[i][j]=random(-2.0,2.0);
        for(int i = 0;i<Nh;i++)
            for(int j = 0 ;j<No;j++)
                Wo[i][j]=random(-2.0,2.0);
        
        this.Ci = new double[Ni][Nh];
        this.Co = new double[Nh][No];
        
    }
    
    private double sigmoid(double x){
        return Math.tanh(x);
    }
    
    private double dsigmoid(double y){
        return 1.0 - y*y;
    }
    
    private double random(double min, double max){
        return (max-min)*Math.random()+min;
    }
    
    private double backPropagate(double[] target, double learning_rate, double momentum){
        double[] dO=new double[No];
        
        for(int k=0;k<No;k++){
            double error=target[k]-Ao[k];
            dO[k]= dsigmoid(Ao[k])*error;
        }
        double[] dH=new double[No];
        for(int j=0;j<Nh;j++){
            double error=0.0;
            for(int k=0;k<No;k++)
                error+=dO[k]+Wo[j][k];
            dH[j]=dsigmoid(Ah[j])*error;
        }
        
        for(int j=0;j<Nh;j++){
            
            for(int k=0;k<No;k++){
                double change = dO[k]*Ah[j];
                Wo[j][k]+=learning_rate*change+momentum*Co[j][k];
                this.Co[j][k]=change;
            }
                
        }
        
        for(int i=0;i<Ni;i++){
            for(int j=0;j<Nh;j++){
                double change =dH[j]*Ai[i];
                Wi[i][j] += learning_rate*change+momentum*Ci[i][j];
                this.Co[i][j]=change;  
            }
            
        }
        double error = 0.0;
        for (int k=0;k<No;k++){
            double d=target[k]-Ao[k];
            
            error+=0.5*d*d;
        }
        
        
        return 0;
    }
    
    public void Train(double[][][] set,int iteration, double learning_rate, double momentum){
        int N = set.length;
        for (int i = 0 ; i<iteration;i++){
            double error = 0.0;
            
            for(int n = 0;n<N;n++){
                double[] input= set[i][0];
                double[] target= set[i][0];
                this.update(input);
                error+=this.backPropagate(target,learning_rate,momentum);
            }
        }
    }

    @Override
    public void Train(double[][][] set) {
        this.Train(set, 10000, 0.5, 0.1);
    }

    @Override
    public void Test(double[][][] set) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void update(double[] input)  {
        if(input.length!=this.Ni - 1) return;//throw new Exception("input length not equal with number input nodes");
        
        for (int i=0;i<Ni-1;i++)
            Ai[i]=input[i];
        
        for (int j=0;j<Nh;j++){
            double sum= 0.0;
            for(int i = 0;i<Ni;i++)
                sum += Ai[i]*Wi[i][j];        
            this.Ah[j]=sigmoid(sum);
        }
        
        for(int k = 0;k<No;k++){
            double sum=0.0;
            for (int j = 0;j<Nh;j++)
                sum+=Ah[j]*Wo[j][k];
            this.Ao[k] = sigmoid(sum);
            
        }
            
    }
    
}
