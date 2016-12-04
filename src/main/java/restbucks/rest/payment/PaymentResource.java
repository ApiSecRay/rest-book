/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.payment;

import org.springframework.hateoas.ResourceSupport;


public class PaymentResource extends ResourceSupport {

  private double amount;
  private String cardNumber;
  private String cardSecurityCode;
  private String cardholderName;
  private String currency;
  private double expiryMonth;
  private double expiryYear;
  private String paymentMethod;

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getCardSecurityCode() {
    return cardSecurityCode;
  }

  public void setCardSecurityCode(String cardSecurityCode) {
    this.cardSecurityCode = cardSecurityCode;
  }

  public String getCardholderName() {
    return cardholderName;
  }

  public void setCardholderName(String cardholderName) {
    this.cardholderName = cardholderName;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public double getExpiryMonth() {
    return expiryMonth;
  }

  public void setExpiryMonth(double expiryMonth) {
    this.expiryMonth = expiryMonth;
  }

  public double getExpiryYear() {
    return expiryYear;
  }

  public void setExpiryYear(double expiryYear) {
    this.expiryYear = expiryYear;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

}
