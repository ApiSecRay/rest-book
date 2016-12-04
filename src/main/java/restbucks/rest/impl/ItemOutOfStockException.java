/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class ItemOutOfStockException extends IllegalArgumentException implements Identifiable {

  public ItemOutOfStockException() {
    super("The menu item you requested is temporarily out of stock.");
  }

  @Override
  public String getId() {
    return Api.ERROR_ITEM_OUT_OF_STOCK;
  }

}
