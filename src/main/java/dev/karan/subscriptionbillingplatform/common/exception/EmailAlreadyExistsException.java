package dev.karan.subscriptionbillingplatform.common.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message){    //constructor
    super(message);
}
}
