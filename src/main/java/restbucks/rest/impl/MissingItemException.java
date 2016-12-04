/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class MissingItemException extends IllegalArgumentException implements Identifiable {

  public MissingItemException() {
    super("The order you provided doesn't contain any menu item.");
  }

  @Override
  public String getId() {
    return Api.ERROR_MISSING_ITEM;
  }

}
