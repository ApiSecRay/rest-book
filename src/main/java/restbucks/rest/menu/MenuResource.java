/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.menu;

import org.springframework.hateoas.ResourceSupport;

import restbucks.rest.item.ItemResource;


public class MenuResource extends ResourceSupport {

  private ItemResource[] items;

  public ItemResource[] getItems() {
    return items;
  }

  public void setItems(ItemResource[] items) {
    this.items = items;
  }

}
