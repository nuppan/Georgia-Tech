
/**
 * This is HW4, Die
 * Joon Ki Hong - xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Die class was originally provided for this assignment and was originally written by Lewis/Loftus.
 * The Die class provides the basic attributes and methods of a die to be used in the Player class and
 * RaceToAMillion class.
 *
 *********************************************************************
 *  Die.java       @author Lewis/Loftus      @version 2/17/11
 *
 *  Represents one die (singular of dice) with faces showing values
 *  between 1 and 6.
 *********************************************************************
 */

public class Die
{
   private final int MAX = 6;  // maximum face value

   private int faceValue;  // current value showing on the die

   /**-----------------------------------------------------------------
   *  Constructor: Sets the initial face value.
   *-----------------------------------------------------------------
   */
   public Die()
   {
      faceValue = 1;
   }

 
   /**-----------------------------------------------------------------
    *  Rolls the die and returns the result.
    *  @return The new face value
    *-----------------------------------------------------------------
    */
   public int roll()
   {
      faceValue = (int)(Math.random() * MAX) + 1;

      return faceValue;
   }


   /**-----------------------------------------------------------------
    *  Face value mutator.
    *-----------------------------------------------------------------
    */
   public void setFaceValue (int value)
   {
      faceValue = value;
   }

  
   /**-----------------------------------------------------------------
    *  Face value accessor.
    *  @return The current face value
    *-----------------------------------------------------------------
    */
   public int getFaceValue()
   {
      return faceValue;
   }

   /**-----------------------------------------------------------------
    *  Returns a string representation of this die.
    *  @return The string representation of this die
    *-----------------------------------------------------------------
    */
   public String toString()
   {
      String result = Integer.toString(faceValue);

      return result;
   }
}
