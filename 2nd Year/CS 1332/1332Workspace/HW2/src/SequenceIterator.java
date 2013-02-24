import java.util.Iterator;

/**
 * 
 * @author Joon Ki Hong CS1332A4 HW02
 *
 * @param <T>
 */
public class SequenceIterator<T> implements Iterator<T>{

	CircularArraySequence<T> sequence;
	private int currentIndex;
	
	
	public SequenceIterator(CircularArraySequence<T> sequence)
	{
		this.sequence = sequence;
		currentIndex = sequence.front;
	}
	@Override
	public boolean hasNext() {
		if (currentIndex==sequence.front){
			if (sequence.backingArray[currentIndex]==null){
				return false;
			}
			else{
				return true;
			}
		}
		if (sequence.backingArray[currentIndex+1]==null){
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public T next() {
		int tempIndex = currentIndex;
		if (currentIndex==sequence.front){
			currentIndex+=1;
			return (sequence.backingArray[tempIndex]);
		}
		else if (currentIndex == sequence.backingArray.length-1){
			currentIndex = 0;
			return (sequence.backingArray[0]);
			
		}
		else{
			currentIndex+=1;
			return (sequence.backingArray[tempIndex]);
		}
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
