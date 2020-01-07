import java.util.ArrayList;
/*
* @Author Colin Orian
* @Description A class that will execute all the transactions on the database
*               and modify the database
* @since 03/09/2019
*/
public class Executer{
  public static ArrayList<Ticket> tickets;
  public static ArrayList<User> users;
  private ArrayList<String> transactions;

  private ArrayList<Ticket> buyTickets = new ArrayList<>();
  private ArrayList<String> buyTrans = new ArrayList<>();

  public Executer(){
    Parser parser = new Parser();
    tickets = parser.requestTickets();
    users = parser.requestUsers();
    transactions = parser.requestTransactions();
  }
  /*
  * @Description Attempt to execute every transaction and make modifcations to the database
  */
  public void executeTransactions(){
    //Iterate over each transaction. current Transaction = trans
    for(String trans: transactions){

      int code = Integer.parseInt(trans.substring(1, 2));
      //Execute the command of the transaction
      switch(code){
        case 0:
          logoff(trans);
          break;
        case 1:
          create(trans);
          break;
        case 2:
          delete(trans);
          break;
        case 3:
          sell(trans);
          break;
        case 4:
          addbuy(trans);
          break;
        case 5:
          refund(trans);
          break;
        case 6:
          addCredit(trans);
          break;
        default:
            System.out.println("ERROR: Unknown transaction\nTransaction-> " + trans);
            break;
      }
    }
  }
  /*
  * Adds a ticket to the tickets that the user wants to buy
  */
  private void addbuy(String trans){
      String eventName = trans.substring(3, 28).trim();
      String seller = trans.substring(29, 44).trim();
      int numTickets = Integer.parseInt(trans.substring(45, 48));
      float price = Float.parseFloat(trans.substring(49, 55));
      Ticket ticket = new Ticket(eventName, seller, numTickets, price);
    		  
      if(findTicket(ticket) == -1){
        System.out.println("ERROR: Ticket isn't in the system.\n Transaction-> " + trans);
      }else{
        // Append terminal output
    	SystemIO.appendOutput("A user is trying to buy ticket named: [" + eventName + "]");
    	  
        buyTickets.add(ticket);
        buyTrans.add(trans);
      }
      

  }
  private void logoff(String trans){
    String userName = trans.substring(3, 18).trim();
    String type = trans.substring(19, 21);
    float credits = Float.parseFloat(trans.substring(22, 31));
    User tempUser = new User(type, userName, credits);
    //Attempt to execute all the buy() operations
    if(buyTickets.size() != 0){
      for(int i = 0; i < buyTickets.size(); i++){
    	  buy(tempUser, buyTickets.get(i), buyTrans.get(i));
      }
      buyTickets.clear();
      buyTrans.clear();
      // Append terminal output
      SystemIO.appendOutput("[" + userName + "] is logging off. Purchases being updated");
    
    }
  }
  
  private int findUserIndexByName(String username){
	    int index = -1;
	    for(int i = 0; i < users.size(); i ++){
	      if(username.equals(users.get(i).name)) {
	        index = i;
	      }
	    }
	    return index;
	  }

  private int findTicketIndexByName(String ticket){
	    int index = -1;
	    for(int i = 0; i < tickets.size(); i ++){
	      if(ticket.equals(tickets.get(i).name)) {
	        index = i;
	      }
	    }
	    return index;
	  }

