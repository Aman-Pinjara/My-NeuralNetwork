package neuralnetwork;

public class NeuralNetworkT {
    int inputsNodes; //Number of inputs for the Network
    int hiddenNodes; // Number of hidden layers 
    int outputsNodes; // Number of outputs in the netwoek
    Matrix weightsIH; // Weight matrix for each layers hidden -> Output
    Matrix weightsHO; // Weight matrix for each layers hidden -> Output
    Matrix biasH; // Bias of each layer
    Matrix biasO; // Bias of each layer
    double learningRate = 0.1; // learning rate

    class Sigmoid implements MatrixMethod{

        //Sigmoid function for making the matrix between 0 and 1
        @Override
        public double change(double n) {
            return (1/( 1 + Math.pow(Math.E,(-1*n))));
        }
    }
    class DSigmoid implements MatrixMethod{
        //Derivative of sigmoid for already sigmoided inputs

        @Override
        public double change(double n) {
            return n * (1-n);
        }
    }

    MatrixMethod sigmoid = new Sigmoid();
    MatrixMethod dSigmoid = new DSigmoid();

    //Contructor for creating Neural Network
    public NeuralNetworkT(int inputs, int hiddenNodes, int outputs){

        this.inputsNodes = inputs;
        this.hiddenNodes = hiddenNodes;
        this.outputsNodes = outputs;

        weightsIH = new Matrix(hiddenNodes, inputsNodes);
        weightsHO = new Matrix(outputsNodes, hiddenNodes);
        biasH = new Matrix(hiddenNodes, 1);
        biasO = new Matrix(outputsNodes, 1);

        weightsIH.randomize(); // Weight matrix for each layers hidden -> Output
        weightsHO.randomize(); // Weight matrix for each layers hidden -> Output
        biasH.randomize(); 
        biasO.randomize();

    }

    //Train the Network -> 1 input at a time
    public void train(double[] input, double[] answers) {
        //To check inputs and answers are according to the networks requirement

        Matrix inputs = Matrix.fromArray(input);


        Matrix hiddenOut = Matrix.multiply(weightsIH, inputs);

        hiddenOut.add(biasH);

        hiddenOut.changeElements(sigmoid);

        Matrix outputOut = Matrix.multiply(weightsHO, hiddenOut);
        outputOut.add(biasO);
        outputOut.changeElements(sigmoid);

        Matrix target = Matrix.fromArray(answers);

        Matrix error = Matrix.subtract(target, outputOut);

        Matrix gradient = Matrix.changeElements(outputOut, dSigmoid);
        gradient.multiply(learningRate);
        gradient.multiply(error);

        Matrix hiddenOutT = Matrix.transpose(hiddenOut);
        Matrix deltaWeightHO = Matrix.multiply(gradient, hiddenOutT);

        weightsHO.add(deltaWeightHO);
        biasO.add(gradient);

        Matrix weightHOT = Matrix.transpose(weightsHO);

        Matrix errorH = Matrix.multiply(weightHOT, error);
        
        Matrix gradientH = Matrix.changeElements(hiddenOut, dSigmoid);
        gradientH.multiply(learningRate);
        gradientH.multiply(errorH);

        Matrix inputT = Matrix.transpose(inputs);
        Matrix deltaWeightIH = Matrix.multiply(gradientH, inputT);

        // weightsIH.printmatrix();
        // deltaWeightIH.printmatrix();

        weightsIH.add(deltaWeightIH);
        biasH.add(gradientH);

    }


    //Predict the output of and Input
    public double[] predict(double[] input){

        //Input into a matrix object
        
        Matrix inputs = Matrix.fromArray(input);

        Matrix hiddenOut = Matrix.multiply(weightsIH, inputs);

        hiddenOut.add(biasH);

        hiddenOut.changeElements(sigmoid);

        Matrix outputOut = Matrix.multiply(weightsHO, hiddenOut);
        outputOut.add(biasO);
        outputOut.changeElements(sigmoid);

        return outputOut.toArray();
    }
}
