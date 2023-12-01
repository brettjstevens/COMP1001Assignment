import java.util.*;
import java.io.*;
import java.text.*;
public class UserInterface
{
	public static final String STEEL = "steel";
	public static final String ALLOY = "alloy";
	public static final String TIT = "titanium";
	public static final String BAT = "battery";
	public static final String DIESEL = "diesel";
	public static final String BIO = "bio";
	public static final double TOL = 0.001;
	
	private ShipStorage shipStorage;
	
	public UserInterface()
	{
		shipStorage = new ShipStorage();
	}
	
	/* SUBMODULE: mainMenu
		IMPORT: 
		EXPORT: 
		ASSERTION: main menu to redirect to appropriate modules
	*/
	public void mainMenu()
	{
		Scanner sc = new Scanner(System.in);
		int selection = 0;
		
			do
			{
				try
				{
					System.out.println("	MAIN MENU:\n" +
							"What would you like to do:\n" +
							"1. add a ship to storage\n" +
							"2. check a destination\n" +
							"3. find duplicates\n" +
							"4. view ships in storage\n" +
							"5. import ships from file\n" +
							"6. export ships to file\n" +
							"7. exit");
					selection = sc.nextInt();
					
					
						switch (selection)
						{
							case 1:
								addShip();
							break;
								
							case 2:	
								destCheck();
							break;
							
							case 3:
								shipStorage.findDuplicates();
							break;
							
							case 4:
								shipStorage.viewShips();
							break;
							
							case 5:
								FileManager.inputFile(shipStorage);
							break;
							
							case 6:
								FileManager.outputToFile(shipStorage);
							break;
							
							case 7:
								System.out.println("Thnx for your time... NOW CLOSING...");
							break;
						}
					
				}
				catch(Exception e)
				{
					System.out.println("ERROR: " + e + "\nPlease enter a valid input.\n");
					sc.nextLine();
				}
			}while(selection != 7);
		
	}
	
	/* SUBMODULE: addShip
		IMPORT: 
		EXPORT: 
		ASSERTION: allows user to select what ship they would like to add
	*/
	public void addShip()
	{
		Scanner sc = new Scanner(System.in);
		int ship = 0;
		
		try
		{
			do
			{
				
				System.out.println("What type of ship would you like to add:\n" +
							"1: Submarine\n" +
							"2: Fighter jet\n" +
							"3. exit");
				ship = sc.nextInt();
			
				switch(ship)
				{
					case 1:
						inSub();
					break;
						
					case 2:
						inFj();
					break;
						
					case 3:
						System.out.println("Exiting to main menu...\n");
					break;
				}
			
			}while(ship > 3 || ship < 1);
		}
		catch(Exception e)
		{
			System.out.println("Invalid input: " + e + "\n");
			sc.nextLine();
		}
	}
	
	/* SUBMODULE: inSub 
		IMPORT: nil
		EXPORT: inSub (Submarine)
		ASSERTION: allows user to input values for a submarine
	*/
	public void inSub()
	{
		Scanner sc = new Scanner(System.in);
		String serialNum = "", hull = "", checkSerialNum;
		int year, checkYear, material;
		double maxDepth, checkMaxDepth;
		Engine engine;
		
		// enter sub serial number
		System.out.println("Enter serial number: ");
		checkSerialNum = sc.nextLine();
		if(validateSerialNum(checkSerialNum))
		{
			serialNum = checkSerialNum;
		}
		
		// enter commission year
		System.out.println("Enter commission year: ");
		checkYear = sc.nextInt();
		if (validateYear(checkYear))
		{
			year = checkYear;
		}
		else 
		{
			throw new IllegalArgumentException("Invalid year input.");
		}
		
		// select engine
		engine = inputEngine();
		
		// select hull material
		do
		{
			System.out.println("What material is the hull:\n" +
						"1. steel\n" +
						"2. alloy\n" +
						"3. titanium\n" +
						"4. exit");
			material = sc.nextInt();
		}while(material > 4 || material < 1);
		
		if (material != 4)
		{
			switch(material)
			{
				case 1:
					hull = STEEL;
				break;
					
				case 2:
					hull = ALLOY;
				break;
					
				case 3:
					hull = TIT;
				break;
			}
			
			// enter max depth
			System.out.println("Enter max depth: ");
			checkMaxDepth = sc.nextDouble();
			if(validateMaxDepth(checkMaxDepth))
			{
				maxDepth = checkMaxDepth;
			}
			else 
			{
				throw new IllegalArgumentException("Invalid max depth input.");
			}
			Submarine inSub = new Submarine(serialNum, year, engine, hull, maxDepth);
			shipStorage.addShip(inSub);
		}
		else
		{
			System.out.println("Exiting to main menu...\n");
		}
	}
	
