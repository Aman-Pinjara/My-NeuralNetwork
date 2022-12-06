# My-NeuralNetwork
My own implementation of a neural Network.

## How to use it?

1. Clone the Repository / Download the source code.
2. Make a new java file.
3. Import the MyNeuralNetwork repository.


## How to Initialise a model?

1. After importing Create a new Object of type `MyNeuralNetwork`.
2. In constructor you have to provide 3 parameter.
    1. Desired Number of Inputs for your model.
    2. Desired Number of Hidden layers.
    3. Desired Number of Outputs.
3. Call the function `objectName.init()`.
4. You need to provide Number of hidden nodes for all the hidden layers specified in the form of an array.
OR.
4. You need to provide an array of weight matrix and an array of bias matrix for all the hidden layers and the outputs layer.

## How to train a model and get predictions?

1. After initialization you can call `objectname.train()` or `objectname.predict()`.
2. The `train()` function expects 2 parameters:
      1. An array of Inputs.
      2. An array of Outputs.
3. It only trains for 1 input at a time (Stochastic) so u can loop if for your data.
4. User `objectname.predict()` to get a prediction. It expects 1 parameter for inputs.


# Info About the Package

Its uses Stochastic method for training a NeuralNetwork.

It is a classic implementation of NeuralNetworks.
Uses Feed Forwarding and Back Propagation.
Also uses the concept of gradial decend and regression


### Sources / Inspiration

https://ml4a.github.io/ml4a/

https://www.youtube.com/playlist?list=PLRqwX-V7Uu6aCibgK1PTWWu9by6XFdCfh
