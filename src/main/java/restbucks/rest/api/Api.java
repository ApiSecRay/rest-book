/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.api;


public interface Api {


  // URIs

  String URL_BILLBOARD = "/";


  // Link relations

  /**
   * See http://www.w3.org/TR/json-ld/#node-identifiers.
   */
  String LINK_REL_ID = "@id";
  /**
   * See http://schema.org/DeleteAction.
   */
  String LINK_REL_DELETEACTION = "http://schema.org/DeleteAction";
  /**
   * See http://schema.org/ListenAction.
   */
  String LINK_REL_LISTENACTION = "http://schema.org/ListenAction";
  /**
   * See http://schema.org/OrderAction.
   */
  String LINK_REL_ORDERACTION = "http://schema.org/OrderAction";
  /**
   * See http://schema.org/PayAction.
   */
  String LINK_REL_PAYACTION = "http://schema.org/PayAction";
  /**
   * See http://schema.org/ReadAction.
   */
  String LINK_REL_READACTION = "http://schema.org/ReadAction";
  /**
   * See http://schema.org/ReceiveAction.
   */
  String LINK_REL_RECEIVEACTION = "http://schema.org/ReceiveAction";
  /**
   * See http://schema.org/ReplaceAction.
   */
  String LINK_REL_REPLACEACTION = "http://schema.org/ReplaceAction";
  /**
   * See http://schema.org/TakeAction.
   */
  String LINK_REL_TAKEACTION = "http://schema.org/TakeAction";


  // Error conditions

  /**
   * The order is already paid and cannot be changed anymore.
   */
  String ERROR_ALREADY_PAID = "http://errors.restbucks.com/already-paid";
  /**
   * The attributes you provided are invalid for the requested menu item.
   */
  String ERROR_INVALID_ITEM = "http://errors.restbucks.com/invalid-item";
  /**
   * The payment details you provided contain invalid values.
   */
  String ERROR_INVALID_PAYMENT = "http://errors.restbucks.com/invalid-payment";
  /**
   * The menu item you requested is temporarily out of stock.
   */
  String ERROR_ITEM_OUT_OF_STOCK = "http://errors.restbucks.com/item-out-of-stock";
  /**
   * The method you used is not supported on this URI.
   */
  String ERROR_METHOD_NOT_ALLOWED = "http://errors.restbucks.com/method-not-allowed";
  /**
   * The order you provided doesn't contain a customer name.
   */
  String ERROR_MISSING_CUSTOMER = "http://errors.restbucks.com/missing-customer";
  /**
   * The order you provided doesn't contain any menu item.
   */
  String ERROR_MISSING_ITEM = "http://errors.restbucks.com/missing-item";
  /**
   *  The media type you requested in the Accept header is not supported for this method on this URI. .
   */
  String ERROR_NOT_ACCEPTIBLE = "http://errors.restbucks.com/not-acceptible";
  /**
   * The URI you requested doesn't exists or is not accessible by you.
   */
  String ERROR_NOT_FOUND = "http://errors.restbucks.com/not-found";
  /**
   * The amount of money you paid is more than what the order costs.
   */
  String ERROR_OVERPAID = "http://errors.restbucks.com/overpaid";
  /**
   * The payment you provided could not be processed.
   */
  String ERROR_PAYMENT_NOT_PROCESSED = "http://errors.restbucks.com/payment-not-processed";
  /**
   *  Something went wrong on our side. We have logged the problem so our staff can look into it. We are sorry for the
       inconvenience. .
   */
  String ERROR_SERVER_ERROR = "http://errors.restbucks.com/server-error";
  /**
   * The server is temporarily not able to handle the request.
   */
  String ERROR_SERVICE_UNAVAILABLE = "http://errors.restbucks.com/service-unavailable";
  /**
   * The menu item you requested is unknown to the server.
   */
  String ERROR_UNKNOWN_ITEM = "http://errors.restbucks.com/unknown-item";


  // Media Types

  String MEDIA_TYPE_HAL_JSON = "application/hal+json";
  String MEDIA_TYPE_DEFAULT = MEDIA_TYPE_HAL_JSON;

}
