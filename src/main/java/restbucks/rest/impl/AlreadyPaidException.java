/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class AlreadyPaidException extends IllegalArgumentException implements Identifiable {

  public AlreadyPaidException() {
    super("The order is already paid and cannot be changed anymore.");
  }

  @Override
  public String getId() {
    return Api.ERROR_ALREADY_PAID;
  }

}
