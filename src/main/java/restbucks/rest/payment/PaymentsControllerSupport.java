/*
 * Generated by RADL.
 */
package restbucks.rest.payment;

import org.springframework.stereotype.Service;

import restbucks.rest.impl.Actions;
import restbucks.rest.receipt.ReceiptResource;
import restbucks.rest.impl.PermittedActions;


@Service
public class PaymentsControllerSupport {

  public PermittedActions<ReceiptResource> post(PaymentResource input) {
    ReceiptResource result = new ReceiptResource();
    // result.setXxx();
    PermittedActions<ReceiptResource> permittedActions = new PermittedActions<ReceiptResource>(result);
    // permittedActions.exclude(Actions.YYY);
    return permittedActions;
  }

}
