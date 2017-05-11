/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package btr;
import org.encog.util.EngineArray;
import org.encog.neural.networks.BasicNetwork;
import java.util.*;

/**
 * Objects model networks that can be cut and appended.
 */
class NetworkEditor {

    private double[] weights;
    private int[] layers;
    private int[] index;

    NetworkEditor(double[] someWeights, int[] someLayers, int[] anIndex)
    {
        this.weights = EngineArray.arrayCopy(someWeights);
        this.layers = EngineArray.arrayCopy(someLayers);
        this.index = EngineArray.arrayCopy(anIndex);
    }

    NetworkEditor(BasicNetwork network)
    {
        this.buildStructure(network);
    }

    /**
    * Interrogates the network to build a structure that represents it.
    */
    private void buildStructure(BasicNetwork network)
    {
        this.index = EngineArray.arrayCopy(network.getFlat().getWeightIndex());
        this.weights = EngineArray.arrayCopy(network.getFlat().getWeights());
        int[] someLayers = EngineArray.arrayCopy(network.getFlat().getLayerCounts());
        for (int i = 0; i < someLayers.length; i++)
        {
            someLayers[i] = someLayers[i] - 1;
        }
        this.layers = someLayers;
    }

    /**
    * Returns the PassiveNetwork that this NetworkBuilder represents.
    */
    PassiveNetwork buildNetwork()
    {
        return new PassiveNetwork(this.weights, this.layers);
    }

    /**
    * Returns a NetworkBuilder that has the first layers number of layers of this NetworkBuilder.
    * @param layers the number of layers to used in the new Network builder (layers >= 2 && layers%2 == 1)
    */
    NetworkEditor firstLayers(int layers)
    {
        if (layers >= this.index.length)
        {
            return this;
        }
        double[] newWeights = Arrays.copyOfRange(this.weights, this.index[this.index.length - layers], this.weights.length);
        int[] newIndex = NetworkEditor.minus(this.index, this.index[this.index.length - layers]);
        newIndex = NetworkEditor.lastIndex(newIndex, layers);
        int[] newLayers = NetworkEditor.lastIndex(this.layers, layers);
        return new NetworkEditor(newWeights, newLayers, newIndex);
    }

    private static int[] lastIndex(int[] anIndex, int firstNumber)
    {
        int len = anIndex.length;
        firstNumber = len - firstNumber;
        int[] returnIndex = Arrays.copyOfRange(anIndex, firstNumber, len);
        int minus = returnIndex[0];
        for (int i = 0; i > firstNumber; i++)
        {
            returnIndex[i] = returnIndex[i] - minus;
        }
        return returnIndex;
    }

    /**
    * Returns a NetworkEditor that has the structure of this one with editor's appended.
    */
    NetworkEditor append(NetworkEditor editor)
    {
        int len = editor.weights.length;
        double[] returnWeights = NetworkEditor.appendWeights(editor.weights, this.weights);
        int[] returnLayers = NetworkEditor.appendIndex(editor.layers, this.layers, 0);
        int[] returnIndex = NetworkEditor.appendIndex(editor.index, this.index, len);
        return new NetworkEditor(returnWeights, returnLayers, returnIndex);
    }

    private static double[] appendWeights(double[] first, double[] second)
    {
        double[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    private static int[] appendIndex(int[] first, int[] second, int plus)
    {
        int[] anotherSecond = new int[second.length];
        for (int i = 0; i < second.length; i++)
        {
            anotherSecond[i] = second[i] + plus;
        }
        int[] result = Arrays.copyOf(first, first.length + anotherSecond.length - 1);
        System.arraycopy(anotherSecond, 1, result, first.length, anotherSecond.length - 1);
        return result;
    }

    private static int[] minus(int[] numbers, int minus)
    {
        int[] returnNumbers = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++)
        {
            returnNumbers[i] = numbers[i] - minus;
        }
        return returnNumbers;
    }

   /**
    * Returns a textual representation of the NetworkEditor.
    * @return A string in the form [aWeight, aWeight...][aLayer, aLayer...][anIndex, anIndex...].
    */
    @Override
    public String toString()
    {
        return Arrays.toString(this.weights) + Arrays.toString(this.layers) + Arrays.toString(this.index);
    }

  /**
    * Creates a network from the textual representation passed to it.
    * @param buildString A string in the form [aWeight, aWeight...][aLayer, aLayer...][anIndex, anIndex...].
    */
    static BasicNetwork fromString(String buildString)
    {
        int postion;
        String sub;
        double[] weights;
        int[] layers;
        int[] index;

        postion = LevelStructure.nextParentheses(buildString);
        sub = buildString.substring(1, postion);
        weights = LevelStructure.toDoubleArray(sub.split(", "));
        buildString = buildString.substring(postion + 1);

        postion = LevelStructure.nextParentheses(buildString);
        sub = buildString.substring(1, postion);
        layers = LevelStructure.toIntArray(sub.split(", "));
        buildString = buildString.substring(postion + 1);

        postion = LevelStructure.nextParentheses(buildString);
        sub = buildString.substring(1, postion);
        index = LevelStructure.toIntArray(sub.split(", "));

        return new NetworkEditor(weights, layers, index).buildNetwork().getNetwork();
    }
}
