echo "Running Weekly Script"
for i in 1 2 3 4 5
do
  echo "Day $i"
  ./daily.sh
done
