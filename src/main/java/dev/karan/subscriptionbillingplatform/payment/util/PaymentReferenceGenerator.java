package dev.karan.subscriptionbillingplatform.payment.util;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;

@Component
public class PaymentReferenceGenerator {

    private static final String PREFIX = "PAY-";

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int REFERENCE_LENGTH = 12;

    private static final SecureRandom RANDOM = new SecureRandom();

    public String generate(){

        StringBuilder referenceBuilder = new StringBuilder(PREFIX);

        for(int i=0;i<REFERENCE_LENGTH;i++){
            int index = RANDOM.nextInt(CHARACTERS.length());
            referenceBuilder.append(CHARACTERS.charAt(index));
        }
        return referenceBuilder.toString();
    }

}
