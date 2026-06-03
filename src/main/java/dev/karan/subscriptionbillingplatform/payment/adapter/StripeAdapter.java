package dev.karan.subscriptionbillingplatform.payment.adapter;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import dev.karan.subscriptionbillingplatform.payment.enums.PaymentGateway;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentStatus;
import dev.karan.subscriptionbillingplatform.payment.gateway.PaymentGatewayRequest;
import dev.karan.subscriptionbillingplatform.payment.gateway.PaymentGatewayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.math.BigDecimal;

@Component
public class StripeAdapter implements PaymentGatewayAdapter{

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Override
    public PaymentGatewayResponse createPayment(PaymentGatewayRequest request) {

        try {

            Stripe.apiKey = stripeSecretKey;

            long amountInSmallestUnit = request.getAmount()
                    .multiply(new BigDecimal("100")).longValue();

            //request body sent to Stripe - product data
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName("Subscription Payment")
                            .build();

            // price date
            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(request.getCurrency().toLowerCase())   //INR -> inr
                            .setUnitAmount(amountInSmallestUnit)
                            .setProductData(productData)
                            .build();

            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl("http://localhost:8080/payment/success")
                            .setCancelUrl("http://localhost:8080/payment/cancel")
                            .putMetadata("paymentReference", request.getPaymentReference())
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            .setPriceData(priceData)
                                            .build()
                            )
                            .build();

            Session session = Session.create(params);

            System.out.println(session.getId());
            System.out.println(session.getUrl());

            return PaymentGatewayResponse.builder()
                    .gatewayOrderId(session.getId())
                    .paymentUrl(session.getUrl())
                    .status(PaymentStatus.PENDING)
                    .build();

        } catch (StripeException e) {
            throw new RuntimeException(
                    "Failed to create Stripe checkout session", e);
        }

    }

    @Override
    public PaymentGateway getSupportedGateway() {
        return PaymentGateway.STRIPE;
    }
}
