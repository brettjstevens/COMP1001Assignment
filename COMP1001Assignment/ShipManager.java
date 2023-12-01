/*  Author: Brett Stevens
File name: ShipManager.java 
Purpose: starting class that constructs a UI object and calls main menu
Last modified: 27/5/19
*/
import java.util.*;
import java.io.*;
import java.text.*;
public class ShipManager
{
	public static void main( String [] args)
	{
		try
		{
			UserInterface userInterface = new UserInterface();
			
			userInterface.mainMenu();
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e);
		}
		finally
		{
			System.out.println("Bye.");
		} 
	}
}	