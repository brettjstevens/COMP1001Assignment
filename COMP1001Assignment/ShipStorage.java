/*  Author: Brett Stevens
File name: ShipStorage.java 
Purpose: class that handles adding ships to storage, checking a speed of the ships in storage, finding duplicates of the ships in storage and viewing ships in storage
Last modified: 27/5/19
*/
import java.util.*;
import java.io.*;
import java.text.*;
public class ShipStorage
{
	private Ship[] shipArray;
	private int num;
	
	/* Default Constructor
		IMPORT: none
		EXPORT: address of new object
		ASSERTION: constructs two arrays and sets counting integers to zero
	*/
	public ShipStorage()
	{
		shipArray = new Ship[30];
		num = 0;
	}
	
	/********************************** MUTATORS *************************************/
	/* SUBMODULE: addShip
		IMPORT: inShip (Shipmarine)
		EXPORT: 
		ASSERTION: checks that Shipmarine object is not null and adds object to next position in the array, otherwise fails
	*/
	public void addShip(Ship inShip)
	{
		if ((inShip != null) && (num < 30))
		{
			shipArray[num] = inShip;
			num++;
			System.out.println("Saving ship with details: " + inShip.toString());
		}
		else if (num >= 30)
		{
			throw new IllegalArgumentException("Invalid: no more space in Ship array");
		}
		else
		{
			throw new IllegalArgumentException("Invalid Import Values");
		}
	}

	/********************************** ACCESSORS *************************************/
	public Ship[] getShipArray()
	{
		return shipArray;
	}

	public int getNum()
	{
		return num;
	}
	
	/********************************** SUBMODULES *************************************/
	/* SUBMODULE: destinationCheck
		IMPORT: distance (integer)
		EXPORT:
		ASSERTION:  imports an integer to loop through each element and then use the calcTravel method and return 
					a string that contains the serial number and type of ship of the fastest ship
	*/
	public void destinationCheck(int distance)
	{
		DecimalFormat df = new DecimalFormat("0.00");
		
		String fastestSerial = "", fastestType = "";
		double time, fastestTime;
		int cylinders;
		char type;
		fastestTime = 10000000;
		
		for(int i = 0; i < num; i++)
		{
			cylinders = shipArray[i].getCylinders();
			time = shipArray[i].travelCalc(distance, cylinders); 
			
			if(time < fastestTime)
			{
				fastestSerial = shipArray[i].getSerialNum();
				fastestTime = time;
				type = shipArray[i].getShipType();
				
				switch(type)
				{
					case 'S':
						fastestType = "submarine";
					break;
					
					case 'F':
						fastestType = "fighter jet";
					break;
				}
			}
		}
	
		System.out.print("The fastest ship is a " + fastestType + " with serial number of " + fastestSerial +". It covers a distance of " + distance + " metres in " + df.format(fastestTime) +" seconds.\n");
	}
	
	/* SUBMODULE: findDuplicates
		IMPORT: 
		EXPORT: 
		ASSERTION: loop through the arrays and display the ship details of any duplicate ships using the toString method
	*/
	public void findDuplicates()
	{
		if(num < 2)
		{
			System.out.println("There is less than 2 ships stored.");
		}
		else
		{
			for (int i = 0; i <= num; i++)
			{
				for (int j = i + 1; j <= num; j++)
				{
					if (shipArray[i].equals(shipArray[j]))
					{					
						System.out.println("Duplicate ship occuring at positions " + i +" and " + j + ". Details: " + shipArray[i].toString() + "\n");
					}
				}
			}
		}
		System.out.println("No more duplicates.\nEnding finding duplcates and exiting to main menu...\n");
	}
	
	/* SUBMODULE: viewShips
		IMPORT: 
		EXPORT: 
		ASSERTION:  loop through both arrays and construct a string with the output detailing each ship
	*/
	public void viewShips()
	{
		for(int i = 0; i < num; i++)
		{
			System.out.println(shipArray[i].toString());
		}
	}
	
	/* SUBMODULE: equals
		IMPORT: inObj (Object)
		EXPORT: same (boolean)
		ASSERTION: compare the content of the arrays of the two objects
	*/
	public boolean equals(Object inObj)
	{
		boolean same = false;
        if(inObj instanceof ShipStorage)
		{
			ShipStorage storeObj = (ShipStorage)inObj;
			Ship[] inShipArray = storeObj.getShipArray();
			int i = 0;
			do
			{
				same = (shipArray[i].equals(inShipArray[i]));
				i++;
			} while ((i < num) || same);
		}
		else
		{
			throw new IllegalArgumentException("Invalid Import Object");
		}
		
		return same;
	}
}