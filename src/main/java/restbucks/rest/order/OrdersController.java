/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.escalon.hypermedia.spring.AffordanceBuilder;
import restbucks.rest.api.Api;
import restbucks.rest.impl.Actions;
import restbucks.rest.impl.Resources;
import restbucks.rest.impl.RestResponse;
import restbucks.rest.payment.PaymentsController;


@RestController
@RequestMapping(Resources.URL_ORDERS)
public class OrdersController {

  @Autowired
  private OrdersControllerSupport support;

  @RequestMapping(method = RequestMethod.POST, consumes = { Api.MEDIA_TYPE_DEFAULT }, produces = {
      Api.MEDIA_TYPE_DEFAULT })
  public ResponseEntity<OrderResource> post(@RequestBody OrderResource input) {
    RestResponse<OrderResource> response = support.post(input);
    OrderResource result = response.getPayload();
    if (response.allows(Actions.CANCEL)) {
      result.add(AffordanceBuilder
        .linkTo(AffordanceBuilder.methodOn(OrderController.class).delete(response.getParameter("orderId")))
        .withRel(Api.LINK_REL_DELETEACTION));
    }
    if (response.allows(Actions.CHANGE)) {
      result.add(AffordanceBuilder
        .linkTo(AffordanceBuilder.methodOn(OrderController.class).put(response.getParameter("orderId"), null))
        .withRel(Api.LINK_REL_REPLACEACTION));
    }
    if (response.allows(Actions.PAY)) {
      result.add(AffordanceBuilder
        .linkTo(AffordanceBuilder.methodOn(PaymentsController.class).post(response.getParameter("orderId"), null))
        .withRel(Api.LINK_REL_PAYACTION));
    }
    return new ResponseEntity<>(result, response.getStatus());
  }

}
