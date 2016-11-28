/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkDiscoverer;


public class HydraLinkDiscovererTest {

  private final LinkDiscoverer discoverer = new HydraLinkDiscoverer();

  @Test
  public void extractsLink() {
    String content = "{\"@context\":{\"@vocab\":\"http://schema.org/\"},"
        + "\"@type\":\"http://schema.org/CafeOrCoffeeShop\","
        + "\"http://schema.org/menu\":{\"@id\":\"http://localhost:8080/menu/\",\"hydra:operation\":[{\"hydra:method\":\"GET\"}]}}";

    Link link = discoverer.findLinkWithRel("http://schema.org/menu", content);
    assertEquals("http://schema.org/menu", link.getRel());
    assertEquals("http://localhost:8080/menu/", link.getHref());
  }

}
