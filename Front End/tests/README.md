# SQA Project - Phase One

### Group members:

- Gavin Gosling (1006265780
- Colin Orian (100622430)
- Yudi Tao (100600773)


### List of Table and What They are to Test:

    See “SQA - Sheet1.csv”

### Test Input and Output Organization: 
#### The tests are organized in the following way:
```
tests 
   |
   | -- >input
   |	   | -- > commandName
   |		       | -- > Command #_commandName.in
   |	
   | -- > output
	   | -- > commandName
		      | -- > Command #_commandName.out
```
__tests:__ is the root folder for the tests

__input:__ Is the folder where the input files are stored.

__commandName:__ For better organization the input files are grouped into different folders. They are grouped by command they are testing (General, login, logout, etc.). Note: The General group will cover cases that all commands need to account for.
 
__Command `#_commandName.in`:__ The actual input files.The files will be named where:

- commandName = The command that is being tested

- Command# = The test number for the corresponding test.Command# can be found in the table`.     _ = _`

__output:__ Where the expected output of the tests are stored.

__commandName:__ See above commandName

__Command `#_commandName.out`:__ Same format as Command #_commandName.in


#### Actual Test Case Inputs & Expect Outputs: 
	See test folder in the submission or test folder on Github.

#### Test Plan: 

We will run a shell script that will go over every input file. It will run the system and input each line of the input file. It will then compare the actual output of the system to expected output of the test.
