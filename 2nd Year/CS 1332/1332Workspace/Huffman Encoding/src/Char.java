/**
 * The Char class essentially represents a char but also holds the encoding information
 * of it as well
 * @author Joon Ki Hong
 */
public class Char {
	/**
	 * The character the object is representing.
	 */
	private char character;
	/**
	 * The encoding of the character.
	 */
	private String encoding;
	
	/**
	 * The constructor takes in a character and initializes the encoding to an empty
	 * string.
	 * @param character -The characer the object is representing.
	 */
	public Char(char character){
		this.character = character;
		encoding = "";
	}
	/**
	 * The getChar method returns the character.
	 * @return -The characet of the Char object.
	 */
	public char getChar(){
		return character;
	}
	/**
	 * The setEncoding method takes in a String and sets it as the encoding.
	 * @param encoding -The String representing the encoding of the character.
	 */
	public void setEncoding(String encoding){
		this.encoding = encoding;
	}
	/**
	 * The getEncoding method returns the encoding of the character.
	 * @return -The encoding of the Char object.
	 */
	public String getEncoding(){
		return encoding;
	}
}
