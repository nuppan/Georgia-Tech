
public interface GapBuffer<T> {
	/**
	 * append this item to the left sequence of the gap buffer
	 * if left = "acs" and right = "def", then inserting a "p"
	 * would result in left="acsp" and right = "def"
	 * 
	 * @param item the item to add
	 */
	public void insert(T item);
	
	/**
	 * move the cursor left.  This involves removing the last item from 
	 * left and prepending it to right.  
	 * if left = "acs" and right = "def", then moving the cursor left would
	 * result in left = "ac" and right = "sdef".  if cursor already full left, do nothing.
	 */
	public void cursorLeft();
	
	/**
	 * delete the item at the cursor. Basically, we just remove the last
	 * item in the left sequence. 
	 * if left = "acs" and right = "def", then deleting would result in:
	 * left = "ac" and right = "def".  If there is nothing to delete, do nothing.
	 */
	public void delete();
	
	/**
	 * move the cursor right.  This involves removing the first item
	 * from the right and appending it to left.
	 * if left = "acs" and right = "def", then moving cursor right would
	 * result in: left="acsd" and right = "ef".  If cursor already full right,
	 * do nothing.
	 */
	public void cursorRight();
	
}