  /**
  *Find thes index that the user is at in the list
  * @param user the thing you're looking for
  * @return the index of the user. -1 if the user isn't in the list
  */
  private int findUser(User user){
    int index = -1;
    for(int i = 0; i < users.size(); i ++){
      if(user.equals(users.get(i))){
        index = i;
      }
    }
    return index;
  }
  /**
  *Find thes index that the ticket is at in the list
  * @param ticket the thing you're looking for
  * @return the index of the ticket. -1 if the ticket isn't in the list
  */
  private int findTicket(Ticket ticket){
    int index = -1;
    for(int i = 0; i < tickets.size(); i ++){
      if(ticket.equals(tickets.get(i))){
        index = i;
      }
    }
    return index;
  }
  /*
  * Executes the create command to create a new user
  * @param trans the transaction you want to execute
  **/
  private void create(String trans){
    String userName = trans.substring(3, 18).trim();
    String type = trans.substring(19, 21);
    float credits = Float.parseFloat(trans.substring(22, 31));
    User tempUser = new User(type, userName, credits);

    if(findUser(tempUser) != -1){
      System.out.println("ERROR: User is already in the system.\nTransaction-> " + trans);
    }else{
      users.add(tempUser);
      // Append terminal output
      SystemIO.appendOutput("[" + userName + "] is a new user being created");
    
    }
  }
  /*
  * Executes the delete command to delete a user
  * @param trans the transaction you want to execute
  **/
  private void delete(String trans){
    String userName = trans.substring(3, 18).trim();
    String type = trans.substring(19, 21);
    float credits = Float.parseFloat(trans.substring(22, 31));
    User tempUser = new User(type, userName, credits);
    int index = findUser(tempUser);

    if(index == -1){
      System.out.println("ERROR: User is not in the system.\nTransaction-> " + trans);
    }else{
	  // Append terminal output
      SystemIO.appendOutput("[" + userName + "] is deleting a user named: [" + 
    		  users.get(index).name + "]");   
      
      users.remove(index);
      //SystemIO.users.remove(index);
    }
    
  }
  /*
  * Executes the addCredit command to give a user credits
  * @param trans the transaction you want to execute
  **/
  private void addCredit(String trans){
    String userName = trans.substring(3, 18);
    String type = trans.substring(19, 21);
    float credits = Float.parseFloat(trans.substring(22, 31));
    User tempUser = new User(type, userName, credits);

    int index = findUser(tempUser);
    if(index == -1){
      System.out.println("ERROR: User is not in the system.\nTransaction-> " + trans);
    }else{
      //TODO FIX BUG. DOESN'T ADD CREDIT, SETS CREDIT
      users.get(index).credit += credits;
    }
  }
  /*
  * Executes the sell command to sell a ticket to a user
  * @param trans the transaction you want to execute
  **/
  private void sell(String trans){
    String eventName = trans.substring(3, 28).trim();
    String seller = trans.substring(29, 44).trim();
    int numTickets = Integer.parseInt(trans.substring(45, 48));
    float price = Float.parseFloat(trans.substring(49, 55));
    Ticket ticket = new Ticket(eventName, seller, numTickets, price);
    
    if(findTicket(ticket) == -1){
      tickets.add(ticket);
    }else{
      System.out.println("ERROR: Ticket is already in the system.\nTransaction->" + trans);
    }
    
    // Append terminal output
    SystemIO.appendOutput("[" + seller + "] is selling a ticket named: [" + eventName +
    		"], for a total of [" + numTickets + "] ticket(s) for [" + price + "] each");
  }
  /*
  * Executes the buy command to let a user buy a ticket
  * @param trans the transaction you want to execute
  **/
  private void buy(User user, Ticket ticket, String trans){
    int index = findTicket(ticket);
    int numBought = ticket.numTickets;
    int userIndex = findUserIndexByName(user.name);	// to append user to "users" updated credits 
    int ticketIndex = findTicketIndexByName(ticket.name);	// to append to "tickets" updated amount 
    float initialCredits = user.credit;

    //User attempt to buy more tickets than there actual are. 
    if(tickets.get(ticketIndex).numTickets < ticket.numTickets){
      SystemIO.appendOutput("ERROR: " + user.name + " attempted to buy more tickets than available tickets.");
      SystemIO.appendOutput("Available Tickets: " + tickets.get(ticketIndex).numTickets + " | Trying to buy: " + ticket.numTickets);
      numBought = tickets.get(index).numTickets - ticket.numTickets;
    }

    user.credit -= ticket.price * numBought;
    if(user.credit < 0){
      System.out.println("ERROR: User can't have less than 0 credits\n Transaction->" + trans);
      user.credit += ticket.price * numBought;
    }else if(tickets.get(index).numTickets <= ticket.numTickets){
      //There aren't any more tickets left
      tickets.remove(index);
    }
    
    // Update credits and ticket amounts to the users and tickets class
    users.get(userIndex).credit = user.credit;
    tickets.get(ticketIndex).numTickets -= numBought;
    
    // Update credits of the seller
    String sellerName = tickets.get(ticketIndex).seller;
    int sellerIndex = findUserIndexByName(sellerName);
    users.get(sellerIndex).credit += ticket.price * numBought;
    
    // Append terminal output
    SystemIO.appendOutput("[" + user.name + "] purchased ticket from event: [" + ticket.name +
    		"], buying [" + numBought + "] ticket(s) for [" + ticket.price + "]\n" +
    		"Credits before and after: [" + initialCredits + "] and [" + user.credit + "]");
   
    
  }
  /*
  * Executes the refund command to refund a ticket purchase
  * @param trans the transaction you want to execute
  **/
  private void refund(String trans){
    String userName = trans.substring(3, 18).trim();
    String seller = trans.substring(19, 34).trim();
    float credit = Float.parseFloat(trans.substring(35, 44));

    User userBuyer = new User(userName, "NL", -1); //Used to find the user in the list. Not an actual user
    int indexBuyer = findUser(userBuyer);

    User userSeller = new User(seller, "NL", -1);
    int indexSeller = findUser(userSeller);
    if(indexBuyer == -1 && indexSeller == -1){
      System.out.println("ERROR: Buyer and Seller is not in the system.\nTransaction-> " + trans);
    }else if(indexBuyer == -1){
      System.out.println("ERROR: Buyer is not in the system.\nTransaction-> " + trans);
    }else if(indexSeller == -1){
      System.out.println("ERROR: Seller is not in the system.\nTransaction-> " + trans);
    }else{
      users.get(indexBuyer).credit += credit;
      users.get(indexSeller).credit -= credit;
      SystemIO.appendOutput("[" + userName + "] is refunding [" + userSeller.name + "] an amount of [" + credit + "]");
    }
  }
  
}
