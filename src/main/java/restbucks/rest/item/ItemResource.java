/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.item;

import org.springframework.hateoas.ResourceSupport;


public class ItemResource extends ResourceSupport {

  private String currency;
  private String milk;
  private String name;
  private double price;
  private String size;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getMilk() {
    return milk;
  }

  public void setMilk(String milk) {
    this.milk = milk;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

}
