echo "Running Daily Script"
echo "Running Front End Simulator"
   SQAProject-Phase_3/program/makefile CurrentUsers.dat AvailableTickets.dat DailyTransactions.dat < ./tests/input/general/01_General.in

echo "Running Back End Simulator"
  java -classpath makefile ticketInputData.txt userInputData.txt dailyTransaction.txt
echo "Done Running Tests"
