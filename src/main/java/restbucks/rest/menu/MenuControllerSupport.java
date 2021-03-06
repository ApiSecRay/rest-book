/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.menu;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restbucks.domain.menu.Drink;
import restbucks.domain.menu.DrinkRepository;
import restbucks.domain.menu.MenuItem;
import restbucks.rest.impl.RestResponse;
import restbucks.rest.item.ItemResource;


@Service
public class MenuControllerSupport {

  @Autowired
  private DrinkRepository repository;

  public RestResponse<MenuResource> get() {
    return new RestResponse<>(getMenu());
  }

  private MenuResource getMenu() {
    MenuResource result = new MenuResource();
    result.setItems(getItems());
    return result;
  }

  private ItemResource[] getItems() {
    Collection<ItemResource> result = new ArrayList<>();
    for (Drink drink : repository.findAll()) {
      for (MenuItem menuItem : MenuItem.variationsOf(drink)) {
        result.add(toItemResource(menuItem));
      }
    }
    return result.toArray(new ItemResource[result.size()]);
  }

  private ItemResource toItemResource(MenuItem menuItem) {
    ItemResource result = new ItemResource();
    result.setCurrency(menuItem.getDrink().getCurrency().getDisplayName());
    result.setMilk(menuItem.getMilk().toString());
    result.setName(menuItem.getDrink().getName());
    result.setPrice(menuItem.getDrink().getPrice());
    result.setSize(menuItem.getSize().toString());
    return result;
  }

}
