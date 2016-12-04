/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.receipt;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.hateoas.ResourceSupport;

import restbucks.rest.item.ItemResource;


public class ReceiptResource extends ResourceSupport {

  private String currency;
  private XMLGregorianCalendar dateTime;
  private ItemResource[] items;
  private String paymentMethod;
  private String shop;
  private double total;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public XMLGregorianCalendar getDateTime() {
    return dateTime;
  }

  public void setDateTime(XMLGregorianCalendar dateTime) {
    this.dateTime = dateTime;
  }

  public ItemResource[] getItems() {
    return items;
  }

  public void setItems(ItemResource[] items) {
    this.items = items;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public String getShop() {
    return shop;
  }

  public void setShop(String shop) {
    this.shop = shop;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

}
