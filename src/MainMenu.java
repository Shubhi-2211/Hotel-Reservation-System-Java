
import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    private static final HotelResource hotelResource = new HotelResource();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    static {
        formatter.setLenient(false);  // 👉 makes invalid dates throw ParseException
    }

    public static void start() {

        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== HOTEL RESERVATION MAIN MENU ===");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin panel");
            System.out.println("5. Exit");
            System.out.print("Enter option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    seeMyReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.start();
                    break;
                case "5":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void findAndReserveRoom() {
        try {
            System.out.print("Enter Check-In Date (dd/MM/yyyy): ");
            Date checkIn = formatter.parse(scanner.nextLine());

            System.out.print("Enter Check-Out Date (dd/MM/yyyy): ");
            Date checkOut = formatter.parse(scanner.nextLine());

            // find available rooms
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);

            if (availableRooms.isEmpty()) {
                System.out.println("No rooms available for selected dates.");
                System.out.print("Shift dates by 7 days? (y/n): ");
                boolean shift = scanner.nextLine().equalsIgnoreCase("y");

                System.out.print("Enter room number: ");
                String roomId = scanner.nextLine();
                IRoom room = hotelResource.getRoom(roomId);

                System.out.print("Enter registered email: ");
                String email = scanner.nextLine();

                Reservation r = hotelResource.bookARoom(email, room, checkIn, checkOut, shift);
                if (r == null) {
                    System.out.println("Reservation cannot be made.");
                } else {
                    System.out.println("\nReservation confirmed:\n" + r);
                }
                return;
            }

            System.out.println("\nAvailable Rooms:");
            for (IRoom r : availableRooms) System.out.println(r);

            System.out.print("\nWould you like to book a room? (y/n): ");
            if (!scanner.nextLine().equalsIgnoreCase("y")) return;

            System.out.print("Enter room number to book: ");
            IRoom selectedRoom = hotelResource.getRoom(scanner.nextLine());

            System.out.print("Enter registered email: ");
            String email = scanner.nextLine();

            Reservation finalReservation =
                    hotelResource.bookARoom(email, selectedRoom, checkIn, checkOut, false);

            System.out.println("\nReservation successful:");
            System.out.println(finalReservation);

        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter date in dd/MM/yyyy format.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void seeMyReservations() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Collection<Reservation> list = hotelResource.getCustomerReservations(email);
        if (list.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("Your Reservations:");
            list.forEach(System.out::println);
        }
    }

    private static void createAccount() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter first name: ");
        String first = scanner.nextLine();

        System.out.print("Enter last name: ");
        String last = scanner.nextLine();

        try {
            hotelResource.createACustomer(email, first, last);
            System.out.println("Account created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
