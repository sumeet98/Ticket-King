// java -cp /usr/local/JUNIT/junit-4.13-beta-2.jar org.junit.runner.JUnitCore  StackTest

import java.io.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class ExecuterTest {

    //Create a transaction file
    private void writeTransaction(ArrayList<String> transactions) throws IOException{
    	BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt"));
    	for (String line : transactions)
    		writer.write(line);
        writer.close();
    }
    //Generate test data that will be read in
    private void genData(){
        ArrayList<String> lines = new ArrayList<String>();
        try{
            Reader in = new FileReader("userInputData.txt");
            BufferedReader reader = new BufferedReader(in);
            
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"));
            
            String line = "";
            //Create the user data
            while(line == reader.readLine()){
                lines.add(line);
            }
            for(String temp : lines){
            	writer.write(temp);
            }
            reader.close();
            in.close();
            writer.close();
         
            
            in = new FileReader("ticketInputData.txt");
            reader = new BufferedReader(in);

            writer = new BufferedWriter(new FileWriter("tickets.txt"));

            //Create the ticket data
            while(line == reader.readLine()){
                lines.add(line);
            }
            for(String temp : lines){
                writer.write(temp);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private ArrayList<String> readFile(String fileName) throws FileNotFoundException, IOException{
    	ArrayList<String> fileContents = new ArrayList<String>();
    	try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
    	    for(String line; (line = br.readLine()) != null; ) {
    	    	fileContents.add(line);
    	    }
    	}
		return null;
    }

    private void assertOutput(ArrayList<String> expectedOutput, ArrayList<String> actualOutput){
        assertEquals(expectedOutput.size(), actualOutput.size());
        if(expectedOutput.size() == actualOutput.size()){
            for(int i = 0; i < expectedOutput.size(); i ++){
                assertEquals(expectedOutput.get(i), actualOutput.get(i));
            }
        }
    }

    //Covers both conditions in the create() function. Will also cover the findUser() function
	@Test
	public void createTest() throws IOException{
		ArrayList<String> trans = new ArrayList<String>();

        trans.add("01 usernameAwesome BS 000010"); 
        trans.add("01 myNameIsAwesome AA 0034.3");
        writeTransaction(trans);
        genData();
        Driver.main(null); // Execute the system

        //Compare the new users to the expected output
        ArrayList<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("usernameAwesome AA 000020.00");
        expectedOutput.add("PidgeonsAwesome FS 000030.00");
        expectedOutput.add("DipperPines     SS 000100.00");
        expectedOutput.add("01 myNameIsAwesome AA 0034.3");

        ArrayList<String> actualOutput = readFile("users.txt");
        //Assert that the output file is the same as the expected output
        assertOutput(expectedOutput, actualOutput);
	}

    //Covers both conditions for the delete function
    @Test
    public void deleteTest() throws IOException{
		ArrayList<String> trans = new ArrayList<String>();

        trans.add("02 usernameAwesome BS 000010"); 
        trans.add("02 myNameIsAwesome AA 0034.3");
        writeTransaction(trans);
        genData();
        Driver.main(null); // Execute the system

        //Compare the new users to the expected output
        ArrayList<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("PidgeonsAwesome FS 000030.00");
        expectedOutput.add("DipperPines     SS 000100.00");
        ArrayList<String> actualOutput = readFile("users.txt");
        //Assert that the output file is the same as the expected output
        assertOutput(expectedOutput, actualOutput);

    }
    //Tests the sell() function
    @Test
    public void sellTest() throws IOException{
        ArrayList<String> trans = new ArrayList<String>();
        trans.add("03 WeirdmegedonPartThr DipperPines    100 000100.12"); //Ticket already exists
        trans.add("03 WeirdmegedonPartFor DipperPines    100 000100.12");

        writeTransaction(trans);
        genData();
        Driver.main(null);

        ArrayList expectedOutput = new ArrayList<String>();
        expectedOutput.add("WeirdmegedonPartOne DipperPines    100 000012");
        expectedOutput.add("WeirdmegedonPartThr PidgeonsAwesome 001 000012");
        expectedOutput.add("WeirdmegedonPartTwo PidgeonsAwesome 010 000100");
        expectedOutput.add("03 WeirdmegedonPartFor DipperPines    100 000100.12");

        ArrayList<String> actualOutput = readFile("tickets.txt");
        assertOutput(expectedOutput, actualOutput);
    }

    //Test the refund() function
    @Test
    public void refundTest() throws IOException{
        ArrayList<String> trans = new ArrayList<String>();

        
        trans.add("05 myNameIsAwesome 12dawkfjqkrksqr 0034.3"); //Test that both are not in the system
        trans.add("05 DipperPines myNameIsAwesome 0034.3");//Seller doesn't exist
        trans.add("05 myNameIsAwesome DipperPines 0034.3");//Buyer doesn't exist
        trans.add("05 DipperPines PidgeonsAwesome 0020.0");
        writeTransaction(trans);
        genData();
        Driver.main(null); // Execute the system
        
        ArrayList<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("usernameAwesome AA 000020.00");
        expectedOutput.add("PidgeonsAwesome FS 000010.00");
        expectedOutput.add("DipperPines     SS 000120.00");

        ArrayList<String> actualOutput = readFile("users.txt");
        assertOutput(expectedOutput, actualOutput);
    }

    //Tests both conditions of the addCredit function()
    @Test
    public void addCreditTest() throws IOException{
        ArrayList<String> trans = new ArrayList<String>();

        trans.add("06 usernameAwesome BS 000010"); 
        trans.add("06 myNameIsAwesome AA 0034.3");
        writeTransaction(trans);
        genData();
        Driver.main(null); // Execute the system

        //Compare the new users to the expected output
        ArrayList<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("usernameAwesome AA 000030.00");
        expectedOutput.add("PidgeonsAwesome FS 000030.00");
        expectedOutput.add("DipperPines     SS 000100.00");

        ArrayList<String> actualOutput = readFile("users.txt");
        //Assert that the output file is the same as the expected output
        assertOutput(expectedOutput, actualOutput);

    }

    //Also Tests the logOff() function
    @Test
    public void buyTest() throws IOException{
        ArrayList<String> trans = new ArrayList<String>();
        trans.add("04 thisTicketDoesntExi DipperPines     20 000030.00"); //Ticket doesn't exist
        trans.add("04 WeirdmegedonPartTwo DipperPines     002 000100"); // User is out of credits
        //It's sucessful and buys the rest of the tickets
        trans.add("04 WeirdmegedonPartThr DipperPines     001 000012"); 
        trans.add("00 DipperPines    SS 000100.00");
        writeTransaction(trans);
        genData();
        Driver.main(null);

        ArrayList<String> expectedUsers = new ArrayList<String>();
        ArrayList<String> expectedOutput = new ArrayList<String>();
		expectedOutput.add("usernameAwesome AA 000030.00");
        expectedOutput.add("PidgeonsAwesome FS 000030.00");
        expectedOutput.add("DipperPines     SS 00088.00");

        ArrayList<String> expectedTickets = new ArrayList<String>();
        expectedOutput.add("WeirdmegedonPartOne DipperPines    100 000012");
        expectedOutput.add("WeirdmegedonPartTwo PidgeonsAwesome 010 000100");

        ArrayList<String> actualUsers = readFile("users.txt");
        ArrayList actualTickets = readFile("tickets.txt");

        assertOutput(expectedUsers, actualUsers);
        assertOutput(expectedTickets, actualTickets);

    }
    //Loops isn't executed
    public void loopCoverageTest() throws IOException{
        genData();
        Driver.main(null);


        ArrayList<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("usernameAwesome AA 000020.00");
        expectedOutput.add("PidgeonsAwesome FS 000030.00");
        expectedOutput.add("DipperPines     SS 000100.00");

        ArrayList<String> actualOutput = readFile("users.txt");
        assertOutput(expectedOutput, actualOutput);
    }

    //Completes branch coverage (the default value). Also does part of loop coverage (1 iteration)
    public void branchCoverageTest() throws IOException{
        ArrayList<String> trans = new ArrayList<String>();
        trans.add("22");
        writeTransaction(trans);
        genData();
        Driver.main(null);

        ArrayList<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("usernameAwesome AA 000020.00");
        expectedOutput.add("PidgeonsAwesome FS 000030.00");
        expectedOutput.add("DipperPines     SS 000100.00");

        ArrayList<String> actualOutput = readFile("users.txt");
        assertOutput(expectedOutput, actualOutput);

    }
    public static junit.framework.Test suite(){
       return new JUnit4TestAdapter(ExecuterTest.class);
    }
}
