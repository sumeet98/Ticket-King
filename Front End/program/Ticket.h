#ifndef TICKET_H
#define TICKET_H
  #include <string>
  using namespace std;
  /**
   * Ticket class that stored information of A event ticket.
   * @author Yudi Tao
   * @version 1.0
   * @since 2/5/2019
   */
  class Ticket{
    private:
      string title;
      float price;
      int numberTicket;
      string seller;
    public:
      /**
       * Initicalize the Ticket class.
       * @param title pass the event title.
       * @param price pass the price of the ticket.
       * @param numberTicket pass the amount of the ticket.
       * @param seller pass the seller name;
       */
      Ticket(string title,float price,int numberTicket,string seller);
      /**
       * get event title
       * @return title the event title
       */
      string getTitle();
      /**
       * set event title
       * @param t pass the event title
       */
      void setTitle(string t);
       /**
       * get ticket price
       * @return price the ticket price.
       */
      float getPrice();
      /**
       * set ticket price.
       * @param p pass the price of a ticket
       */
      void setPrice(float p);
      /**
       * get amount of  tickets.
       * @return numberticket the number of tickets to sell.
       */
      int getNumberTicket();
      /**
       * set number of tickets to sell.
       * @param n pass number of the ticket to sell.
       */
      void setNumberTicket(int n);
      /**
       * get seller of tickets.
       * @return seller the user name of tickets that is selling.
       */
      string getSeller();
      /**
       * set seller of tickets.
       * @param s pass user name of the ticket that is selling.
       */
      void setSeller(string s);
      /*
      * Compares two tickets to each other. Returns true if they are the same ticket
      * @param otherTicket The ticket you're comparing to
      * @return true if the tickets are the same. False other wise.
      **/
      bool operator==(string otherTicket);

  };
#endif
