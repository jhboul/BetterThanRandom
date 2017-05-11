package btr;

/*
 * An object is used to set many propperties of a level.
 */
class LevelStructure {

    int stateSize;
    int ActiveInternalNodes;
    int ActiveNumLayers;
    int ActiveOneLearnReps;
    int ActiveArrayLearnReps;
    int PassiveInternalNodes;
    int PassiveNumLayers;
    int AbstractionNumLayers;
    int PassiveOneLearnReps;
    int PassiveArrayLearnReps;

    LevelStructure()
    {
        super();
    }

    /*
     * Returns the position of the first parentheses' closing match.
     * @param aString a string that starts with an open parentheses.
     * @return the position of the first parentheses' closing match.
     */
    static int nextParentheses(String aString)
    {
        if (aString.charAt(0) != '[')
        {
            return 0;
        }

        int count = 1;

        for (int i = 1; i < aString.length(); i++)
        {
            if (aString.charAt(i) == '[')
            {
                count++;
            }
            if (aString.charAt(i) == ']')
            {
                count--;
            }
            if (count == 0)
            {
                return i;
            }
        }
        return 0;
    }

    /*
     * Returns the array as integers.
     * @param someStrings an array of strings that evaluate to integers.
     */
    static int[] toIntArray(String[] someStrings)
    {
        int len = someStrings.length;
        int[] returnInts = new int[len];
        for (int i = 0; i < len; i++)
        {
            returnInts[i] = Integer.parseInt(someStrings[i]);
        }
        return returnInts;
    }

    /*
     * Returns the array as doubles.
     * @param someStrings an array of strings that evaluate to doubles.
     */
    static double[] toDoubleArray(String[] someStrings)
    {
        int len = someStrings.length;
        double[] returnDouble = new double[len];
        for (int i = 0; i < len; i++)
        {
            returnDouble[i] = Double.parseDouble(someStrings[i]);
        }
        return returnDouble;
    }

    /*
     * Returns the array reversed.
     * @param someInts an array of integers.
     */
    static int[] reverseIntArray(int[] someInts)
    {
        int len = someInts.length;
        int[] someReversedInts = new int[len];
        for(int i = 0; i < len / 2; i++)
        {
            someReversedInts[i] = someInts[len - i - 1];
            someReversedInts[len - i - 1] = someInts[i];
        }
        return someReversedInts;
    }

    /*
     * Returns array with elements appended.
     * @param firstConstructs an array of Constructs. firstConstructs.length === lastConstructs.length
     * @param lastConstructs an array of Constructs.
     */
    static Construct[] appendConstructs(Construct[] firstConstructs, Construct[] lastConstructs)
    {
        int len = firstConstructs.length;
        Construct[] returnConstructs = new Construct[len];
        for (int i = 0; i < len; i++)
        {
            returnConstructs[i] = firstConstructs[i].append(lastConstructs[i]);
        }
        return returnConstructs;
    }
}
