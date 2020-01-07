/*
* @Author Colin Orian
* @Description A Class to represent the tickets. This is essentially a struct in c++
* @Since 03/09/2019
*/
public class Ticket{
  public String name;
  public String seller;
  public int numTickets;
  public float price;

  public Ticket(String name, String seller, int numTick, float price){
    this.name = name;
    this.seller = seller;
    this.numTickets = numTick;
    this.price = price;
  }
  /* Compares if the tickets are equal to each other
  *
  */
  public boolean equals(Ticket otherTicket){
    return this.name.compareTo(otherTicket.name) == 0;
  }
}
