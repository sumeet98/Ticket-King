import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class ParserTest {

  private void assertOutput(ArrayList<String> expectedOutput, ArrayList<String> actualOutput){
      assertEquals(expectedOutput.size(), actualOutput.size());
      if(expectedOutput.size() == actualOutput.size()){
          for(int i = 0; i < expectedOutput.size(); i ++){
              assertEquals(expectedOutput.get(i), actualOutput.get(i));
          }
      }
  }

    @Test
    public void requestTicketsTest() throws FileNotFoundException, IOException{
      ArrayList expectedOutput = new ArrayList<String>();
      expectedOutput.add("WeirdmegedonPartOne DipperPines    100 000012");
      expectedOutput.add("WeirdmegedonPartThr PidgeonsAwesome 001 000012");
      expectedOutput.add("WeirdmegedonPartTwo PidgeonsAwesome 010 000100");

      ArrayList<String> actualOutput = SystemIO.fileToArray("tickets.txt");
      assertOutput(expectedOutput, actualOutput);
    }

    @Test
    public void requestUsersTest() throws FileNotFoundException, IOException{
      ArrayList expectedOutput = new ArrayList<String>();
      expectedOutput.add("usernameAwesome AA 000020.00");
      expectedOutput.add("PidgeonsAwesome FS 000030.00");
      expectedOutput.add("DipperPines     SS 000100.00");
      expectedOutput.add("01 myNameIsAwesome AA 0034.3");

      ArrayList<String> actualOutput = SystemIO.fileToArray("users.txt");
      assertOutput(expectedOutput, actualOutput);
    }

    @Test
    public void requestTransactionsTest() throws FileNotFoundException, IOException{
      ArrayList expectedOutput = new ArrayList<String>();
      expectedOutput.add("10 usernameAwesome AA 000020.00");
      expectedOutput.add("01 usernameAwesome AA 000020.00");
      expectedOutput.add("10 PidgeonsAwesome FS 000030.00");
      expectedOutput.add("01 PidgeonsAwesome FS 000030.00");

      ArrayList<String> actualOutput = SystemIO.fileToArray("transaction.txt");
      assertOutput(expectedOutput, actualOutput);
    }

    @Test
    public void parseTicketsTest(){
      String ticketSearch = "WeirdmegedonPartOne";
      String[] expectedOutput = {"WeirdmegedonPartOne", "DipperPines", "100", "000012"};

      //actualOutput, checks the loop
      String[] newTicketList = new String[SystemIO.ticketList.size()];
      String[] ticketFound = new String[4];
      for(int i = 0; i < newTicketList.length; ++i){
        if (newTicketList[i].equals(ticketSearch)){
          ticketFound[0] = newTicketList[i];
          ticketFound[1] = newTicketList[i+1];
          ticketFound[2] = newTicketList[i+2];
          ticketFound[3] = newTicketList[i+3];
        }
      }
      assertEquals(expectedOutput, ticketFound);
    }

    @Test
    public void parseAccountsTest(){
      String accountSearch = "usernameAwesome";
      String[] expectedOutput = {"usernameAwesome", "AA", "000020.00"};

      //actualOutput, checks the loop
      String[] newUserList = new String[SystemIO.userList.size()];
      String[] accountFound = new String[3];
      for(int i = 0; i < newUserList.length; ++i){
        if (newUserList[i].equals(accountSearch)){
          accountFound[0] = newUserList[i];
          accountFound[1] = newUserList[i+1];
          accountFound[2] = newUserList[i+2];
        }
      }
      assertEquals(expectedOutput, accountFound);
    }

    @Test
    public void accountsToStringTest(){
      String[] expectedOutput = {"usernameAwesome AA 000020.00",
      "PidgeonsAwesome FS 000030.00",
      "DipperPines     SS 000100.00"};

      String[] userList = new String[Executer.users.size()];
      userList = Executer.users.toArray(userList);
      assertEquals(expectedOutput, userList);
    }

    @Test
    public void ticketsToStringTest(){
      String[] expectedOutput = {"WeirdmegedonPartOne DipperPines    100 000012",
      "WeirdmegedonPartThr PidgeonsAwesome 001 000012",
      "WeirdmegedonPartTwo PidgeonsAwesome 010 000100"};

      String[] ticketList = new String[Executer.tickets.size()];
      ticketList = Executer.tickets.toArray(ticketList);
      assertEquals(expectedOutput, ticketList);
    }

    public static junit.framework.Test suite(){
       return new JUnit4TestAdapter(ParserTest.class);
    }
  }
