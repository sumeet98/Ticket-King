import java.io.IOException;

/*
 * Driver is the main class that runs the system. This program runs like:
 * Java myProgram tickets.txt users.txt transactions.txt
 * Parameters are optional, they default to ticketInputData.txt, userInputData.txt, dailyTransaction.txt
 **/
public class Driver{
	public static void main(String[] args) throws IOException{
	
	// Initialize arguments
	String transactionFileArgument = "";
	String ticketFileArgument = "";
	String userFileArgument = "";
  
	// Ticket File Optional Argument
	try {
		ticketFileArgument = args[0];	
		if (ticketFileArgument != "")
			SystemIO.ticketFile = ticketFileArgument;
	}
	catch(Exception e){
		System.out.println("Warning: Program was run without TICKET file argument\n"
				+ "Default used: tickets.txt\n"
				+ "To run daily files, refer to format: Java myProgram tickets.txt users.txt daily.txt");
	}
	
	// User File Optional Argument
	try {
		userFileArgument = args[1];	
		if (userFileArgument != "")
			SystemIO.userFile = userFileArgument;
	}
	catch(Exception e){
		System.out.println("Warning: Program was run without USER file argument\n"
				+ "Default used: transactions.txt\n"
				+ "To run daily files, refer to format: Java myProgram tickets.txt users.txt daily.txt");
	}
	  
	// Transaction File Optional Argument
	try {
		transactionFileArgument = args[2];	
		if (transactionFileArgument != "")
			SystemIO.transactionFile = transactionFileArgument;
	}
	catch(Exception e){
		System.out.println("Warning: Program was run without TRANSACTION file argument\n"
				+ "Default used: transactions.txt\n"
				+ "To run daily files, refer to format: Java myProgram tickets.txt users.txt daily.txt");
	}
	
	// Execute transactions from input files
	Executer executer = new Executer();
	executer.executeTransactions();
	
	// Create new and updated output ticket, user, and log files after transactions are completed
	SystemIO.outputTicketFile("outputFiles/outputTicketFile.txt");
	SystemIO.outputUserFile("outputFiles/outputUserFile.txt");
	SystemIO.outputTerminalLog("outputFiles/outputTerminalLog.txt");
	System.out.println("Updated ticket, user, and terminal log files created in outputFiles directory.");
  }
}
