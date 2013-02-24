import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * The EdgeList class as as a collection class that holds Edges in a Priority Queue.
 * 
 * I worked on this assignment alone using only the resources provided to me in this
 * year's 2011 CS1332 resources.
 * @author Joon Ki Hong
 * CS 1332 A4
 */
public class EdgeList {
	
	/**
	 * The Collection object that holds the 'list' of edges.
	 */
	private PriorityQueue<Edge> edges;
	
	/**
	 * The constructor takes in a File object and parses the file for information regarding
	 * the edges. It then initializes the PriorityQueue instance variable.
	 * @param txtFile -The File object that contains the information necessary to create the list of edges.
	 * @throws IOException
	 */
	public EdgeList(File txtFile) throws IOException {
		edges = new PriorityQueue<Edge>();
		Scanner scanner = new Scanner(txtFile);
		while (scanner.hasNext()){
			String currentLine = scanner.nextLine();
			Scanner scannerIn = new Scanner(currentLine); 
			if (currentLine.length()>1){
				Vertex vertex1 = new Vertex(scannerIn.next());
				Vertex vertex2 = new Vertex(scannerIn.next());
				int weight = Integer.parseInt(scannerIn.next());
				edges.add(new Edge(vertex1,vertex2,weight));
			}
		}

	}
	
	/**
	 * The toString method returns the string representation of the the EdgeList.
	 * *Extra Credit*
	 * @return -The String representation of the EdgeList.
	 */
	@Override
	public String toString() {
		String result = "";
		for (Edge edge:edges){
			result = result+edge+"\n";
		}
		return result;
	}
	
	/**
	 * The getEdges method returns the PriorityQueue of edges.
	 * @return -The Collection of edges (Priority Queue).
	 */
	// Provided Methods
	public PriorityQueue<Edge> getEdges() {
		return edges;
	}
}
