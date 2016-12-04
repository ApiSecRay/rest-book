/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class ServiceUnavailableException extends RuntimeException implements Identifiable {

  public ServiceUnavailableException() {
    super("The server is temporarily not able to handle the request.");
  }

  @Override
  public String getId() {
    return Api.ERROR_SERVICE_UNAVAILABLE;
  }

}
