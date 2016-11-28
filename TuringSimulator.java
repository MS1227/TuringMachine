import java.io.*;
import java.util.*;
public class TuringSimulator
{
	public static int state;

	public static void main(String [] args) throws IOException
	{
		ArrayList<Transition> tArray = new ArrayList<Transition>();
		selectFile(tArray);
		menuSelect(tArray);
		
     		
	}
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
		int count = 0;
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
	private static void clearScreen()
	{		
		System.out.println("\u001b[H\u001b[2J");
     	}
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
