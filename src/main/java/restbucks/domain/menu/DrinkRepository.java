/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.domain.menu;

import org.springframework.data.repository.CrudRepository;


public interface DrinkRepository extends CrudRepository<Drink, String> {

  Drink findByName(String name);

}
