package neuralnetwork;

public class NeuralNetwork{


    int inputs; //Number of inputs for the Network
    int hiddenLayers; // Number of hidden layers 
    int outputs; // Number of outputs in the netwoek
    Matrix[] weights; // Weight matrix for each layers hidden -> Output
    Matrix[] bias; // Bias of each layer
    double learningRate = 0.1; // learning rate

    class Sigmoid implements MatrixMethod{

        //Sigmoid function for making the matrix between 0 and 1
        @Override
        public double change(double n) {
            return 1/(1+Math.exp(-n));
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
    public NeuralNetwork(int inputs, int hiddenLayers, int outputs){

        this.inputs = inputs;
        this.hiddenLayers = hiddenLayers;
        this.outputs = outputs;
        this.weights = new Matrix[hiddenLayers+1]; // +1 For outputs weights
        this.bias = new Matrix[hiddenLayers+1];
    }

    //Initializing the Network with random weights and biases
    public void init(int[] hiddenLayersNodes){
        //If number of nodes given is less than the number of Hidden layers specified throw
        if(hiddenLayersNodes.length != hiddenLayers){
            throw new ArithmeticException("Invalid Hiddenlayers Nodes");
        }

        //hiddenLayersNodes is the number of nodes in hidden layers

        
        int i = 0;
        //To keep track of the number of rows in the Previous Layer to generate new Layers weights and bias
        int prev = inputs;

        for(int node: hiddenLayersNodes){
            weights[i] = new Matrix(node, prev); 
            weights[i].randomize();
            
            bias[i] = new Matrix(node, 1);
            bias[i].randomize();

            prev = node;
            i++;
        }

        //Extra for Weights and biases of Output Layer
        weights[i] = new Matrix(this.outputs, prev);
        bias[i] = new Matrix(this.outputs, 1);

        weights[i].randomize();
        bias[i].randomize();

        //Apply the sigmoid function
        // for (int j = 0; j < weights.length; j++) {
        //     weights[j].changeElements(sigmoid);
        //     bias[j].changeElements(sigmoid);
        // }
        // weights[0].printmatrix();
    }

    //Initializing the Network with specific Weights and Biases
    public void init(Matrix[] weights,Matrix[] bias){
        //To check Weights and biases given are according to the layers specified
        if(weights.length != hiddenLayers+1 || bias.length != hiddenLayers+1){
            throw new ArithmeticException("Invalid Hiddenlayers Nodes");
        }
        
        
        int prev = inputs;

        for (int i = 0; i < weights.length; i++) {
            //check for invalid weights and biases
            if((weights[i].data[0].length != prev) || (bias[i].data.length != prev)){
                throw new ArithmeticException("Invalid Weight Or Bias matrix");
            }


            this.weights[i] = weights[i];
            this.bias[i] = bias[i];
            prev = weights[i].data.length;
        }
    }

    //Train the Network -> 1 input at a time
    public void train(double[] input, double[] answers) {
        // for (Matrix d : weights) {
        //     d.printmatrix();
        // }
        
        //To check inputs and answers are according to the networks requirement
        if(input.length != inputs || answers.length !=  outputs){
            throw new ArithmeticException("inputs or output parameter length must be same as inputs and outputs of the neural network");
        }

        //Outputs of each layers 
        Matrix[] outputsFromLayers = new Matrix[weights.length];

        //Input to each layers
        Matrix[] inputsForLayers = new Matrix[weights.length];

        //To calculate input for each layers
        Matrix inputM = Matrix.fromArray(input);

        for (int i = 0; i < weights.length; i++) {
            //Output from this layers layer
            Matrix output = Matrix.multiply(weights[i], inputM);
            //Adding biases from respective layer
            output.add(bias[i]);
            output.changeElements(sigmoid);

            //adding output to the array
            outputsFromLayers[i] = output;

            // adding inputs in the array
            inputsForLayers[i] = inputM;
            inputM = output;
        }

        //prediction by the network
        Matrix predicted = outputsFromLayers[weights.length-1];

        //Expected Object of matric from the answers supplied
        Matrix expected = Matrix.fromArray(answers);

        //Calculating initial error
        Matrix error = Matrix.subtract(expected, predicted);

        //Finding errors of each layer and changing the weights and bias
        for (int i = weights.length-1; i >= 0; i--) {
            
            //Calculating transpose of weight
            Matrix weightT = Matrix.transpose(weights[i]);


            //Getting the derivative of sigmoid
            Matrix gradient = Matrix.changeElements(outputsFromLayers[i], dSigmoid);
            gradient.multiply(learningRate);
            gradient.multiply(error);

            //Inputs to the current layers transposed
            Matrix inputFromPreviousT = Matrix.transpose(inputsForLayers[i]);

            //Calculating delta weight and bias
            Matrix weightDelta = Matrix.multiply(gradient,inputFromPreviousT);
            // weightDelta.printmatrix();
            weights[i].add(weightDelta);
            bias[i].add(gradient);


            //Calculating error of the previous layer
            error = Matrix.multiply(weightT, error);

        }

        // System.out.println("AFTER training -------------------------");

        // for (Matrix d : weights) {
        //     d.printmatrix();
        // }

    }


    //Predict the output of and Input
    public double[] predict(double[] input){

        //Input into a matrix object
        Matrix prediction = Matrix.fromArray(input);

        for (int i = 0; i < weights.length; i++) {
            //Output from each layer
            prediction = Matrix.multiply(weights[i], prediction);
            //Adding biases from respective layer
            prediction.add(bias[i]);
            //Doing sigmoid of predictions
            prediction.changeElements(sigmoid);
        }

        return prediction.toArray();
    }

}
