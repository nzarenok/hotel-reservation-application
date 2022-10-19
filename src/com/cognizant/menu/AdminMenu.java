package com.cognizant.menu;

import com.cognizant.api.AdminResource;

import java.util.List;
import java.util.Scanner;

/**
 * Admin Menu class
 */
public class AdminMenu extends Menu {
    private AdminMenu() {
    }

    private static final List<String> ADMIN_MENU = List.of(
            "Admin Menu",
            "--------------------------------------------------",
            "1. See all Customers",
            "2. See all Rooms",
            "3. See all Reservations",
            "4. Add a Room",
            "5. Add Test Data",
            "6. Back to Main Menu",
            "--------------------------------------------------",
            "Please select a number for the menu option");

    /**
     * Prints Admin menu
     */
    public static void printMenu() {
        int selectedMainMenu = Menu.printMenu(ADMIN_MENU, ADMIN_MENU.size() - 4);
        menuChoice(selectedMainMenu);
    }

    /**
     * Processes menu choice
     *
     * @param selectedMainMenu selected menu option
     */
    private static void menuChoice(int selectedMainMenu) {
        switch (selectedMainMenu) {
            case 1:
                Menu.printCollection(AdminResource.getInstance().getAllCustomers());
                AdminMenu.printMenu();
                break;
            case 2:
                Menu.printCollection(AdminResource.getInstance().getAllRooms());
                AdminMenu.printMenu();
                break;
            case 3:
                Menu.printCollection(AdminResource.getInstance().getAllReservations());
                AdminMenu.printMenu();
                break;
            case 4:
                addRoom();
                AdminMenu.printMenu();
                break;
            case 5:
                System.out.println("You entered menu: 'Add Test Data'. Menu is not implemented");
                AdminMenu.printMenu();
                break;
            case 6:
                MainMenu.printMenu();
                break;
            default:
                System.out.println("Please enter correct number of Admin Menu");
                AdminMenu.printMenu();
        }
    }

    /**
     * Adds room
     */
    private static void addRoom() {
        final String roomNumber = getRoomNumber();

        final double pricePerNight = getRoomPrice();

        int roomType = getRoomType();

        AdminResource.getInstance().addRoom(roomNumber, pricePerNight, roomType);

        if (Menu.isYesResponse("Would you like to add another room? y/n")) {
            addRoom();
        }
    }

    /**
     * Gets room number
     *
     * @return Room number
     */
    private static String getRoomNumber() {
        final Scanner scanner = new Scanner(System.in);
        String roomNumber = null;

        do {
            System.out.println("Enter room number");
            roomNumber = scanner.nextLine();
        } while (roomNumber.isBlank());

        return roomNumber;
    }

    /**
     * Gets room type
     *
     * @return Room type
     */
    private static int getRoomType() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter room type: 1 for single bed, 2 for double bed");
        while (!sc.hasNext("[12]")) {
            System.out.println("Please enter 1 for single bed, 2 for double bed");
            sc.next();
        }
        return Integer.parseInt(sc.next());
    }

    /**
     * Gets room price
     *
     * @return Room price
     */
    private static double getRoomPrice() {
        Scanner sc = new Scanner(System.in);
        double price = 0.0;
        boolean isError = false;

        do {
            isError = false;
            System.out.println("Enter price per night");
            while (!sc.hasNextDouble()) {
                System.out.println("Enter correct price");
                sc.next();
            }
            price = sc.nextDouble();
            if (price < 0) {
                isError = true;
                System.out.println("Price can't be less than 0");
            }
        } while (isError);

        return price;
    }
}
