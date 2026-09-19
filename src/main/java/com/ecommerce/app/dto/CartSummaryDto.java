package com.ecommerce.app.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartSummaryDto {

    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal shippingFee;
    private BigDecimal grandTotal;
    private Integer totalItems;

    public CartSummaryDto() {}

    public CartSummaryDto(List<CartItemDto> items, BigDecimal subtotal, BigDecimal taxAmount, BigDecimal shippingFee, BigDecimal grandTotal, Integer totalItems) {
        this.items = items != null ? items : new ArrayList<>();
        this.subtotal = subtotal;
        this.taxAmount = taxAmount;
        this.shippingFee = shippingFee;
        this.grandTotal = grandTotal;
        this.totalItems = totalItems;
    }

    public List<CartItemDto> getItems() { return items; }
    public void setItems(List<CartItemDto> items) { this.items = items; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
    public BigDecimal getShippingFee() { return shippingFee; }
    public void setShippingFee(BigDecimal shippingFee) { this.shippingFee = shippingFee; }
    public BigDecimal getGrandTotal() { return grandTotal; }
    public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }
    public Integer getTotalItems() { return totalItems; }
    public void setTotalItems(Integer totalItems) { this.totalItems = totalItems; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private List<CartItemDto> items = new ArrayList<>();
        private BigDecimal subtotal;
        private BigDecimal taxAmount;
        private BigDecimal shippingFee;
        private BigDecimal grandTotal;
        private Integer totalItems;

        public Builder items(List<CartItemDto> items) { this.items = items; return this; }
        public Builder subtotal(BigDecimal subtotal) { this.subtotal = subtotal; return this; }
        public Builder taxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; return this; }
        public Builder shippingFee(BigDecimal shippingFee) { this.shippingFee = shippingFee; return this; }
        public Builder grandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; return this; }
        public Builder totalItems(Integer totalItems) { this.totalItems = totalItems; return this; }

        public CartSummaryDto build() {
            return new CartSummaryDto(items, subtotal, taxAmount, shippingFee, grandTotal, totalItems);
        }
    }
}
