/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.domain.order;

import org.springframework.data.repository.CrudRepository;


public interface OrderRepository extends CrudRepository<Order, String> {

}
