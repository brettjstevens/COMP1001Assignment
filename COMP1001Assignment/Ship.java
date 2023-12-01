/*  Author: Brett Stevens
File name: Ship.java 
Purpose: super class that can be accessed by both fighterjet and submarine
Last modified: 27/5/19
*/
import java.util.*;
import java.io.*;
import java.text.*;
public abstract class Ship
{
	public static final String BAT = "battery";
	public static final String DIESEL = "diesel";
	public static final String BIO = "bio";
	public static final double TOL = 0.001;
	
	//common class fields
	private String serialNum;
	private int year;
	private Engine engine;
	
	/* Default Constructor
		IMPORT: none
		EXPORT: address of new Ship object
		ASSERTION: Ship of serial number 100.001 made in 1998
				is a valid defualt state
	*/
	public Ship()
    {
        serialNum = "100.001";
        year = 1950;
        engine = new Engine(2, "bio");
    }
	
	/* ALternate #1
		IMPORT: inSerialNum (String), inYear (integer), inEngine (Engine)
		EXPORT: address of new Ship object
		ASSERTION: Creates the object if the imports are valid and FAILS otherwise
	*/
	public Ship(String inSerialNum, int inYear, Engine inEngine)
	{
		setSerialNum(inSerialNum);
		setYear(inYear);
		engine = new Engine(inEngine.getCylinders(), inEngine.getFuel());
	}
	
	/* Copy
		IMPORT: inShip (Ship)
		EXPORT: address of new Ship object 
		ASSERTION: Creates an object with an identical object state as the import.
	*/
	public Ship(Ship inShip)
	{
		serialNum = inShip.getSerialNum();
		year = inShip.getYear();
		engine = new Engine(engine.getCylinders(), engine.getFuel());
	}
	
	/* Clone
		IMPORT: 
		EXPORT: 
		ASSERTION: Creates an abstract clone method
	*/
	public abstract Ship clone();
	
	/******************* MUTATORS *********************/
	public void setSerialNum(String inSerialNum)
	{
		if (validateSerialNum(inSerialNum))
		{
			serialNum = inSerialNum;
		}
	}
	
	public void setYear(int inYear)
	{
		if (validateYear(inYear))
		{
			year = inYear;
		}
		else 
		{
			throw new IllegalArgumentException("Invalid year input.");
		}
	}
	
	// engine setters ----------------------------------------
	public void setCylinders(int inCylinders)
	{
		if(validateCylinders(inCylinders))
		{
			engine.setCylinders(inCylinders);
		}
	}
	
	public void setFuel(String inFuel)
	{
		if(validateFuel(inFuel))
		{
			engine.setFuel(inFuel);
		}
	}
	
	/******************* ACCESSORS *********************/
	public String getSerialNum()
	{
		return serialNum;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public String toString()
	{
		return "The ship "+ serialNum +" was comissioned in "+ year + ". " + engine.toString();
	}
	
	// engine getters----------------------------------
	public int getCylinders()
	{
		return engine.getCylinders();
	}
	
	public String getFuel()
	{
		return engine.getFuel();
	}
	
	/******************* SUBMODULES *********************/	
	/* SUBMODULE: getShipType
		IMPORT: 
		EXPORT: 
		ASSERTION: can be called to determinme ship type
	*/
	public abstract char getShipType();
	
	/* SUBMODULE: travelCalc
		IMPORT: distance (integer)
		EXPORT: time 
		ASSERTION: can be called to calculate travel time
	*/
	public abstract double travelCalc(int distance, int cylinders);
	
	/* SUBMODULE: equals
		IMPORT: inObj (Object)
		EXPORT: same (boolean)
		ASSERTION: Two Ships are interchangeable if they have the same serial number, comission year
	*/ 
	public boolean equals(Object inObj)
    {
        boolean same = false;
        if(inObj instanceof Ship)
        {
            Ship inShip = (Ship)inObj;
            same =  (serialNum.equals(inShip.getSerialNum())) && (year == inShip.getYear());
        }
	    return same;
    }
	
	/* SUBMODULE: validateSerialNum
		IMPORT: inSerialNum (String)
		EXPORT: valid (boolean)
		ASSERTION: must be between 100.001 and 300.999 inclusive
	*/
	public static boolean validateSerialNum(String inSerialNum)
	{
		boolean valid = false;
		String[] splitSerialNum = inSerialNum.split("\\.");
		int numX = Integer.parseInt(splitSerialNum[0]);
		int numY = Integer.parseInt(splitSerialNum[1]);
		
		if ((splitSerialNum[0].length() == 3) && (splitSerialNum[1].length() <= 3))
		{
			if ((numX >= 100) && (numX <= 300) && (numY >= 001) && (numY <= 999))
			{
				valid = true;
			}
			else 
			{
				throw new IllegalArgumentException("Invalid serial number input.");
			}
		}
		else 
		{
			throw new IllegalArgumentException("Invalid serial number length.");
		}
		
		return valid;
	}
	
	/* SUBMODULE: validateYear
		IMPORT: inYear (integer)
		EXPORT: valid (boolean)
		ASSERTION: must be between 1950 and 2022 inclusive
	*/
	public boolean validateYear(int inYear)
	{
		return ((inYear >= 1950) && (inYear <= 2022));
	}
	
	// engine validates ------------------------------------------
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