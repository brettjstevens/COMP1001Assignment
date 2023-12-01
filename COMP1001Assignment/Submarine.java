/*  Author: Brett Stevens
File name: Submarine.java 
Purpose: class that handles the variables and construction of submarine objects
Last modified: 27/5/19
*/
import java.util.*;
import java.io.*;
import java.text.*;
public class Submarine extends Ship
{
	public static final String STEEL = "steel";
	public static final String ALLOY = "alloy";
	public static final String TIT = "titanium";
	public static final double TOL = 0.001;
	
	private String hull;
	private double maxDepth;
	
	/*Default Constructor
		IMPORT: none
		EXPORT: address of new Submarine object
		ASSERTION: submarine of serial number 100.001 made in 1998, hull of steel that can reach a max depth of 450m is a valid defualt state
	*/
	public Submarine()
	{
		super();
		hull = STEEL;
		maxDepth = -450;
	}
	
	/* ALternate #1
		IMPORT: inHull (String), inMaxDepth (real)
		EXPORT: address of new Submarine object
		ASSERTION: Creates the object if the imports are valid 
	*/
	public Submarine(String inSerialNum, int inYear, Engine inEngine, String inHull, double inMaxDepth)
	{
		super(inSerialNum, inYear, inEngine);
		setHull(inHull);
		setMaxDepth(inMaxDepth);
	}
	
	/* Copy
		IMPORT: inSubmarine (Submarine)
		EXPORT: address of new Submarine object 
		ASSERTION: Creates an object with an identical object state as the import.
	*/
	public Submarine(Submarine inSubmarine)
	{
		super(inSubmarine); 
		hull = inSubmarine.getHull();
		maxDepth = inSubmarine.getMaxDepth();
	}
	
	/* Clone
		IMPORT: NONE
		EXPORT: adress of new Submarine object
		ASSERTION: Creates an object with an identical object state as the import
	*/
	@Override
	public Submarine clone()
    {
        Submarine cloneSubmarine = new Submarine(this);
        
        return cloneSubmarine;
    }
	
	/******************* MUTATORS *********************/
	public void setHull(String inHull)
	{
		if (validateHull(inHull))
		{
			hull = inHull;
		}
		else 
		{
			throw new IllegalArgumentException("Invalid hull input.");
		}
	}
	
	public void setMaxDepth(double inMaxDepth)
	{
		if (validateMaxDepth(inMaxDepth))
		{
			maxDepth = inMaxDepth;
		}
		else 
		{
			throw new IllegalArgumentException("Invalid max depth input.");
		}
	}
	
	/******************* ACCESSORS *********************/
	public String getHull()
	{
		return hull;
	}
	
	public double getMaxDepth()
	{
		return maxDepth;
	}
	
	public String toString()
	{
		DecimalFormat df = new DecimalFormat("0.00");
		String str;
		str = super.toString() + " It is a submarine with a "+ hull.toLowerCase() +" hull and has a max depth of "+ df.format(maxDepth) +" metres.\n";
		return str;
	}
	
	/* SUBMODULE: getShipType
		IMPORT: 
		EXPORT: 
		ASSERTION: can be called to determinme ship type
	*/
	@Override
	public char getShipType()
	{
		return 'S';
	}
	
	/******************* SUBMODULES *********************/
	/* SUBMODULE: equals
		IMPORT: inObj (Object)
		EXPORT: same (boolean)
		ASSERTION: Two submarines are interchangeable if they have the same serial number, comission year, hull material and max depth
	*/
	public boolean equals(Object inObj)
    {
        boolean same = false;
        if(inObj instanceof Submarine)
        {
            Submarine inSubmarine = (Submarine)inObj;
            same =  super.equals(inSubmarine) && (hull.equals(inSubmarine.getHull()))
					&& (inSubmarine.getMaxDepth() > maxDepth - TOL) && (inSubmarine.getMaxDepth() < maxDepth + TOL);
        }
	    return same;
    }
	
	/* SUBMODULE: travelCalc
		IMPORT: distance (integer)
		EXPORT: time 
		ASSERTION: can be called to calculate the travel time for submarine
	*/
	@Override
	public double travelCalc(int distance, int cylinders)
	{ 
		double time;
		
		time = ((double)distance / (double)cylinders) * ( 1.0 / (10.0 + maxDepth * -1.0));
		return time;
	}
	
	/* SUBMODULE: validateHull
		IMPORT: inHull(String)
		EXPORT: valid (boolean)
		ASSERTION: hull must be STEEL or ALLOY or TITANIUM
	*/
	private boolean validateHull(String inHull)
    {
        String stripped = inHull;
        return ((stripped.equalsIgnoreCase("steel")) || (stripped.equalsIgnoreCase("alloy")) || (stripped.equalsIgnoreCase("titanium")));
    }
	
	/* SUBMODULE: validateMaxDepth
		IMPORT: inMaxDepth (real)
		EXPORT: valid (boolean)
		ASSERTION: must be between -500.0 and 0.0 inclusive
	*/
	public boolean validateMaxDepth(double inMaxDepth)
	{
		return ((inMaxDepth >= (-500.0 - TOL)) && (inMaxDepth <= (0.0 + TOL)));
	}
}