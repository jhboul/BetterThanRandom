package btr;

/**
 * Records a Goal.
 */
public class Action extends Construct
{

   /**
    * Constructor for objects of class Action.
    * Sets content to someContent.
    * @param someContent the content
    */
   Action(double[] someContent)
   {
      super(someContent);
   }

   /**
    * Constructor for objects of class Action.
    * Sets content to aConstruct.
    * @param aConstruct Construct to be cloned
    */
   Action(Construct aConstruct)
   {
      super(aConstruct);
   }

   /**
    * Constructor for objects of class Action.
    * Sets content to the array represented by a string.
    * @param stringContent a string in the form "aDouble, aDouble..."
    */
   Action(String stringContent)
   {
      super(stringContent);
   }

   /**
   * Returns a Action of length.
   * Each value is a random number between 0-0.999.
   */
  static Action randomContent(int length)
  {
      return (Action)Construct.randomContent(length);
  }
}