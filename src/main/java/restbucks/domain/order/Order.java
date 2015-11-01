package restbucks.domain.order;

import java.util.Collection;
import java.util.Iterator;

import javax.money.MonetaryAmount;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import restbucks.domain.DomainObject;
import restbucks.domain.menu.Drink;
import restbucks.domain.payment.Payment;
import restbucks.rest.impl.AlreadyPaidException;
import restbucks.rest.impl.InvalidPaymentException;
import restbucks.rest.impl.MissingCustomerException;
import restbucks.rest.impl.MissingItemException;
import restbucks.rest.impl.OverpaidException;


@Entity
@Table(name = "sales") // ORDER is a reserved word in SQL
public class Order extends DomainObject {

  private String customer;
  @ManyToMany
  private Collection<Drink> drinks;
  private double paidAmount;

  protected Order() {
    // For JPA
  }
  
  public Order(String customer, Collection<Drink> drinks) {
    if (customer == null) {
      throw new MissingCustomerException();
    }
    this.customer = customer;
    if (drinks == null || drinks.isEmpty()) {
      throw new MissingItemException();
    }
    this.drinks = drinks;
  }

  public String getCustomer() {
    return customer;
  }

  public Collection<Drink> getDrinks() {
    return drinks;
  }

  public double getPaidAmount() {
    return paidAmount;
  }

  public MonetaryAmount getCost() {
    Iterator<Drink> iterator = drinks.iterator();
    MonetaryAmount result = iterator.next().getCost();
    while (iterator.hasNext()) {
      result.add(iterator.next().getCost());
    }
    return result;
  }

  public void acceptPayment(Payment payment) {
    MonetaryAmount cost = getCost();
    double totalAmount = cost.getNumber().doubleValue();
    if (totalAmount <= paidAmount) {
      throw new AlreadyPaidException();
    }
    if (!cost.getCurrency().getCurrencyCode().equals(payment.getCurrencyCode())) {
      throw new InvalidPaymentException();
    }
    double payingAmount = payment.getAmount();
    if (payingAmount > totalAmount - paidAmount) {
      throw new OverpaidException();
    }
    if (payingAmount < totalAmount - paidAmount) {
      // For now only accept payments for the whole amount
      throw new InvalidPaymentException();
    }
    paidAmount += payingAmount;
  }
  
  @Override
  public String toString() {
    return customer + " ordered " + drinks;
  }

}
