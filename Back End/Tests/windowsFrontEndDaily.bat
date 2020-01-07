setlocal EnableDelayedExpansion

CD .\SQAProject-Phase_3\program\
g++ -c *.cpp
g++ *.o -o a.out
a.out CurrentUsers.dat AvailableTickets.dat DailyTransactions.dat 

timeout 15