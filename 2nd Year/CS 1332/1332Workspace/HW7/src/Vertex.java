/**
 * The Vertex class represents a vertex in a graph data structure. The defining feature of
 * the vertex is the name.
 * 
 * I worked on this assignment alone using only the resources provided to me in this
 * year's 2011 CS1332 resources.
 * @author Joon Ki Hong
 * CS 1332 A4
 */
public class Vertex {
	
	/**
	 * The name of the vertex.
	 */
	private String name;

	/**
	 * The constructor takes in a string and initializes it as the name.
	 * @param name -The String representation of the vertex.
	 */
	public Vertex(String name) {
		this.name = name;
	}

	/**
	 * The toString method returns the string representation of the vertex (in this case the name). *Extra Credit*
	 * @return -The name of the vertex.
	 */
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * The getName method returns the name of the vertex.
	 * @return -The name of the vertex.
	 */
	// Provided Methods
	public String getName() {
		return name;
	}
	
	/**
	 * The equals method compares an object with an instance of this object by name.
	 * @return -The boolean value describing the equality of the two objecting being compared.
	 */
	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Vertex) {
			Vertex v = (Vertex) o;
			return name.equals(v.name);
		} else {
			return false;
		}
	}
	
	/**
	 * The hashCode method returns the default hashCode reprentation of the name.
	 * @return -The integer representation of the hash.
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
