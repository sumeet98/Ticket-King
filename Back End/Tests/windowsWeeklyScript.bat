setlocal EnableDelayedExpansion
javac Driver.java
:: Run the daily 5 times using the updated output files, writes to outputTicketFileDay1, 2, 3, 4, and 5 as well for each day
for /L %%i in (1 1 5) do (
java Driver outputFiles\outputTicketFile.txt outputFiles\outputUserFile.txt dailyTransaction.txt
copy outputFiles\outputTicketFile.txt outputFiles\outputTicketFileDay%%i.txt
)
timeout 30