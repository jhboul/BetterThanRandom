package btr;
/**
 * Encapsulates the data inputed into and
 * outputted out of Neural Networks.
 */
class Construct
{
   //instance variables
   private final double[] content;

   /**
    * Constructor for objects of class Construct.
    * Sets content to someContent.
    * @param someContent the content
    */
   Construct(double[] someContent)
   {
      content = someContent;
   }

   /**
    * Constructor for objects of class Construct.
    * Sets content to aConstruct.
    * @param aConstruct Construct to be cloned
    */
   Construct(Construct aConstruct)
   {
      this.content = aConstruct.getContent();
   }

   /**
    * Constructor for objects of class Construct.
    * Sets content to the array represented by a string.
    * @param stringContent a string in the form "aDouble, aDouble..."
    */
   Construct(String stringContent)
   {
      double[] newContent = LevelStructure.toDoubleArray(stringContent.split(", "));
      this.content = newContent;
   }

   /**
    * Returns the array that the Construct encapsulates.
    * @param none
    */
   double[] getContent()
   {
      return this.content.clone();
   }

   /**
    * Appends aConstruct to a clone of itself and returns the result.
    * @param aConstruct to be appended
    * @return a Construct with the content of this object and aConstruct's append.
    */
   Construct append(Construct aConstruct)
   {
      int len1 = this.getLength();
      int len2 = aConstruct.getLength();
      double[] appendArray = new double[len1 + len2];
      System.arraycopy(this.getContent(), 0, appendArray, 0, len1);
      System.arraycopy(aConstruct.getContent(), 0, appendArray, len1, len2);
      return new Construct(appendArray);
   }

   /**
    * Returns the length of thee array that the Construct encapsulates.
    * @param none
    */
  int getLength()
   {
      return this.content.length;
   }

  /**
   * Creates a textual representation of the Construct.
   * @return A string in the form "[aDouble, aDouble...]"
   */
  @Override
  public String toString()
  {
      return this.getContent().toString();
  }

  /**
   * Returns a Construct of length.
   * Each value is a random number between 0-0.999.
   */
  static Construct randomContent(int length)
  {
      double[] someContent = new double[length];
      for(int i = 0; i < length; i++)
      {
          someContent[i] = Math.random();
      }
      return new Construct(someContent);
  }
}