package restbucks.domain.payment;


public class CreditcardPayment extends BasePayment {

  public CreditcardPayment(String currencyCode, double amount) {
    super(currencyCode, amount);
  }

}
