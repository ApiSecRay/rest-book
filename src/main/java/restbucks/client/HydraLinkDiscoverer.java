package restbucks.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkDiscoverer;
import org.springframework.http.MediaType;

import de.escalon.hypermedia.spring.HypermediaTypes;


public class HydraLinkDiscoverer implements LinkDiscoverer {

  private final HydraLinks hydraLinks = new HydraLinks();

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
      return findLinksWithRel(rel, IOUtils.toString(representation, StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Link> findLinksWithRel(String rel, String representation) {
    return hydraLinks.findLinksWithRel(rel, representation);
  }

}
