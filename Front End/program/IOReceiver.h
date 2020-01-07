#ifndef IOReceiver_H
#define IOReceiver_H

#include <string> 
#include <vector>
#include "Ticket.h"
#include "User.h"
/* A class that contains functions to read and write files
 * @author Colin Orian
 * @version 1.0
 * @since 2/12/2019
 */
class IOReceiver{
private:
    std::string userFileName;
    std::string ticketFileName;
    std::string transactionFileName;
    std::vector<std::string> transactions;
    /**
    * A simple file reader that will read over each line of the file
    * @param fileName The file you want to read
    * @return A vector that contains each line of the file.
    **/
    std::vector<std::string> readFile(string fileName);
    /*
    * Writes a vector of lines into a file
    * @param fileName The name of the file you want to write to
    * @param content A vector that contains the lines of the file.
    **/
    void writeFile(std::string fileName, std::vector<std::string> content);
public:
    /** 
    * Will read in the avaliable tickets and parse each line to create a ticket for that line.
    * @return A vector of each avaliable ticket. Each ticket will contain information regarding that ticket.
    **/
    std::vector<Ticket> readAvaliableTickets();
    /*
    *  Reads the current user file and parse each line
    **/
    std::vector<User> readCurrentUser();
    /*
    * Writes data into the transaction file
    * @param content what you're wanting to write to the file. Each element in the vector is a line
    */
    void writeTransactionFile();
    /*
     * Appends a transaction to the vector of transactions
     * @param transaction the transaction that you're append
     */
    void appendTransaction(string transaction);


    string removeTrailing(string name);

    void setTicketFile(string fileName);
    void setUserFile(string fileName);
    void setTransactionFile(string fileName);
};
#endif
