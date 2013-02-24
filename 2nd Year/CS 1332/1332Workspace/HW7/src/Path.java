import java.util.LinkedList;

/**
 * The Path class represents a collection of edges that make up the shortest path from a source node to
 * the end node. This class is specifically written for the Dijkstra class.
 * 
 * I worked on this assignment alone using only the resources provided to me in this
 * year's 2011 CS1332 resources.
 * @author Joon Ki Hong
 * CS 1332 A4
 */
public class Path implements Comparable<Path> {

	/**
	 * The cumulative weight of the path.
	 */
	private int weight;
	
	/**
	 * The first and last vertices of the path.
	 */
	private Vertex start, end;

	/**
	 * The collection of edges that represent the path.
	 */
	private LinkedList<Edge> edges;

	/**
	 * Create an initial path where start and end are the given vertex
	 * 
	 * @param vertex
	 *            a vertex
	 */
	public Path(Vertex vertex) {
		start = vertex;
		end = vertex;
		edges = new LinkedList<Edge>();
		weight = 0;
	}

	/**
	 * Assume that one of the vertices (either u or v) in the edge is the end vertex of old. Copies
	 * the old path and adds the edge e. (Make sure to update the end vertex, and weight correctly)
	 * 
	 * @param old
	 *            a path that ends in u or v
	 * @param e
	 *            an edge between vertices u and v
	 */
	public Path(Path old, Edge e) {
		LinkedList<Edge> edges = new LinkedList<Edge>(old.edges);
		edges.add(e);
		this.start = edges.getFirst().getU();
		this.end = e.getV();
		this.edges = edges;
		for (int i=0;i<this.edges.size();i++){
			weight += this.edges.get(i).getWeight();
		}
	}

	/**
	 * The compareTo method compares the cumulative weights of two Path objects
	 * @return -The integer representing the comparison between the two Path objects.
	 */
	@Override
	public int compareTo(Path that) {
		int result = 0;
		if (this.weight<that.weight){
			result = -1;
		}
		else if (this.weight>that.weight){
			result = 1;
		}
		return result;
	}
	
	/**
	 * The equals method compares two Path objects and determines whether they are equal by weight and
	 * instances of the start and end vertices.
	 * @param path -The Path object being compared.
	 * @return -The boolean value describing the two objects' equality.
	 */
	public boolean equals(Path path){
		if (path.start.equals(this.start) && path.end.equals(this.end) && path.weight==this.weight){
			return true;
		}
		return false;
	}
	
	/**
	 * The toString method returns the string representation of the path. *Extra Credit*
	 * @return -The String representation of the path in the specified format.
	 */
	@Override
	public String toString() {
		String result = "";
		if (start==end){
			result = "["+weight+"] ("+start+")";
		}
		else{
			result = "["+weight+"]";
			for(Edge edge:edges){
				result = result+" ("+edge.getU()+", "+edge.getV()+", "+edge.getWeight()+")";
			}
		}
		return result;
	}

	/**
	 * The getWeight method acts as a getter for the weight of the path.
	 * @return -The integer representing the weight of the path.
	 */
	// Provided Methods
	public int getWeight() {
		return weight;
	}
	
	/**
	 * The getStart method acts as a getter for the starting (first) vertex of the path.
	 * @return -The Vertex object that is first in the path.
	 */
	public Vertex getStart() {
		return start;
	}
	
	/**
	 * The getEnd method acts as a getter for the ending (last) vertex of the path.
	 * @return -The Vertex object that is last in the path.
	 */
	public Vertex getEnd() {
		return end;
	}
	
	/**
	 * The getEdges method acts as a getter for the collection of edges (ArrayList).
	 * @return -The ArrayList of edges in the path.
	 */
	public LinkedList<Edge> getEdges() {
		return edges;
	}
}
