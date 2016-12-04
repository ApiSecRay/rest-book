/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class OverpaidException extends IllegalArgumentException implements Identifiable {

  public OverpaidException() {
    super("The amount of money you paid is more than what the order costs.");
  }

  @Override
  public String getId() {
    return Api.ERROR_OVERPAID;
  }

}
