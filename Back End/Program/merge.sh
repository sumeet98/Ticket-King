touch transactions.txt #Create and remove any data from previous runs
 > transactions.txt

#Go through every file in the dailyTransactions directory and append output to merged data
for input in ./dailyTransactions/*
do
    cat $input >> dailyTransaction.txt
    echo "" >> dailyTransaction.txt
done
