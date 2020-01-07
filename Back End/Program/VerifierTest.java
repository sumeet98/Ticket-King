import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class VerifierTest {

  Parser parser = new Parser();

  @Test
  public void verifyTicketsTest(){
    String[] tickets = {"WeirdmegedonPartOne", "DipperPines", "100", "000012"};
    String[] expectedOutput = tickets;

    //actual output with for loop and if statement check
    for(int i = 0; i < parser.ticketFound.length; ++i){
      if(parser.ticketFound[i] == null){
        System.err.println("Error something wrong with finding that event: " +
                           parser.ticketSearch);
      }
    }
    /*if this is the expected output then if statement is checked
    String[] ticketsError = {"WeirdmegedonPartOne", "DipperPines", "100"};
    expectedOutput = ticketsError;
    */
    assertEquals(expectedOutput, parser.ticketFound);
  }

  @Test
  public void verifyAccountsTest(){
    String[] accounts = {"usernameAwesome", "AA", "000030.00"};
    String[] expectedOutput = accounts;

    //actual output with for loop and if statement check
    for(int i = 0; i < parser.accountFound.length; ++i){
      if(parser.accountFound[i] == null){
        System.err.println("Error something wrong with finding that account: " +
                           parser.accountSearch);
      }
    }
    /*if this is the expected output then if statement is checked
    String[] accountsError = {"usernameAwesome", "000030.00"};
    expectedOutput = accountsError;
    */
    assertEquals(expectedOutput, parser.accountFound);
  }

  public static junit.framework.Test suite(){
     return new JUnit4TestAdapter(VerifierTest.class);
  }

}
