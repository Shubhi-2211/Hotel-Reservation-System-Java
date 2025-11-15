import api.AdminResource;
import model.IRoom;
import model.Room;
import model.FreeRoom;
import model.RoomType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class AdminMenu {

    private static final AdminResource adminResource = new AdminResource();
    private static final Scanner scanner = new Scanner(System.in);

    public static void start() {

        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showAllCustomers();
                    break;
                case "2":
                    showAllRooms();
                    break;
                case "3":
                    showAllReservations();
                    break;
                case "4":
                    addRoom();
                    break;
                case "5":
                    return; // back to MainMenu
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    private static void showAllCustomers() {
        adminResource.getAllCustomers().forEach(System.out::println);
    }

    private static void showAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (rooms == null || rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    private static void showAllReservations() {
        adminResource.displayAllReservations();
    }

    private static void addRoom() {
        Collection<IRoom> roomsToAdd = new ArrayList<>();

        boolean addMore = true;
        while (addMore) {

            System.out.print("Enter room number: ");
            String roomNumber = scanner.nextLine();

            System.out.print("Enter room price: ");
            String priceInp = scanner.nextLine();

            System.out.print("Enter room type: 1 for SINGLE, 2 for DOUBLE: ");
            String typeOption = scanner.nextLine();

            RoomType roomType;
            if (typeOption.equals("1")) {
                roomType = RoomType.SINGLE;
            } else if (typeOption.equals("2")) {
                roomType = RoomType.DOUBLE;
            } else {
                System.out.println("Invalid room type. Try again.");
                continue;
            }

            IRoom room;

            // price = 0.0 means FreeRoom
            if (priceInp.trim().equals("0") || priceInp.equals("0.0")) {
                room = new FreeRoom(roomNumber, roomType);
            } else {
                try {
                    double price = Double.parseDouble(priceInp);
                    room = new Room(roomNumber, price, roomType);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price. Try again.");
                    continue;
                }
            }

            roomsToAdd.add(room);

            System.out.print("Would you like to add another room? (y/n): ");
            addMore = scanner.nextLine().equalsIgnoreCase("y");
        }

        try {
            adminResource.addRoom(roomsToAdd);
            System.out.println("Room(s) added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
