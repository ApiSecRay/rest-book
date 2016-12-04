/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.order;

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
import restbucks.rest.payment.PaymentsController;
import restbucks.rest.serving.ServingController;
import restbucks.rest.serving.ServingResource;


@RestController
@RequestMapping(Resources.URL_ORDER)
public class OrderController {

  @Autowired
  private OrderControllerSupport support;

  @RequestMapping(method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable("order-id") String orderId) {
    RestResponse<Void> response = support.delete(orderId);
    return new ResponseEntity<>(response.getStatus());
  }

  @RequestMapping(method = RequestMethod.GET, produces = { Api.MEDIA_TYPE_DEFAULT })
  public ResponseEntity<ServingResource> get(@PathVariable("order-id") String orderId) {
    RestResponse<ServingResource> response = support.get(orderId);
    ServingResource result = response.getPayload();
    if (response.allows(Actions.RECEIVE_NOTIFICATION)) {
      result.add(AffordanceBuilder
        .linkTo(AffordanceBuilder.methodOn(ServingController.class).get(orderId))
        .withRel(Api.LINK_REL_LISTENACTION));
    }
    if (response.allows(Actions.WAIT)) {
      result.add(AffordanceBuilder
        .linkTo(AffordanceBuilder.methodOn(ServingController.class).get(orderId))
        .withRel(Api.LINK_REL_ID));
    }
    return new ResponseEntity<>(result, response.getStatus());
  }

  @RequestMapping(method = RequestMethod.PUT, consumes = { Api.MEDIA_TYPE_DEFAULT }, produces = { Api.MEDIA_TYPE_DEFAULT
      })
  public ResponseEntity<OrderResource> put(@PathVariable("order-id") String orderId, @RequestBody OrderResource input) {
    RestResponse<OrderResource> response = support.put(orderId, input);
    OrderResource result = response.getPayload();
    if (response.allows(Actions.CANCEL)) {
      result.add(AffordanceBuilder
        .linkTo(AffordanceBuilder.methodOn(OrderController.class).delete(orderId))
        .withRel(Api.LINK_REL_DELETEACTION));
    }
    if (response.allows(Actions.CHANGE)) {
      result.add(AffordanceBuilder
        .linkTo(AffordanceBuilder.methodOn(OrderController.class).put(orderId, null))
        .withRel(Api.LINK_REL_REPLACEACTION));
    }
    if (response.allows(Actions.PAY)) {
      result.add(AffordanceBuilder
        .linkTo(AffordanceBuilder.methodOn(PaymentsController.class).post(orderId, null))
        .withRel(Api.LINK_REL_PAYACTION));
    }
    return new ResponseEntity<>(result, response.getStatus());
  }

}
