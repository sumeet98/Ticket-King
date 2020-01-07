/*
* @Author Colin Orian
* @Description A Class to represent the users. This is essentially a struct in c++
* @Since 03/09/2019
*/
public class User{
  public String type;
  public String name;
  public float credit;

  public User(String type, String name, float credit){
    this.type = type;
    this.name = name;
    this.credit = credit;
  }
  /* 
  * Compares if the values are the same users
  */
  public boolean equals(User usr){
    return this.name.compareTo(usr.name) == 0;
  }
}
