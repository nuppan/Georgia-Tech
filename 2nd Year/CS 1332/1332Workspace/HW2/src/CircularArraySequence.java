import java.util.Iterator;
/**
 * 
 * @author Joon Ki Hong CS1332A4 HW02
 *
 * @param <T>
 */

public class CircularArraySequence<T> implements Sequence{
	
	protected T[] backingArray;;
	protected int front;
	protected int rear;
	protected int fillCount;

	public CircularArraySequence(int size){
		backingArray = (T[]) new Object[size];
		fillCount = 0;
		rear = 0;
		front = 0;
	}
	
	public CircularArraySequence(CircularArraySequence<T> seq){
		backingArray = seq.backingArray.clone();
		fillCount = seq.fillCount;
		rear = seq.rear;
		front = seq.front;
	}

	@Override
	public Object head() {
		CircularArraySequence<T> tempSequence = new CircularArraySequence<T>(this);
		T firstObj = null;
		if (front == 0 || backingArray[front]==null){
			if (backingArray[front]==null){
				firstObj = null;
			}
			else{
				firstObj = tempSequence.backingArray[front];
				backingArray[front] = null;
				front+=1;
				fillCount-=1;	
			}
		}
		else if (front == backingArray.length-1){
			firstObj = tempSequence.backingArray[backingArray.length-1];
			backingArray[backingArray.length-1] = null;
			front = 0;
			fillCount-=1;
		}
		else{
			firstObj = tempSequence.backingArray[front];
			backingArray[front] = null;
			front+=1;
			fillCount-=1;
		}
		return firstObj;
		
	}

	@Override
	public Object last() {
		CircularArraySequence<T> tempSequence = new CircularArraySequence<T>(this);
		T lastObj;
		if (rear == 0){
			if (tempSequence.backingArray[tempSequence.backingArray.length-1]==null && tempSequence.backingArray[tempSequence.rear] != null){
				lastObj = tempSequence.backingArray[tempSequence.rear];
				backingArray[rear] = null;
				fillCount-=1;
				rear = 0;
			}
			else if(tempSequence.backingArray[tempSequence.backingArray.length-1]==null && tempSequence.backingArray[tempSequence.rear] == null){
				lastObj = null;
			}
			else{
				lastObj = tempSequence.backingArray[tempSequence.backingArray.length-1];
				backingArray[backingArray.length-1] = null;
				fillCount-=1;
				rear = tempSequence.backingArray.length-1;
			}
		}
		else{
			lastObj = (T) tempSequence.backingArray[tempSequence.rear-1];
			backingArray[rear-1] = null;
			fillCount-=1;
			rear-=1;
		}
		return lastObj;
	}

	@Override
	public Sequence front() {
		CircularArraySequence<T> tempSequence = new CircularArraySequence<T>(this);
		boolean halt = false;
		int index = front;
		while (halt == false){
			if (index == backingArray.length){
				index = 0;
			}
			if (index == rear){
				halt = true;
			}
			if(backingArray[index] == null){
				if (index == 0){
					tempSequence.backingArray[backingArray.length-1] = null;
					tempSequence.fillCount -=1;
					tempSequence.rear = tempSequence.backingArray.length-1;
				}
				else{
					tempSequence.backingArray[index-1] = null;
					tempSequence.fillCount-=1;
					tempSequence.rear-=1;
				}
			}
			index+=1;
			
		}
		return tempSequence;
	}

	@Override
	public Sequence tail() {
		CircularArraySequence<T> tempSequence = new CircularArraySequence<T>(this);
		if (backingArray[front]!=null){
			tempSequence.backingArray[front] = null;
			tempSequence.fillCount-=1;
			if (front == backingArray.length-1){
				tempSequence.front = 0;
			}
			else{
				tempSequence.front+=1;
			}
		}
		
		
		return tempSequence;
	}

	@Override
	public Sequence<T> concat(Sequence seq) {
		CircularArraySequence<T> sequence1 = new CircularArraySequence<T>(this);
		CircularArraySequence<T> sequence2 = new CircularArraySequence<T>((CircularArraySequence<T>)seq);
		boolean halt = false;
		int index = sequence2.front;
		while (halt == false){
			if (index == backingArray.length){
				index = 0;
			}
			if (index == sequence2.rear){
				halt = true;
			}
			else{
				sequence1.append(sequence2.backingArray[index]);
				index += 1;
			}
			
		}
		return sequence1;
	}

