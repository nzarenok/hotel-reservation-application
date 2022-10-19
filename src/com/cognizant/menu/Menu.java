package com.cognizant.menu;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Menu class
 */
public abstract class Menu {
    static int printMenu(List<String> menu, int size) {
        printCollection(menu);

        return getMenuNumber(size);
    }

    /**
     * Prints collection
     *
     * @param collection to print
     */
    static void printCollection(Collection<?> collection) {
        if (collection == null || collection.size() == 0) {
            System.out.println("No any data to dispaly");
            return;
        }
        for (Object item : collection) {
            System.out.println(item);
        }
    }

    /**
     * Checking response
     *
     * @param question to display
     * @return boolean value
     */
    static boolean isYesResponse(String question) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println(question);
        final String response = scanner.nextLine();

        if ("y".equalsIgnoreCase(response)) {
            return true;
        } else if ("n".equalsIgnoreCase(response)) {
            return false;
        } else {
            return isYesResponse("Please enter Y (yes) or N (No)");
        }
    }

    /**
     * Gets menu number
     *
     * @param size of menu
     * @return selected menu
     */
    private static int getMenuNumber(int size) {
        Scanner sc = new Scanner(System.in);
        int selectedMenu;
        boolean isError;

        do {
            isError = false;
            while (!sc.hasNextInt()) {
                System.out.println("Please enter correct number from 1 to " + size);
                sc.next();
            }
            selectedMenu = sc.nextInt();
            if (selectedMenu <= 0 || selectedMenu > size) {
                isError = true;
                System.out.println("Please enter correct number from 1 to " + size);
            }
        } while (isError);

        return selectedMenu;
    }

}