	/* SUBMODULE: inFj (FighterJet)
		IMPORT: 
		EXPORT: 
		ASSERTION: allows user to input values for a fighter jet
	*/
	public void inFj()
	{
		Scanner sc = new Scanner(System.in);
		String serialNum = "", checkSerialNum, ordnance = "", checkOrd = "meh";
		int year, checkYear;
		double wingSpan, checkWingSpan;
		Engine engine;
		
		// enter sub serial number
		System.out.println("Enter fighter jets serial number: ");
		checkSerialNum = sc.nextLine();
		if(validateSerialNum(checkSerialNum))
		{
			serialNum = checkSerialNum;
		}
		
		// enter commission year
		System.out.println("Enter commission year: ");
		checkYear = sc.nextInt();
		if (validateYear(checkYear))
		{
			year = checkYear;
		}
		else 
		{
			throw new IllegalArgumentException("Invalid year input.");
		}
		
		// enter engine
		engine = inputEngine();
		sc.nextLine(); // clear buffer
		
		// select ordnance
		System.out.println("Enter fighter jets ordnance:");
		checkOrd = sc.nextLine();
		while(validateOrdnance(checkOrd) == false)
		{
			System.out.println("Ordnance must not be an empty string\n" +
								"Please enter fighter jets ordnance:");
			checkOrd = sc.nextLine();
		}
		ordnance = checkOrd;
		
		// enter wing span
		System.out.println("Enter wing span: ");
		checkWingSpan = sc.nextDouble();
		if(validateWingSpan(checkWingSpan))
		{
			wingSpan = checkWingSpan;
		}
		else 
		{
			throw new IllegalArgumentException("Invalid wing span input.");
		}
		
		FighterJet inFj = new FighterJet(serialNum, year, engine, ordnance, wingSpan);
		shipStorage.addShip(inFj);
	}
	
	/* SUBMODULE: inputEngine (Engine)
		IMPORT: 
		EXPORT: 
		ASSERTION: allows user to input values for engine
	*/
	public Engine inputEngine()
	{
		Scanner sc = new Scanner(System.in);
		int cylinders, fuelChoice;
		String fuel = "";
		
		System.out.println("Please enter number of cylinders: ");
		cylinders = sc.nextInt();
		
		do 
		{
			System.out.println("What fuel does the ship use:\n" +
						"1. bio fuel\n" +
						"2. diesel\n" +
						"3. battery\n" +
						"4. exit");
			fuelChoice = sc.nextInt();
		}while(fuelChoice > 4 || fuelChoice < 1);
		
		if (fuelChoice != 4)
		{
			switch(fuelChoice)
			{
				case 1:
					fuel = BIO;
				break;
				
				case 2:
					fuel = DIESEL;
				break;
				
				case 3:
					fuel = BAT;
				break;
			}
		}
		else
		{
			System.out.println("Exiting to main menu...\n");
		}
		
		Engine engine = new Engine(cylinders, fuel);
		return engine;
	}

	public void destCheck()
	{
		Scanner sc = new Scanner(System.in);
		int distance;
			
		try
		{
			System.out.println("Enter distance: ");
			distance = sc.nextInt();
			
			if (distance >= 0 )
			{
				shipStorage.destinationCheck(distance);
			}
			else
			{
				throw new IllegalArgumentException("Invalid distance value; distance must be greater than zero");
			}
		}
		catch(Exception e)
		{
			System.out.println("Invalid input: " + e + "\n");
		}
	}
	
	/******************************** VALIDATE SUBMODULES *************************************/
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