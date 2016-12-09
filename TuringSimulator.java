//A java program to that reads in a Turing Machine Descriptor File (TMDF) and executes input strings
//on that machine.
//By: Matt Schnider
//Fall 2016 - CSC340
//Dr. Allen
import java.io.*;
import java.util.*;
public class TuringSimulator
{
	private static int state;		//Global variable to hold the current state.

	public static void main(String [] args) throws IOException
	{
		ArrayList<Transition> tArray = new ArrayList<Transition>(); 		//ArrayList of transitions to hold the delta funtion of the Turing Machine.
		selectFile(tArray);							
		menuSelect(tArray);
		
     		
	}
	//Method to retrieve the index in the ArrayList tArray that corresponds to the current state and tape character.
	//ArrayList tArray is passed in along with the current character on the tape and the current state. A local variable
	//indexOf is set to -1 as a not found flag. A for loop parses tArray. An if statement checks to see if the current
	//tape character is equal to current tape character of the transition in slot i. If it is and the current state in the 
	//transition matches the current state, the appropriate transition has been found and indexOf is updated to reflect that.
	//indexOf is then passed back to the calling function.
	//PRECONDITION: tArray, currChar, currState passed in.
	//POSTCONDITION: int index of the appropriate trasition is passed back.
	private static int indexOfTransition(ArrayList<Transition> tArray, char currChar, int currState)
	{
		int indexOf = -1;
		
		for(int i = 0; i<tArray.size(); i++)
		{	

			if(tArray.get(i).getCurrTape().charAt(0) == currChar && tArray.get(i).getCurrState() == currState)
			{
				indexOf = i;
				return indexOf;
			}
		}
          return indexOf;
		
	}
	//Method to simulate the running of the selected Turing machine. Clearscreen method is called to wipe the screen of any text.
	//A series of print statements print out the Simulation header. A scanner is initialiezed to read input from the keyboard and
	//the user is prompted to enter an input string. The string object, input, captures the incoming string and is then used to create
	//a StringBuffer object to allow for easier parsing and character manipulation. The character B is appended to the end of the 
	//tapeSim StringBuffer to denote blank space at the end of the input string. Local variables are used to simulate parts of the 
	//machine. rHead is the read head and it is initialized 0 to point to the first character on the input tape. currState is set to 0
	//to set up the machine in the start state q0. CharChar is set to hold the current character that the read head is pointing to in tapeSim.
	//A while loop is then set up to keep the machine running until the end of the tapeSim is reached. The indexOfTransition method retrieves 
	//the index of the current transition in tArray. If this value is less than 0, indicating the at the flag val of -1 was returned, a message
	//prints alerting the user of bad input/state not found and the loop terminates. The Tape is then updated to reflect the character rules
	//of the transition and is then printed out to show the result. A series of if esle if statements then evaluate if the head needs to move
	//L or Right and updates rHead accordingly. At the end of the loop, if the currState is equal to n-states-2, the input accepts, else it rejects.
	//The user is then offered the choice of selecting a for another simulation or enter for the main menu.
        private static void runSimulation(ArrayList<Transition> tArray)
	{
		clearScreen();

		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("                          ****** Simulation ******                           ");
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println();

		Scanner in = new Scanner(System.in);
		System.out.print("Please type an input string: ");
		String input = in.nextLine();
		StringBuffer tapeSim = new StringBuffer(input);
		tapeSim.append('B');
		int rHead = 0;
		int currState = 0;
		char currChar = tapeSim.charAt(rHead);
		
		while(rHead < tapeSim.length())
		{
		
			
			int transition = indexOfTransition(tArray, currChar, currState);
			if(transition < 0)
			{
				System.out.println("Invalid input/state not found, reject by default");
				break;
			}
		 	tapeSim.setCharAt(rHead, tArray.get(transition).getWriteTape().charAt(0));
			System.out.println(tapeSim);

			if(tArray.get(transition).getMoveHead().charAt(0) == 'R')
			{
				rHead += 1;
			}
			else if(rHead == 0)
			{
				rHead = rHead;
			}
			else if(tArray.get(transition).getMoveHead().charAt(0) == 'L')
			{
				rHead -=1;
			}
			currState = tArray.get(transition).getNextState();
			if(rHead < tapeSim.length())
			currChar = tapeSim.charAt(rHead);
		}
		if(currState == state-2)
			System.out.println("Input was accepted and the result of the the machine was: " + tapeSim + " ('B' denotes blank space).");	
		else
			System.out.println("Input was rejected! Result: " + tapeSim);	
		System.out.print("Type (a) to run another simulation, <enter> to return to main menu: ");
		String select = in.nextLine();
		if(select.toLowerCase().compareTo("a") == 0)
			runSimulation(tArray);		


		
	}
	//Method to clear the screen
	private static void clearScreen()
	{		
		System.out.println("\u001b[H\u001b[2J");
     	}
	//Method to read in menu selections passed in from the printMenu method. A scanner object is set up to read input from the keyboard.
	//A switch statement then takes the String passed back from printMenu to select the appropriate case. If an invalid input is entered
	//the user is notified and asked to try again. menuSelect is called at the end of each case to keep the program going until the user quits.
	private static void menuSelect(ArrayList<Transition> tArray) throws IOException
	{ Scanner in = new Scanner(System.in);
		switch(printMenu())
		{
			case "r":
			       runSimulation(tArray);
		       	       menuSelect(tArray);	       
				break;
			case "s":
				tArray.clear();
				selectFile(tArray);
				menuSelect(tArray);
				break;
			case "q":
				System.exit(0);
				break;
			default: 
				System.out.print("Invalid input, press <enter> to continue");
				in.nextLine();
				menuSelect(tArray);
				break;
		}

	}
	//Method to print out the main menu. Series of print statements display the text menu. A scanner is set up to read keyboard input and
	//and the selection is passed back to the calling function.
	private static String printMenu()
	{
		clearScreen();
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("                          ****** Main Menu ******                            ");
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("\t" + "r - Run Simulation");
		System.out.println("\t" + "s - Select another machine");
		System.out.println("\t" + "q - Exit simulator");

		Scanner in = new Scanner(System.in);
		System.out.println();
		System.out.print("Please type one of above choices and press <enter>: ");
		String selection = in.nextLine();
		selection = selection.toLowerCase();
	  return selection;
	}
	//Method to display a list of TMDF (.tm) files in the current directory and read in the selected filename. String inFile is declared.
	//clearScreen is called to wipe the screen. A series of print statements then print out header and a method call to getFiles() displays
	//the current .tm files in the directory. The user is then prompted for a filename and the scanner is then reconfigured to read in data from the
	//inputted filename. A while loop the parses the .tm file and builds the arrayList of transitions. After this has been completed, the information
	//about the imported Turing machine is displayed.
	private static ArrayList<Transition> selectFile(ArrayList<Transition> tArray) throws IOException
	{	
		String inFile;

		
		clearScreen();
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("                   Welcome to the Turing Machine Simulator                   ");
		System.out.println("                              by: Matt Schnider                              ");
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("Please select from the following Turing Machine descriptor files: " + "\n");
		getFiles();

		Scanner in = new Scanner(System.in);
		System.out.print("Filename: ");
		inFile = in.nextLine();
		in = new Scanner(new File(inFile));

		int numStates = in.nextInt();
		state = numStates;
		in.nextLine();
		String sigma = in.nextLine();
		String gamma = in.nextLine();
		int count = in .nextInt();
		in.nextLine();
		while(in.hasNext())
		{
			int currState = in.nextInt();
			String currTape = in.next();
			int nextState = in.nextInt();
			String writeTape = in.next();
			String movHead = in.next();
			tArray.add(new Transition(currState,currTape,nextState,writeTape,movHead));
			in.nextLine();			
		}
		System.out.println();
		System.out.println("The following Turing Machine has been created from the file " + inFile + ": ");
		System.out.printf("%13s", "States: ");
		System.out.print(numStates + "\n");
		System.out.printf("%13s", "Sigma: ");
		System.out.print(sigma + "\n");
		System.out.printf("%13s", "Arcs: ");
		System.out.print(count + "\n");
		if(gamma.length() == 0)
		{
			System.out.printf("%13s","Gamma: ");
			System.out.print("Sigma + B"+"\n");
		}	
		else
			System.out.printf("%13s","Gamma: ");
			System.out.print(gamma + "\n");
		System.out.println("Transitions:");
		for(int i = 0; i < tArray.size(); i++)
			System.out.println("\t"+tArray.get(i));
		in = new Scanner(System.in);
		System.out.print("Press <enter> to continue...");
		in.nextLine();
	  return tArray;
	}
	//getFiles builds an array of strings that end in .tm in the current directory
	private static void getFiles()
	{
		
		File curDir = new File(".");
		String[] fileNames = curDir.list();
		ArrayList<String> data = new ArrayList<String>();
		
		for(String s:fileNames)
			if(s.endsWith(".tm"))
				data.add(s);
		for(int i = 0 ; i<data.size(); i++)
		{
			System.out.println("\t" + data.get(i));
     		}
	}
}
