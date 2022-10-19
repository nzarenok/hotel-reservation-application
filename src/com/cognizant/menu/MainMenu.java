package com.cognizant.menu;

import com.cognizant.api.HotelResource;
import com.cognizant.model.Customer;
import com.cognizant.model.IRoom;
import com.cognizant.model.Reservation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Main Menu class
 */
public class MainMenu extends Menu {
    private static final List<String> MAIN_MENU = List.of(
            "Welcome to the Hotel Reservation Application",
            "--------------------------------------------------",
            "1. Find and reserve a room",
            "2. See my reservation",
            "3. Create an Account",
            "4. Admin",
            "5. Exit",
            "--------------------------------------------------",
            "Please select a number for the menu option");

    private MainMenu() {
    }

    /**
     * Prints Main menu
     */
    public static void printMenu() {
        int selectedMainMenu = Menu.printMenu(MAIN_MENU, MAIN_MENU.size() - 4);
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
                createReservation();
                MainMenu.printMenu();
                break;
            case 2:
                Menu.printCollection(seeMyReservation());
                MainMenu.printMenu();
                break;
            case 3:
                createAccount();
                MainMenu.printMenu();
                break;
            case 4:
                AdminMenu.printMenu();
                break;
            case 5:
                break;
            default:
                System.out.println("Please enter correct number of Main Menu");
                MainMenu.printMenu();
        }
    }

    /**
     * Creates account
     */
    private static void createAccount() {
        final Scanner scanner = new Scanner(System.in);

        final String email = getEmail(true);

        System.out.println("First Name");
        final String firstName = scanner.nextLine();

        System.out.println("Last Name");
        final String lastName = scanner.nextLine();

        HotelResource.getInstance().createCustomer(firstName, lastName, email);
    }

    /**
     * Gets email
     *
     * @param isCustomerExistCheck boolean flag
     * @return email
     */
    private static String getEmail(boolean isCustomerExistCheck) {
        final Scanner scanner = new Scanner(System.in);
        String email;
        boolean isError;

        do {
            isError = false;
            System.out.println("Enter email (format: name@domain.com)");
            email = scanner.nextLine();
            if (!Customer.patternMatches(email, Customer.EMAIL_PATTERN)) {
                isError = true;
            } else if (isCustomerExistCheck && HotelResource.getInstance().getCustomer(email) != null) {
                System.out.println("Sorry, this user is already exists!");
                isError = true;
            }
        } while (isError);

        return email;
    }

    /**
     * Creates reservation
     */
    private static void createReservation() {
        Date checkInDate = formatDate("Enter ChekIn Date (format: mm/dd/yyyy)");
        Date checkOutDate = formatDate("Enter ChekOut Date (format: mm/dd/yyyy)");

        Collection<IRoom> availableRooms = HotelResource.getInstance().findAvailableRooms(checkInDate, checkOutDate);
        if (availableRooms.size() == 0) {
            final Date alternativeCheckInDate = HotelResource.getInstance().addDefaultDays(checkInDate);
            final Date alternativeCheckOutDate = HotelResource.getInstance().addDefaultDays(checkOutDate);
            Collection<IRoom> availableAltRooms = HotelResource.getInstance().findAvailableRooms(alternativeCheckInDate, alternativeCheckOutDate);
            if (availableAltRooms.size() == 0) {
                System.out.println("Sorry, no available rooms, come back later!");
                return;
            }
            availableRooms = availableAltRooms;
            checkInDate = alternativeCheckInDate;
            checkOutDate = alternativeCheckOutDate;
            System.out.println("We've only found rooms for alternative dates:" +
                    "\nCheck In Date:" + alternativeCheckInDate +
                    "\nCheck Out Date:" + alternativeCheckOutDate);
        }

        Menu.printCollection(availableRooms);

        if (Menu.isYesResponse("Would you like to book a room? y/n")) {
            reserveRoom(checkInDate, checkOutDate, availableRooms);
        }
    }

    /**
     * Reserves room
     *
     * @param checkInDate    check in date
     * @param checkOutDate   check out date
     * @param availableRooms Collection of available rooms
     */
    private static void reserveRoom(Date checkInDate, Date checkOutDate, Collection<IRoom> availableRooms) {
        final Scanner scanner = new Scanner(System.in);

        final boolean isAccountExist = Menu.isYesResponse("Do you have an account with us? y/n");
        if (!isAccountExist) {
            System.out.println("Please create an account in Main Menu");
            return;
        }

        System.out.println("Enter Email (format:name@domail.com)");
        final String enteredEmail = scanner.nextLine();

        if (HotelResource.getInstance().getCustomer(enteredEmail) == null) {
            System.out.println("Customer not found, you need create a new account.");
            return;
        }

        final String reserveRoomNumber = getRoomNumber(availableRooms);

        Reservation reservation = HotelResource.getInstance().bookRoom(enteredEmail, reserveRoomNumber, checkInDate, checkOutDate);

        if (reservation != null) {
            System.out.println("Reservation created successfully!");
            System.out.println(reservation);
        }
    }

    /**
     * Returns user reservations
     *
     * @return Collection of Resevation
     */
    private static Collection<Reservation> seeMyReservation() {

        String enteredEmail = getEmail(false);

        return HotelResource.getInstance().getCustomerReservation(enteredEmail);
    }

    /**
     * Formats date
     *
     * @param message user input message
     * @return Date from String
     */
    private static Date formatDate(String message) {
        Scanner sc = new Scanner(System.in);
        boolean isException;
        Date validDate = null;

        do {
            System.out.println(message);
            final String d = sc.nextLine();

            try {
                isException = false;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate formattedDate = LocalDate.parse(d, formatter);
                validDate = Date.from(formattedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            } catch (DateTimeParseException e) {
                isException = true;
                System.out.println("Date is invalid, please enter again");
            }

        } while (isException);

        return validDate;
    }

    /**
     * Gets room number
     *
     * @return Room number
     */
    private static String getRoomNumber(Collection<IRoom> availableRooms) {
        Scanner sc = new Scanner(System.in);
        String roomNumber;
        boolean isError;

        do {
            System.out.println("What room number would you like to reserve?");
            roomNumber = sc.nextLine();
            isError = false;

            if (roomNumber.isBlank() || HotelResource.getInstance().getRoom(roomNumber) == null
                    || !HotelResource.getInstance().isRoomExistInAvailable(roomNumber, availableRooms)) {
                isError = true;
                System.out.println("Sorry, we don't have such room, please enter room number again.");
            }

        } while (isError);

        return roomNumber;
    }
}