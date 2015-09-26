package com.restbucks.bdd;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;

import restbucks.main.Application;


public class RestbuckSteps {

  private static final String HOST = "localhost";
  private static final int PORT = 8080;
  private static final String BILLBOARD = "/";
  private static final URI BILLBOARD_URI = URI.create(String.format("http://%s:%d:%s", HOST, PORT, BILLBOARD));

  private final HttpClient client = HttpClientBuilder.create().build();
  private HttpResponse response;
  private String customer;
  private Thread serverThread;

  @BeforeStories
  public void init() throws InterruptedException {
    serverThread = new Thread(() -> Application.main(new String[0]));
    serverThread.start();
    Thread.sleep(10000);
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
  public void getMenu() throws ClientProtocolException, IOException {
    HttpUriRequest request = new HttpGet(BILLBOARD_URI);
    response = client.execute(request);
  }

//  @When("she orders a $drink")
  public void order(String drink) {
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
