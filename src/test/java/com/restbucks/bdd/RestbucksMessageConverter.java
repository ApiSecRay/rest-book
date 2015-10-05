package com.restbucks.bdd;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.escalon.hypermedia.spring.hydra.HydraMessageConverter;


public class RestbucksMessageConverter extends HydraMessageConverter {

  @Override
  public void setObjectMapper(ObjectMapper objectMapper) {
    super.setObjectMapper(objectMapper);
  }

}
