package dev.karan.subscriptionbillingplatform.common.exception;

public class BusinessValidationException extends RuntimeException{

    public BusinessValidationException(String message){
        super(message);
    }
}
