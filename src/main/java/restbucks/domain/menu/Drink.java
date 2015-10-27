package restbucks.domain.menu;

import java.util.Currency;
import java.util.Locale;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.persistence.Entity;

import restbucks.domain.DomainObject;


@Entity
public class Drink extends DomainObject {

  private String name;
  private String milk;
  private String size;
  private double price;
  private String currency;
  
  protected Drink() {
    // For JPA
  }
  
  public Drink(String name, Milk milk, Size size, double price, String currency) {
    this.name = name;
    this.milk = milk.toString();
    this.size = size.toString();
    this.price = price;
    this.currency = currency;
  }

  public String getName() {
    return name;
  }

  public Milk getMilk() {
    return Milk.valueOf(milk);
  }

  public Size getSize() {
    return Size.valueOf(size);
  }

  public double getPrice() {
    return price;
  }

  public Currency getCurrency() {
    return Currency.getInstance(currency);
  }
  
  public MonetaryAmount getCost() {
    return Monetary.getDefaultAmountFactory()
        .setCurrency(currency)
        .setNumber(price)
        .create();
  }

  @Override
  public String toString() {
    return size.toLowerCase(Locale.getDefault()) + ' ' + milk.toLowerCase(Locale.getDefault())
        + " milk " + name;
  }
  
}
