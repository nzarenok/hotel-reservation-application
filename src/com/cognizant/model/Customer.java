package com.cognizant.model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Customer class
 */
public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9]+[.+\\-\\w]*@[A-Za-z0-9]+(?:[.\\-_][A-Za-z0-9]+)*\\.[A-Za-z]{2,}$";

    /**
     * Gets customer
     *
     * @param firstName first name
     * @param lastName  last name
     * @param email     email
     */
    public Customer(String firstName, String lastName, String email) {
        if (!patternMatches(email, Customer.EMAIL_PATTERN)) {
            throw new IllegalArgumentException("Email is not valid");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Customer() {
    }

    /**
     * Checking if pattern matches
     *
     * @param checkedValue checked value
     * @param regexPattern regex pattern
     * @return pattern
     */
    public static boolean patternMatches(String checkedValue, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(checkedValue)
                .matches();
    }

    /**
     * Gets First name
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets First name
     *
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets Last name
     *
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets Last name
     *
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer" +
                " First Name: " + firstName +
                ", Last Name: " + lastName +
                ", email: " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return firstName.equals(customer.firstName) && lastName.equals(customer.lastName) && email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
