/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.domain.payment;


public class BasePayment implements Payment {

  private final String currencyCode;
  private final double amount;

  public BasePayment(String currencyCode, double amount) {
    this.currencyCode = currencyCode;
    this.amount = amount;
  }

  @Override
  public String getCurrencyCode() {
    return currencyCode;
  }

  @Override
  public double getAmount() {
    return amount;
  }

}