	@Override
	public boolean contains(Sequence subseq) {
		CircularArraySequence<T> subSeq = new CircularArraySequence<T>((CircularArraySequence<T>)subseq);
		boolean halt = false;
		int indexSubSeq = subSeq.front;
		int indexThis = front;
		while (halt == false){
			if (indexThis == backingArray.length){
				indexThis = 0;
			}
			if (indexThis == rear){
				halt = true;
			}
			else{
				if (backingArray[indexThis].equals(subSeq.backingArray[subSeq.front])){
					boolean halt2 = false;
					int indexThis2 = indexThis +1;
					int indexSubSeq2 = indexSubSeq+1;
					while (halt2 == false){
						if (indexThis2 == backingArray.length){
							indexThis2 = 0;
						}
						if (indexThis2 == subSeq.rear){
							return true;
						}
						else{
							if (backingArray[indexThis2].equals(subSeq.backingArray[indexSubSeq2])){
								indexThis2++;
								indexSubSeq2++;
							}
							else{
								halt2 = true;
							}
						}
					}
					
				}
				else{
					indexThis += 1;
				}
			}
		}
		return false;
	}

	@Override
	public boolean contains(Object element) {
		boolean halt = false;
		int index = front;
		while (halt == false){
			if (index == backingArray.length){
				index = 0;
			}
			if (index == rear){
				halt = true;
			}
			else{
				if (backingArray[index].equals(element)){
					return true;
				}
				else{
					index += 1;
				}
			}
		}
		return false;
	}
	private boolean fullCheck(){
		if (fillCount == backingArray.length){
			return true;
		}
		return false;
	}

	@Override
	public void append(Object element) {
		if (backingArray[rear] != null && rear!=backingArray.length-1){
			backingArray[rear+1] = (T)element;
			rear+=1;
			fillCount +=1;
			if (fullCheck()==true){
				regrow();
			}
			
		}
		else if(backingArray[rear] !=null && rear==backingArray.length-1){
			rear = 0;
			backingArray[rear] = (T)element;
			fillCount+=1;
			if (fullCheck()==true){
				regrow();
			}
		}
		else if(backingArray[rear] == null){
			backingArray[rear] = (T)element;
			rear+=1;
			fillCount+=1;
			if (fullCheck()==true){
				regrow();
			}
		}

	}

	@Override
	public void prepend(Object element) {
		if (backingArray[front] != null && front == 0){
			backingArray[backingArray.length-1] = (T)element;
			front = backingArray.length-1;
			fillCount+=1;
			if (fullCheck()==true){
				regrow();
			}
		}
		else if(backingArray[front] == null){
			backingArray[front] = (T)element;
			fillCount +=1;
			rear+=1;
			if (fullCheck()==true){
				regrow();
			}
		}
		else if(backingArray[front] != null){
			backingArray[front-1] = (T)element;
			front-=1;
			fillCount+=1;
			if (fullCheck()==true){
				regrow();
			}
		}
		
	}

	@Override
	public boolean isEmpty() {
		if (fillCount>0){
			return false;
		}
		return true;
	}

	@Override
	public Object peekHead() {
		
		return backingArray[front];
	}

	@Override
	public Object peekLast() {
		if (rear==0){
			if (backingArray[backingArray.length-1]==null){
				return backingArray[rear];
			}
			else{
				return backingArray[backingArray.length-1];
			}
		}
		return backingArray[rear-1];
	}

	@Override
	public boolean equals(Sequence seq) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int length() {
		
		return fillCount;
	}

	@Override
	public Iterator<T> iterator() {
		SequenceIterator<T> iterator = new SequenceIterator<T>(this);
		return iterator;
		// TODO Auto-generated method stub
	}
	
	private void regrow(){
		T[] tempArray = (T[])new Object[backingArray.length*2];
		for (int i=0;i<backingArray.length;i++){
			tempArray[i] = backingArray[i];
		}
		
		int tempfillCount = fillCount;
		int index = front;
		int newIndex = 0;
		while (tempfillCount!=0){
			if (index == backingArray.length){
				index = 0;
			}
			
			tempArray[newIndex]=backingArray[index];
			index += 1;
			tempfillCount-=1;
			
			
		}
		front = 0;
		rear = fillCount;
		backingArray = tempArray;
	}
	
	public String toString(){
		String stringSeq = "";
		boolean halt = false;
		int index = front;
		while (halt == false){
			if (index == backingArray.length){
				index = 0;
			}
			if (index == rear){
				halt = true;
			}
			else{
				stringSeq = stringSeq + backingArray[index].toString();
				index += 1;
			}
			
		}
		return stringSeq;
	}

}
