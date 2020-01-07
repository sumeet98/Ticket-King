echo Merging daily transaction files using Windows (Batch) script, use the linux shell (.sh) script first as priority (this script is a backup)
copy /b .\dailyTransactions\*.txt .\dailyTransaction.txt
timeout 15