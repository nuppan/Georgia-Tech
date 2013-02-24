import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
/**
 * The RunProblems class is used to test run the Djikstra and Kruskal algorithm/objects.
 * 
 * I worked on this assignment alone using only the resources provided to me in this
 * year's 2011 CS1332 resources.
 * @author Joon Ki Hong
 * CS 1332 A4
 */
public class RunProblems {

	/**
	 * TEST YOUR CODE here (if you do not implement JUnit tests). This method is for your benefit
	 * and will not be graded.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		runDijkstra(new File("bookgraph.txt"), "v1");
		runDijkstra(new File("debuggraph.txt"),"a");
		runKruskal(new File("bookgraph.txt"));
		runKruskal(new File("debuggraph.txt"));
	}

	/**
	 * Create an adjacency list (using the AdjacencyList constructor) from the given txtFile and run
	 * Dijkstra's algorithm with the given AdjacencyList and the given source vertex
	 * 
	 * @param source
	 * @param txtFile
	 * @return
	 */
	public static Map<Vertex, Path> runDijkstra(File txtFile, String source) throws IOException {
		Vertex startVertex = new Vertex(source);
		AdjacencyList adjList = new AdjacencyList(txtFile);
		Map<Vertex,Path> results = Dijkstra.dijkstra(startVertex, adjList);
		System.out.println("----- Dijkstra");
		System.out.println("----------"+txtFile.getName()+", "+source);
		Set<Entry<Vertex,Path>> entrySet = results.entrySet();
		String pResult = "";
		for (Entry<Vertex,Path> entry:entrySet){
			pResult = pResult + entry.getKey()+": "+entry.getValue()+"\n";
		}
		System.out.println(pResult);
		return results;
	}

	/**
	 * Create an edge list (using the EdgeList constructor) from the given txtFile and run Kruskal's
	 * algorithm with that EdgeList
	 * 
	 * @param txtFile
	 * @return
	 */
	public static Collection<Edge> runKruskal(File txtFile) throws IOException {
		EdgeList edgeList = new EdgeList(txtFile);
		Collection<Edge> results = Kruskal.kruskal(edgeList);
		System.out.println("----- Kruskal");
		System.out.println("---------- "+ txtFile.getName());
		String pResult = "";
		for (Edge edge:results){
			pResult = pResult+edge+"\n";
		}
		System.out.println(pResult);
		return results;
	}
}
