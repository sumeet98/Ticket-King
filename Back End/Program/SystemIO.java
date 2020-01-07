/*
* @Author Kenny Le
* @Description A class that will read from merged transaction file, parsed accounts file,
                and parsed available tickets file to output a new accounts and tickets file
* @since 03/10/2019
*/

import java.io.*;
import java.util.Scanner;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SystemIO {
	/* ------------------------------------------------------------------------------
	 * Static Variables accessible to other systems for Executer and Parser to modify
	 * ------------------------------------------------------------------------------
	 * ACCESSIBLE STRING LISTS:	SystemIO.ticketList, SystemIO.userList, 
	 * 							SystemIO.transactionsList, SystemIO.logOutput 
	 * 
	 * ACCESSIBLE CLASS LISTS:	SystemIO.tickets, SystemIO.users
	 * 
	 * EXAMPLE ADD NEW TICKET:
	 * 		SystemIO.tickets.add(new Ticket("TestEvent", "SellerName", 10, 15.00f));	
	 *
	 * EXAMPLE ADD NEW USER:
	 * 		SystemIO.users.add(new User("UserName", "AA", 15.00f));	
	 * 
	 * EXAMPLE ADD NEW TRANSACTION:
	 * 		SystemIO.transactionsList.add("00 AdminTest       AA 001111.00");
	 * 
	 * EXAMPLE APPEND LOG OUTPUT:
	 *		SystemIO.logOutput.add("User successfully logged out");
	 *
	 * HOW TO ACCESS AND USE SystemIO:
	 *  1. Add tickets or users accordingly using SystemIO.tickets.add... or SystemIO.users.add...
	 *  2. Call SystemIO.outputTicketFile(outputFilename) or outputUserFile... to CREATE updated OUTPUT file
	 */
  public static ArrayList<String> logOutput = new ArrayList<>();		// concatenation of terminal output
  public static ArrayList<String> ticketList = new ArrayList<>();		// reads in ticket list as string
  public static ArrayList<String> userList = new ArrayList<>();			// reads in user account list as string
  public static ArrayList<String> transactionsList = new ArrayList<>(); // All transactions merged
  public static ArrayList<Ticket> tickets = new ArrayList<>(); 			// The Ticket array in Ticket class structure
  public static ArrayList<User> users = new ArrayList<>();   			// The User array in User class structure
  
  // Default (these can be override for example: Java MyProgram ticket.txt user.txt transaction.txt)
  public static String transactionFile = "dailyTransaction.txt";		// The default transaction file
  public static String ticketFile = "ticketInputData.txt";				// The default tickets file
  public static String userFile = "userInputData.txt";					// The default users file
  
  /*
  * Constructor
  **/
  public SystemIO() {
  }

  // /*
  // * Append to terminal output messages, call writeTo(logOutput, filename) to create file
  // * @param str string input terminal line
  // **/
  public static void appendOutput(String str) {
	  logOutput.add(str);
  }

  /*
   * Reads a file to STRING array, also appends to the respective CLASS ARRAY
   * @param file takes filename with extension, ie. "file.txt"
   **/
  public static ArrayList<String> fileToArray(String file) throws FileNotFoundException, IOException {
	  String str;
	  if (file == ticketFile) {
		Scanner s = new Scanner(new File(file));
		while (s.hasNextLine()){
			str = s.nextLine();
			ticketList.add(str);
			appendOutput("[Input] Tickets were loaded and appended to the Ticket array class -> " + str);
		}
		s.close();
		arrayToTicketArray(ticketList); // Append strings to the TICKET class array
		return ticketList;
	  } else if (file == userFile) {
		 Scanner s = new Scanner(new File(file));
		 while (s.hasNextLine()) {
			 str = s.nextLine();
			 userList.add(str);
			 appendOutput("[Input] Users were loaded and appended to the User array class -> " + str);
		 }
		 s.close();
		 arrayToUserArray(userList); // Append strings to the USER class array
		 return userList;
	  } else if (file == transactionFile) {
		  File f = new File(file);
		  if(f.exists() && !f.isDirectory()) { 
			  Scanner s = new Scanner(new File(file));
			  while (s.hasNextLine()) {
					 transactionsList.add(s.nextLine());
			  }
			  s.close();	  
		  } else {
			  String fileInDirectory = "dailyTransactions/" + file;
			  Scanner s = new Scanner(new File(fileInDirectory));
			  while (s.hasNextLine()) {
					 transactionsList.add(s.nextLine());
			  }
			  s.close();
		  } 
		 return transactionsList;
	  }
  return userList;
}

  
  /*
   * Appends the Ticket STRING Array to the TICKET CLASS
   */
  private static void arrayToTicketArray(ArrayList<String> strTickets) {
	  for (String str : strTickets) {
		  // Max Event String = 25 Characters
		  // Max User String = 15 Characters 
		  // Max Quantity String = 3 Characters
		  // Max Price String = 6 Characters
		  // Substring "Test Show".substring(0,4) would return "Test", "Test Show".substring(5,9) would return "Show"
		  
		  String eventName 	= str.substring(0, 25).trim();				// 25 chars, and trim leading and trailing spaces
		  String sellerName	= str.substring(26, 41).trim();				// whitespace on 25th index, start at 26, 15 chars
		  int numTickets 	= Integer.parseInt(str.substring(42, 45));	// whitespace on 41st index, start at 42, 3 chars
		  float price 		= Float.parseFloat(str.substring(46, 52));	// whitespace on 45th index, start at 46, 6 chars		
		  
		  Ticket newTicket = new Ticket(eventName, sellerName, numTickets, price);	// create ticket
		  tickets.add(newTicket);	// add ticket to ticket array list
	  }
  }

  
  /*
   * Appends the User STRING Array to the USER CLASS
   */
  private static void arrayToUserArray(ArrayList<String> strUsers) {
	  for (String str : strUsers) {
		  // Max User String = 15 Characters
		  // Max User Type = 2 Characters (AA, FS, BS, SS)
		  // Max User Credits = 9 Characters
		  
		  String userName 	= str.substring(0, 15).trim();				// 15 chars, and trim leading and trailing spaces
		  String userType	= str.substring(16, 18);					// whitespace on 15th index, start at 16, 2 chars
		  float credits 	= Float.parseFloat(str.substring(19, 28));	// whitespace on 18th index, start at 19, 9 chars		
		  
		  User newUser = new User(userType, userName, credits);	// create user
		  users.add(newUser);	// add user to user array list
	  }
  }
  
  
  /*
   * takes the TICKET array and returns the entire content as a FORMATTED STRING to create the new tickets file
   */
  public static String ticketArrayToString(ArrayList<Ticket> ticketArray) {
	  /* ----- Example -----
	   * INPUT PARAMETER (ticketArray):
	   *  New Ticket1
	   *  Ticket1.name = "A Fun Event"
	   *  Ticket1.seller = "AdminSeller"
	   *  Ticket1.numTickets = "25"
	   *  Ticket1.price = "5.00"
	   *  
	   *  New Ticket2
	   *  Ticket2.name = "A Boring Event"
	   *  Ticket2.seller = "AdminSeller"
	   *  Ticket2.numTickets = "5"
	   *  Ticket2.price = "2.50"
	   * 
	   * RETURN OUTPUT (String):
	   * "A Fun Event               AdminSeller 025 0005.00
	   *  A Boring Event            AdminSeller 005 0002.50"
	   */
	  
	  String str = "";
	  for (Ticket ticket : ticketArray) {

		  // ----- Event Name -----
		  // Append Ticket Event Name
		  str += ticket.name;
		  
		  // Create whitespace accordingly for event (example: since max is 25 chars, if event is 15 chars, create 10+1 whitespace)
		  for (int x = 0; x <= 25-ticket.name.length(); x++) 
			  str += " ";
		  // -----------------------
		  
		  
		  // ------- Seller --------
		  // Append Seller Name
		  str += ticket.seller;
		  
		  // Create whitespace accordingly for seller (example: since max is 15 chars, if seller is 10 chars, create 5+1 whitespace)
		  for (int x = 0; x <= 15-ticket.seller.length(); x++) 
			  str += " ";
		  // -----------------------
		  
		  
		  // ------- Quantity ------
		  DecimalFormat dfq = new DecimalFormat("000"); 
		  String formattedQuantity = dfq.format(ticket.numTickets);	// Example: 5 = 005, 50 = 050, 500 = 500

		  str += formattedQuantity + " ";
	     // ------------------------
		  
		  
		  // ------- Cost ------
		  DecimalFormat dfc = new DecimalFormat("000000.00"); 
		  String formattedPrice = dfc.format(ticket.price);	// Example: 5 = 000005.00, 12.50 = 000012.50

		  str += formattedPrice;
		  // ------------------------
		  
		  
		  str += "\n";	// Line break
	  }
	  System.out.println("ticketArrayToString->\n" + str);
	  return str;
  }
  
  
  /*
   * takes the USER array and returns the entire content as a FORMATTED STRING to create the new users file
   */
  public static String userArrayToString(ArrayList<User> userArray) {
	  String str = "";
	  
	  for (User user : userArray) {

		  // ----- User Type -----
		  // Append User Type
		  str += user.name;
		  
		  // Create whitespace accordingly for user (example: since max is 15 chars, if event is 10 chars, create 10+1 whitespace)
		  for (int x = 0; x <= 15-user.name.length(); x++) 
			  str += " ";
		  // -----------------------
		  
		  
		  // ------ User Name -------
		  // Append User Name
		  str += user.type + " ";
		  // -----------------------
		  
		  
		  // ------- Credits ------
		  DecimalFormat dfc = new DecimalFormat("000000.00"); 
		  String formattedPrice = dfc.format(user.credit);	// Example: 10 = 000010.00

		  str += formattedPrice;
		  // ------------------------
		  
		  str += "\n";	// Line break
	  }

	  System.out.println("userArrayToString->\n" + str);
	  return str;
  }

   
  /*
  * Creates new TICKET file from the TICKET array by converting the TICKET array to a STRING
  **/
  public static void outputTicketFile(String filename) throws IOException {
	  writeTo(ticketArrayToString(tickets),filename);
  }

  /*
  * Creates new USER file from the USER array by converting the USER array to a STRING
  **/
  public static void outputUserFile(String filename) throws IOException {
	  writeTo(userArrayToString(users),filename);
  }
  
  /*
   * Creates a terminal log output of actions done in this session
   **/
  public static void outputTerminalLog(String filename) throws IOException {
	String path = SystemIO.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	String decodedPath = URLDecoder.decode(path, "UTF-8");
	String newFile = decodedPath + filename;
	String str = "";
	for (String line : logOutput) {
		  str += line + "\n";
	}
	// Output to new file
	BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
	writer.write(str);
	writer.close();
  }

  /*
  * Uses BufferedWriter for efficient character stream output of array lists to file
  * @param str the array list of tickets or users or terminal output
  * @param path the file path and output name to write to
  **/
  private static void writeTo(String str, String filename) throws IOException
  {
    // Get absolute path of this executing program (which includes the program path)
    String path = SystemIO.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    // Decode path in case of spaces and special characters (will not work for some characters like "+")
    String decodedPath = URLDecoder.decode(path, "UTF-8");

    // Add filename to path to write to
    String newFile = decodedPath + filename;

    // Output to new file
    BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
    writer.write(str);
    writer.close();
    // Append to terminal output file
    appendOutput("Wrote to new file " + newFile);
  }

}
