/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class InvalidItemException extends IllegalArgumentException implements Identifiable {

  public InvalidItemException() {
    super("The attributes you provided are invalid for the requested menu item.");
  }

  @Override
  public String getId() {
    return Api.ERROR_INVALID_ITEM;
  }

}
