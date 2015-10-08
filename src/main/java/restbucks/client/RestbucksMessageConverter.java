package restbucks.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import org.apache.commons.io.IOUtils;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.escalon.hypermedia.spring.hydra.HydraMessageConverter;


public class RestbucksMessageConverter extends HydraMessageConverter {

  private final HydraLinks hydraLinks = new HydraLinks();

  @Override
  public void setObjectMapper(ObjectMapper objectMapper) {
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    super.setObjectMapper(objectMapper);
  }

  @Override
  public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    HttpInputMessage wrapped = new RepeatableHttpInputMessage(inputMessage);
    Object result = super.read(type, contextClass, wrapped);
    if (result instanceof ResourceSupport) {
      addLinks((ResourceSupport)result, wrapped.getBody());
    }
    return result;
  }

  private void addLinks(ResourceSupport object, InputStream inputStream) {
    try {
      String content = IOUtils.toString(inputStream);
      hydraLinks.add(object, content);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  
  private static class RepeatableHttpInputMessage implements HttpInputMessage {

    private final HttpInputMessage wrapped;
    private ByteArrayInputStream cached;

    public RepeatableHttpInputMessage(HttpInputMessage inputMessage) {
      this.wrapped = inputMessage;
    }

    @Override
    public HttpHeaders getHeaders() {
      return wrapped.getHeaders();
    }

    @Override
    public InputStream getBody() throws IOException {
      if (cached == null) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(wrapped.getBody(), out);
        cached = new ByteArrayInputStream(out.toByteArray());
      }
      cached.reset();
      return cached;
    }

    @Override
    public String toString() {
      try {
        return IOUtils.toString(getBody());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

  }

}