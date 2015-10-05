package com.restbucks.bdd;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.client.RestTemplate;

import de.escalon.hypermedia.spring.HypermediaTypes;
import restbucks.Application;
import restbucks.rest.api.Api;
import restbucks.rest.item.ItemResource;
import restbucks.rest.menu.MenuResource;
import restbucks.rest.order.OrderResource;


public class RestbuckSteps {

  private static final int STARTUP_TIME = 15000;
  private static final String HOST = "localhost";
  private static final int PORT = 8080;
  private static final String BILLBOARD = "/";
  private static final URI BILLBOARD_URI = URI.create(String.format("http://%s:%d%s", HOST, PORT, BILLBOARD));

  private Thread serverThread;
  private ResourceSupport Resource;
  private String customer;
  private final Client client = new Client(BILLBOARD_URI, HypermediaTypes.APPLICATION_JSONLD);

  @BeforeStories
  public void init() throws InterruptedException {
    serverThread = new Thread(() -> Application.main(new String[0]));
    serverThread.start();
    Thread.sleep(STARTUP_TIME);
    client.setLinkDiscoverers(Collections.singletonList(new HydraLinkDiscoverer()));
    client.setRestOperations(new RestTemplate());
  }

  @AfterStories
  public void done() throws InterruptedException {
    serverThread.interrupt();
    serverThread.join();
  }

  @Given("a customer $customer")
  public void setCustomer(String customer) {
    this.customer = customer;
  }

  @When("she reads the menu")
  public void getMenu() throws IOException {
    Resource = client.follow(Api.LINK_REL_MENU).toObject(MenuResource.class);
  }

  @When("she orders a $drink")
  public void order(String drink) {
    ItemResource item = parseItem(drink);
    ItemResource result = findMenuItem(item);
    assertNotNull("Item not on the menu: " + item.name, result);

    OrderResource order = new OrderResource();
    order.customer = customer;
    order.item = new ItemResource[] { item };
    Resource = client.follow(order, Api.LINK_REL_ORDERACTION).toObject(OrderResource.class);
  }

  private ItemResource findMenuItem(ItemResource item) {
    ItemResource result = null;
    MenuResource menu = (MenuResource)Resource;
    if (menu == null || menu.item == null) {
      return result;
    }
    for (ItemResource candidate : menu.item) {
      if (candidate.name.equals(item.name)) {
        result = candidate;
      }
    }
    return result;
  }

  private ItemResource parseItem(String drink) {
    ItemResource item = new ItemResource();
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
