/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.domain.menu;

import java.util.ArrayList;
import java.util.Collection;


public class MenuItem {

  private final Drink drink;
  private final Size size;
  private final Milk milk;

  public MenuItem(Drink drink, Size size, Milk milk) {
    this.drink = drink;
    this.size = size;
    this.milk = milk;
  }

  public static Iterable<MenuItem> variationsOf(Drink drink) {
    Collection<MenuItem> result = new ArrayList<>();
    for (Size size : Size.values()) {
      for (Milk milk : Milk.values()) {
        result.add(new MenuItem(drink, size, milk));
      }
    }
    return result;
  }

  public Drink getDrink() {
    return drink;
  }

  public Size getSize() {
    return size;
  }

  public Milk getMilk() {
    return milk;
  }

}
