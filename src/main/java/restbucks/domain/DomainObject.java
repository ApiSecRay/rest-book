/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved. 
 */
package restbucks.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class DomainObject implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private final String id;

  public DomainObject() {
    this.id = UUID.randomUUID().toString();
  }

  public String getId() {
    return id;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof DomainObject)) {
      return false;
    }
    DomainObject other = (DomainObject)obj;
    return id.equals(other.id);
  }

}
