/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.serving;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.escalon.hypermedia.spring.AffordanceBuilder;
import restbucks.rest.api.Api;
import restbucks.rest.impl.Actions;
import restbucks.rest.impl.Resources;
import restbucks.rest.impl.RestResponse;


@RestController
@RequestMapping(Resources.URL_SERVING)
public class ServingController {

  @Autowired
  private ServingControllerSupport support;

  @RequestMapping(method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable("order-id") String orderId) {
    RestResponse<Void> response = support.delete(orderId);
    return new ResponseEntity<>(response.getStatus());
  }

  @RequestMapping(method = RequestMethod.GET, produces = { Api.MEDIA_TYPE_DEFAULT })
  public ResponseEntity<ServingResource> get(@PathVariable("order-id") String orderId) {
    RestResponse<ServingResource> response = support.get(orderId);
    ServingResource result = response.getPayload();
    if (response.allows(Actions.TAKE_SERVING)) {
      result.add(AffordanceBuilder
        .linkTo(AffordanceBuilder.methodOn(ServingController.class).delete(orderId))
        .withRel(Api.LINK_REL_TAKEACTION));
    }
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

}
