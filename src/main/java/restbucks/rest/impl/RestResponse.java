/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;


public class RestResponse<T> {

  private final T payload;
  private final Collection<String> excludedActions = new ArrayList<>();
  private final Map<String, String> parameters = new HashMap<>();
  private HttpStatus status = HttpStatus.OK;

  public RestResponse(T payload) {
    this.payload = payload;
  }

  public T getPayload() {
    return payload;
  }

  public void deny(String action) {
    excludedActions.add(action);
  }

  public boolean allows(String action) {
    return !excludedActions.contains(action);
  }

  public String getParameter(String name) {
    return parameters.get(name);
  }

  public void setParameter(String name, String value) {
    parameters.put(name, value);
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

}
