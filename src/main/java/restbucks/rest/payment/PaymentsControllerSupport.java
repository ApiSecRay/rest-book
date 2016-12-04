/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import restbucks.domain.order.Order;
import restbucks.domain.order.OrderRepository;
import restbucks.domain.payment.CreditcardPayment;
import restbucks.domain.payment.Payment;
import restbucks.rest.impl.InvalidPaymentException;
import restbucks.rest.impl.NotFoundException;
import restbucks.rest.impl.RestResponse;
import restbucks.rest.receipt.ReceiptResource;


@Service
public class PaymentsControllerSupport {

  @Autowired
  private OrderRepository orders;

  public RestResponse<ReceiptResource> post(String orderId, PaymentResource input) {
    Order order = orders.findOne(orderId);
    if (order == null) {
      throw new NotFoundException();
    }
    order.acceptPayment(newPayment(input));
    orders.save(order);

    ReceiptResource result = new ReceiptResource();
    result.setCurrency(input.getCurrency());
    result.setPaymentMethod(input.getPaymentMethod());
    result.setShop("RESTBucks");
    result.setTotal(order.getPaidAmount());

    RestResponse<ReceiptResource> response = new RestResponse<>(result);
    response.setStatus(HttpStatus.CREATED);
    return response;
  }

  private Payment newPayment(PaymentResource input) {
    if ("creditcard".equals(input.getPaymentMethod())) {
      return new CreditcardPayment(input.getCurrency(), input.getAmount());
    }
    throw new InvalidPaymentException();
  }

}
