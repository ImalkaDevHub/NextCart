package com.ecommerce.app.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardStatsDto {

    private BigDecimal totalRevenue;
    private Long totalOrders;
    private Long totalProducts;
    private Long totalCustomers;
    private List<OrderResponseDto> recentOrders;
    private List<ProductDto> lowStockProducts;
    private List<ProductDto> topSellingProducts;
    private Map<String, BigDecimal> monthlySales;
    private Map<String, Long> categoryDistribution;

    public DashboardStatsDto() {}

    public DashboardStatsDto(BigDecimal totalRevenue, Long totalOrders, Long totalProducts, Long totalCustomers, List<OrderResponseDto> recentOrders, List<ProductDto> lowStockProducts, List<ProductDto> topSellingProducts, Map<String, BigDecimal> monthlySales, Map<String, Long> categoryDistribution) {
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
        this.totalProducts = totalProducts;
        this.totalCustomers = totalCustomers;
        this.recentOrders = recentOrders;
        this.lowStockProducts = lowStockProducts;
        this.topSellingProducts = topSellingProducts;
        this.monthlySales = monthlySales;
        this.categoryDistribution = categoryDistribution;
    }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    public Long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Long totalOrders) { this.totalOrders = totalOrders; }
    public Long getTotalProducts() { return totalProducts; }
    public void setTotalProducts(Long totalProducts) { this.totalProducts = totalProducts; }
    public Long getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(Long totalCustomers) { this.totalCustomers = totalCustomers; }
    public List<OrderResponseDto> getRecentOrders() { return recentOrders; }
    public void setRecentOrders(List<OrderResponseDto> recentOrders) { this.recentOrders = recentOrders; }
    public List<ProductDto> getLowStockProducts() { return lowStockProducts; }
    public void setLowStockProducts(List<ProductDto> lowStockProducts) { this.lowStockProducts = lowStockProducts; }
    public List<ProductDto> getTopSellingProducts() { return topSellingProducts; }
    public void setTopSellingProducts(List<ProductDto> topSellingProducts) { this.topSellingProducts = topSellingProducts; }
    public Map<String, BigDecimal> getMonthlySales() { return monthlySales; }
    public void setMonthlySales(Map<String, BigDecimal> monthlySales) { this.monthlySales = monthlySales; }
    public Map<String, Long> getCategoryDistribution() { return categoryDistribution; }
    public void setCategoryDistribution(Map<String, Long> categoryDistribution) { this.categoryDistribution = categoryDistribution; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private BigDecimal totalRevenue;
        private Long totalOrders;
        private Long totalProducts;
        private Long totalCustomers;
        private List<OrderResponseDto> recentOrders;
        private List<ProductDto> lowStockProducts;
        private List<ProductDto> topSellingProducts;
        private Map<String, BigDecimal> monthlySales;
        private Map<String, Long> categoryDistribution;

        public Builder totalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; return this; }
        public Builder totalOrders(Long totalOrders) { this.totalOrders = totalOrders; return this; }
        public Builder totalProducts(Long totalProducts) { this.totalProducts = totalProducts; return this; }
        public Builder totalCustomers(Long totalCustomers) { this.totalCustomers = totalCustomers; return this; }
        public Builder recentOrders(List<OrderResponseDto> recentOrders) { this.recentOrders = recentOrders; return this; }
        public Builder lowStockProducts(List<ProductDto> lowStockProducts) { this.lowStockProducts = lowStockProducts; return this; }
        public Builder topSellingProducts(List<ProductDto> topSellingProducts) { this.topSellingProducts = topSellingProducts; return this; }
        public Builder monthlySales(Map<String, BigDecimal> monthlySales) { this.monthlySales = monthlySales; return this; }
        public Builder categoryDistribution(Map<String, Long> categoryDistribution) { this.categoryDistribution = categoryDistribution; return this; }

        public DashboardStatsDto build() {
            return new DashboardStatsDto(totalRevenue, totalOrders, totalProducts, totalCustomers, recentOrders, lowStockProducts, topSellingProducts, monthlySales, categoryDistribution);
        }
    }
}
