import java.util.Iterator;

/**
 * A sequence data structure based on he Zed/Math idea of a sequence
 * @author watersr
 *
 */
public interface Sequence<T> extends Iterable<T>{
	/**
	 * Remove and return the first element of this sequence.  The current 
	 * sequence is modified.  If the sequence is <a b c d> then head() would
	 * return a, and the sequence would be <b c d>
	 * 
	 * @return the first element in the sequence, null if empty
	 */
	 T head();
	 
	 /**
	  * Remove and return the last element of a sequence. The current sequence is
	  * modified.  If the sequence is <a b c d>, then last() would return d and the
	  * sequence would be <a b c>
	  * 
	  * @return the last element in the sequence, null if empty
	  */
	 T last();
	
	 /**
	  * Returns all but the last element of a sequence.  The current sequence is
	  * unmodified.  If the sequence is <a b c d>, then front() would return <a b c> and the
	  * original sequence would still be <a b c d>
	  * 
	  * @return a sequence of all but last element, empty sequence if empty
	  */
	 Sequence<T> front();
	 
	 /**
	  * Returns all but the first element of the sequence.  The current 
	  * sequence is unchanged.  If the sequence is <a b c d> then tail() would return
	  * <b c d>, and the original sequence would still be <a b c d>
	  * 
	  * @return a new sequence of all but the first element of this sequence, if
	  *              empty or only one element return the empty sequence
	  */
	 Sequence<T> tail();
	 
	 /**
	  * Concatenate the provided sequence to this sequence and return the 
	  * new sequence.  The original sequence is unchanged.  Extra credit for 
	  * homework 2.  If you don't want to do this, just stub out and return null.
	  * @param seq the seq to concatenate
	  * @return the concatenation of this sequence and the provided one
	  */
	 Sequence<T> concat(Sequence<T> seq);
	 
	 /**
	  * Check for an occurrence of the subseq inside this sequence
	  * Searching will be a later homework.  Not required for homework 2.
	  * Just stub out this function and return true.
	  * 
	  * @param subseq the sequence to check for
	  * @return true if subseq is inside me, false otherwise
	  */
	 boolean contains(Sequence<T> subseq);
	 
	 /**
	  * Check if a single element is in the sequence.  Not required for
	  * homework 2.  Just stub out and return true.
	  * 
	  * @return true if element present, false otherwise
	  */
	 boolean contains(T element);
	 
	 /**
	  * Appends (add to end) the element to this sequence.  The sequence is modified.
	  * If the sequence is <a b c> and we call append(d), then the sequence would 
	  * become <a b c d>
	  * 
	  * @param element the element to append
	  */
	 void append(T element);
	 
	 /**
	  * Prepends (add to front) the element to this sequence.  The sequence is modified.
	  * If the sequence is <a b c> and we call prepend(d), then teh sequence would become
	  * <d a b c>
	  * 
	  * @param element the element to prepend
	  */
	 void prepend(T element);
	 
     /**
      * 
      * @return true if sequence is empty, false otherwise
      */
	 boolean isEmpty();
	 
    /**
     * get the first element without changing the sequence.  If the sequence is
     * <a b c d> and we call peekHead(), the return value would be: a and the sequence
     * would remain <a b c d> 
     * 
     * @return first element in sequence.  sequence is unchanged. null if empty
     */
	T peekHead();
	
    /**
     * get the last element without changing the sequence.  If the sequence is
     * <a b c d> and we call peekLast(), the return value would be: d and the
     * sequence would remain <a b c d>
     * 
     * @return last element in sequence.  sequence is unchanged. null if empty
     */
	T peekLast();
	
	/**
	 * check if one sequence equals another.
	 * To be equal, both sequences must be same length and have identical elements
	 * at each position. Not required for Homework 2.  Just stub out and return true.
	 * @param seq the sequence to check myself against
	 * @return true if I am equal to sequence seq.  false otherwise
	 */
	boolean equals(Sequence<T> seq);
	
	/**
	 * returns a count of the number of elements in the sequence.  Thus if the 
	 * sequence is <a b c d> the length() would return 4.
	 * 
	 * @return the number of elements in the sequence
	 */
	int length();
	
	/**
	 * Request an iterator over this sequence
	 */
	Iterator<T> iterator();
}     
