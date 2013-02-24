import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class represents a "fake" processor for the purposes of HW assignment 6
 * @author Derek Olejnik
 */
public class Processor {
    /** the map of processes and times */
    public Map<Process, Integer> waitingTime = new TreeMap<Process, Integer>();
    
    /**
     * Fills the processes collection
     */
    public Processor() {
        waitingTime.put(new Process("A", 10), 0);
        waitingTime.put(new Process("B", 25), 0);
        waitingTime.put(new Process("C", 30), 0);
        waitingTime.put(new Process("D", 5), 0);
        waitingTime.put(new Process("E", 19), 0);
        waitingTime.put(new Process("F", 1), 0);
        waitingTime.put(new Process("G", 42), 0);
        waitingTime.put(new Process("H", 105), 0);
        waitingTime.put(new Process("I", 37), 0);
        waitingTime.put(new Process("J", 12), 0);
        waitingTime.put(new Process("K", 9), 0);
    }
    
    /**
     * Runs the processor with a given sorting algorithm
     * @param sA - sorting algorithm to run
     */
    public void runProcessor(SortingAlgorithm<Process> sA) {
        System.out.println("Processor initializing Sorting Algorithm...");
        Collection<Process> processes = new HashSet<Process>();
        for (Process process:waitingTime.keySet()) {
            sA.add(process);
            processes.add(process);
        }
        System.out.println("-------------------------------------------");
        
        while (sA.size() > 0) {
            Process currentRunning = sA.remove();
            processes.remove(currentRunning);
            System.out.println("Running Process: " + currentRunning.getName() +
                    " for " + currentRunning.getCyclesToComplete() + " cycles");
            
            for (Process otherProcess:processes)
                waitingTime.put(otherProcess, waitingTime.get(otherProcess) + 
                        currentRunning.getCyclesToComplete());
        }
        
        System.out.print("\n");
        System.out.println("-------------------------------------------");
        int totalCyclesWaited = 0;
        System.out.println("Number of cycles each process waited: ");
        for (Process process:waitingTime.keySet()) {
            totalCyclesWaited += waitingTime.get(process);
            System.out.println("Process: " + process.getName() + " waited " +
                    waitingTime.get(process) + " cycles");
            waitingTime.put(process, 0);
        }
        System.out.println("-------------------------------------------");
        System.out.println("Average waiting cycles: " + 
                ((double)totalCyclesWaited)/waitingTime.keySet().size());
    }
    
    /**
     * Runs the processor with each sorting structure
     * @param args - not used
     */
    public static void main(String[] args) {
        Processor proc = new Processor();
        System.out.println("STACK");
        proc.runProcessor(new Stack<Process>());
        System.out.println("\nQUEUE");
        proc.runProcessor(new Queue<Process>());
        System.out.println("\nShortest Job First");
        proc.runProcessor(new MinSort<Process>());
        System.out.println("\nLongest Job First");
        proc.runProcessor(new MaxSort<Process>());
    }
}
