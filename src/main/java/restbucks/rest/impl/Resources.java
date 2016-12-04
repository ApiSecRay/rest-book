/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;


public interface Resources {


  // URIs

  String URL_MENU = "/menu/";
  String URL_ORDERS = "/orders/";
  String URL_ORDER = "/orders/{order-id}/";
  String URL_PAYMENTS = "/orders/{order-id}/payments/";
  String URL_SERVING = "/orders/{order-id}/serving/";

}
