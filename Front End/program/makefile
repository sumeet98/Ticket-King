all: build cleano run clean

build:
	g++ -std=c++11 -c CommandReceiver.cpp IOReceiver.cpp User.cpp Ticket.cpp main.cpp
	g++ -o main CommandReceiver.o IOReceiver.o User.o Ticket.o main.o

cleano:
	rm -rf *.o

run:
	./main CurrentUsers.dat AvaliableTickets.dat DailyTransaction.dat

clean:
	rm -rf *.o
	rm -rf main
	rm -rf a.out


test:
	./runTestSuit.sh