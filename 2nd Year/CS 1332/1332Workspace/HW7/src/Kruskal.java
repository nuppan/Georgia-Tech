import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
/**
 * The Kruskal class represents an object that will run the Kruskal algorithm given an
 * edgeList of a graph to find the minimum spanning tree.
 * 
 * I worked on this assignment alone using only the resources provided to me in this
 * year's 2011 CS1332 resources.
 * @author Joon Ki Hong
 * CS 1332 A4
 */
public class Kruskal {

	/**
	 * The kruskal static method runs the Kruskal algorithm given an EdgeList. This in effect
	 * returns the minimum spanning tree of a graph (MST).
	 * @param edgeList -The collection of edges for a graph.
	 * @return -The Collection representing the minimum spanning tree.
	 */
	public static Collection<Edge> kruskal(EdgeList edgeList) {
		ArrayList<Edge> mST = new ArrayList<Edge>();
		UnionFind dataStruct = new UnionFind(edgeList);
		PriorityQueue<Edge> edges = edgeList.getEdges();
		while (!edges.isEmpty()){
			Edge minEdge = edges.poll();
			boolean condition = dataStruct.sameComponent(minEdge.getU(),minEdge.getV());
			if (condition==false){
				dataStruct.union(minEdge.getU(), minEdge.getV());
				mST.add(minEdge);
			}
		}
		return mST;
	}
	
}
