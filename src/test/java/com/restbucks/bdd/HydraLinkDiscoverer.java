package com.restbucks.bdd;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkDiscoverer;
import org.springframework.http.MediaType;

import de.escalon.hypermedia.affordance.ActionDescriptor;
import de.escalon.hypermedia.affordance.Affordance;
import de.escalon.hypermedia.spring.HypermediaTypes;


public class HydraLinkDiscoverer implements LinkDiscoverer {

  @Override
  public boolean supports(MediaType mediaType) {
    return mediaType.isCompatibleWith(HypermediaTypes.APPLICATION_JSONLD);
  }

  @Override
  public Link findLinkWithRel(String rel, String representation) {
    List<Link> links = findLinksWithRel(rel, representation);
    return links.isEmpty() ? null : links.get(0);
  }

  @Override
  public Link findLinkWithRel(String rel, InputStream representation) {
    List<Link> links = findLinksWithRel(rel, representation);
    return links.isEmpty() ? null : links.get(0);
  }

  @Override
  public List<Link> findLinksWithRel(String rel, InputStream representation) {
    try {
      return findLinksWithRel(rel, IOUtils.toString(representation));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Link> findLinksWithRel(String rel, String representation) {
    JSONObject object = new JSONObject(representation == null ? "{}" : representation);
    if (object.has(rel)) {
      Object value = object.get(rel);
      if (value instanceof JSONObject) {
        JSONObject linkObject = (JSONObject)value;
        if (linkObject.has("@id") && linkObject.has("hydra:operation")) {
          String href = linkObject.getString("@id");
          JSONArray operations = linkObject.getJSONArray("hydra:operation");
          Affordance affordance = new Affordance(href);
          affordance.addRel(rel);
          List<ActionDescriptor> actionDescriptors = new ArrayList<>();
          for (int i = 0; i < operations.length(); i++) {
            JSONObject operation = operations.getJSONObject(i);
            String method = operation.getString("hydra:method");
            actionDescriptors.add(new ActionDescriptor(method, method));
          }
          affordance.setActionDescriptors(actionDescriptors);
          return Collections.singletonList(affordance);
        }
      }
    }
    return Collections.emptyList();
  }

}
