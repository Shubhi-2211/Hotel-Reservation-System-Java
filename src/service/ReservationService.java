package service;
import model.IRoom;
import model.Customer;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static final ReservationService INSTANCE = new ReservationService();
    private ReservationService() {}

    public static ReservationService getInstance() {
        return INSTANCE;
    }
    private static final Map<IRoom, List<Reservation>> reservationStorage = new HashMap<>();
    public void addRoom(IRoom room) {
        if (reservationStorage.containsKey(room)) {
            throw new IllegalArgumentException("Room already exists");
        }
        reservationStorage.put(room, new ArrayList<>());
    }
    public IRoom getARoom(String roomId){
        for (IRoom room : reservationStorage.keySet()) {
            if (room.getRoomNumber().equalsIgnoreCase(roomId)) {
                return room;
            }
        }
        return null;
    }
    public Reservation reserveARoom(Customer guest,IRoom room,Date checkIn,Date checkOut,  boolean shiftIfUnavailable){
        List<Reservation> roomBookings = reservationStorage.get(room);

        if (isAvailable(roomBookings, checkIn, checkOut)) {
            Reservation r = new Reservation(guest, room, checkIn, checkOut);
            roomBookings.add(r);
            return r;
        }
        if (shiftIfUnavailable) {
            long sevenDays = 7L * 24 * 60 * 60 * 1000;
            Date shiftedIn = new Date(checkIn.getTime() + sevenDays);
            Date shiftedOut = new Date(checkOut.getTime() + sevenDays);

            if (isAvailable(roomBookings, shiftedIn, shiftedOut)) {
                Reservation r = new Reservation(guest, room, shiftedIn, shiftedOut);
                roomBookings.add(r);
                return r;
            }
        }
        return null;
    }
    public Collection<IRoom> findRooms(Date checkIn, Date checkOut){
        List<IRoom> available = new ArrayList<>();

        for (Map.Entry<IRoom, List<Reservation>> entry : reservationStorage.entrySet()) {
            IRoom room = entry.getKey();
            if (isAvailable(entry.getValue(), checkIn, checkOut)) {
                available.add(room);
            }
        }

        return available;
    }
    public Collection<Reservation>getCustomersReservation(Customer customer){
        List<Reservation> results = new ArrayList<>();

        for (List<Reservation> reservations : reservationStorage.values()) {
            for (Reservation r : reservations) {
                if (r.fetchGuest().equals(customer)) {
                    results.add(r);
                }
            }
        }
        return results;
    }
    public void printAllReservation(){
        for (List<Reservation> reservations : reservationStorage.values()) {
            for (Reservation r : reservations) {
                System.out.println(r);
            }
        }
    }
    public Collection<IRoom> getAllRooms() {
        return reservationStorage.keySet();
    }


    //Helper method
    private boolean isAvailable(List<Reservation> list, Date in, Date out) {
        for (Reservation r : list) {
            boolean overlap = in.before(r.departureOn()) && out.after(r.arrivalOn());
            if (overlap) return false;
        }
        return true;
    }

}
