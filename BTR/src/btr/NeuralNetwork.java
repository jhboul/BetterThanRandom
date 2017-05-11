package btr;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.neural.flat.FlatNetwork;
/**
 * Encapsulates a neural network.
 */
abstract class NeuralNetwork
{
   //instance variables
   private final BasicNetwork network;

   /**
    * Constructor for objects of class NeuralNetwork.
    * Initialises network to a a new Network.
    * @param none
    */
   NeuralNetwork()
   {
      this.network = new BasicNetwork();
   }

   /**
    * Constructor for objects of class NeuralNetwork.
    * Initialises network to aNetwork.
    * @param aNetwork the BasicNetwork.
    */
   NeuralNetwork(BasicNetwork aNetwork)
   {
      this.network = aNetwork;
   }

   /**
    * Constructor for objects of class NeuralNetwork
    * with someWeights that will be give to the flat network and
    * someLayers that are the numbers of neurons in each layer.
    */
   NeuralNetwork(double[] someWeights, int[] someLayers)
   {
       this.network = new BasicNetwork();
       someLayers = LevelStructure.reverseIntArray(someLayers);
       this.setLayers(someLayers);
       FlatNetwork flat = getNetwork().getStructure().getFlat();
       flat.setWeights(someWeights);
       this.network.getStructure().setFlat(flat);
   }

   /**
    * Set the number of nodes in each layer to
    * the corresponding number in layers.
    * @param layers an array with the number of nodes in each layer, ordered from input to output.
    */
   final void setLayers(int[] layers)
   {
      for(int i = 0; i < layers.length; i++)
      {
         this.network.addLayer(new BasicLayer(new ActivationSigmoid(), true, layers[i]));
      }
      this.network.getStructure().finalizeStructure();
      this.network.reset();
   }

   /**
    * Get the output from a certain input.
    * @param input to be input into the network. (input.getLength() == this.getInputQuantity())
    * @return the output of the network.
    */
   Construct think(Construct input)
   {
      double[] dataList = input.getContent();
      BasicMLData data = new BasicMLData(dataList);

      Construct returnConstruct = new Construct(this.getNetwork().compute(data).getData());

      return returnConstruct;
   }

   /**
    * Train the NeuralNetwork with a certain input and output.
    * @param input to be input into the network. (input.getLength() == this.getInputQuantity())
    * @param output the correct output for that input. (output.getLength() == this.getOutputQuantity())
    * @return the network's amount of error (how close it got to the right answer).
    */
   double learn(Construct input, Construct output, int numReps)
   {
      double[][] unboxedInputs = new double[1][input.getContent().length];
      unboxedInputs[0] = input.getContent();
      double[][] unboxedOutputs = new double[1][output.getContent().length];
      unboxedOutputs[0] = output.getContent();
      MLDataSet trainingSet = new BasicMLDataSet(unboxedInputs, unboxedOutputs);
      return this.train(1, new Backpropagation(this.network, trainingSet));
   }

   /**
    * Train the NeuralNetwork with Construct arrays of input and output.
    * @param inputs an array of Constructs to be input into the network. (inputs[i].getLength() == this.getInputQuantity())
    * @param output an array of the corresponding correct output for that input. (outputs[i].getLength() == this.getOutputQuantity())
    * @param numReps the maximum number of times to repeat training.
    * @param numError the minimum error to warrant continued training.
    * @return the network's amount of error (how close it got to the right answer).
    */
   double arrayLearn(Construct[] inputs, Construct[] outputs, int numReps, double numError)
   {
      double[][] unboxedInputs = NeuralNetwork.unbox(inputs);
      double[][] unboxedOutputs = NeuralNetwork.unbox(outputs);
      MLDataSet trainingSet = new BasicMLDataSet(unboxedInputs, unboxedOutputs);
      return this.train(numReps, numError, new ResilientPropagation(this.network, trainingSet));
   }
   
   /**
    * Overloads arrayLearn.
    * Defaults numError to -1 so the it just does numReps.
    * @param inputs an array of Constructs to be input into the network. (inputs[i].getLength() == this.getInputQuantity())
    * @param output an array of the corresponding correct output for that input. (outputs[i].getLength() == this.getOutputQuantity())
    * @param numReps the maximum number of times to repeat training.
    * @param numError the minimum error to warrant continued training.
    * @return the network's amount of error (how close it got to the right answer).
    */
   double arrayLearn(Construct[] inputs, Construct[] outputs, int numReps)
   {
      return arrayLearn(inputs, outputs, numReps, -1);
   }

   /**
    * Trains trainer either numReps times
    * or until error rate goes below numError.
    * @param numReps the maximum number of times to repeat training.
    * @param numError the minimum error to warrant continued training.
    * @param trainer the training object (the training algorithm used).
    * @return the network's amount of error (how close it got to the right answer). (>0)
    */
   private double train(int numReps, double numError, MLTrain trainer)
   {
      double error;
      int i = 0;
      do
      {
         trainer.iteration();
         error = trainer.getError();
         i++;
      } while (i < numReps && error > numError);
      trainer.finishTraining();
      return error;
   }

   /**
    * Overloads train.
    * Defaults numError to -1 so the it just does numReps.
    * @param numReps the maximum number of times to repeat training. (>0)
    * @param trainer the training object (the training algorithm used).
    * @return the network's amount of error (how close it got to the right answer).
    */
   private double train(int numReps, MLTrain trainer)
   {
      return this.train(numReps, -1.0, trainer);
   }

   /**
    * Takes an array of Arrangements
    * and returns an array of doubles,
    * based on there contents.
    * @param boxed Constructs to be returned as doubles.
    * @return the unboxed contents of boxed.
    */
   private static double[][] unbox(Construct[] boxed)
   {
      int arrayLen = boxed.length;
      int contentLen = boxed[0].getContent().length;
      double[][] unboxed = new double[arrayLen][contentLen];
      for(int i = 0; i < arrayLen; i++)
      {
         unboxed[i] = boxed[i].getContent();
      }
      return unboxed;
   }

   /**
    * Gets the number of input nodes.
    * @param none
    * @return the number of input nodes.
    */
   int getInputQuantity()
   {
      return this.getNetwork().getInputCount();
   }

   /**
    * Gets the number of output nodes.
    * @param none
    * @return the number of output nodes.
    */
   int getOutputQuantity()
   {
      return this.getNetwork().getOutputCount();
   }

   /**
    * Returns the network.
    * @param none
    * @return objects the network.
    */
   final BasicNetwork getNetwork()
   {
      return (BasicNetwork) this.network.clone();
   }

  /**
    * Returns a textual representation of the NeuralNetwork.
    * @return A string in the form [aWeight, aWeight...][aLayer, aLayer...][anIndex, anIndex...]
    */
    @Override
    public String toString()
    {
        return new NetworkEditor(this.getNetwork()).toString();
    }
}