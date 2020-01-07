#ifndef COMMANDRECEIVER_H
#define COMMANDRECEIVER_H

class CommandReceiver;
class User;

#include <string> 
#include <vector>
#include "Ticket.h"
#include "User.h"
#include "IOReceiver.h"

/**
* CommandReceiver class that used to parse commands and send valid results to the IOReceiever.
* @author Gavin Gosling
* @version 1.0
* @since 2/5/2019
*/
class CommandReceiver{
public:
        std::vector<User> userList;
        std::vector<Ticket> ticketList;
       /**
       * Initalizes the Command Receiver class.
       */
        CommandReceiver();
       /**
       * Checks if the user is an admin.
       * @param User is the user being checked for being an admin.
       * @return  true if the user is an admin.
       *          false if the user is not an admin.
       */
        bool isAdmin(User user);
       /**
       * Checks if the user exists.
       * @param User is the user being checked for existing.
       * @return  true if the user exists.
       *          false if the user does not exist.
       */
        bool doesExist(string user);
       /**
       * Checks if the user has sell permissions.
       * @param User is the user being checked for sell permissions.
       * @return  true if the user has sell permissions.
       *          false if the user doesnt have sell permissions.
       */
        bool checkSell(User user);
       /**
       * Checks if the user has buy permissions.
       * @param User is the user being checked for buy permissions.
       * @return  true if the user has buy permissions.
       *          false if the user doesnt have buy permissions.
       */
        bool checkBuy(User user);
       /**
       * Checks if the command has less than MAX_LENGTH or more than MIN_LENGTH.
       * @param Command is the string sent to the receiver to be checked for valid length.
       * @return  true if the command is inbetween MAX_LENGTH and MIN_LENGTH.
       *          false if the command is not inbetween MAX_LENGTH and MIN_LENGTH.
       */
        bool checkLength(string command);
       /**
       * Checks if the command only has alphanumeric characters in it.
       * @param Command is the string sent to the receiver to be checked for valid characters.
       * @return  true if the command only contains alphanumeric characters.
       *          false if the command contains more than alphanumeric characters.
       */
        bool checkCharacters(string command);
       /**
       * Checks if the command has the correct amount of paramaters in it.
       * @param Command is the string sent to the receiver to be checked for valid paramater count.
       * @return  true if the command only contains alphanumeric characters.
       *          false if the command contains more than alphanumeric characters.
       */
        bool checkParamaters(int params, int max_param, int min_param);
       /**
       * Checks if the amount given is valid.
       * @param Amount is a float sent to receiver to check if it is valid.
       * @return  true if the amount is valid.
       *          false if the amount is invalid.
       */
        bool checkAmount(float amount, User u);
       /**
       * Checks if the price given is valid.
       * @param price is a float sent to receiver to check if it is valid.
       * @return  true if the price is valid.
       *          false if the price is invalid.
       */
        bool checkPrice(float price);
       /**
       * Checks if the tickets asked is valid.
       * @param asked is a int sent to receiver to check if it askeds for a valid amount of tickets.
       * @return  true if the tickets asked is less than or equal to available or more than 0.
       *          false if the tickets asked is invalid.
       */
        bool checkTickets(int available, int asked);
       /**
       * Checks if the user is logged in.
       * @param User is sent to the command receiver and is checked.
       * @return returns true if the user is logged out.
       *         returns false if the user isnt logged in.
       */
        static bool isLoggedin(User* user);
       /**
       * A general check used for each input.
       * @param command string is sent and it's length and characters are checked.
       * @return returns true if command string is correct.
       *         returns false if command string is incorrect.
       */
        bool receive(string s);
       /**
       * Selects the command to be followed.
       * @param command is the command which the user desires to execute.
       * @param user is the user who is calling for the command
       * @return returns the user found in the list.
       */
        string select(string command, User *user);
       /**
       * Finds a user in the user list
       * @param user is the user that is searched for in the list.
       * @return returns the user found in the list.
       */
        User findUser(string user);
       /**
       * Finds a ticket in the ticket list
       * @param ticket is the ticket that is searched for in the list.
       * @return returns the ticket found in the list.
       */
        Ticket findTicket(string ticket);
       /**
       * Affirms whether user accepts a buy command.
       * @param check is the response to the affirmation the user is requested in a command.
       * @return  true if "yes"
       *          false if "no".
       */
        bool check(string check);

        /*Adds extra spaces to a user's name if it's less than the max length
        * @param name the name before padding
        * @return the name after padding
        */
        string addPadding(string name);
        User login();

        bool checkSellNumber(int num);
        bool checkUserNameLength(string name);

        bool cheackCreditOverflow(User user,float credit);
        bool cheackCreditLack(User user,float credit);

};

#endif