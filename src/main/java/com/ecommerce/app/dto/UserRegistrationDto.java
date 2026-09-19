package com.ecommerce.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email address is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private String confirmPassword;

    private String phone;

    private String address;

    public UserRegistrationDto() {}

    public UserRegistrationDto(String fullName, String email, String password, String confirmPassword, String phone, String address) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phone = phone;
        this.address = address;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String fullName;
        private String email;
        private String password;
        private String confirmPassword;
        private String phone;
        private String address;

        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder confirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder address(String address) { this.address = address; return this; }

        public UserRegistrationDto build() {
            return new UserRegistrationDto(fullName, email, password, confirmPassword, phone, address);
        }
    }
}
