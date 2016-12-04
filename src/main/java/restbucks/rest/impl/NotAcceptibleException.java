/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class NotAcceptibleException extends RuntimeException implements Identifiable {

  public NotAcceptibleException() {
    super("The media type you requested in the Accept header is not supported for this method on this URI.");
  }

  @Override
  public String getId() {
    return Api.ERROR_NOT_ACCEPTIBLE;
  }

}
