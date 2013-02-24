import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
/**
 * The Dijkstra class provides the static method for the Dijkstra algorithm given a 
 * starting "node" as well as an adjacency list representation of a graph.
 * 
 * I worked on this assignment alone using only the resources provided to me in this
 * year's 2011 CS1332 resources.
 * @author Joon Ki Hong
 * CS 1332 A4
 */
public class Dijkstra {
	
	/**
	 * The dijkstra static method finds the shortest paths from all vertices to the
	 * given starting node given adjacency list of a graph.
	 * @param source -The starting "node"
	 * @param adjList -The adjacency list representation of the graph.
	 * @return -A Map<Vertex,Path> containing the shortest paths to the source node.
	 */
	public static Map<Vertex, Path> dijkstra(Vertex source, AdjacencyList adjList) {
		HashMap<Vertex,Path> returnMap = new HashMap<Vertex,Path>();
		ArrayList<Vertex> visitedList = new ArrayList<Vertex>();
		PriorityQueue<Path> pQueue = new PriorityQueue<Path>();
		pQueue.add(new Path(source));
		while (!pQueue.isEmpty()){
			Path curr = pQueue.poll();
			if (!visitedList.contains(curr.getEnd())){
				visitedList.add(curr.getEnd());
				returnMap.put(curr.getEnd(),curr);
				for (Entry<Vertex,Integer> vertex: adjList.adjVertices(curr.getEnd())){
					Edge edge = new Edge(curr.getEnd(),vertex.getKey(), vertex.getValue());
					Path path = new Path(curr,edge);
					pQueue.add(path);
				}
			}
		}
		return returnMap;
	}
}
