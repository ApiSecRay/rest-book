/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class NotFoundException extends RuntimeException implements Identifiable {

  public NotFoundException() {
    super("The URI you requested doesn't exists or is not accessible by you.");
  }

  @Override
  public String getId() {
    return Api.ERROR_NOT_FOUND;
  }

}
