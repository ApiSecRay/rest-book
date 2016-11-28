/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.domain.payment;


public interface Payment {

  String getCurrencyCode();

  double getAmount();

}
