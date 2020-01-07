#ifndef USER_H
#define USER_H
  #include <string>
  #include <vector>

  class User;
  class CommandReceiver;

  #include "Ticket.h"
  #include "CommandReceiver.h"
  
  using namespace std;

  /**
   * User class that used to stored user type, user name, tickets that user has.
   * @author Yudi Tao  
   * @version 1.0
   * @since 2/5/2019
   */
  class User{
    private: 
      int type;
      float credit;
      string name;
      bool isloggedin;
      vector<Ticket> tickets;
    public:
      /**
       * Initalize the user class.
       * @param type pass the user type represent by int, 0 = BS 1 = SS 2 = FS 3 = AA.
       * @param name pass the name of the user.
       * @param ticket pass the ticket vector that the user has.
       */
      User();
      User(int type, string name,float credit);
      /**
      * Will Send logout signal into CommandReceiver.
      * @param cmd takes into the Commandreciver to build connection 
      * @return  true if command running successfully.
      *         false if command hit errors.
      */
      string logout(CommandReceiver cmd, User& user);
      /**
      * Will Send buy signal into CommandReceiver.
      * @param cmd takes into the Commandreciver to build connection 
      * @return  true if command running successfully.
      *          false if command hit errors.
      */
      string buy(CommandReceiver cmd, User user);
      /**
      * Will Send sell signal into CommandReceiver.
      * @param cmd takes into the Commandreciver to build connection
      * @return  true if command running successfully.
      *          false if command hit errors.
      */
      string sell(CommandReceiver cmd,User user);
      /** 
      * get user type.
      * @return type type of user.
      */
      int getType();
      string getTypeS();
      /**
      * set user type.
      * @param t pass type of user.
      */
      void setType(int t);
      /**
      * get user credit.
      * @return credit credit that user have.
      */
      float getCredit();
      /**
      * set user credit.
      * @param c pass credit that user have.
      */
      void setCredit(float c);
      /**
      * get user name.
      * @return name name of user.
      */
       string getName();
      /**
      * set user name.
      * @param n pass user name.
      */
      void setName( string n);
      /**
      * get user tickets.
      * @return ticket user tickets;
      */
       vector<Ticket> getTickets();
      /**
      * set user tickets.
      * @param t pass tickets to user.
      */
      void setTickets( vector<Ticket> t);
      /*
      * Compares if two users are the same. 
      * @param otherUser the user we're comparing too
      * @return true if the users are the same. False otherwise
      */
      bool operator==(string otherUser);
      bool getLogin();
      void setLogin(bool type);
      string create(CommandReceiver cmd,User user);
      string deleteUser(CommandReceiver cmd,User user);
      string refund(CommandReceiver cmd,User user);
      string addcredit(CommandReceiver cmd,User user);
  };
#endif

