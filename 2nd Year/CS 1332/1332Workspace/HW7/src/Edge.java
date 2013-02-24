/**
 * The Edge class represents an edge between two vertices in a graph. It implements the 
 * Comparable interface so that it may be properly used in the built-in PriorityQueue class.
 * 
 * I worked on this assignment alone using only the resources provided to me in this
 * year's 2011 CS1332 resources.
 * @author Joon Ki Hong
 * CS 1332 A4
 */
public class Edge implements Comparable<Edge> {
	
	/**
	 * The weight associated to the two vertices.
	 */
	private int weight;
	
	/**
	 * The two vertices at the ends of the edge.
	 */
	private Vertex u, v;
	
	/**
	 * The constructor takes in the two necessary vertices as well as the associated weight
	 * and initializes the instance variables.
	 * @param u -One of the vertices of this edge.
	 * @param v -One of the vertices of this edge.
	 * @param weight -The weight of this edge.
	 */
	public Edge(Vertex u, Vertex v, int weight) {
		this.u = u;
		this.v = v;
		this.weight = weight;
	}
	
	/**
	 * The toString method returns the string representation of the Edge in the specified 
	 * format. *Extra Credit*
	 * @return -The String representation of the Edge.
	 */
	@Override
	public String toString() {
		String result = "(" +u.toString()+ ", " +v.toString()+ ", " +weight+")";
		return result;
	}

	/**
	 * The compareTo method is compares two edges by weight.
	 * @return -the integer describing the comparison between the two edges.
	 */
	@Override
	public int compareTo(Edge that) {
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
	 * The getWeight method acts as a getter for the weight.
	 * @return -The integer describing the weight of the edge.
	 */
	// Provided Methods
	public int getWeight() {
		return weight;
	}

	/**
	 * The getU method acts as a getter for the "u" vertex.
	 * @return -One of the vertices of the edge.
	 */
	public Vertex getU() {
		return u;
	}

	/**
	 * The getV method acts as a getter for the "v" vertex.
	 * @return -One of the vertices of the edge.
	 */
	public Vertex getV() {
		return v;
	}

	/**
	 * The equals method compares two edges, checks whether or not the object is an instance
	 * of Edge and also checks whether or not the two instances are equal.
	 * @return -the boolean value describing whether or not the two instances of Edge are 
	 * equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Edge) {
			Edge e = (Edge) o;
			boolean w = weight == e.weight;
			if (u.equals(e.u) && v.equals(e.v)) {
				return w;
			} else if (u.equals(e.v) && v.equals(e.u)) {
				return w;
			} else {
				return false;
			}
		} else { 
			return false;
		}
	}
	
	/**
	 * The hashCode method provides a hash code based on two vertices and weight.
	 * @return -the int representation of the hash code.
	 */
	@Override
	public int hashCode() {
		return u.hashCode() ^ v.hashCode() ^ weight;
	}
}
