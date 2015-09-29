/*
 * Generated by RADL.
 */
package restbucks.rest.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restbucks.domain.menu.Drink;
import restbucks.domain.menu.DrinkRepository;
import restbucks.domain.menu.MenuItem;
import restbucks.rest.item.ItemDto;
import restbucks.rest.item.ItemsDto;


@Service
public class MenuControllerHelper {

  @Autowired
  private DrinkRepository drinks;
  
  public MenuDto get() {
    MenuDto result = new MenuDto();
    Collection<ItemDto> items = new ArrayList<>();
    Locale locale = Locale.getDefault();
    for (Drink drink : drinks.findAll()) {
      for (MenuItem menuItem : MenuItem.variationsOf(drink)) {
        ItemDto item = new ItemDto();
        item.name = menuItem.getDrink().getName();
        item.size = menuItem.getSize().toString().toLowerCase(locale);
        item.milk = menuItem.getMilk().toString().toLowerCase(locale);
        item.price = Double.toString(menuItem.getDrink().getPrice());
        item.currency = menuItem.getDrink().getCurrency().getDisplayName(locale);
        items.add(item);
      }
    }
    result.items = new ItemsDto();
    result.items.item = items.toArray(new ItemDto[items.size()]);
    return result;
  }

  public boolean isLinkEnabled(String linkRelation) {
    return true;
  }

}
