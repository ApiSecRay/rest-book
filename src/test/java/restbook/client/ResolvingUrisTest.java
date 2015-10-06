package restbook.client;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;


public class ResolvingUrisTest {

  @Test
  public void resolve() {
    URI baseUri = URI.create("http://www.example.com/foo/bar/");
    URI resolvedUri = baseUri.resolve("baz/");
    assertEquals(URI.create("http://www.example.com/foo/bar/baz/"), resolvedUri);

    baseUri = URI.create("http://www.example.com/foo/bar");
    resolvedUri = baseUri.resolve("baz/");
    assertEquals(URI.create("http://www.example.com/foo/baz/"), resolvedUri);
  }
  
}
