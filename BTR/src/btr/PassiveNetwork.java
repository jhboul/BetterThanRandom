package btr;
import org.encog.neural.networks.BasicNetwork;

/**
 * A network that is designed to to in the same kind (and amount)
 * of data as it outputs.
 */
class PassiveNetwork extends NeuralNetwork
{
   /**
    * Constructor for objects of class PassiveNetwork.
    * Creates a network with layers of alternating numbers of nodes.
    * The input and output layers have stateNodes number of nodes.
    * The whole network alternates between stateNodes number of nodes
    * and internalNodes number of nodes.
    * There are numLayers number of layers with internalNodes number of nodes.
    * @param stateNodes the number of input and output nodes. (>0)
    * @param internalNodes the number of nodes on each internal layer. (>0)
    * @param numLayers the number of internal layers. (>=0)
    */
   PassiveNetwork(int stateNodes, int internalNodes, int numLayers)
   {
      int[] allLayers = new int[numLayers * 2 + 1];
      for(int i = 0; i < numLayers; i++)
      {
         allLayers[i * 2] = stateNodes;
         allLayers[i * 2 + 1] = internalNodes;
      }
      allLayers[numLayers * 2] = stateNodes;
      allLayers[allLayers.length - 1] = stateNodes;
      this.setLayers(allLayers);
   }

   /**
    * Constructor for objects of class PassiveNetwork
    * with aNetwork as the network.
    * @param aNetwork the BasicNetwork.
    */
   PassiveNetwork(BasicNetwork aNetwork)
   {
      super(aNetwork);
   }

   /**
    * Constructor for objects of class PassiveNetwork
    * with someWeights that will be give to the flat network and
    * someLayers that are the numbers of neurons in each layer.
    */
   PassiveNetwork(double[] someWeights, int[] someLayers)
   {
       super(someWeights, someLayers);
   }

   /**
    * Get the early output from a certain input.
    * (the output from early nodes)
    * @param input to be input into the network. (input.getLength() == this.getInputQuantity())
    * @return the early output of the network.
    */
   Construct suppose(Construct input, int numLayers)
   {
      return this.getSuppose(numLayers).think(input);
   }

   /**
    * Get the early section of the network as a network itself.
    * @return a network that contains the first numLayers * 2 + 1 of this one (meaning it's input and output layers have same number of nodes).
    */
   PassiveNetwork getSuppose(int numLayers)
   {
      NetworkEditor editor = new NetworkEditor(this.getNetwork());
      editor = editor.firstLayers((numLayers * 2) + 1);
      return editor.buildNetwork();
   }

   /**
    * Appends aPassiveNetwork to itself and returns the result.
    * @param aPassiveNetwork the network to be appended.
    * @return a PassiveNetwork with aPassiveNetwork appended onto this PassiveNetwork
    */
   PassiveNetwork append(PassiveNetwork aPassiveNetwork)
   {
      NetworkEditor thisEditor = new NetworkEditor(this.getNetwork());
      NetworkEditor appendEditor = new NetworkEditor(aPassiveNetwork.getNetwork());
      NetworkEditor editor = thisEditor.append(appendEditor);
      return editor.buildNetwork();
   }

   /**
    * Creates a network from the textual representation passed to it.
    * @param buildString A string in the form [aWeight, aWeight...][aLayer, aLayer...][anIndex, anIndex...].
    */
    static PassiveNetwork fromString(String buildString)
    {
        return new PassiveNetwork(NetworkEditor.fromString(buildString));
    }
}