package btr;

/**
 * Learns to predict a next state based on a last state and an action.
 */
class ActivePredictor
{
    private ActiveNetwork network;
    private double error;
    private LevelStructure structure;

    ActivePredictor(LevelStructure aStructure)
    {
        network = new ActiveNetwork(aStructure.stateSize, aStructure.stateSize, aStructure.ActiveInternalNodes, aStructure.ActiveNumLayers);
        structure = aStructure;
    }

    /**
    * Predicts the next state of the environment,
    * based on the last state and the next action.
    */
   Construct prediction(State lastState, Action anAction)
   {
       return this.network.think(lastState.append(anAction));
   }

   /**
    * Learns to predict the next state of the environment,
    * based on the last state and the next action.
    * @return the error achieved by the network.
    */
   double learn(State lastState, Action anAction, State nextState)
   {
       double anError = this.network.learn(lastState.append(anAction), nextState, structure.ActiveOneLearnReps);
       this.setError(anError);
       return anError;
   }

   /**
    * Learns to predict the next state of the environment,
    * based on arrays of last states and next actions.
    * @return the error achieved by the network.
    */
   double learn(State[] lastStates, Action[] actions, State[] nextStates)
   {
       double anError = this.network.arrayLearn(LevelStructure.appendConstructs(lastStates, actions), nextStates, structure.ActiveArrayLearnReps);
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
