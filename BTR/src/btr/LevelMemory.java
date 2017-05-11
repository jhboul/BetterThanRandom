package btr;

/**
 * Records a number of the last states/goals/actions.
 */
class LevelMemory
{

    private State[] states;
    private int statePosition = 0;
    private Goal[] goals;
    private int goalPosition = 0;
    private Action[] actions;
    private int actionPosition = 0;
    private LevelStructure structure;

    /**
     * Initialises states, goals, and actions to arrays of random
     * States, Goals, and Actions.
     * @param stateNum the length states
     * @param goalNum the length goals
     * @param actionNum the length actions
     * @param aStructure the level structure (contains info about the level)
     */
    LevelMemory(int stateNum, int goalNum, int actionNum, LevelStructure aStructure)
    {
        this.structure = aStructure;

    }

    /**
     * Sets states, goals, and actions to arrays of random
     * States, Goals, and Actions.
     * @param stateNum the length states
     * @param goalNum the length goals
     * @param actionNum the length actions
     */
    private void randomContent(int stateNum, int goalNum, int actionNum)
    {
        int length = structure.stateSize;
        for(int i = 0; i < stateNum; i++)
        {
                this.states[i] = State.randomContent(length);
        }
        for(int i = 0; i < goalNum; i++)
        {
                this.goals[i] = Goal.randomContent(length);
        }
        for(int i = 0; i < actionNum; i++)
        {
                this.actions[i] = Action.randomContent(length);
        }
    }

    /**
     * Overwrites a slot in states with aMemory.
     * Overwrites the memory that has been held the longest.
     * @param aMemory the new memory.
     */
    void addMemory(State aMemory)
    {
        this.states[this.statePosition] = aMemory;
        this.statePosition++;
        if (this.statePosition == this.states.length)
        {
            this.statePosition = 0;
        }
    }

    /**
     * Overwrites a slot in goals with aMemory.
     * Overwrites the memory that has been held the longest.
     * @param aMemory the new memory.
     */
    void addMemory(Goal aMemory)
    {
        this.goals[this.goalPosition] = aMemory;
        this.goalPosition++;
        if (this.goalPosition == this.goals.length)
        {
            this.goalPosition = 0;
        }
    }

    /**
     * Overwrites a slot in actions with aMemory.
     * Overwrites the memory that has been held the longest.
     * @param aMemory the new memory.
     */
    void addMemory(Action aMemory)
    {
        this.actions[this.actionPosition] = aMemory;
        this.actionPosition++;
        if (this.actionPosition == this.actions.length)
        {
            this.actionPosition = 0;
        }
    }

    /**
     * Generates a new State based on it memories.
     * @return a random but typical State.
     */
    State newState()
    {
        return new State(LevelMemory.generatesSelection(this.states));
    }

    /**
     * Generates a new Goal based on it memories.
     * @return a random but typical Goal.
     */
    Goal newGoal()
    {
        return new Goal(LevelMemory.generatesSelection(this.goals));
    }

    /**
     * Generates a new Action based on it memories.
     * @return a random but typical Action.
     */
    Action newAction()
    {
        return new Action(LevelMemory.generatesSelection(this.actions));
    }

    /**
     * Generates a typical example of the content of the elements in someMemories
     * by filling each slot of the new array with the corresponding
     * element of a randomly selected Construct in someMemories.
     * @param someMemories an array of new constructs to base the newly generated array on.
     * @return a random array based that should be similar to the contents of someMemories.
     */
    private static double[] generatesSelection(Construct[] someMemories)
    {
        int selectLength = someMemories.length;
        int genarateLength = someMemories[0].getLength();
        double[] returnArray = new double[genarateLength];
        int selectionNumber;
        for(int i = 0; i < genarateLength; i++)
        {
            selectionNumber = (int)(Math.random() * selectLength);
            returnArray[i] = someMemories[selectionNumber].getContent()[i];
        }
        return returnArray;
    }
}
