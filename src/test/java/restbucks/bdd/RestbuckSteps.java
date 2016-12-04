/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.bdd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;

import restbucks.rest.api.Api;
import restbucks.rest.item.ItemResource;
import restbucks.rest.menu.MenuResource;
import restbucks.rest.order.OrderResource;
import restbucks.rest.payment.PaymentResource;
import restbucks.rest.receipt.ReceiptResource;
import restbucks.rest.serving.ServingResource;


public class RestbuckSteps {

  private static final long MAX_WAIT_TIME = 10000;
  private static final int INCREMENTAL_WAIT_TIME = 100;
  private static final String HOST = "localhost";
  private static final int PORT = 8080;
  private static final String BILLBOARD = "/";
  private static final URI BILLBOARD_URI = URI.create(String.format("http://%s:%d%s", HOST, PORT, BILLBOARD));

  private ResourceSupport resource;
  private String customer;
  private double paidAmount;
  private String paidCurrency;
  private final Client client = new Client(BILLBOARD_URI, MediaTypes.HAL_JSON);

  @Given("a customer $customer")
  public void setCustomer(String customer) {
    this.customer = customer;
  }

  @When("she reads the menu")
  public void getMenu() throws IOException {
    resource = client.get(Api.LINK_REL_READACTION).toObject(MenuResource.class);
  }

  @When("she orders a $drink")
  public void order(String drink) {
    ItemResource item = parseItem(drink);
    ItemResource result = findMenuItem(item);
    assertNotNull("Item not on the menu: " + item.getName(), result);

    OrderResource order = new OrderResource();
    order.setCustomer(customer);
    order.setItems(new ItemResource[] { item });
    resource = client.post(order, Api.LINK_REL_ORDERACTION).toObject(OrderResource.class);
  }

  private ItemResource findMenuItem(ItemResource item) {
    MenuResource menu = (MenuResource)resource;
    if (menu == null || menu.getItems() == null) {
      return null;
    }
    for (ItemResource candidate : menu.getItems()) {
      if (candidate.getName().equals(item.getName())) {
        return candidate;
      }
    }
    return null;
  }

  private ItemResource parseItem(String drink) {
    ItemResource item = new ItemResource();
    String[] parts = drink.split("\\s+");
    item.setSize(parts[0]);
    item.setMilk(parts[1]);

    Assert.assertEquals("No milk in drink", "milk", parts[2]);
    item.setName(parts[3] + ' ' + parts[4]);
    return item;
  }

  @Then("she is due $currency $total")
  public void assertOrderTotal(String currency, double total) {
    assertCreated();
    OrderResource order = (OrderResource)resource;
    assertEquals("Total", total, order.getTotal(), 0.01);
    assertEquals("Currency", currency, order.getCurrency());
  }

  private void assertCreated() {
    assertEquals("Status", 201, client.getStatusCode());
  }

  @When("she pays")
  public void pay() {
    PaymentResource payment = paymentForOrder();
    paidAmount = payment.getAmount();
    paidCurrency = payment.getCurrency();
    resource = client.post(payment, Api.LINK_REL_PAYACTION).toObject(ReceiptResource.class);
  }

  private PaymentResource paymentForOrder() {
    OrderResource order = (OrderResource)resource;
    PaymentResource result = new PaymentResource();
    result.setAmount(order.getTotal());
    result.setCurrency(order.getCurrency());
    result.setPaymentMethod("creditcard");
    result.setCardholderName("C.C. Conway");
    result.setCardNumber("5525366617069778");
    result.setExpiryYear(2019);
    result.setExpiryMonth(6);
    result.setCardSecurityCode("836");
    return result;
  }

  @Then("she is handed a receipt")
  public void assertReceipt() {
    assertCreated();
    ReceiptResource receipt = (ReceiptResource)resource;
    assertEquals("Currency", paidCurrency, receipt.getCurrency());
    assertEquals("Total", paidAmount, receipt.getTotal(), 0.01);
    assertEquals("Shop", "RESTBucks", receipt.getShop());

    /* TODO:
    Calendar today = Calendar.getInstance();
    assertNotNull("Missing dateTime", receipt.dateTime);
    assertEquals("Year", today.get(Calendar.YEAR), receipt.dateTime.getYear());
    assertEquals("Month", today.get(Calendar.MONTH) + 1, receipt.dateTime.getMonth());
    assertEquals("Day", today.get(Calendar.DATE), receipt.dateTime.getDay());
    */
  }

  @When("she takes the receipt")
  public void takeReceipt() {
    resource = client.get(Api.LINK_REL_RECEIVEACTION).toObject(OrderResource.class);
  }

  @Then("she must wait for the barista")
  public void waitForServing() {
    long start = System.currentTimeMillis();
    while (isPreparing()) {
      assertTrue("Waited too long for serving", System.currentTimeMillis() - start > MAX_WAIT_TIME);
      waitAWhile();
    }
  }

  private boolean isPreparing() {
    return !resource.hasLink(Api.LINK_REL_LISTENACTION);
  }

  private void waitAWhile() {
    try {
      Thread.sleep(INCREMENTAL_WAIT_TIME);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    resource = client.get(Api.LINK_REL_LISTENACTION).toObject(OrderResource.class);
  }

  @When("the barista calls her name")
  public void served() {
    resource = client.get(Api.LINK_REL_LISTENACTION).toObject(ServingResource.class);
  }

  @Then("her serving is ready")
  public void assertServing() {
    assertTrue("Missing link", resource.hasLink(Api.LINK_REL_TAKEACTION));
  }

  @When("she takes her serving")
  public void takeServing() {
    resource = client.delete(Api.LINK_REL_TAKEACTION).toObject(ResourceSupport.class);
  }

  @Then("she is happy")
  public void end() {
    // Nothing to do
  }

}
