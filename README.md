# SQA Project Part 2

## How to build
Linux
1. Navigate to directory in command line
2. Compile code
    > make build
3. Run code
    >make run
  
## Scripts  
#### Linux (preferred):  
1. merge.sh  
2. daily.sh (to complete a transaction of the front end, type: login, then Admin, then logout)  
3. weekly.sh  
4. Take a look at outputFiles directory for the resulting output tickets, users, terminal log  
5. (Optional) daily_backend_only_example.sh (to run the backend only)
  
#### Windows (alternative):  
1. windowsFrontEndDaily.bat (to complete a transaction of the front end, type: login, then Admin, then logout)  
2. windowsMergeScript.bat  
3. windowsDailyScript.bat  
4. windowsWeeklyScript.bat (press enter once to complete each day and look at outputFiles directory for change in each iteration)  
5. Take a look at outputFiles directory for the resulting output tickets, users, terminal log  
  
#### Notes  
For weekly, it is very preferred to run the windowsWeeklyScript.bat if you want repeat days to take the previous day's output as the new input, this script will also output each individual day of Available Tickets in the outputFiles directory, whereas the weekly.sh linux script only writes to the same output file repeatedly.  
  
When running weekly script, make sure that daily is run first to generate the first batch of output files, and also to reset the user accounts and tickets.  
  
You may delete all the files in fileOutput directory if you want to, the daily script, followed by weekly script should repopulate it.  
  

