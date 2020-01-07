/**
 * To Bulid this program, use [make build].
 * To Run this program, use [make run]. 
 * To Clean, use [make clean].
 * 
 * Alternatively, use [g++ -o main -std=c++11 
 *                      Admin.cpp CommandReceiver.cpp 
 *                      IOReceiver.cpp User.cpp Ticket.cpp main.cpp]
 *                to compile. And use [./main] to run.
 * 
 * This is the file that runs the entire project. The user is prompted to input a command.
 * The user can select three commands currently: login, buy, logout.
 * If you desire to login:
 *  - write "login" and press enter.
 *  - type in a name. ("Admin", "buyer", "seller", "fuller") 
 * If you desire to buy:
 *  - write "buy" and press enter.
 *  - Type in an Title(Title), a Seller(seller), and Amount(1-4)
 *  - affirm you want to buy by saying "yes"
 * If you desire to logout:
 *  - write "logout" and press enter.
 **/

#include <iostream>
#include <string>

#include "CommandReceiver.h"
#include "Ticket.h"
#include "User.h"
#include "IOReceiver.h"

using namespace std;

int main(int argc, char const *argv[])
{
    // Create CommnandReceiver
    IOReceiver io;
    io.setUserFile(argv[1]);
    io.setTicketFile(argv[2]);
    io.setTransactionFile(argv[3]);
    CommandReceiver cmd;
    // Create IOReceiver
    // Create Admin
    User admin(3, "Admin", 1000);
    User buyer(0, "buyer", 0);
    User seller(1, "seller", 0);
    User fuller(2, "fuller", 0);
    // Create Ticket
    Ticket ticket("Title", 10, 100, "seller");
    // Enter Commands

    //cmd.userList.push_back(buyer);
    //cmd.userList.push_back(seller);
    //cmd.userList.push_back(fuller);
    //cmd.ticketList.push_back(ticket);

    cmd.userList = io.readCurrentUser();
    cmd.ticketList = io.readAvaliableTickets();
    // cout << cmd.userList[0].getName();

    string s;
    bool session = true;

    while (session){
        cout << "Please Login." << endl;
        cout <<"Login Command: ";
        cin >> s;
        if(cin.fail()){
            exit(0);
        }
        if (s.compare("login") == 0){
            User user = cmd.login();
            if (user.getName().compare("NULL") == 0){
                cerr << "Invalid Username." << endl;
            } else {
                while (user.getLogin()){
                    cout << "Input Command:";
                    cin >> s;

                    bool command = cmd.receive(s);
                    if (command){
                        string result = cmd.select(s, &user);
                        if(result.compare("") != 1){
                            io.appendTransaction(result);
                        }
                    }
                }
            }
        }else if(s.size() > 25){
            cerr << "Error: Command length is beyond MAX_LENGTH." << endl;
        }

        if (s.compare("exit") == 0){
            session = false;
            io.writeTransactionFile();
        }
    }
}