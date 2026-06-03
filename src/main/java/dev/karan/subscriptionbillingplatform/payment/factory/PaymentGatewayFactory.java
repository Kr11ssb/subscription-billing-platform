package dev.karan.subscriptionbillingplatform.payment.factory;

import dev.karan.subscriptionbillingplatform.common.exception.BusinessValidationException;
import dev.karan.subscriptionbillingplatform.payment.adapter.PaymentGatewayAdapter;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentGatewayFactory {

    private final List<PaymentGatewayAdapter> adapters;

    public PaymentGatewayAdapter getAdapter(PaymentGateway gateway) {
        for (PaymentGatewayAdapter adapter : adapters)
            if (adapter.getSupportedGateway() == gateway) {
                return adapter;
            }
        throw new BusinessValidationException("unsupported payment gateway: " + gateway);
    }
}
