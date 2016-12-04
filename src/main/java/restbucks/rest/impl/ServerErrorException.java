/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import restbucks.rest.api.Api;


public class ServerErrorException extends IllegalStateException implements Identifiable {

  public ServerErrorException() {
    super("Something went wrong on our side. We have logged the problem so our staff can look into it. "
        + "We are sorry for the inconvenience.");
  }

  @Override
  public String getId() {
    return Api.ERROR_SERVER_ERROR;
  }

}
