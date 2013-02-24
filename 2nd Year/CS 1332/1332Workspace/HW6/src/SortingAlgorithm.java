/**
 * Abstract class representing a sorting algorithm
 * Will be extended by all your sorting algorithm classes
 * 
 * @author Derek Olejnik
 */
public abstract class SortingAlgorithm<T> {    
    /**
     * Adds the object of type <T> onto the sorting algorithm structure
     * @param object - object added to structure
     */
    public abstract void add(T object);
    
    /**
     * Removes the object of type <T> off the sorting algorithm structure
     * @return next object in structure - null if nothing
     */
    public abstract T remove();
    
    /**
     * Returns the size of the structure
     * @return - size of the structure
     */
    public abstract int size();
}
