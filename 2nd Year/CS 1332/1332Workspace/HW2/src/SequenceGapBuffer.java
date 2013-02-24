/**
 * 
 * @author Joon Ki Hong CS1332A4 HW02
 *
 * @param <T>
 */
public class SequenceGapBuffer<T> implements GapBuffer {
	
	private CircularArraySequence<T> rightSequence;
	private CircularArraySequence<T> leftSequence;
	private int cursor;
	
	public SequenceGapBuffer(){
		leftSequence = new CircularArraySequence<T>(10);
		rightSequence = new CircularArraySequence<T>(10);
		cursor = 0;
		
	}

	@Override
	public void insert(Object item) {
		leftSequence.append(item);
	}

	@Override
	public void cursorLeft() {
		if (leftSequence.peekLast() == null){

		}
		else{
			T lastElement = (T) leftSequence.last();
			rightSequence.prepend(lastElement);
		}
	}

	@Override
	public void delete() {
		if (leftSequence.isEmpty() == false){
			leftSequence.last();
		}
		
	}

	@Override
	public void cursorRight() {
		if (rightSequence.peekHead() == null){
			
		}
		else{
			T lastElement = (T) rightSequence.head();
			leftSequence.append(lastElement);
		}
	}
	public String toString(){
		String totalString = leftSequence.toString() + "^" + rightSequence.toString();
		return totalString;
	}

}
