package service;
import model.Customer;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class CustomerService {
    private static final CustomerService INSTANCE = new CustomerService();
    private CustomerService() {}

    public static CustomerService getInstance() {
        return INSTANCE;
    }

    private final List<Customer> customersList = new ArrayList<>();
    public void addCustomer(String email,String firstName,String lastName){
        for (Customer c : customersList) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
        Customer customer=new Customer(firstName,lastName,email);
        customersList.add(customer);
    }
    public Customer getCustomer(String customerEmail){
        for(Customer c:customersList){
            if(c.getEmail().equalsIgnoreCase(customerEmail)){
                return c;
            }
        }return null;
    }
    public Collection<Customer>getAllCustomers(){
        return new ArrayList<>(customersList);
    }
}
