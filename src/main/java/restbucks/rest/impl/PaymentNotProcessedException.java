/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class PaymentNotProcessedException extends IllegalArgumentException implements Identifiable {

  public PaymentNotProcessedException() {
    super("The payment you provided could not be processed.");
  }

  @Override
  public String getId() {
    return Api.ERROR_PAYMENT_NOT_PROCESSED;
  }

}
