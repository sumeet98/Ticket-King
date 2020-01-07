#include "IOReceiver.h"
#include <string> 
#include <iostream>
#include <fstream>
#include <algorithm>
class User;

std::vector<std::string> IOReceiver::readFile(std::string fileName){ 
    std::ifstream inStream;
    std::vector<std::string> content;
    std::string line;
    inStream.open(fileName);
    //Error checking
    if(!inStream){
        std::cout << "Error: File does not exist." << std::endl;
        content.push_back("-1");
    }else{
        while(std::getline(inStream, line)){
            content.push_back(line);
        }
    }

    inStream.close();
    return content;
}

std::vector<Ticket> IOReceiver::readAvaliableTickets(){
    std::vector<std::string> lines;
    lines = readFile(ticketFileName);
    
    std::vector<Ticket> tickets;
    //std::cout << lines[0] << endl;
    for(int i = 0; i < lines.size(); i ++){
        
        std::string name = lines[i].substr(0, 19);
        //FIX-THIS: parse tickets
        name = removeTrailing(name);

        std::string seller = lines[i].substr(20, 13);
        
        int numTickets = stoi(lines[i].substr(36, 4));
        
        float price = stof(lines[i].substr(40, 6));
        
        Ticket tempTick = Ticket(name,price,numTickets,seller);
        tickets.push_back(tempTick);
    }
    return tickets;
}

std::vector<User> IOReceiver::readCurrentUser(){
    std::vector<std::string> lines;
    lines = readFile(userFileName);
    std::vector<User> users;
    
    for(int i = 0; i < lines.size(); i ++){
        std::string line = lines[i];
        
        std::string name = line.substr(0, 15);

        name = removeTrailing(name);
        std::string type = line.substr(16, 2);
        int typeInt;
        if(type.compare("BS")==0){
            typeInt = 0;
        }else if(type.compare("SS")==0){
            typeInt = 1;
        }else if(type.compare("FS")==0){
            typeInt = 2;
        }else if(type.compare("AA")==0){
            typeInt = 3;
        }

        std::string credit = line.substr(19, 9);
        int index = 0;
        bool foundMoney = false;
        while(!foundMoney){
            if(credit[index] != '0'){
                foundMoney = true;
            }else{
                index ++;
            } 
        }
        
        credit = credit.substr(index, credit.size());
        double money = stod(credit);
       //Append user to vector
       User tempUser(typeInt, name, money);
       users.push_back(tempUser);
    }
    return users;
}

void IOReceiver::writeFile(std::string fileName, std::vector<std::string> content){
    std::ofstream outFile;
    if(outFile){
        outFile.open(fileName);
        //Itterate over the vector and append a new line to the end of it
        for(int i = 0; i < content.size(); i ++){
            outFile << content[i] << "\n";
        }
    }else{
        std::cout << "Error: File does not exist." << endl;
    }
    
    
    outFile.close();
}


string IOReceiver::removeTrailing(string name){
    int length = name.size();
    int lastNonSpace = length;
    bool lastFound = false;
    int i = length - 1;
    while(!lastFound){
        if(name[i] != ' '){
            lastFound = true;
        }else{
            i --;
        }
    }

    return name.substr(0, i+1);
}
void IOReceiver::writeTransactionFile(){
    writeFile(transactionFileName, transactions);
}

void IOReceiver::appendTransaction(std::string transaction){
    transactions.push_back(transaction);
}

void IOReceiver::setTicketFile(string fileName){
    ticketFileName = fileName;
}

void IOReceiver::setUserFile(string fileName){
    userFileName = fileName;
}

void IOReceiver::setTransactionFile(string fileName){
    transactionFileName = fileName;
}