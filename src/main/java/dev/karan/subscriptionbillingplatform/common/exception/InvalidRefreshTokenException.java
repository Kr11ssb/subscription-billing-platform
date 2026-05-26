package dev.karan.subscriptionbillingplatform.common.exception;

public class InvalidRefreshTokenException extends RuntimeException{

    public InvalidRefreshTokenException(String message){
        super(message);
    }
}
