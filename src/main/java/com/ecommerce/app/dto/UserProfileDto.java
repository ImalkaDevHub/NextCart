package com.ecommerce.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserProfileDto {

    private Long id;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email address is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    private String phone;

    private String address;

    private String currentPassword;

    private String newPassword;

    public UserProfileDto() {}

    public UserProfileDto(Long id, String fullName, String email, String phone, String address, String currentPassword, String newPassword) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCurrentPassword() { return currentPassword; }
    public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String fullName;
        private String email;
        private String phone;
        private String address;
        private String currentPassword;
        private String newPassword;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder address(String address) { this.address = address; return this; }
        public Builder currentPassword(String currentPassword) { this.currentPassword = currentPassword; return this; }
        public Builder newPassword(String newPassword) { this.newPassword = newPassword; return this; }

        public UserProfileDto build() {
            return new UserProfileDto(id, fullName, email, phone, address, currentPassword, newPassword);
        }
    }
}
