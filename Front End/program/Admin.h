#ifndef ADMIN_H
#define ADMIN_H

#include <string> 
#include <vector>
#include "User.h"
/*
* A class that represents users with higher priviliges compared to other users. 
* @author Colin Orian
* @version 1.0
* @since 2/15/2019
*/
class Admin: public User{
    public:
        /*
        * Creates a user with certian priviliges, money and user name
        * @param command The different attributes of the user.
        * @return the new user
        * */
        void create(std::string command);
        /*
        * Removes a user from the data base
        * @param command The user to delete
        */
        void deleteUser(std::string command);
        /*
        * A command to move money from a Seller's account to a Buyer's account
        * @param command FIX
        */
        void refund(std::string command);
        /*
        * A command to give a user credit
        * @param command FIX
        */
        void addCredit(std::string command);
};
#endif