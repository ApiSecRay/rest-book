/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CentralErrorHandler {

  @ExceptionHandler({ IllegalArgumentException.class })
  public ResponseEntity<ErrorResource> illegalArgument(IllegalArgumentException e) {
    return error(e, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ NotFoundException.class })
  public ResponseEntity<ErrorResource> notFound(NotFoundException e) {
    return error(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({ Throwable.class })
  public ResponseEntity<ErrorResource> internalError(Throwable e) {
    return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({ ServiceUnavailableException.class })
  public ResponseEntity<ErrorResource> serviceUnavailable(ServiceUnavailableException e) {
    return error(e, HttpStatus.SERVICE_UNAVAILABLE);
  }

  private ResponseEntity<ErrorResource> error(Throwable t, HttpStatus statusCode) {
    ErrorResource error = new ErrorResource();
    if (t instanceof Identifiable) {
      error.setType(((Identifiable)t).getId());
    }
    error.setTitle(getNonRevealingMessage(t));
    return new ResponseEntity<>(error, statusCode);
  }

  private String getNonRevealingMessage(Throwable t) {
    StringBuilder result = new StringBuilder(64);
    result.append(t.getMessage());
    int index = result.indexOf("Exception");
    while (index >= 0) {
      int start = findIdentifierEnd(result, index, -1);
      int end = findIdentifierEnd(result, index, +1);
      result.delete(start + 1, end);
      index = result.indexOf("Exception", start + 1);
    }
    return result.toString();
  }

  private int findIdentifierEnd(StringBuilder text, int start, int delta) {
    int index = start;
    while (!isAtEnd(text, index, delta)
        && (Character.isJavaIdentifierPart(text.charAt(index)) || text.charAt(index) == '.')) {
      index += delta;
    }
    while (!isAtEnd(text, index, delta) && isNonWord(text.charAt(index))) {
      index += delta;
    }
    return index;
  }

  private boolean isAtEnd(StringBuilder text, int index, int delta) {
    return delta < 0 ? index < 0 : index == text.length();
  }

  private boolean isNonWord(char ch) {
    return Character.isWhitespace(ch) || isPunctuation(ch);
  }

  private boolean isPunctuation(char ch) {
    return ch == '.' || ch == ';' || ch == ':' || ch == '-';
  }

}
