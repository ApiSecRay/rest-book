/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import de.escalon.hypermedia.affordance.ActionDescriptor;
import de.escalon.hypermedia.affordance.Affordance;


public class HydraLinks {

  private static final String EMPTY_JSON_OBJECT = "{}";
  private static final String HREF_MEMBER = "@id";
  private static final String OPERATION_MEMBER = "hydra:operation";
  private static final String METHOD_MEMBER = "hydra:method";
  private static final String COLLECTION_MEMBER = "hydra:collection";
  private static final String MANAGES_MEMBER = "hydra:manages";
  private static final String PROPERTY_MEMBER = "hydra:property";

  public List<Link> findLinksWithRel(String rel, String representation) {
    JSONObject object = toJson(representation);
    if (object.has(rel)) {
      LinkObject result = getLinkObject(object, rel);
      if (result != null) {
        return Collections.singletonList(toAffordance(result.getRel(), result.getOperation()));
      }
    }
    return Collections.emptyList();
  }

  private JSONObject toJson(String representation) {
    return new JSONObject(isEmpty(representation)  ? EMPTY_JSON_OBJECT : representation);
  }

  private boolean isEmpty(String representation) {
    return representation == null || representation.trim().isEmpty();
  }

  private LinkObject getLinkObject(JSONObject object, String key) {
    Object value = object.get(key);
    if (value instanceof JSONObject) {
      JSONObject link = (JSONObject)value;
      if (link.has(HREF_MEMBER)  && link.has(OPERATION_MEMBER)) {
        return new LinkObject(key, link);
      }
    } else if (value instanceof JSONArray && COLLECTION_MEMBER.equals(key)) {
      JSONObject item = (JSONObject)((JSONArray)value).get(0);
      return new LinkObject(item.getJSONObject(MANAGES_MEMBER).getString(PROPERTY_MEMBER), item);
    }
    return null;
  }

  private Affordance toAffordance(String rel, JSONObject linkObject) {
    Affordance result = new Affordance(linkObject.getString(HREF_MEMBER));
    result.addRel(rel);
    List<ActionDescriptor> actionDescriptors = new ArrayList<>();
    JSONArray operations = linkObject.getJSONArray(OPERATION_MEMBER);
    for (int i = 0; i < operations.length(); i++) {
      JSONObject operation = operations.getJSONObject(i);
      String method = operation.getString(METHOD_MEMBER);
      actionDescriptors.add(new ActionDescriptor(method, method));
    }
    result.setActionDescriptors(actionDescriptors);
    return result;
  }

  public void add(ResourceSupport resource, String representation) {
    JSONObject object = toJson(representation);
    for (String key : object.keySet()) {
      LinkObject linkObject = getLinkObject(object, key);
      if (linkObject != null) {
        resource.add(toAffordance(linkObject.getRel(), linkObject.getOperation()));
      }
    }
  }

  private static class LinkObject {

    private final String rel;
    private final JSONObject operation;

    LinkObject(String rel, JSONObject operation) {
      this.rel = rel;
      this.operation = operation;
    }

    public String getRel() {
      return rel;
    }

    public JSONObject getOperation() {
      return operation;
    }

  }

}
