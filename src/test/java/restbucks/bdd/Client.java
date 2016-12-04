/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.bdd;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkDiscoverer;
import org.springframework.hateoas.LinkDiscoverers;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.client.Traverson.TraversalBuilder;
import org.springframework.hateoas.hal.HalLinkDiscoverer;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.plugin.core.OrderAwarePluginRegistry;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Component to ease navigating hypermedia APIs by following links with relation types.
 *
 * @author Ray Sinnema
 * @since 0.20
 */
public class Client {

  private static final LinkDiscoverers DEFAULT_LINK_DISCOVERERS;

  static {
    LinkDiscoverer discoverer = new HalLinkDiscoverer();
    DEFAULT_LINK_DISCOVERERS = new LinkDiscoverers(OrderAwarePluginRegistry.create(Arrays.asList(discoverer)));
  }

  private final String baseUri;
  private final List<MediaType> mediaTypes;
  private final HttpHeaders headers = new HttpHeaders();
  private RestOperations operations;
  private LinkDiscoverers discoverers;
  private ResponseEntity<? extends ResourceSupport> response;

  /**
   * Creates a new {@link Client} interacting with the given base URI and using the given {@link MediaType}s to interact
   * with the service.
   *
   * @param baseUri must not be {@literal null}.
   * @param mediaType must not be {@literal null} or empty.
   */
  public Client(URI baseUri, MediaType... mediaTypes) {

    Assert.notNull(baseUri, "Base URI must not be null!");
    Assert.notEmpty(mediaTypes, "At least one media type must be given!");

    this.baseUri = baseUri.toString();
    this.mediaTypes = Arrays.asList(mediaTypes);
    this.discoverers = DEFAULT_LINK_DISCOVERERS;

    setRestOperations(createDefaultTemplate(this.mediaTypes));
  }

  private static RestOperations createDefaultTemplate(List<MediaType> mediaTypes) {
    List<HttpMessageConverter<?>> converters = new ArrayList<>();
    converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

    if (mediaTypes.contains(MediaTypes.HAL_JSON)) {
      converters.add(getHalConverter());
    }

    RestTemplate template = new RestTemplate();
    template.setMessageConverters(converters);

    return template;
  }

  /**
   * Creates a new {@link HttpMessageConverter} to support HAL.
   *
   * @return
   */
  private static HttpMessageConverter<?> getHalConverter() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new Jackson2HalModule());
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    converter.setObjectMapper(mapper);
    converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));

    return converter;
  }

  /**
   * Configures the {@link RestOperations} to use. If {@literal null} is provided a default {@link RestTemplate} will be
   * used.
   *
   * @param restOperations
   * @return
   */
  public Client setRestOperations(RestOperations restOperations) {
    this.operations = restOperations == null ? createDefaultTemplate(mediaTypes) : restOperations;
    return this;
  }

  /**
   * Sets the {@link LinkDiscoverers} to use. By default a single {@link HalLinkDiscoverer} is registered. If
   * {@literal null} is provided the default is reapplied.
   *
   * @param discoverer can be {@literal null}.
   * @return
   */
  public Client setLinkDiscoverers(List<? extends LinkDiscoverer> discoverer) {
    this.discoverers = discoverers == null ? DEFAULT_LINK_DISCOVERERS
        : new LinkDiscoverers(OrderAwarePluginRegistry.create(discoverer));

    return this;
  }

  /**
   * Sets up a {@link TraversalBuilder} to follow the given rels.
   *
   * @param rels must not be {@literal null} or empty.
   * @return
   * @see TraversalBuilder
   */
  public Action get(String rel) {
    return new Action(rel, GET);
  }

  public Action post(Object input, String rel) {
    return new Action(rel, POST, input);
  }

  public Action delete(String rel) {
    return new Action(rel, DELETE);
  }

  private HttpEntity<?> prepareRequest(HttpHeaders httpHeaders, Object input) {
    HttpHeaders toSend = new HttpHeaders();
    toSend.putAll(httpHeaders);

    if (toSend.getAccept().isEmpty()) {
      toSend.setAccept(mediaTypes);
    }
    if (input != null && toSend.getContentType() == null) {
      toSend.setContentType(mediaTypes.get(0));
    }

    return new HttpEntity<>(input, toSend);
  }

  private String getUriForRel(String rel) {
    if (response == null) {
      response = call(baseUri, GET, null, ResourceSupport.class);
    }
    Link link = response.getBody().getLink(rel);
    if (link == null) {
      throw new IllegalStateException("Missing link with relation " + rel);
    }
    return link.getHref();
  }

  private <T extends ResourceSupport> ResponseEntity<T> call(String uri, HttpMethod method, Object input,
      Class<T> type) {
    ResponseEntity<T> result;
    System.err.println(method + " " + uri);
    try {
      result = operations.exchange(uri, method, prepareRequest(headers, input), type);
    } catch (RuntimeException e) {
      System.err.println("=> !!! " + e.getMessage() + "\n");
      e.printStackTrace();
      throw e;
    }
    System.err.println("=> " + result.getStatusCode() + ' ' + result.getBody() + "\n");
    return result;
  }

  public int getStatusCode() {
    return response.getStatusCode().value();
  }

  public class Action {

    private final String rel;
    private final HttpMethod method;
    private final Object input;

    public Action(String rel, HttpMethod method) {
      this(rel, method, null);
    }

    public Action(String rel, HttpMethod method, Object input) {
      this.rel = rel;
      this.method = method;
      this.input = input;
    }

    public String getRel() {
      return rel;
    }

    public HttpMethod getMethod() {
      return method;
    }

    @SuppressWarnings("unchecked")
    public <T extends ResourceSupport> T toObject(Class<T> type) {
      Assert.notNull(type, "Target type must not be null!");
      response = call(getUriForRel(rel), method, input, type);
      return (T)response.getBody();
    }

  }

}
