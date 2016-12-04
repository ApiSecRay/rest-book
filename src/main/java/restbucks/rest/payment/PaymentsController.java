/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.escalon.hypermedia.spring.AffordanceBuilder;
import restbucks.rest.api.Api;
import restbucks.rest.impl.Actions;
import restbucks.rest.impl.Resources;
import restbucks.rest.impl.RestResponse;
import restbucks.rest.order.OrderController;
import restbucks.rest.receipt.ReceiptResource;


@RestController
@RequestMapping(Resources.URL_PAYMENTS)
public class PaymentsController {

  @Autowired
  private PaymentsControllerSupport support;

  @RequestMapping(method = RequestMethod.POST, consumes = { Api.MEDIA_TYPE_DEFAULT }, produces = {
      Api.MEDIA_TYPE_DEFAULT })
  public ResponseEntity<ReceiptResource> post(@PathVariable("order-id") String orderId, @RequestBody PaymentResource
      input) {
    RestResponse<ReceiptResource> response = support.post(orderId, input);
    ReceiptResource result = response.getPayload();
    if (response.allows(Actions.TAKE_RECEIPT)) {
      result.add(AffordanceBuilder
        .linkTo(AffordanceBuilder.methodOn(OrderController.class).get(orderId))
        .withRel(Api.LINK_REL_RECEIVEACTION));
    }
    return new ResponseEntity<>(result, response.getStatus());
  }

}
