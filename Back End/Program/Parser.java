/*
* @Author Sumeet Dhillon
* @Description A class that will parse all the transactions
* @since 03/09/2019
*/

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Parser {

  boolean matchFound;
  String[] ticketFound = new String[4];
  String[] accountFound = new String[3];
  String[] newTicketList = new String[SystemIO.ticketList.size()];
  String[] newUserList = new String[SystemIO.userList.size()];
  String ticketSearch;
  String accountSearch;

    /*
  * Requests for the tickets, generates ArrayList<Tickets>
  * @param None
  * @return None
  **/
  public ArrayList<Ticket> requestTickets(){
    try {
      SystemIO.fileToArray((SystemIO.ticketFile));
    } catch (IOException ex) {
      System.err.println(ex);
    }

	return SystemIO.tickets;
  }

  /*
  * Requests for the Users, generates ArrayList<Users>
  * @param None
  * @return None
  **/
  public ArrayList<User> requestUsers(){
    try {
    SystemIO.fileToArray(SystemIO.userFile);
  } catch (IOException ex) {
    System.err.println(ex);
  }

	 return SystemIO.users;
  }

  /*
  * Requests for the Users, generates ArrayList<Users>
  * @param None
  * @return None
  **/
  public ArrayList<String> requestTransactions(){
    try {
    SystemIO.fileToArray(SystemIO.transactionFile);
  } catch (IOException ex) {
    System.err.println(ex);
  }

	 return SystemIO.transactionsList;
  }


  /*
  * Parses thru the ticket ArrayList and returns a match if there is one in a string array
  * @param String ticketSearch
  * @return ticketFound[]
  **/
  public String[] parseTickets(String ticket){
    ticketSearch = ticket;
    newTicketList = SystemIO.ticketList.toArray(newTicketList);
    matchFound = false;
    for(int i = 0; i < newTicketList.length; ++i){
      if (newTicketList[i].equals(ticketSearch)){
        matchFound = true;
        ticketFound[0] = newTicketList[i];
        ticketFound[1] = newTicketList[i+1];
        ticketFound[2] = newTicketList[i+2];
        ticketFound[3] = newTicketList[i+3];
      }
    }

   return ticketFound;
  }

  /*
  * Parses thru the account ArrayList and returns a match if there is one in a string array
  * @param String accountSearch
  * @return accountFound
  **/
  public String[] parseAccounts(String account){
    accountSearch = account;
    newUserList = SystemIO.userList.toArray(newUserList);
    matchFound = false;
    for(int i = 0; i < newUserList.length; ++i){
      if (newUserList[i].equals(accountSearch)){
        matchFound = true;
        accountFound[0] = newUserList[i];
        accountFound[1] = newUserList[i+1];
        accountFound[2] = newUserList[i+2];
      }
    }

  return accountFound;
  }

  // public static void main(String[] args){
  //   Parser parser = new Parser();
  //   parser.requestTickets();
  //   parser.requestUsers();
  //   parser.parseTickets("Event2");
  //   parser.parseAccounts("user01");
  // }
}
