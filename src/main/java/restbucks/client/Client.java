package restbucks.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkDiscoverer;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestOperations;

import de.escalon.hypermedia.affordance.Affordance;


public class Client {

  private final URI uri;
  private final MediaType mediaType;
  private final Iterable<LinkDiscoverer> linkDiscoverers;
  private final RestOperations operations;
  private HttpEntity<?> response;

  public Client(URI uri, MediaType mediaType, Iterable<LinkDiscoverer> linkDiscoverers, RestOperations operations) {
    this.uri = uri;
    this.mediaType = mediaType;
    this.linkDiscoverers = linkDiscoverers;
    this.operations = operations;
  }

  public Traversal follow(String... rels) {
    return follow(null, rels);
  }

  public Traversal follow(Object input, String... rels) {
    return new Traversal(input, rels);
  }


  public class Traversal {

    private final Object input;
    private final String[] rels;

    public Traversal(Object input, String[] rels) {
      this.input = input;
      this.rels = rels;
    }

    @SuppressWarnings("unchecked")
    public <T extends ResourceSupport> T toObject(Class<T> dtoClass) {
      for (HopInfo info : getHops(dtoClass)) {
        Link link = getLink(info.getRel());
        response = operations.exchange(link.getHref(), getMethod(link), getRequestEntity(info.getInput()),
            info.getDtoClass());
      }
      return (T)response.getBody();
    }

    private Iterable<HopInfo> getHops(Class<?> dtoClass) {
      List<HopInfo> result = new ArrayList<>();
      for (String rel : rels) {
        result.add(new HopInfo(rel));
      }
      HopInfo info = result.get(result.size() - 1);
      info.setInput(input);
      info.setDtoClass(dtoClass);
      if (response == null) {
        result.add(0, new HopInfo(null));
      }
      return result;
    }

    private Link getLink(String rel) {
      if (rel == null) {
        return new Link(uri.toString());
      }
      if (response.getBody() instanceof ResourceSupport) {
        ResourceSupport resource = (ResourceSupport)response.getBody();
        Link link = resource.getLink(rel);
        if (link == null) {
          throw new IllegalArgumentException("Missing link with relation " + rel + " in: " + resource.getLinks());
        }
        return link;
      }
      for (LinkDiscoverer discoverer : linkDiscoverers) {
        Link link = discoverer.findLinkWithRel(rel, (String)response.getBody());
        if (link != null) {
          return link;
        }
      }
      throw new IllegalArgumentException("Missing link with relation " + rel + " in: " + response.getBody());
    }

    private HttpMethod getMethod(Link link) {
      if (link instanceof Affordance) {
        return HttpMethod.valueOf(((Affordance)link).getActionDescriptors().get(0).getHttpMethod());
      }
      return HttpMethod.GET;
    }

    private HttpEntity<?> getRequestEntity(Object input) {
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Collections.singletonList(mediaType));
      if (input == null) {
        return new HttpEntity<Void>(headers);
      }
      return new HttpEntity<Object>(input);
    }

  }


  private static class HopInfo {

    private final String rel;
    private Class<?> dtoClass;
    private Object input;

    public HopInfo(String rel) {
      this.rel = rel;
      this.dtoClass = ResourceSupport.class;
    }

    public void setDtoClass(Class<?> dtoClass) {
      this.dtoClass = dtoClass;
    }

    public void setInput(Object input) {
      this.input = input;
    }

    public String getRel() {
      return rel;
    }

    public Class<?> getDtoClass() {
      return dtoClass;
    }

    public Object getInput() {
      return input;
    }

  }

}
