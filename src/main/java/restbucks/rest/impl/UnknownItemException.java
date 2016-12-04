/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class UnknownItemException extends IllegalArgumentException implements Identifiable {

  public UnknownItemException() {
    super("The menu item you requested is unknown to the server.");
  }

  @Override
  public String getId() {
    return Api.ERROR_UNKNOWN_ITEM;
  }

}
