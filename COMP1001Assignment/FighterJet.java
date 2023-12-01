/*  Author: Brett Stevens
File name: FighterJet.java 
Purpose: class that handles the variables and construction of fighterjet objects
Last modified: 27/5/19
*/
import java.util.*;
import java.io.*;
import java.text.*;
public class FighterJet extends Ship
{
	public static final String BAT = "battery";
	public static final String DIESEL = "diesel";
	public static final String BIO = "bio";
	public static final double TOL = 0.001;
	
	private String ordnance;
	private double wingSpan;
	
	/*Default Constructor
		IMPORT: none
		EXPORT: address of new FighterJet object
		ASSERTION: calls super defualt constructor,
				ordnance of a flux rifle with a wingspan of 4.0m is a valid defualt state
	*/
	public FighterJet()
	{
		super();
		ordnance = "flux rifle";
		wingSpan = 4.0;
	}
	
	/* ALternate #1
		IMPORT: inOrdnance (String), inWingSpan (real)
		EXPORT: address of new FighterJet object
		ASSERTION: Creates the object if the imports are valid and FAILS otherwise
	*/
	public FighterJet(String inSerialNum, int inYear, Engine inEngine, String inOrdnance, double inWingSpan)
	{
		super(inSerialNum, inYear, inEngine);
		setOrdnance(inOrdnance);
		setWingSpan(inWingSpan);
	}
	
	/* Copy
	IMPORT: inFighterJet (FighterJet)
    EXPORT: address of new FighterJet object 
    ASSERTION: Creates an object with an identical object state as the import.
	*/
	public FighterJet(FighterJet inFighterJet)
	{
		super(inFighterJet); 
		ordnance = inFighterJet.getOrdnance();
		wingSpan = inFighterJet.getWingSpan();
	}
	
	/* Clone
		IMPORT: none
		EXPORT: adress of new fighter jet object
		ASSERTION: Creates an object with an identical object state as the import
	*/
	@Override
	public FighterJet clone()
    {
        FighterJet cloneFighterJet = new FighterJet(this);
        
        return cloneFighterJet;
    }
	
	/******************* MUTATORS *********************/	
	public void setOrdnance(String inOrdnance)
	{
		if (validateOrdnance(inOrdnance))
		{
			ordnance = inOrdnance;
		}
		else 
		{
			throw new IllegalArgumentException("Invalid Ordnance input.");
		}
	}
	
	public void setWingSpan(double inWingSpan)
	{
		if (validateWingSpan(inWingSpan))
		{
			wingSpan = inWingSpan;
		}
		else 
		{
			throw new IllegalArgumentException("Invalid Wing Span input.");
		}
	}
	
	/******************* ACCESSORS *********************/
	public String getOrdnance()
	{
		return ordnance;
	}
	
	public double getWingSpan()
	{
		return wingSpan;
	}
	
	public String toString()
	{
		DecimalFormat df = new DecimalFormat("0.00");
		String str;
		str = super.toString() + " It is a fighter jet with a "+ ordnance +" ordnance and has a wing span of "+ df.format(wingSpan) +" metres.\n";
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
		return 'F';
	}
	
	/******************* SUBMODULES *********************/
	/* SUBMODULE: equals ????
		IMPORT: inObj (Object)
		EXPORT: same (boolean)
		ASSERTION: Two fighter jets are interchangeable if they have the same serial number, comission year, ordnance material and max depth
	*/
	public boolean equals(Object inObj)
    {
        boolean same = false;
        if(inObj instanceof FighterJet)
        {
            FighterJet inFighterJet = (FighterJet)inObj;
            same =  super.equals(inFighterJet) && (ordnance.equals(inFighterJet.getOrdnance())) 
					&& (wingSpan == inFighterJet.getWingSpan());
        }
	    return same;
    }

	/* SUBMODULE: travelCalc
		IMPORT: distance (integer)
		EXPORT: time
		ASSERTION: can be called to calculate the travel time for fighter jets
	*/
	@Override
	public double travelCalc(int distance, int cylinders) 
	{
		double time;
		
		time = (double)distance / (wingSpan * (double)cylinders * 150.0);
		return time;
	}
	
	/* SUBMODULE: validateOrdnance
		IMPORT: inOrdnance(String)
		EXPORT: valid (boolean)
		ASSERTION: ordnance must not be empty string
	*/
	private boolean validateOrdnance(String inOrdnance)
    {
        return (!(inOrdnance.equals("")));
    }
	
	/* SUBMODULE: validateWingSpan
		IMPORT: inWingSpan (real)
		EXPORT: valid (boolean)
		ASSERTION: must be between -500.0 and 0.0 inclusive
	*/
	public boolean validateWingSpan(double inWingSpan)
	{
		return ((inWingSpan >= (2.20 - TOL)) && (inWingSpan <= (25.60 + TOL)));
	}
}