package btr;

/**
 * Learns to predict a next state based on a last state.
 */
public class PassivePredictor
{
    private PassiveNetwork network;
    private double error;
    private LevelStructure structure;

    PassivePredictor(LevelStructure aStructure)
    {
        network = new PassiveNetwork(aStructure.stateSize, aStructure.PassiveInternalNodes, aStructure.PassiveNumLayers);
        structure = aStructure;
    }

    /**
    * Predicts the next state of the environment,
    * based on the last state.
    */
   Construct prediction(State lastState)
   {
       return this.network.think(lastState);
   }

   /**
    * Abstract the state of the environment.
    */
   Construct abstraction(State aState)
   {
       return this.network.suppose(aState, structure.AbstractionNumLayers);
   }

   /**
    * Returns the abstracting network.
    */
   PassiveNetwork getAbstraction()
   {
       return this.network.getSuppose(structure.AbstractionNumLayers);
   }

   /**
    * Learns to predict the next state of the environment,
    * based on the last state.
    * @return the error achieved by the network.
    */
   double learn(State lastState, State nextState)
   {
       double anError = this.network.learn(lastState, nextState, structure.PassiveOneLearnReps);
       this.setError(anError);
       return anError;
   }

   /**
    * Learns to predict the next state of the environment,
    * based on an array of last states.
    * @return the error achieved by the network.
    */
   double learn(State[] lastStates, State[] nextStates)
   {
       double anError = this.network.arrayLearn(lastStates, nextStates, structure.PassiveArrayLearnReps);
       this.setError(anError);
       return anError;
   }

   private void setError(double anError)
   {
       this.error = anError;
   }

   double getError()
   {
       return this.error;
   }
}
