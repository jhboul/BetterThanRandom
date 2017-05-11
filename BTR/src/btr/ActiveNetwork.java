package btr;
import org.encog.neural.networks.BasicNetwork;
/**
 * A network that is designed to to in the same kind (and amount)
 * of data as it outputs, with some additional data inputed.
 */
class ActiveNetwork extends NeuralNetwork
{
   /**
    * Constructor for objects of class ActiveNetwork.
    */
   ActiveNetwork()
   {
      super();
   }

   /**
    * Constructor for objects of class ActiveNetwork.
    * Creates a Network with enough input nodes for a state and an moreNodes.
    * It will have as many internal layers as layers,
    * and each will have as many nodes as internalNodes.
    * It will have as many output nodes as stateNodes.
    * @param stateNodes the number of output nodes and some of the input nodes. (>0)
    * @param moreNodes the rest of the input nodes. (>=0)
    * @param internalNodes the number of nodes on each internal layer. (>0)
    * @param numLayers the number of internal layers. (>=0)
    */
   public ActiveNetwork(int stateNodes, int moreNodes, int internalNodes, int numLayers)
   {
      int[] allLayers = new int[numLayers + 2];
      allLayers[0] = stateNodes + moreNodes;
      for(int i = 1; i < numLayers + 1; i++)
      {
         allLayers[i] = internalNodes;
      }
      allLayers[allLayers.length - 1] = stateNodes;
      this.setLayers(allLayers);
   }

   /**
    * Constructor for objects of class ActiveNetwork
    * with aNetwork as the network.
    * @param aNetwork the BasicNetwork.
    */
   public ActiveNetwork(BasicNetwork aNetwork)
   {
      super(aNetwork);
   }
   
   /**
    * Creates a network from the textual representation passed to it.
    * @param buildString A string in the form [aWeight, aWeight...][aLayer, aLayer...][anIndex, anIndex...].
    */
    static ActiveNetwork fromString(String buildString)
    {
        return new ActiveNetwork(NetworkEditor.fromString(buildString));
    }
}