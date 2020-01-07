#include "Ticket.h"
using namespace std;

Ticket::Ticket(string title,float price,int numberTicket,string seller){
  this->title=title;
  this->price=price;
  this->numberTicket=numberTicket;
  this->seller=seller;
}

string Ticket::getTitle(){return this->title;}

void Ticket::setTitle(string t){this->title=t;}

float Ticket::getPrice(){return this->price;}

void Ticket::setPrice(float p){this->price=p;}

int Ticket::getNumberTicket(){return this->numberTicket;}
void Ticket::setNumberTicket(int n){this->numberTicket;}

string Ticket::getSeller(){return this->seller;}

void Ticket::setSeller(string s){this->seller=s;}

bool Ticket::operator==(string otherTicket){
  return this->title==otherTicket;
}
