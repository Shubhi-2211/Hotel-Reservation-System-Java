package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room,
                                 Date checkInDate, Date checkOutDate, boolean shiftIfUnavailable) {

        Customer customer = customerService.getCustomer(customerEmail);

        if (customer == null) {
            throw new IllegalArgumentException("Customer is not registered");
        }

        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate, shiftIfUnavailable);
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return reservationService.findRooms(checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail) {
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.getCustomersReservation(customer);
    }
}
