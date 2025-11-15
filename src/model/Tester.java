package model;

public class Tester {
    public static void main(String[] args) {
        Customer customer = new Customer("first", "second", "j@domain.com");
        System.out.println(customer);

        try {
            Customer invalid = new Customer("first", "second", "email");
            System.out.println(invalid);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid email was detected.");
        }

        Customer valid = new Customer("first", "second", "name@domain.com");
        System.out.println(valid);
    }
}
