#!/bin/bash
#Test file that used to test commands and
# program failures.

#Create and reset out file.
touch failedTests.dat
echo -n >failedTests.dat
#counters
counter=0
failcount=0
#go over All functional commands
for command in "AddCredit" "Buy" "Create" "Delete" "General" "Login" "Logout" "Refund" "Sell"
do
  echo "Running tests for command $command"

  #go over all test cases in this commmand
  for test in ../tests/input/$command/*.in
  do

    #parse file name
    OUTFILE=${test:15}
    OUTFILE=${OUTFILE%".in"}
    OUTFILE="$OUTFILE.out"

    #initiallize single output
    touch actual.out
    touch dum.dum
    echo "Running Test $OUTFILE:"
    echo
    #run program for maximun 0,5 second.
    timeout 0.5 ./main CurrentUsers.dat AvaliableTickets.dat DailyTransaction.dat < $test >dum.dum 2>> actual.out
    # timeout 0.01 ./main CurrentUsers.dat AvaliableTickets.dat DailyTransaction.dat < $test

    echo

    diffResults=$(diff -wrqEeB actual.out ../tests/output/$OUTFILE 2>/dev/null)
    diff -wrqEeB actual.out ../tests/output/$OUTFILE 2>/dev/null

    #check if any difference between expected and actual output
    if [ -n "$diffResults" ]; then
      echo "Test $test FAILED" >> failedTests.dat
      failcount=$((failcount+1))
    fi

    #clear output
    rm actual.out
    rm dum.dum
    echo "============================================="
    counter=$((counter+1))
  done
done


echo -e "\n=============================================" >> failedTests.dat
echo "Toatal test count: $counter" >> failedTests.dat
echo "Failed test count: $failcount">> failedTests.dat
