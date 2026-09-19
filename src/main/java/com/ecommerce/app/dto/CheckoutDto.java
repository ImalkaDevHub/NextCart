package com.ecommerce.app.dto;

import com.ecommerce.app.entity.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CheckoutDto {

    @NotBlank(message = "Recipient name is required")
    private String recipientName;

    @NotBlank(message = "Phone number is required")
    private String recipientPhone;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotNull(message = "Please select a payment method")
    private PaymentMethod paymentMethod;

    private String cardNumber;
    private String cardExpiry;
    private String cardCvv;
    private String upiId;
    private Boolean simulateFailure;

    public CheckoutDto() {}

    public CheckoutDto(String recipientName, String recipientPhone, String shippingAddress, PaymentMethod paymentMethod, String cardNumber, String cardExpiry, String cardCvv, String upiId, Boolean simulateFailure) {
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.cardExpiry = cardExpiry;
        this.cardCvv = cardCvv;
        this.upiId = upiId;
        this.simulateFailure = simulateFailure;
    }

    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public String getRecipientPhone() { return recipientPhone; }
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public String getCardExpiry() { return cardExpiry; }
    public void setCardExpiry(String cardExpiry) { this.cardExpiry = cardExpiry; }
    public String getCardCvv() { return cardCvv; }
    public void setCardCvv(String cardCvv) { this.cardCvv = cardCvv; }
    public String getUpiId() { return upiId; }
    public void setUpiId(String upiId) { this.upiId = upiId; }
    public Boolean getSimulateFailure() { return simulateFailure; }
    public void setSimulateFailure(Boolean simulateFailure) { this.simulateFailure = simulateFailure; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String recipientName;
        private String recipientPhone;
        private String shippingAddress;
        private PaymentMethod paymentMethod;
        private String cardNumber;
        private String cardExpiry;
        private String cardCvv;
        private String upiId;
        private Boolean simulateFailure;

        public Builder recipientName(String recipientName) { this.recipientName = recipientName; return this; }
        public Builder recipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; return this; }
        public Builder shippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; return this; }
        public Builder paymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public Builder cardNumber(String cardNumber) { this.cardNumber = cardNumber; return this; }
        public Builder cardExpiry(String cardExpiry) { this.cardExpiry = cardExpiry; return this; }
        public Builder cardCvv(String cardCvv) { this.cardCvv = cardCvv; return this; }
        public Builder upiId(String upiId) { this.upiId = upiId; return this; }
        public Builder simulateFailure(Boolean simulateFailure) { this.simulateFailure = simulateFailure; return this; }

        public CheckoutDto build() {
            return new CheckoutDto(recipientName, recipientPhone, shippingAddress, paymentMethod, cardNumber, cardExpiry, cardCvv, upiId, simulateFailure);
        }
    }
}
