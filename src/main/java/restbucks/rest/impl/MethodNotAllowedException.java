/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class MethodNotAllowedException extends RuntimeException implements Identifiable {

  public MethodNotAllowedException() {
    super("The method you used is not supported on this URI.");
  }

  @Override
  public String getId() {
    return Api.ERROR_METHOD_NOT_ALLOWED;
  }

}
