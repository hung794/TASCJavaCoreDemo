package views;

import model.ProductSearchBody;
import service.FileProductService;
import util.ValidateUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static FileProductService fileProductService;

    static {
        try {
            fileProductService = new FileProductService();
        } catch (IOException e) {
            notification("An error occurred, press any button to return to the main menu.");
        }
    }

    public static void showMainMenu() {
        List<String> listChoose = Arrays.asList("0", "1", "2", "3", "4", "5");
        while (true) {
            System.out.println("                                            ");
            System.out.println("PRODUCT MANAGER:");
            System.out.println("*****************************************************");
            System.out.println("1. Add product.");
            System.out.println("2. View products list.");
            System.out.println("3. Search product.");
            System.out.println("4. Update product.");
            System.out.println("5. Delete product.");
            System.out.println("0. Exit. ");
            System.out.println("*****************************************************");
            System.out.print("Enter your choose: ");
            String choose = scanner.nextLine().trim();
            while (!listChoose.contains(choose)) {
                System.out.println("Wrong choice, please choose again!");
                System.out.print("Enter your choose: ");
                choose = scanner.nextLine().trim();
            }
            if (choose.equals("0")) {
                scanner.close();
                fileProductService.saveId();
                fileProductService.closeFileReader();
                System.exit(0);
            } else {
                switch (choose) {
                    case "1":
                        addProduct();
                        break;
                    case "2":
                        getProductList();
                        break;
                    case "3":
                        searchProduct();
                        break;
                    case "4":
                        updateProduct();
                        break;
                    case "5":
                        deleteProduct();
                        break;
                }
            }
        }
    }

    public static void getProductList() {
        System.out.println("                                            ");
        System.out.println("PRODUCTS LIST: ");
        System.out.println("--------------------------------------------------------------------------" +
                "----------------------------------------------------------");
        System.out.printf("%2s%3s%4s%14s%4s%16s%13s%12s%15s%13s%4s%15s%5s%5s%4s",
                "", "Id", "|",
                "", "Name", "|",
                "", "Manufacturer", "|",
                "", "Line", "|",
                "", "Price", "");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------" +
                "----------------------------------------------------------");
        try {
            fileProductService.getAllProduct();
        } catch (IOException e) {
            notification("An error occurred, press any button to return to the main menu.");
        }
        System.out.println("--------------------------------------------------------------------------" +
                "----------------------------------------------------------");
        System.out.println("Press any button to return to the main menu.");
        scanner.nextLine();
        showMainMenu();
    }

    public static void addProduct() {
        System.out.println("                                            ");
        System.out.println("ADD PRODUCT: ");
        String productStr = getProductInput("create", null);
        if (fileProductService.addProduct(productStr)) {
            notification("Add product success! Press any key to return main menu.");
        } else {
            notification("An error occurred, press any button to return to the main menu.");
        }
    }

    public static void updateProduct() {
        System.out.println("                                            ");
        System.out.println("UPDATE PRODUCT: ");
        System.out.println("Please enter product id: ");
        String id = scanner.nextLine();
        if (fileProductService.getProductDetail(id) == null) {
            notification("Product not found! Press any button to return to the main menu.");
        } else {
            System.out.println("Note: if you do not want to update information, please leave it blank.");
            if (fileProductService.updateProduct(id, getProductInput("update", id))) {
                notification("Update product success! Press any button to return to the main menu.");
            } else {
                notification("An error occurred, press any button to return to the main menu.");
            }
        }
    }

    public static void deleteProduct() {
        System.out.println("                                            ");
        System.out.println("DELETE PRODUCT: ");
        System.out.println("Please enter product id: ");
        String id = scanner.nextLine();
        if (fileProductService.getProductDetail(id) == null) {
            notification("Product not found! Press any button to return to the main menu.");
        } else {
            if (fileProductService.deleteProduct(id)) {
                notification("Delete product success! Press any button to return to the main menu.");
            } else {
                notification("An error occurred, press any button to return to the main menu.");
            }
        }
    }

    public static void searchProduct() {
        System.out.println("                                            ");
        System.out.println("SEARCH PRODUCT: ");
        System.out.println("Note: if you do not want to search by certain information, please leave that information blank.");
        System.out.println("Please enter product name: ");
        String name = scanner.nextLine();
        System.out.println("Please enter manufacturer name: ");
        String manufacturer = scanner.nextLine();
        System.out.println("Please enter product line name: ");
        String line = scanner.nextLine();
        System.out.println("Please enter product start price: ");
        String startPrice = scanner.nextLine();
        while (!ValidateUtil.checkNumber(startPrice)) {
            System.out.println("Start price is not in the correct format, please re-enter the product price: ");
            startPrice = scanner.nextLine();
        }
        System.out.println("Please enter product end price: ");
        String endPrice = scanner.nextLine();
        while (!ValidateUtil.checkNumber(endPrice)) {
            System.out.println("End price is not in the correct format, please re-enter the product price: ");
            endPrice = scanner.nextLine();
        }
        ProductSearchBody searchBody = new ProductSearchBody(name, manufacturer, line, startPrice, endPrice);
        System.out.println("                                            ");
        System.out.println("SEARCH PRODUCT RESULT: ");
        System.out.println("--------------------------------------------------------------------------" +
                "----------------------------------------------------------");
        System.out.printf("%2s%3s%4s%14s%4s%16s%13s%12s%15s%13s%4s%15s%5s%5s%4s",
                "", "Id", "|",
                "", "Name", "|",
                "", "Manufacturer", "|",
                "", "Line", "|",
                "", "Price", "");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------" +
                "----------------------------------------------------------");
        try {
            fileProductService.searchProduct(searchBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("--------------------------------------------------------------------------" +
                "----------------------------------------------------------");
        System.out.println("Press any button to return to the main menu.");
        scanner.nextLine();
        showMainMenu();
    }

    private static String getProductInput(String mode, String id) {
        System.out.println("Please enter product name: ");
        String name = scanner.nextLine();
        if (mode.equals("create")) {
            while (name.trim().isEmpty()) {
                System.out.println("Product name cannot be left blank, please re-enter the product name: ");
                name = scanner.nextLine();
            }
        }
        while (!ValidateUtil.checkString(name)) {
            System.out.println("The name is not in the correct format, please re-enter the product name: ");
            name = scanner.nextLine();
        }
        System.out.println("Please enter manufacturer name: ");
        String manufacturer = scanner.nextLine();
        if (mode.equals("create")) {
            while (manufacturer.isEmpty()) {
                System.out.println("Manufacturer name cannot be left blank, please re-enter the manufacturer name: ");
                manufacturer = scanner.nextLine();
            }
        }
        while (!ValidateUtil.checkString(manufacturer)) {
            System.out.println("Manufacturer name is not in the correct format, please re-enter the manufacturer name: ");
            manufacturer = scanner.nextLine();
        }
        System.out.println("Please enter product line name: ");
        String line = scanner.nextLine();
        if (mode.equals("create")) {
            while (line.isEmpty()) {
                System.out.println("Product liner name cannot be left blank, please re-enter the product line name: ");
                line = scanner.nextLine();
            }
        }
        while (!ValidateUtil.checkString(manufacturer)) {
            System.out.println("Product line name is not in the correct format, please re-enter the product line name: ");
            line = scanner.nextLine();
        }
        System.out.println("Please enter product price: ");
        String price = scanner.nextLine();
        if (mode.equals("create")) {
            while (price.isEmpty()) {
                System.out.println("Product price cannot be left blank, please re-enter the product price: ");
                price = scanner.nextLine();
            }
        }
        while (!ValidateUtil.checkNumber(price)) {
            System.out.println("Product price is not in the correct format, please re-enter the product price: ");
            price = scanner.nextLine();
        }
        if (mode.equals("update")) {
            if (name.trim().length() == 0) {
                name = "null";
            }
            if (manufacturer.trim().length() == 0) {
                manufacturer = "null";
            }
            if (line.trim().length() == 0) {
                line = "null";
            }
            if (price.trim().length() == 0) {
                price = "null";
            }
            return id + " - " + name + " - " + manufacturer + " - " + line + " - " + price;
        } else return name + " - " + manufacturer + " - " + line + " - " + price;
    }

    private static void notification(String msg) {
        System.out.println(msg);
        scanner.nextLine();
        showMainMenu();
    }
}