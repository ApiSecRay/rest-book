package com.restbucks.bdd;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.client.RestTemplate;

import de.escalon.hypermedia.spring.hydra.HydraMessageConverter;
import restbucks.Application;
import restbucks.rest.home.HomeDto;
import restbucks.rest.item.ItemDto;
import restbucks.rest.menu.MenuDto;


public class RestbuckSteps {

  private static final int STARTUP_TIME = 15000;
  private static final String HOST = "localhost";
  private static final int PORT = 8080;
  private static final String BILLBOARD = "/";
  private static final URI BILLBOARD_URI = URI.create(String.format("http://%s:%d%s", HOST, PORT, BILLBOARD));

  private Thread serverThread;
  private final RestTemplate restTemplate = new RestTemplate();
  private ResourceSupport dto;
  private String customer;

  @BeforeStories
  public void init() throws InterruptedException {
    serverThread = new Thread(() -> Application.main(new String[0]));
    serverThread.start();
    Thread.sleep(STARTUP_TIME);
    restTemplate.getMessageConverters().add(new HydraMessageConverter());
  }
  
  @AfterStories
  public void done() throws InterruptedException {
    serverThread.interrupt();
    serverThread.join();
  }
  
  @Given("a customer $customer")
  public void setCustomer(String customer) {
    this.customer = customer;
    dto = get(BILLBOARD_URI, HomeDto.class);
  }

  private <T extends ResourceSupport> T get(URI uri, Class<T> dtoClass) {
    String entity = restTemplate.getForObject(uri, String.class);
    T result;
    try {
      result = dtoClass.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    JSONObject json = new JSONObject(entity);
    JSONArray names = json.names();
    for (int i = 0; i < names.length(); i++) {
      String name = names.getString(i);
      if (name.startsWith("@")) {
        continue;
      }
      Object value = json.get(name);
      if (value instanceof String) {
        setProperty(name, value, result);
      } else if (value instanceof JSONObject) {
        JSONObject val = (JSONObject)value;
        if (name.startsWith("http://")) {
          addLink(name, val, result);
        }
      } else if (value instanceof JSONArray) {
        JSONArray val = (JSONArray)value;
//        setProperty(name, val, result);
      } else {
        throw new IllegalStateException("Unknown value of type: " + value.getClass().getName());
      }
    }
    
    assertNotNull("Missing response entity @ " + uri, result);
    return result;
  }

  private void setProperty(String name, Object value, Object object) {
    try {
      Field field = object.getClass().getField(name);
      field.set(object, value);
    } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private void addLink(String relation, JSONObject link, ResourceSupport object) {
    object.add(new Link(link.getString("@id"), relation));
  }
  
  @When("she reads the menu")
  public void getMenu() throws IOException {
    dto = get(resolveLink("http://schema.org/menu"), MenuDto.class);
  }

  private URI resolveLink(String relation) {
    Link result = dto.getLink(relation);
    if (result == null) {
      StringBuilder message = new StringBuilder()
          .append("Missing link for relation ")
          .append(relation)
          .append(". Got:\n");
      for (Link link : dto.getLinks()) {
        message.append(link.getRel()).append('\n');
      }
      message.append(dto.getClass().getName()).append(':').append(dto);
      fail(message.toString());
    }
    return URI.create(result.getHref());
  }

  @When("she orders a $drink")
  public void order(String drink) {
    ItemDto item = parseItem(drink);
    ItemDto result = findMenuItem(item);
    assertNotNull("Item not on the menu: " + item.name, result);
  }

  private ItemDto findMenuItem(ItemDto item) {
    MenuDto menu = (MenuDto)dto;
    ItemDto result = null;
    if (menu.items == null) {
      return result;
    }
    for (ItemDto candidate : menu.items.item) {
      if (candidate.name.equals(item.name)) {
        result = candidate;
      }
    }
    return result;
  }

  private ItemDto parseItem(String drink) {
    ItemDto item = new ItemDto();
    String[] parts = drink.split("\\s+");
    item.size = parts[0];
    item.milk = parts[1];
    Assert.assertEquals("No milk in drink", "milk", parts[2]);
    item.name = parts[3] + ' ' + parts[4];
    return item;
  }

//  @Then("she is due \\$$amount")
  public void assertAmount(double amount) {
  }

//  @When("she pays")
  public void pay() {
  }

//  @Then("she is handed a receipt")
  public void assertReceipt() {
  }

//  @When("she takes the receipt")
  public void takeReceipt() {
  }

//  @Then("she must wait for the barista")
  public void waitForServing() {
  }

//  @When("the barista calls her name")
  public void served() {
  }

//  @Then("her serving is ready")
  public void assertServing() {
  }

//  @When("she takes her serving")
  public void takeServing() {
  }

//  @Then("she is happy")
  public void end() {
  }

}
