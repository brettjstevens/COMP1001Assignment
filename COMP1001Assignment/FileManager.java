/*  Author: Brett Stevens
File name: FileManager.java 
Purpose: to manage all I/O of files
Last modified: 27/5/19
*/
import java.util.*;
import java.io.*;
import java.text.*;
public class FileManager
{
public static final String STEEL = "steel";
	public static final String ALLOY = "alloy";
	public static final String TIT = "titanium";
	public static final String BAT = "battery";
	public static final String DIESEL = "diesel";
	public static final String BIO = "bio";
	public static final double TOL = 0.001;
	
	/* SUBMODULE: inputFile
		IMPORT: shipStorage
		EXPORT: 
		ASSERTION: reads ships from file
	*/
	public static void inputFile(ShipStorage shipStorage)
	{
		Scanner sc = new Scanner(System.in);
		
		FileInputStream fileStrm = null;
		InputStreamReader rdr;
		BufferedReader bufRdr;
		String inSerialNum, inHull, serialNum, hull, inOrd, ordnance, inFuel, fuel, line;
		int inYear, year, inCylinders, cylinders;
		double inMaxDepth, maxDepth = 0.0, inWingSpan, wingSpan;
		char type;
		String inFilename;
		Engine engine;
		
		System.out.println("Enter file name: ");
		inFilename = sc.nextLine();
		
		try
		{
			fileStrm = new FileInputStream(inFilename);
			rdr = new InputStreamReader(fileStrm);	 
			bufRdr = new BufferedReader(rdr);
			
			line = bufRdr.readLine();
			
			while (line != null) 
			{
				String[] lineArray = line.split( "," );
				
				if (lineArray.length != 7)
				{
					throw new IllegalArgumentException("Invalid data length");
				}
				
				if (lineArray[0].length() != 1)
				{
					throw new IllegalArgumentException("Invalid data length...");
				}
				
				type = lineArray[0].charAt(0);
				
				inSerialNum = lineArray[1];
				if(validateSerialNum(inSerialNum))
				{
					serialNum = inSerialNum;
				}
				else
				{
					throw new IllegalArgumentException("Invalid serial number within file.");
				};

				inYear = Integer.parseInt(lineArray[2]);
				if(validateYear(inYear))
				{
					year = inYear;
				}
				else
				{
					throw new IllegalArgumentException("Invalid year within file.");
				};
				
				inCylinders = Integer.parseInt(lineArray[3]);
				if(validateCylinders(inCylinders))
				{
					cylinders = inCylinders;
				}
				else
				{
					throw new IllegalArgumentException("Invalid cylinders within file");
				}
				
				inFuel = lineArray[4];
				if(validateFuel(inFuel))
				{
					fuel = inFuel;
				}
				else
				{
					throw new IllegalArgumentException("Invalid fuel within file");
				}
				
				engine = new Engine(cylinders, fuel);
				
				switch (type)
				{
					case 'S' : case 's':
						inHull = lineArray[5];
						if(validateHull(inHull))
						{
							hull = inHull;
						}
						else
						{
							throw new IllegalArgumentException("Invalid hull within file.");
						};
						
						inMaxDepth = Double.parseDouble(lineArray[6]);
						if(validateMaxDepth(inMaxDepth))
						{
							maxDepth = inMaxDepth;
						}
						else
						{
							throw new IllegalArgumentException("Invalid max depth within file.");
						};
				
						Submarine subObj = new Submarine(serialNum, year, engine, hull , maxDepth);
						shipStorage.addShip(subObj);
					break;
				
					case 'F' : case 'f':
						inWingSpan = Double.parseDouble(lineArray[5]);
						if(validateWingSpan(inWingSpan))
						{
							wingSpan = inWingSpan;
						}
						else
						{
							throw new IllegalArgumentException("Invalid wing span within file.");
						};
						
						inOrd = lineArray[6];
						if(validateOrdnance(inOrd))
						{
							ordnance = inOrd;
						}
						else
						{
							throw new IllegalArgumentException("Invalid ordnance within file.");
						};
						
						FighterJet fighterObj = new FighterJet(serialNum , year , engine, ordnance , wingSpan);
						shipStorage.addShip(fighterObj);
					break;
						
					default:
						System.out.println("Invalid first letter");
					break;
				}
				line = bufRdr.readLine();
			}
			fileStrm.close();
		}	
		catch (IOException e) 
		{			        
			if (fileStrm != null) 
			{		         
				try 
				{
					fileStrm.close(); 
				}
				catch (IOException ex2) 
				{
				}
			
			}
			System.out.println("Error in file processing: " + e.getMessage());
		}
	}
	
