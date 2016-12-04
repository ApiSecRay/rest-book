/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.escalon.hypermedia.spring.AffordanceBuilder;
import restbucks.rest.api.Api;
import restbucks.rest.impl.Actions;
import restbucks.rest.impl.RestResponse;
import restbucks.rest.menu.MenuController;


@RestController
@RequestMapping(Api.URL_BILLBOARD)
public class HomeController {

  @Autowired
  private HomeControllerSupport support;

  @RequestMapping(method = RequestMethod.GET, produces = { Api.MEDIA_TYPE_DEFAULT })
  public ResponseEntity<HomeResource> get() {
    RestResponse<HomeResource> response = support.get();
    HomeResource result = response.getPayload();
    if (response.allows(Actions.READ_MENU)) {
      result.add(AffordanceBuilder
        .linkTo(AffordanceBuilder.methodOn(MenuController.class).get())
        .withRel(Api.LINK_REL_READACTION));
    }
    return new ResponseEntity<>(result, response.getStatus());
  }

}
