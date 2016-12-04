/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.order;

import org.springframework.hateoas.ResourceSupport;

import restbucks.rest.item.ItemResource;


public class OrderResource extends ResourceSupport {

  private String currency;
  private String customer;
  private ItemResource[] items;
  private double total;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public ItemResource[] getItems() {
    return items;
  }

  public void setItems(ItemResource[] items) {
    this.items = items;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

}
