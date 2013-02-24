import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
/**
 * The AdjacencyList class represents the adjacency list representation of a graph. The class takes in a File
 * object and parses it for two vertices and a weight associated with those vertices per line. 
 * 
 * I worked on this assignment alone using only the resources provided to me in this
 * year's 2011 CS1332 resources.
 * @author Joon Ki Hong
 * CS 1332 A4
 */
public class AdjacencyList {

	/**
	 * This is a map (Hash Table) of vertices to their adjacent vertices and the weight associated with that edge
	 */
	private HashMap<Vertex, Map<Vertex, Integer>> adjList;
	
	
	/**
	 * The constructor takes in a File object and parses for the information pertaining to the graph and the 
	 * adjacencies related to each pair of vertices. It then initializes a HashMap<Vertex, Map<Vertex,Integer>>
	 * in which each Vertex maps to another Map (HashMap) which contains the paths and associated weights to the
	 * source node.
	 * @param txtFile -The source .txt file that contains the vertices/weights and adjacencies
	 * @throws IOException
	 */
	public AdjacencyList(File txtFile) throws IOException {
		adjList = new HashMap<Vertex, Map<Vertex, Integer>>();
		Scanner scanner = new Scanner(txtFile);
		while (scanner.hasNext()){
			String currentLine = scanner.nextLine();
			Scanner scannerIn = new Scanner(currentLine); 
			if (currentLine.length()>1){
				Vertex vertex1 = new Vertex(scannerIn.next());
				Vertex vertex2 = new Vertex(scannerIn.next());
				int weight = Integer.parseInt(scannerIn.next());
				if (!adjList.containsKey(vertex1)){
					adjList.put(vertex1, new HashMap<Vertex,Integer>());
				}
				if (!adjList.containsKey(vertex2)){
					adjList.put(vertex2, new HashMap<Vertex,Integer>());
				}
				adjList.get(vertex1).put(vertex2, weight);
				adjList.get(vertex2).put(vertex1, weight);
			}
		}
	}
	
	/**
	 * The adjVertices class returns the set of Entries that essentially contain
	 * the values of the above Hash Map. It is returned as a Set so that it 
	 * remains iterable for future conveniences.
	 * @param last -The reference vertex in which all adjacencies are found.
	 * @return -The Collection (Set) of Entry objects that contains vertices and
	 * associated weights. 
	 */
	public Set<Entry<Vertex,Integer>> adjVertices(Vertex last){
		return adjList.get(last).entrySet();
	}

	/**
	 * The toString method returns the String representation of the adjacency 
	 * list. *Extra Credit*
	 * @return -The String representation of the adjacency list.
	 */
	@Override
	public String toString() {
		String result = "";
		Set<Entry<Vertex,Map<Vertex,Integer>>> set = adjList.entrySet();
		for (Entry<Vertex,Map<Vertex,Integer>> entry:set){
			result = result+entry.getKey()+":";
			Map<Vertex,Integer> map = entry.getValue();
			Set<Entry<Vertex,Integer>> adjVert = map.entrySet();
			for (Entry<Vertex,Integer> vEntry:adjVert){
				result = result+" ("+vEntry.getKey()+", "+vEntry.getValue()+")";
			}
			result = result+"\n";
		}
		return result;
	}
	
	/**
	 * The getAdjList returns the Hash Map for this instance of the AdjacencyList.
	 * @return -The Hash Map associated with this instance of an AdjacencyList.
	 */
	// Provided Methods
	public Map<Vertex, Map<Vertex, Integer>> getAdjList() {
		return adjList;
	}
}
