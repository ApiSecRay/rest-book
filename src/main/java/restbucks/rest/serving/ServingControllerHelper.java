/*
 * Generated by RADL.
 */
package restbucks.rest.serving;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Service
public class ServingControllerHelper {

  public ResponseEntity<Void> delete() {
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); // TODO: Implement
  }

  public ServingResource get() {
    return new ServingResource(); // TODO: Implement
  }

  public boolean isLinkEnabled(String linkRelation) {
    return true;// TODO: Implement
  }

}
