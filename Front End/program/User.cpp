class User;
class CommandReceiver;

#include "CommandReceiver.h"
#include "Ticket.h"
#include "User.h"
#include <string>
#include <iostream>
using namespace std;

User::User(int type, string name, float credit){
  this->type = type;
  this->name = name;
  this->credit = credit;
  this->isloggedin=false;
}

string User::logout(CommandReceiver cmd, User& user){
      user.setLogin(false);
      cout << user.getLogin() << endl;
      cout << "User " << user.getName() << " logged out." << endl;
      return "00 " + cmd.addPadding(user.getName()) + " " + std::to_string(user.getCredit());
}

string User::buy(CommandReceiver cmd, User user){
    if(!cmd.checkBuy(user)){
      cerr << "User does not have the privileges to buy." << endl;
      return "";
    }

    string title;
    string seller;
    string affirm;
    int number;
    int tickets;

    cout << "Title: ";
    cin >> title;
    if(!cmd.receive(title)){
      cerr << "Invalid title inputted." << endl;
      return "";
    }

    cout << "Seller: ";
    cin >> seller; 
    if(!cmd.receive(seller)){
      cerr << "Invalid title inputted." << endl;
      return "";
    }

    cout << "Number: ";
    cin >> number;
    if(!cmd.receive(std::to_string(number))){
      cerr << "Invalid number tickets." << endl;
      return "";
    }

    Ticket t = cmd.findTicket(title);
    if(t.getTitle().compare("NULL")==0){
      cerr << "Title not found." << endl;
      return "";
    }

    float cpm = t.getPrice();
    printf("Cost per ticket: %f, Total cost: %f\n", cpm, cpm*number);
    cout << "Select: yes or no" << endl;
    cin >> affirm;
    
    if(!cmd.receive(affirm)){
      cerr << "Invalid affirmation." << endl;
      return "";
    }
    if(!cmd.check(affirm)){
      cerr << "User rejected the buy." << endl;
      return "";
    }

    if(!cmd.checkAmount(cpm*number, user)){
      cerr << "User lacks required funds." << endl;
      return "";
    }

    User sell = cmd.findUser(seller);
    if(sell.getName().compare("NULL") ==0){
      cerr << "Seller not found." << endl;
      return "";
    }

    tickets = t.getNumberTicket();
    t.setNumberTicket(tickets - number);

    cout << user.getName() << " bought ticket(s) for " << title << " at the price of " << cpm  << "$ each." << endl;
    return "04 " + t.getTitle() + " " + sell.getName() + " " + std::to_string(t.getNumberTicket()) + " " + std::to_string(cpm);
}
  
string User::sell(CommandReceiver cmd,User user){
    if(!cmd.checkSell(user)){
      cerr << "User does not have the privileges to sell." << endl;
      return "";
    }

    string title;
    float price;
    int number;

    cout << "Title:";
    cin >> title;
    if(!cmd.receive(title)){
      cerr << "Invalid title inputted." << endl;
      return "";
    }
    Ticket t = cmd.findTicket(title);
    if(!t.getTitle().compare("NULL")==0){
      cerr << "Title not found." << endl;
      return "";
    }


    cout << "price:";
    cin >> price; 
    if(!cmd.checkPrice(price)){
      cerr << "Invalid price inputted." << endl;
      return "";
    }
    cout << "Number:";
    cin >> number;
  
    if(!cmd.checkSellNumber(number)){
      cerr << "Invalid number of tickets inputted." << endl;
      return "";
    }

    cout << user.getName() << " create a ticket sell for " << title << " at the price of " << price << "$ each." << endl;
    return "03 " + title + " " + cmd.addPadding(user.getName()) + " " + std::to_string(number) + " " + std::to_string(price);
}

string User::create(CommandReceiver cmd, User user){
  if(!cmd.isAdmin(user)){
    cerr << "User is not an admin." << endl;
    return "";
  }

  string type;
  string name;
  float credits;

  cout << "Name:";
  cin >> name;

  User u = cmd.findUser(name);
  if(u.getName().compare("NULL")==0){
    cerr << "User not found." << endl;
    return "";
  }

  cout << "Select one of four (BS, SS,FS, AA):";
  cin >> type;
  if(!(type == "SS" || type == "BS" || type == "FS" || type == "AA")){
    cerr << "Incorrect user type." << endl;
    return "";
  }

  cout << "Credits:";
  cin >> credits;
  if(credits > 999999 || credits < 0){
    cerr << "Invalid amount of credits specified." << endl;
    return "";
  }

  cout << "Created user " << name << " of type " << type << " with a credit amount of " << credits  << "." << endl;
  return "01 " + cmd.addPadding(name) + " " + type + " " + std::to_string(credits);
}

