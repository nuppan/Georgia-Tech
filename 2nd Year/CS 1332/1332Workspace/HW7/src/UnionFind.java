import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
/**
 * The UnionFind class represents a Union-Find data-structure used to help facilitate
 * the Kruskal algorithm/class
 * 
 * I worked on this assignment alone using only the resources provided to me in this
 * year's 2011 CS1332 resources.
 * @author Joon Ki Hong
 * CS 1332 A4
 */
public class UnionFind {
	/**
	 * The primitive int array in which each cell represents a set.
	 */
	int[] indices;
	
	/**
	 * The Map that maps vertices to integers in which each integer
	 * represents the primitive array index to which each vertex corresponds to.
	 */
	Map<Vertex,Integer> vertexMap;
	
	/**
	 * The constructor takes in an EdgeList object which retains all edges up for 
	 * processing and initializes a HashMap as well as the primitive int array based
	 * on the number of unique vertices.
	 * @param edgeList The edgeList that holds all edges necessary for processing.
	 */
	public UnionFind(EdgeList edgeList) {
		vertexMap = new HashMap<Vertex,Integer>();
		PriorityQueue<Edge> edges = edgeList.getEdges();
		int counter = 0;
		for (Edge edge:edges){
			if (!vertexMap.containsKey(edge.getU())){
				vertexMap.put(edge.getU(), counter);
				counter++;
			}
			if (!vertexMap.containsKey(edge.getV())){
				vertexMap.put(edge.getV(), counter);
				counter++;
			}
		}
		indices = new int[counter];
		for (int i=0;i<counter;i++){
			indices[i]=-1;
		}
	}

	/**
	 * Assume that u and v are vertices that were in the edgeList. Determine if they are currently
	 * in the same component of this UnionFind structure
	 * 
	 * @param u
	 *            a vertex
	 * @param v
	 *            a vertex
	 * @return true if u and v are in the same component, false otherwise
	 */
	public boolean sameComponent(Vertex u, Vertex v) {
		int indexU = vertexMap.get(u);
		int indexV = vertexMap.get(v);
		int root1 = indexU;
		int root2 = indexV;
		while (indices[root1]>=0){
			root1 = indices[root1];
		}
		while (indices[root2]>=0){
			root2 = indices[root2];
		}
		if (root1!=root2){
			return false;
		}
		return true;
	}

	/**
	 * Assume that u and v are vertices that were in the edgeList. Assume that u and v are in
	 * different components. Union the component containing u and the component containing v
	 * together.
	 * 
	 * @param u
	 *            a vertex
	 * @param v
	 *            a vertex
	 */
	public void union(Vertex u, Vertex v) {
		int indexU = vertexMap.get(u);
		int indexV = vertexMap.get(v);
		indices[indexV] = indexU;
	}
}
