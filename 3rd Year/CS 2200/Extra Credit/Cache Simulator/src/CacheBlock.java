
public class CacheBlock {
	private int dirty_bit;
	private int tag;
	private int valid_bit;
	
	//default constructor
	public CacheBlock(){
		dirty_bit = 0;
		tag = 0;
		valid_bit = 0;
	}
	//secondary constructor
	public CacheBlock(int dirty, int tag,int valid){
		dirty_bit = dirty;
		this.tag = tag;
		valid_bit = valid;
	}
	
	//getters and setters
	public int getTag(){
		return tag;
	}
	
	public boolean isDirty(){
		if(dirty_bit==1){
			return true;
		}
		return false;
	}
	
	public void setTag(int tag){
		this.tag = tag;
	}
	
	public void setDirty(int dirty){
		dirty_bit = dirty;
	}
	
	public void setValid(int valid){
		valid_bit = valid;
	}
	
	public boolean isValid(){
		if(valid_bit==1){
			return true;
		}
		return false;
	}
}