string User::refund(CommandReceiver cmd,User user){
  if(!cmd.isAdmin(user)){
    cerr << "User is not an admin." << endl;
    return "";
  }

  string buyer;
  string seller;
  float credits;

  cout<<"Buyer Username:";
  cin >> buyer;
  if(!cmd.receive(buyer)){
    cerr << "Buyer is invalid." << endl;
    return "";
  }

  cout<<"Seller Username:";
  cin >> seller;
  if(!cmd.receive(seller)){
    cerr << "Seller is invalid." << endl;
    return "";
  }

  cout<<"Credit Amount:";
  cin >> credits;

  User usell = cmd.findUser(seller);
  if(usell.getName().compare("NULL")==0){
    cerr << "Seller not found." << endl;
    return "";
  } 
  User ubuy = cmd.findUser(buyer);
  if(ubuy.getName().compare("NULL")==0){
    cerr << "Buyer not invalid." << endl;
    return "";
  }

  if(ubuy.getName().compare(usell.getName())==0){
    cerr << "Cannot refund to yourself." << endl;
    return "";
  }

  float scredit = usell.getCredit();
  float bcredit = ubuy.getCredit();

  if(cmd.cheackCreditOverflow(usell,credit) && cmd.cheackCreditLack(ubuy,credit)){
    usell.setCredit(scredit-credits);
    ubuy.setCredit(bcredit+credits);
    return "";
  }

  cout << buyer << " refunded " << credits << " from " << seller;
  return "05 " + cmd.addPadding(buyer) + " " + cmd.addPadding(seller) + " " + std::to_string(credits);
}

string User::deleteUser(CommandReceiver cmd, User user){
  if(!cmd.isAdmin(user)){
    cerr << "User is not an admin." << endl;
    return "";
  }
  cout << "Enter name of user to be deleted. (Cannot be current user):" << endl;
  cin >> name;

  User u = cmd.findUser(name);
  if(u.getName().compare("NULL")){
    cerr << "User not found." << endl;
    return "";

  } else if(user.getName().compare(name)){
    cerr << "Cannot delete yourself." << endl;
    return "";

  } else {

    cout << "An admin deleted " << name << "which was of type " << user.getTypeS() << "and had " << user.getCredit() << " credits" << endl;
    return "02 " + cmd.addPadding(user.getName()) + " " + user.getTypeS() + " " + std::to_string(user.getCredit());
  }
}

string User::addcredit(CommandReceiver cmd, User user){
  if(!cmd.isAdmin(user)){
    cerr << "User is not an admin." << endl;
    return "";
  }

  cout << "Enter user to add credits to: ";
  string userName;
  cin >> userName;

  if(cmd.doesExist(userName)){

    float credits;
    cout << "Enter the number of credits to add: ";
    cin >> credits;
    cout << endl;

    if(credits >= 0){

        User newUser = cmd.findUser(userName);
        if(cmd.cheackCreditOverflow(newUser,credit)){
          return "";
        }

        newUser.setCredit(newUser.getCredit() + credit);
        string code;

        return "06 " + cmd.addPadding(newUser.getName()) + " " + newUser.getTypeS() + " " + to_string(newUser.getCredit());
    }else{
      cerr << "Error:  credit to add cannot be over 1000." << endl;
      return "";
    }
  }else{
    return "";
  }
}

bool User::operator==(string otherUser){
    return(name == otherUser);
}

//getters and setters.
string User::getTypeS(){
  if(this->type == 0){
      return "BS";
  } else if(this->type == 1){
      return "SS";
  } else if(this->type == 2){
      return "FS";
  } else {
      return "AA";
  }
}

int User::getType(){
  return this->type;
}

void User::setType(int t){this->type=t;}

 string User::getName(){return this->name;}

void User::setName( string n){this->name=n;}

vector<Ticket> User::getTickets(){return this->tickets;}

void User::setTickets( vector<Ticket> t){this->tickets=t;}

bool User::getLogin(){
  return this->isloggedin;
}

void User::setLogin(bool type){
  this->isloggedin = type;
}

float User::getCredit(){return this->credit;}

void User::setCredit(float c){this->credit=c;}