	/* SUBMODULE outputToFile
		IMPORT: shipStorage
		EXPORT:
		ASSERTION: outputs ships to a file 
	*/
	public static void outputToFile(ShipStorage shipStorage)
	{
		Scanner sc = new Scanner(System.in);
		
		int num;
		String filename, line;
		FileOutputStream fileStrm = null;
		PrintWriter pw;
		char type;
		
		System.out.println("Input your file name: (dont forget the .csv)");
		filename = sc.nextLine();
		
		try
		{
			fileStrm = new FileOutputStream(filename);
			pw = new PrintWriter(fileStrm);
			
			num = shipStorage.getNum();
			Ship[] shipArray = shipStorage.getShipArray();
			
			for(int i = 0; i < num; i++)
			{
				type = shipArray[i].getShipType();
				
				switch(type)
				{
					case 'S':
						Submarine sub = new Submarine();
						sub = (Submarine)shipArray[i];
						
						pw.println("S, " + shipArray[i].getSerialNum() + ", " + shipArray[i].getYear() + ", " + shipArray[i].getCylinders() + ", " + shipArray[i].getFuel() + ", " + sub.getHull() + ", " + sub.getMaxDepth());
					break;
					
					case 'F':
						FighterJet fj = new FighterJet();
						fj = (FighterJet)shipArray[i];
	
						pw.println("F, " + shipArray[i].getSerialNum() + ", " + shipArray[i].getYear() + ", " + shipArray[i].getCylinders() + ", " + shipArray[i].getFuel() + ", " + fj.getOrdnance() + ", " + fj.getWingSpan());
					break;
				}
				
			}
			pw.close();
			System.out.println("Ships in storage output to file...\n");
		}
		catch(IOException e)
		{
			System.out.println("Error in outputting to file:" + e.getMessage());
		}
	}
	
	/******************* SUBMODULES *********************/
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
	public static boolean validateYear(int inYear)
	{
		return ((inYear >= 1950) && (inYear <= 2022));
	}
	
	/* SUBMODULE: validateHull
		IMPORT: inHull(String)
		EXPORT: valid (boolean)
		ASSERTION: hull must be STEEL or ALLOY or TITANIUM
	*/
	private static boolean validateHull(String inHull)
    {
        String stripped = inHull;
        return ((stripped.equalsIgnoreCase("steel")) || (stripped.equalsIgnoreCase("alloy")) || (stripped.equalsIgnoreCase("titanium")));
    }
	
	/* SUBMODULE: validateMaxDepth
		IMPORT: inMaxDepth (real)
		EXPORT: valid (boolean)
		ASSERTION: must be between -500.0 and 0.0 inclusive
	*/
	public static boolean validateMaxDepth(double inMaxDepth)
	{
		return ((inMaxDepth >= (-500.0 - TOL)) && (inMaxDepth <= (0.0 + TOL)));
	}
	
		/* SUBMODULE: validateOrdnance
		IMPORT: inOrdnance(String)
		EXPORT: valid (boolean)
		ASSERTION: ordnance must not be empty string
	*/
	private static boolean validateOrdnance(String inOrdnance)
    {
        return (!(inOrdnance.equals("")));
    }
	
	/* SUBMODULE: validateWingSpan
		IMPORT: inWingSpan (real)
		EXPORT: valid (boolean)
		ASSERTION: must be between -500.0 and 0.0 inclusive
	*/
	public static boolean validateWingSpan(double inWingSpan)
	{
		return ((inWingSpan >= (2.20 - TOL)) && (inWingSpan <= (25.60 + TOL)));
	}
	
	/* SUBMODULE: validateCylinders
		IMPORT: inCylinders (integer)
		EXPORT: valid (boolean)
		ASSERTION: must be between 1950 and 2022 inclusive
	*/
	public static boolean validateCylinders(int inCylinders)
	{
		return ((inCylinders >= 2) && (inCylinders <= 20));
	}
	
	/* SUBMODULE: validateFuel
		IMPORT: inFuel(String)
		EXPORT: valid (boolean)
		ASSERTION: fuel must be BAT or DIESEL or BIO
	*/
	private static boolean validateFuel(String inFuel)
    {
        String stripped = inFuel;
        return ((stripped.equalsIgnoreCase(BAT)) || (stripped.equalsIgnoreCase(DIESEL)) || (stripped.equalsIgnoreCase(BIO)));
    }
}