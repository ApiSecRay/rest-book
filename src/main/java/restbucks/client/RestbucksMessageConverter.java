package restbucks.client;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import org.apache.commons.io.IOUtils;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;

import de.escalon.hypermedia.spring.hydra.HydraMessageConverter;


public class RestbucksMessageConverter extends HydraMessageConverter {

  @Override
  protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    return super.readInternal(clazz, inputMessage);
  }

  @Override
  public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    Object result = super.read(type, contextClass, inputMessage);
    if (result instanceof ResourceSupport) {
      addLinks((ResourceSupport)result, inputMessage.getBody());
    }
    return result;
  }

  private void addLinks(ResourceSupport object, InputStream inputStream) {
    try {
      String content = IOUtils.toString(inputStream);
      HydraLinks.add(object, content);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
