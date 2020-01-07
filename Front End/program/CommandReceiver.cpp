#include <iostream>
#include <regex>
#include <string>
#include <algorithm>
#include <vector>
#include <iterator>

class CommandReceiver;
class User;

#include "Ticket.h"
#include "CommandReceiver.h"
#include "User.h"
#include "IOReceiver.h"

using namespace std;

using std::cout;
using std::string;
using std::vector;

const int MAX_LENGTH = 25;
const float MAX_MONEY = 99999.99;
const int MIN_MONEY = 0;
const int PRICE_MAX = 1000;

vector<User> userList;
vector<Ticket> ticketList;

CommandReceiver::CommandReceiver(){}

bool CommandReceiver::receive(string s){
    if(this->checkLength(s)){
        return true;
    }
    return false;
    //  && this->checkCharacters(s)
}

bool CommandReceiver::isLoggedin(User* user){
    if(user->getLogin() == true){
        return true;
    } else {
        return false;
    }
}

User CommandReceiver::login(){
    string name;
    cout << "Name: ";
    cin >> name;

    User u = this->findUser(name);    
    if(name.compare("NULL")==0){
        cerr << "User not found." << endl;
        User null = User(0, "NULL",0);
        return u;
    }
    u.setLogin(true);
    cout << "User logged in." << endl;
    return u;
}


string CommandReceiver::select(string s, User *user){
    if(s.compare("buy")==0){
        cout << "Buy initiatied." << endl;
        return user->buy(*this, *user);
    } else if(s.compare("sell")==0) {
        cout << "Sell initiatied." << endl;
        return user->sell(*this, *user);
    } else if(s.compare("logout")==0){
        cout << "Logout initiatied." << endl;
        return user->logout(*this, *user);
    } else if(s.compare("create")==0) {
        cout << "Create initiatied." << endl;
        return user->create(*this, *user);
    } else if(s.compare("refund")==0) {
        cout << "Refund initiatied." << endl;
        return user->refund(*this, *user);
    } else if(s.compare("addcredit")==0){
        cout << "addCredit initiatied." << endl;
        return user->addcredit(*this, *user);
    } else if(s.compare("delete")==0) {
        cout << "Delete User initiatied." << endl;
        return user->deleteUser(*this, *user);
    } else { 
        cerr << "No command selected" << endl;
        return "";
    }
}

bool CommandReceiver::isAdmin(User user){
    if(user.getType() == 3){
        return true;
    }
    cerr << "Error: User is not an admin" << endl;
    return false;
}

bool CommandReceiver::doesExist(string user){
    if(std::find(userList.begin(), userList.end(), user) != userList.end()){
        cout << "User exists." << endl;
        return true;
    } else {
        cerr << "Error: Is not a user." << endl;
        return false;
    }
}

bool CommandReceiver::check(string s){
    if(s.compare("yes")==0){
        return true;
    } else if(s.compare("no")==0) {
        return false;
    }
}

User CommandReceiver::findUser(string user){
    auto it = std::find(userList.begin(), userList.end(), user);
    // Get index of element from iterator
    int index = std::distance(userList.begin(), it);
    if(index >= 0 && index < userList.size()){
        return userList.at(index);
    } 
    cerr << "No user found" << endl;
    User null = User(0, "NULL",0);
    return null;
}

Ticket CommandReceiver::findTicket(string ticket){
    auto it = std::find(ticketList.begin(), ticketList.end(), ticket);
    // Get index of element from iterator
    int index = std::distance(ticketList.begin(), it);
    if(index >= 0 && index < ticketList.size()){
        return ticketList.at(index);
    } 
    cerr << "No ticket found" << endl;
    Ticket null = Ticket("NULL", 0, 1, "admin");
    return null;
}

bool CommandReceiver::checkBuy(User user){
    if(user.getType() != 1){
        return true;
    } else {
        cerr << "Error: User cannot buy." << endl;
        return false;
    }
}

bool CommandReceiver::checkSell(User user){
    if(user.getType() != 0){
        return true;
    }
    cerr << "Error: User cannot sell." << endl;
    return false;
}

bool CommandReceiver::checkLength(string command){
    if(command.length() < MAX_LENGTH || command.length() <= 0){
        return true;
    } else {
        cerr << "Error: Command length is beyond MAX_LENGTH or MIN_LENGTH." << endl;
        return false;
    }
}

bool CommandReceiver::checkCharacters(string command){
    std::regex regex("^[a-zA-Z0-9_]*$");
    std::smatch match;
    std::regex_search(command, match, regex);
    if(match.size() > 0){
        cerr << "Error: Only unicode characters accepted." << endl;
        exit(0);
        return false;
    }
    return true;
}


bool CommandReceiver::checkParamaters(int params, int max_param, int min_param){
    if(params > max_param || params < min_param){
        cerr << "Error: Incorrect amount of paramaters in command." << endl;
        return false;
    }
    return true;
}

bool CommandReceiver::checkAmount(float amount, User u){
    if(amount+u.getCredit() > MAX_MONEY || amount+u.getCredit() < MIN_MONEY){
        cerr << "Error: Cannot finish command since user will have an invalid amount of credits." << endl;
        return false;
    }
    return true;
}

bool CommandReceiver::checkPrice(float price){
    if(price > MAX_MONEY || price < MIN_MONEY){
        cerr << "Error: Invald price for object." << endl;
        return false;
    }
    return true;
}

bool CommandReceiver::checkTickets(int available, int asked){
    if(asked > available || asked < available){
        cerr<< "Error: Invalid amount of tickets requested." << endl;
        return false;
    }
    return true;
}

string CommandReceiver::addPadding(string name){
  if(name.size() < 16){
    int diff = 16-name.size();
    name.append(diff,' ');
  }
  return name;
}

bool CommandReceiver::checkSellNumber(int num){
    if(num >100 || num<0){
        cerr<<"Error: Number of tickets in one sell should not more than 100."<<endl;
        return false;
    }
    return true;
}

bool CommandReceiver::checkUserNameLength(string name){
    if(name.length() > 15 || name.length() < 0){
        cerr<<"Error: Username should be in 15 characters."<<endl;
        return false;
    }
    return false;
}

bool CommandReceiver::cheackCreditOverflow(User user,float credit){
    if(user.getCredit()-credit<0){
        cerr<<"ERROR: User does not has enough credit."<<endl;
        return false;
    }
    return true;
}
bool CommandReceiver::cheackCreditLack(User user,float credit){
     if(user.getCredit()+credit>MAX_MONEY){
        cerr<<"ERROR: User Reach maximun credit."<<endl;
        return false;
    }
    return true;
}
