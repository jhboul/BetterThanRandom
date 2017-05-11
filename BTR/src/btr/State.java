package btr;

/**
 * Records a state of the environment.
 */
public class State extends Construct
{

   /**
    * Constructor for objects of class State.
    * Sets content to someContent.
    * @param someContent the content
    */
   State(double[] someContent)
   {
      super(someContent);
   }

   /**
    * Constructor for objects of class State.
    * Sets content to aConstruct.
    * @param aConstruct Construct to be cloned
    */
   State(Construct aConstruct)
   {
      super(aConstruct);
   }

   /**
    * Constructor for objects of class State.
    * Sets content to the array represented by a string.
    * @param stringContent a string in the form "aDouble, aDouble..."
    */
   State(String stringContent)
   {
      super(stringContent);
   }

   /**
   * Returns a State of length.
   * Each value is a random number between 0-0.999.
   */
  static State randomContent(int length)
  {
      return (State)Construct.randomContent(length);
  }
}
