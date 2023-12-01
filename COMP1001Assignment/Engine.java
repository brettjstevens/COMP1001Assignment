/*  Author: Brett Stevens
File name: Engine.java 
Purpose: class that handles the variables and construction of engine objects
Last modified: 27/5/19
*/
import java.util.*;
import java.io.*;
import java.text.*;
public class Engine
{

	public static final String BAT = "battery";
	public static final String DIESEL = "diesel";
	public static final String BIO = "bio";
	
	private String fuel;
	private int cylinders;
	
	/* Default Constructor
		IMPORT: none
		EXPORT: address of new Engine object
		ASSERTION: an engine with 18 cylinders that runs on bio is a valid defualt state
	*/
	public Engine()
	{
		cylinders = 18;
		fuel = BIO;
	}
	
	/* ALternate #1
		IMPORT: inCylinders (integer), inFuel (String)
		EXPORT: address of new Engine object
		ASSERTION: Creates the object if the imports are valid and FAILS otherwise
	*/
	public Engine( int inCylinders, String inFuel)
	{
		if ((validateCylinders(inCylinders)) && (validateFuel(inFuel)))
		{
			cylinders = inCylinders;
			fuel = inFuel;
		}
		else
        {
            throw new IllegalArgumentException("Invalid Import Values");
        }
	}
	
	/* Copy
	IMPORT: inEngine (Engine)
    EXPORT: address of new Engine object 
    ASSERTION: Creates an object with an identical object state as the import.
	*/
	public Engine(Engine inEngine)
	{
		cylinders = inEngine.getCylinders();
		fuel = inEngine.getFuel();
	}
	
	/* Clone
		IMPORT: none
		EXPORT: adress of new fighter jet object
		ASSERTION: Creates an object with an identical object state as the import
	*/
	public Engine clone()
    {
        Engine cloneEngine = new Engine(this.cylinders, this.fuel);
        
        return cloneEngine;
    }
	
	/******************* MUTATORS *********************/
	public void setCylinders(int inCylinders)
	{
		cylinders = inCylinders;
	}
	
	public void setFuel(String inFuel)
	{
		fuel = inFuel;
	}
	
	/******************* ACCESSORS *********************/	
	public int getCylinders()
	{
		return cylinders;
	}
	
	public String getFuel()
	{
		return fuel;
	}
	
	public String toString()
	{
		return ("Its engine has " + cylinders + " cylinders and runs on " + fuel.toLowerCase() + ".");
	}
	
	/******************* SUBMODULES *********************/
	/* SUBMODULE: equals
		IMPORT: inObj (Object)
		EXPORT: same (boolean)
		ASSERTION: Two engine are interchangeable if they have the same serial number, comission cylinders, fuel material and max depth
	*/
	public boolean equals(Object inObj)
    {
        boolean same = false;
        if(inObj instanceof Engine)
        {
            Engine inEngine = (Engine)inObj;
            same =  (cylinders == inEngine.getCylinders()) && (fuel.equals(inEngine.getFuel()));
        }
	    return same;
    }
	
	/* SUBMODULE: validateCylinders
		IMPORT: inCylinders (integer)
		EXPORT: valid (boolean)
		ASSERTION: must be between 1950 and 2022 inclusive
	*/
	public boolean validateCylinders(int inCylinders)
	{
		return ((inCylinders >= 2) && (inCylinders <= 20));
	}
	
	/* SUBMODULE: validateFuel
		IMPORT: inFuel(String)
		EXPORT: valid (boolean)
		ASSERTION: fuel must be BAT or DIESEL or BIO
	*/
	private boolean validateFuel(String inFuel)
    {
        String stripped = inFuel;
        return ((stripped.equalsIgnoreCase(BAT)) || (stripped.equalsIgnoreCase(DIESEL)) || (stripped.equalsIgnoreCase(BIO)));
    }
	
}