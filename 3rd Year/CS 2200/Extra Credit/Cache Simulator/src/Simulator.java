import java.io.*;
import java.util.*;

public class Simulator {
	
	public static LinkedList<Integer>[] lruArray;
	public static CacheBlock[][] cache;
	public static Integer c;
	public static Integer b;
	public static Integer s;
	public static Integer p;
	public static int numCacheLines;
	public static int numCaches;
	
	//Statistics
	public static int cacheAccesses;
	public static int readHits;
	public static int readMisses;
	public static int writeHits;
	public static int writeMisses;
	public static int prefetchedBlocks;
	
	public static void cacheAccess(char rw, int offset, int tag, int index){
		cacheAccesses++;
		if(rw=='r'){
			boolean hit = false;
			//Check each cache
			for(int i=0;i<numCaches;i++){
				CacheBlock cacheBlock = cache[i][index];
				if(cacheBlock.getTag()==tag && cacheBlock.isValid()){ // We got a read hit on our first byte
					hit = true;
					if(lruArray[index].contains(i)){
						lruArray[index].remove(i);
						lruArray[index].addFirst(i);
					}else{
						lruArray[index].addFirst(i); //Update our LRU for this cache line
					}
					//Setup for overflow checking since we need to read 4 bytes which may be on multiple cache lines
					int bytesInBlock = (int)Math.pow(2, b);
					int overflow = offset+3; //index of last byte we need to read
					int lineIndex = index+1; //index of the new line we should read
					int newTag = tag;
					while(overflow>(bytesInBlock-1)){ //if our overflow index is out of bounds then we know we need to read the next line.
						if(lruArray[index].contains(i)){  //Update LRU for this line
							lruArray[index].remove(i);
							lruArray[index].addFirst(i);
						}else{
							lruArray[index].addFirst(i);
						}
						CacheBlock tempCacheBlock = cache[i][lineIndex];
						if(tempCacheBlock.getTag()!=newTag || !tempCacheBlock.isValid()){ //Check if the line is invalid
							hit = false;
						}
						overflow = overflow-((bytesInBlock-1)+offset); //recalculate overflow index
						lineIndex++;
						if(lineIndex>numCacheLines-1){ //edge case in which we're reading from the last cache line and need the next line.
							lineIndex = 0;
							newTag++;
						}
					}
				}
			}
			if(hit){readHits++;}else{
				readMisses++;
				prefetchedBlocks+=8;
				int setIndex = 0; //which cache to load the values in
				//Check LRU to choose where to load prefetched values
				if(!lruArray[index].isEmpty()){
					setIndex = lruArray[index].removeLast();
				}
				lruArray[index].addFirst(setIndex);
				int tempTag = tag;
				for(int i=0;i<p;i++){ //prefetch the blocks and load them in
					if(index+i>numCacheLines-1){ //if go wrap around the cache while loading prefetched values
						tempTag++;
					}
					cache[setIndex][index+i] = new CacheBlock(0,tempTag,1);
				}
			}	
		}else{ //write
			boolean hit = false;
			//Check each cache
			for(int i=0;i<numCaches;i++){
				CacheBlock cacheBlock = cache[i][index];
				if(cacheBlock.getTag()==tag && cacheBlock.isValid()){ // We got a read hit on our first byte
					hit = true;
					if(lruArray[index].contains(i)){
						lruArray[index].remove(i);
						lruArray[index].addFirst(i);
					}else{
						lruArray[index].addFirst(i); //Update our LRU for this cache line
					}
					//Setup for overflow checking since we need to write 4 bytes which may be to multiple cache lines
					int bytesInBlock = (int)Math.pow(2, b);
					int overflow = offset+3; //index of last byte we need to read
					int lineIndex = index+1; //index of the new line we should read
					int newTag = tag;
					while(overflow>(bytesInBlock-1)){ //if our overflow index is out of bounds then we know we need to read the next line.
						if(lruArray[index].contains(i)){  //Update LRU for this line
							lruArray[index].remove(i);
							lruArray[index].addFirst(i);
						}else{
							lruArray[index].addFirst(i);
						}
						CacheBlock tempCacheBlock = cache[i][lineIndex];
						if(tempCacheBlock.getTag()!=newTag || !tempCacheBlock.isValid()){ //Check if the line is invalid
							hit = false;
						}
						overflow = overflow-((bytesInBlock-1)+offset); //recalculate overflow index
						lineIndex++;
						if(lineIndex>numCacheLines-1){ //edge case in which we're reading from the last cache line and need the next line.
							lineIndex = 0;
							newTag++;
						}
					}
				}
			}
			if(hit){writeHits++;}else{writeMisses++;}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
				
		//Get arguments
		String input_file = args[0]; //name of trace file
		c = Integer.parseInt(args[1]); //total data storage
		b = Integer.parseInt(args[2]); //block size
		s = Integer.parseInt(args[3]); //associativity
		p = Integer.parseInt(args[4]); //pre-fetcher size in blocks
		numCacheLines = (int)Math.pow(2, c) / (int)( Math.pow(2, b)*Math.pow(2, s) ); //number of cache lines
		numCaches = (int)Math.pow(2, s);
		
		//Initialize cache ArrayList
		cache = new CacheBlock[numCaches][numCacheLines];
		for(int i=0;i<numCaches;i++){
			for(int x=0;x<numCacheLines;x++){
				cache[i][x] = new CacheBlock();
			}
		}
		
		//Initialize LRU array

		lruArray = new LinkedList[numCacheLines];
		for(int i=0;i<numCacheLines;i++){
			lruArray[i] = new LinkedList<Integer>();
		}
		
		//Initialize statistics variables
		cacheAccesses = 0;
		readHits = 0;
		readMisses = 0;
		writeHits = 0;
		writeMisses = 0;
		prefetchedBlocks = 0;

		//End statistic initialization

		File trace_file = new File(input_file);
		try {
			Scanner scan = new Scanner(trace_file);
			int cacheLineBits = (int)(Math.log10(s)/Math.log10(2));
			String nextLine;
			int address;
			char rw;
			int offset;
			int offsetMask = ~((~0>>b)<<b);
			int tag;
			int tagMask = ~0<<(b+cacheLineBits);
			int index;
			int indexMask = ~(offsetMask|tagMask);

			
			while(scan.hasNextLine()){
				nextLine = scan.nextLine();
				rw = nextLine.charAt(0);
				address = Integer.parseInt(nextLine.substring(2),16);
				
				offset = address&offsetMask;
				tag = (address&tagMask)>>(b+cacheLineBits);
				index = (address&indexMask)>>(b);
			
				cacheAccess(rw,offset,tag,index);	
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			e.printStackTrace();
		}
		System.out.println("Cache accesses: "+cacheAccesses);
		System.out.println("Read hits: "+readHits);
		System.out.println("Read misses: "+readMisses);
		System.out.println("Write hits: "+writeHits);
		System.out.println("Write misses: "+writeMisses);
	
		
	
	}
}
