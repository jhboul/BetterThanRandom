package btr;

/**
 * Records a Goal.
 */
public class Goal extends Construct
{

   /**
    * Constructor for objects of class Goal.
    * Sets content to someContent.
    * @param someContent the content
    */
   Goal(double[] someContent)
   {
      super(someContent);
   }

   /**
    * Constructor for objects of class Goal.
    * Sets content to aConstruct.
    * @param aConstruct Construct to be cloned
    */
   Goal(Construct aConstruct)
   {
      super(aConstruct);
   }

   /**
    * Constructor for objects of class Goal.
    * Sets content to the array represented by a string.
    * @param stringContent a string in the form "aDouble, aDouble..."
    */
   Goal(String stringContent)
   {
      super(stringContent);
   }

   /**
   * Returns a Goal of length.
   * Each value is a random number between 0-0.999.
   */
  static Goal randomContent(int length)
  {
      return (Goal)Construct.randomContent(length);
  }
}