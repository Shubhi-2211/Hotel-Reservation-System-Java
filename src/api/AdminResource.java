package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

public class AdminResource {

    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(Collection<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
