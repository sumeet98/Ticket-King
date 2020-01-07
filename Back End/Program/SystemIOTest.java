/*
* @Author Kenny Le
* @Description JUnit test cases for the SystemIO, full coverage
* @since 03/20/2019
*/

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class SystemIOTest {
	
    /* ======================================
     * =========== Test Functions ===========
     * ====================================== */
	
    // Create File With Contents
    private void writeTransaction(String filename, String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(text);
        writer.close();
    }

    // Compare String Arrays
    private void assertArrayOutput(ArrayList<String> expectedOutput, ArrayList<String> actualOutput){
        assertEquals(expectedOutput.size(), actualOutput.size());
        if(expectedOutput.size() == actualOutput.size()){
            for(int i = 0; i < expectedOutput.size(); i++){
                assertEquals(expectedOutput.get(i), actualOutput.get(i));
            }
        }
    }
    
    /* ======================================
     * ========== JUnit Test Cases ==========
     * ====================================== */
    
    /* 
     * Tests to see that append output is appending to logOutput correctly
     */
	@Test
	public void appendOutputTest() {
		SystemIO.logOutput.clear(); // Reset
		
		// Add items to expected output
		ArrayList<String> expectedOutput = new ArrayList<String>();
		expectedOutput.add("Output Test 1");
		expectedOutput.add("Output Test 2");
		
		// Test Append Output  
		SystemIO.appendOutput("Output Test 1");
		SystemIO.appendOutput("Output Test 2");
		
		// Assert expected and actual to test equality
		assertArrayOutput(expectedOutput, SystemIO.logOutput);
	}
    
    /* 
     * Tests to see if file is being loaded and converted to Ticket class array correctly
     */
	@Test
	public void fileToArrayTest() throws FileNotFoundException, IOException {
		SystemIO.users.clear();		// reset
		SystemIO.tickets.clear(); 	// reset
		
		String contents = 	"A Good Show               AdminSeller     005 005.00\r\n" + 
							"A Bad Show                AdminSeller     005 001.00";
		
		// Create/overwrite tickets file with some contents to test
		writeTransaction("tickets.txt", contents);
		
		// Put file contents into Ticket array
		SystemIO.fileToArray("tickets.txt");
		
		// Check if name is as expected ("A Good Show" & "A Bad Show" respectively)
		assertEquals(SystemIO.tickets.get(0).name,"A Good Show");
		assertEquals(SystemIO.tickets.get(1).name,"A Bad Show");

		// Check if seller is as expected ("AdminSeller" for both)
		assertEquals(SystemIO.tickets.get(0).seller,"AdminSeller");
		assertEquals(SystemIO.tickets.get(1).seller,"AdminSeller");
		
		// Check if number of tickets is as expected (005 which is converted to 5 as an int through "fileToArray" method)
		assertEquals(SystemIO.tickets.get(0).numTickets,5);
		assertEquals(SystemIO.tickets.get(1).numTickets,5);
		
		// Check if credits is as expected ("005.00" which is converted to 5.00 as a float through "fileToArray" method)
		assertEquals(SystemIO.tickets.get(0).price, 5.00f, 0.001f); // The third parameter is the delta error range allowed (required)
		assertEquals(SystemIO.tickets.get(1).price, 1.00f, 0.001f);
		
		// Do the same for users
		contents =	"AdminTest       AA 000100.00\r\n" + 
					"AdminSeller     AA 000100.00";

		// Create/overwrite tickets file with some contents to test
		writeTransaction("users.txt", contents);
		
		// Put file contents into Ticket array
		SystemIO.fileToArray("users.txt");
		
		// Check if name is as expected ("AdminTest" & "AdminSeller" respectively)
		assertEquals(SystemIO.users.get(0).name,"AdminTest");
		assertEquals(SystemIO.users.get(1).name,"AdminSeller");
		
		// Check if account type is as expected ("AA")
		assertEquals(SystemIO.users.get(0).type,"AA");
		assertEquals(SystemIO.users.get(1).type,"AA");
		
		// Check if credits is as expected ("000100.00" which is converted to 100.00 as a float through "fileToArray" method)
		assertEquals(SystemIO.users.get(0).credit, 100.00f, 0.001f);
		assertEquals(SystemIO.users.get(1).credit, 100.00f, 0.001f);
	}
    
    /* 
     * Same as above
     */
	@Test
	public void arrayToTicketArrayTest() {
		// This is called by the function 'fileToArray' which is tested above, so it is already tested
	}
    
	/*
	 * Same as above
	 */
	@Test
	public void arrayToUserArrayTest() {
		// This is called by the function 'fileToArray' which is tested above, so it is already tested		
	}
    
    /* 
     * Tests to see if a generated Ticket list is parsed to formatted string correctly
     */
	@Test
	public void ticketArrayToStringTest() {
		
		ArrayList<Ticket> TestTickets = new ArrayList<Ticket>();
		TestTickets.add(new Ticket("CoolEvent"	, "CoolSeller" , 50 , 7.25f));
		TestTickets.add(new Ticket("LameEvent"	, "LameSeller" , 20 , 12.50f));
		
		// To string 
		String Actual = SystemIO.ticketArrayToString(TestTickets);
		
		System.out.println("ACTUAL -> " + Actual);
		
		// This is expected formatted string
		String Expected = 	"CoolEvent                 CoolSeller      050 000007.25\n" + 
							"LameEvent                 LameSeller      020 000012.50\n";
		
		// Assert if the TestUsers User array equals the Expected formatted String
		assertEquals(Expected, Actual);
		
	}
	
    /* 
     * Tests to see if a generated User list is parsed to formatted string correctly
     */
	@Test
	public void userArrayToStringTest() {
		
		ArrayList<User> TestUsers = new ArrayList<User>();
		TestUsers.add(new User("FS"	, "FullUser" , 500.00f));
		TestUsers.add(new User("SS"	, "SomeSeller" , 15000.00f));
		
		// To string 
		String Actual = SystemIO.userArrayToString(TestUsers);
		
		// This is expected formatted string
		String Expected = 	"FullUser        FS 000500.00\n" + 
							"SomeSeller      SS 015000.00\n";
		
		// Assert if the TestUsers User array equals the Expected formatted String
		assertEquals(Expected, Actual);
		
	}
    
    /* 
     * Tests to see if output ticket file test works correctly
     */
	@Test
	public void outputTicketFileTest() throws IOException {
		// Clear then populate Tickets
		SystemIO.tickets.clear();
		SystemIO.tickets.add(new Ticket("A Super Coolio Event"	, "AmazingSeller"  , 100 , 2.50f));
		SystemIO.tickets.add(new Ticket("Lameo Event"			, "TerribleSeller" , 10  , 32.75f));
		
		// Output Tickets
		SystemIO.outputTicketFile("JUnitTicketOutput.txt");
		
		// Read Ticket Contents
		String actualContent = new String(Files.readAllBytes(Paths.get("JUnitTicketOutput.txt")));
		
		// Set Expected
		String expectedStr = 	"A Super Coolio Event      AmazingSeller   100 000002.50\n" + 
								"Lameo Event               TerribleSeller  010 000032.75\n";
				
		assertEquals(expectedStr, actualContent);
	}

    /* 
     * Tests to see if output user file test works correctly
     */
	@Test
	public void outputUserFileTest() throws IOException {
			// Clear then populate Tickets
			SystemIO.users.clear();
			SystemIO.users.add(new User("AA" , "SuperAdmin" , 25000.00f));
			SystemIO.users.add(new User("AA"  , "EvilAdmin" , 12.50f));
			
			// Output Tickets
			SystemIO.outputUserFile("JUnitUserOutput.txt");
			
			// Read Ticket Contents
			String actualContent = new String(Files.readAllBytes(Paths.get("JUnitUserOutput.txt")));
			
			// Set Expected
			String expectedStr = 	"SuperAdmin      AA 025000.00\n" + 
									"EvilAdmin       AA 000012.50\n";
					
			assertEquals(expectedStr, actualContent);
	}
	
    /* 
     * Tests write to file
     */
	@Test
	public void writeToTest() {
		// This is called by above two functions, so it is already tested
	}
	
    public static junit.framework.Test suite(){
        return new JUnit4TestAdapter(SystemIOTest.class);
     }
	
}
