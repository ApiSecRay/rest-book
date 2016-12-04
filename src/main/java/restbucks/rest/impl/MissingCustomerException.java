/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class MissingCustomerException extends IllegalArgumentException implements Identifiable {

  public MissingCustomerException() {
    super("The order you provided doesn't contain a customer name.");
  }

  @Override
  public String getId() {
    return Api.ERROR_MISSING_CUSTOMER;
  }

}
