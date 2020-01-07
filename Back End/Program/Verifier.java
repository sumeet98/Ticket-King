/*
* @Author Sumeet Dhillon
* @Description A class that will verify all the transactions
* @since 03/09/2019
*/

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Verifier {

  Parser parser = new Parser();

  /*
  * Verifies the tickets in transactions
  * @param ArrayList<Tickets> all the tickets
  * @return None
  **/
  public String[] verifyTickets(String[] tickets) {
    for(int i = 0; i < parser.ticketFound.length; ++i){
      if(parser.ticketFound[i] == null){
        System.err.println("Error something wrong with finding that event: " +
                           parser.ticketSearch);
      }
    }
    return parser.ticketFound;
  }

  /*
  * Verifies the users account in transactions
  * @param ArrayList<Users> all the users
  * @return None
  **/
    public String[] verifyAccounts(String[] accounts) {
      for(int i = 0; i < parser.accountFound.length; ++i){
        if(parser.accountFound[i] == null){
          System.err.println("Error something wrong with finding that account: " +
                             parser.accountSearch);
        }
      }
      return parser.accountFound;
    }


    // public static void main(String[] args){
    //   Verifier verifier = new Verifier();
    //   verifier.parser.requestTickets();
    //   verifier.parser.requestUsers();
    //   verifier.parser.parseTickets("Event1");
    //   verifier.parser.parseAccounts("user01");
    //   verifier.verifyAccounts(verifier.parser.accountFound);
    //   verifier.verifyTickets(verifier.parser.ticketFound);
    // }
}
