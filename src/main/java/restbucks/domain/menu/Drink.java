package restbucks.domain.menu;

import java.util.Currency;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Drink {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String name;
  private double price;
  private Currency currency;
  
  protected Drink() {
    // For JPA
  }
  
  public Drink(String name, double price, Currency currency) {
    this.name = name;
    this.price = price;
    this.currency = currency;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public Currency getCurrency() {
    return currency;
  }
  
}
