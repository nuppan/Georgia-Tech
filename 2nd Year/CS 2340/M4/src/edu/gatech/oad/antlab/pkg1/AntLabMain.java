package edu.gatech.oad.antlab.pkg1;

//if you get an error on this import
//you do not have resources.jar on the classpath
import edu.cs2335.antlab.pkg3.*;
import edu.gatech.oad.antlab.person.*;
import edu.gatech.oad.antlab.pkg2.*;


/**
 * CS2335 Ant Lab
 *
 * Prints out a simple message gathered from all of the other classes
 * in the package structure
 */
 public class AntLabMain {
    
    /**antlab11.java message class*/
    private AntLab11 ant11;
    
    /**antlab12.java message class*/
    private AntLab12 ant12;
    
    /**antlab21.java message class*/
    private AntLab21 ant21;
    
    /**antlab22.java message class*/
    private AntLab22 ant22;
    
    /**antlab31 java message class which is contained in a jar resource file*/
    private AntLab31 ant31;
    
    
    
    /**
     * the constructor that intializes all the helper classes
     */
    public AntLabMain () {
        
        ant11 = new AntLab11();
        ant12 = new AntLab12();
        ant21 = new AntLab21();
        ant22 = new AntLab22();
		//if you get an error here, you do not have resources.jar on the classpath
        ant31 = new AntLab31();
        
        
    }
    
    /**
     * gathers a string from all the other classes and prints the message
     * out to the console     
     * 
     */
    public void printOutMessage() {
        
        String toPrint = 
            ant11.getMessage() + ant12.getMessage() + ant21.getMessage()
          + ant22.getMessage() + ant31.getMessage();
		  //Person1 replace P1 with your name
		  //and ggg000x with your gt num
		  Person1 p1 = new Person1("P1");
		  toPrint += p1.toString("ggg001x");
		  //Person2 replace P2 with your name
		  //and ggg000x with your gt num
		  Person2 p2 = new Person2("P2");
		  toPrint += p2.toString("ggg002x");
		  //Person3 replace P3 with your name
		  //and ggg000x with your gt num
		  Person3 p3 = new Person3("P3");
		  toPrint += p3.toString("ggg003x");
          //Person4 replace P4 with your name
          //and ggg000x with your gt num
          Person4 p4 = new Person4("P4");
          toPrint += p4.toString("ggg004x");
		  //Person5 replace P5 with your name
          //and ggg000x with your gt num
          Person5 p5 = new Person5("P5");
          toPrint += p5.toString("ggg005x");
        System.out.println(toPrint);
        
        
    }
     
    
    /**
     * entry point for the program
     */
     public static void main(String[] args) {
        
        new AntLabMain().printOutMessage();
        
     } 
    
    
    
    
 } 