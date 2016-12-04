/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.escalon.hypermedia.spring.AffordanceBuilder;
import restbucks.rest.api.Api;
import restbucks.rest.impl.Actions;
import restbucks.rest.impl.Resources;
import restbucks.rest.impl.RestResponse;
import restbucks.rest.order.OrdersController;


@RestController
@RequestMapping(Resources.URL_MENU)
public class MenuController {

  @Autowired
  private MenuControllerSupport support;

  @RequestMapping(method = RequestMethod.GET, produces = { Api.MEDIA_TYPE_DEFAULT })
  public ResponseEntity<MenuResource> get() {
    RestResponse<MenuResource> response = support.get();
    MenuResource result = response.getPayload();
    if (response.allows(Actions.PLACE_ORDER)) {
      result.add(AffordanceBuilder
        .linkTo(AffordanceBuilder.methodOn(OrdersController.class).post(null))
        .withRel(Api.LINK_REL_ORDERACTION));
    }
    return new ResponseEntity<>(result, response.getStatus());
  }

}
