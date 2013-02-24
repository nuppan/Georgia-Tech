/**
 * Represents a process in the theoretical processor
 * @author Derek Olejnik
 */
public class Process implements Comparable<Process> {
    /** the name of this process */
    private String name;
	
	/** the number of cycles or steps to run */
    private int cyclesToComplete; 
     
    /**
     * Creates a process with a run-time of one cycle
     * @name - name of the process
     */
    public Process(String name) {
        this(name, 1);
    }
    
    /**
     * Creates a process with a run-time of "cycles" cycles
     * @param name - name of the process
     * @param cycles - # of cycles to run
     */
    public Process(String name, int cycles) {
        this.name = name;
        this.cyclesToComplete = cycles;
    }
    
    /**
     * Runs the process for a cycles
     * @throws NoCyclesLeftException - If there are no cycles remaining
     */
    public void runForCycle() throws NoCyclesLeftException {
        if (cyclesToComplete-- <= 0)
            throw new NoCyclesLeftException(this);
    }
    
    /**
     * Returns the number of cycles to complete
     * @return - # of cycles left
     */
    public int getCyclesToComplete() {
        return cyclesToComplete;
    }
    
    /**
     * Gets the name of the process
     * @return - name of the process
     */
    public String getName() {
        return name;
    }

    /**
     * Used to extend comparable (by default, compare by name)
     * @param o - process to compare to
     * @return int - comparison value
     */
    public int compareTo(Process o) {
        return name.compareTo(o.name);
    }
    
    /**
     * For inner process testing only
     * @param args - not used
     */
    public static void main(String[] args) {
        Process p1 = new Process("p1", 10);
        Process p2 = new Process("p2", 15);
        for (int i=0; i<12; i++) {
            try {
                p1.runForCycle();
            } catch (NoCyclesLeftException e) {
                e.printStackTrace();
            }
            try {
                p2.runForCycle();
            } catch (NoCyclesLeftException e) {
                e.printStackTrace();
            }
            System.out.println(p1.getCyclesToComplete());
            System.out.println(p2.getCyclesToComplete());
        }
    }
}

class NoCyclesLeftException extends Exception {
    private static final long serialVersionUID = 2953557139160540199L;

    /**
     * Generates an exception message stressing there are no cycles remaining
     * @param p - Process this exception refers to
     */
    public NoCyclesLeftException(Process p) {
        super(p.getName() + " has no remaining cycles");
    }
}