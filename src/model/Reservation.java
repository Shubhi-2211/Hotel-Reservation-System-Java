package model;

import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date arrivalDate;
    private Date departureDate;

    @Override
    public String toString() {
        return "Customer: " + customer + ", Room: " + room +
                ", Check-In: " + arrivalDate + ", Check-out: " + departureDate;
    }

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.arrivalDate = checkInDate;
        this.departureDate = checkOutDate;
    }

    public Customer fetchGuest() {
        return customer;
    }

    public IRoom fetchReservedRoom() {
        return room;
    }

    public Date arrivalOn() {
        return arrivalDate;
    }

    public Date departureOn() {
        return departureDate;
    }
}
