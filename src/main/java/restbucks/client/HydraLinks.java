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

  public static List<Link> findLinksWithRel(String rel, String representation) {
    JSONObject object = toJson(representation);
    if (object.has(rel)) {
      JSONObject result = getLinkObject(object, rel);
      if (result != null) {
        return Collections.singletonList(toAffordance(rel, result));
      }
    }
    return Collections.emptyList();
  }

  private static JSONObject toJson(String representation) {
    return new JSONObject(representation == null ? EMPTY_JSON_OBJECT : representation);
  }

  private static JSONObject getLinkObject(JSONObject object, String key) {
    JSONObject result = null;
    Object value = object.get(key);
    if (value instanceof JSONObject) {
      result = (JSONObject)value;
      if (!result.has(HREF_MEMBER) || !result.has(OPERATION_MEMBER)) {
        result = null;
      }
    }
    return result;
  }

  private static Affordance toAffordance(String rel, JSONObject linkObject) {
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

  public static void add(ResourceSupport resource, String representation) {
    JSONObject object = toJson(representation);
    for (String name : JSONObject.getNames(object)) {
      JSONObject linkObject = getLinkObject(object, name);
      if (linkObject != null) {
        resource.add(toAffordance(name, linkObject));
      }
    }
  }

}
