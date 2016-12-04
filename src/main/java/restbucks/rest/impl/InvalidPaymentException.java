/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class InvalidPaymentException extends IllegalArgumentException implements Identifiable {

  public InvalidPaymentException() {
    super("The payment details you provided contain invalid values.");
  }

  @Override
  public String getId() {
    return Api.ERROR_INVALID_PAYMENT;
  }

}
