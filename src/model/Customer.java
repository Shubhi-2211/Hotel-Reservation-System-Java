package model;

import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    @Override
    public String toString() {
        return "FirstName:" + firstName + ", LastName: " + lastName + ", Email: " + email;
    }

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern emailValidator = Pattern.compile(EMAIL_PATTERN);

    public Customer(String firstName, String lastName, String email) {
        if (!emailValidator.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer cust = (Customer) o;
        return email.equals(cust.email);
    }
    @Override
    public int hashCode() {
        return email.hashCode();
    }

}
