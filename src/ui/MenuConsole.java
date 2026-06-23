package ui;

import domain.Store;
import domain.VideoGame;
import domain.Category;
import domain.Customer;
import domain.Employee;
import domain.RecommendationEngine;
import java.util.ArrayList;

public class MenuConsole {
    private Store store;

    public MenuConsole(Store store) {
        this.store = store;
        // Ya no necesitamos el Scanner aquí, ahora se encarga la clase Console
    }

    public void start() {
        int option = 0;
        do {
            Console.writeLine("\n=========================================");
            Console.writeLine("     VIDEO GAME STORE - INTERNAL SYSTEM  ");
            Console.writeLine("=========================================");
            Console.writeLine("1. Register Video Game");
            Console.writeLine("2. List & Search Video Games");
            Console.writeLine("3. Modify Video Game");
            Console.writeLine("4. Delete Video Game");
            Console.writeLine("5. Register Customer");
            Console.writeLine("6. Register a Sale (Transaction)");
            Console.writeLine("7. AI Component: View Customer Recommendations");
            Console.writeLine("8. View Financial Revenue Report");
            Console.writeLine("9. Exit");
            
            try {
                String input = Console.readLine("Select an option: ");
                option = Integer.parseInt(input); // Convierte el texto a número de forma segura
            } catch (Exception e) {
                Console.writeLine("Invalid input. Please enter a number.");
                option = 0;
                continue;
            }

            switch (option) {
                case 1: registerGameForm(); break;
                case 2: listAndSearchGames(); break;
                case 3: modifyGameForm(); break;
                case 4: deleteGameForm(); break;
                case 5: registerCustomerForm(); break;
                case 6: registerSaleForm(); break;
                case 7: aiRecommendationForm(); break;
                case 8: viewRevenueReport(); break;
                case 9: Console.writeLine("Exiting and closing system..."); break;
                default: Console.writeLine("Invalid option. Try again.");
            }
        } while (option != 9);
    }

    // 1. REGISTRO DE VIDEOJUEGOS
    private void registerGameForm() {
        Console.writeLine("\n--- REGISTER NEW VIDEO GAME ---");
        String code = Console.readLine("Enter unique code (e.g., G03): ");
        
        if (store.findVideoGame(code) != null) {
            Console.writeLine("Error: A game with this code already exists.");
            return;
        }

        String title = Console.readLine("Enter title: ");
        
        Console.writeLine("Select Category:");
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            Console.writeLine((i + 1) + ". " + categories[i]);
        }
        
        int catOpt = Integer.parseInt(Console.readLine("Option: "));
        Category category = categories[Math.max(1, Math.min(catOpt, categories.length)) - 1];

        double price = Double.parseDouble(Console.readLine("Enter price: "));
        int stock = Integer.parseInt(Console.readLine("Enter initial stock: "));

        store.addVideoGame(new VideoGame(code, title, category, price, stock));
        Console.writeLine("Game successfully added to inventory!");
    }

    // 2. LISTADO Y BÚSQUEDA
    private void listAndSearchGames() {
        Console.writeLine("\n--- INVENTORY ---");
        if (store.getGames().isEmpty()) {
            Console.writeLine("No games in stock.");
        } else {
            for (VideoGame game : store.getGames()) {
                Console.writeLine(game);
            }
        }

        String search = Console.readLine("\nDo you want to search a specific game? (y/n): ");
        if (search.equalsIgnoreCase("y")) {
            String code = Console.readLine("Enter game code to search: ");
            VideoGame found = store.findVideoGame(code);
            if (found != null) {
                Console.writeLine("Found: " + found);
            } else {
                Console.writeLine("Game not found.");
            }
        }
    }

    // 3. MODIFICACIÓN
    private void modifyGameForm() {
        Console.writeLine("\n--- MODIFY VIDEO GAME ---");
        String code = Console.readLine("Enter game code to modify: ");
        VideoGame game = store.findVideoGame(code);

        if (game == null) {
            Console.writeLine("Game code not found.");
            return;
        }

        Console.writeLine("Current data: " + game);
        String newTitle = Console.readLine("Enter new title: ");
        
        Console.writeLine("Select new Category:");
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            Console.writeLine((i + 1) + ". " + categories[i]);
        }
        int catOpt = Integer.parseInt(Console.readLine("Option: "));
        Category newCategory = categories[Math.max(1, Math.min(catOpt, categories.length)) - 1];

        double newPrice = Double.parseDouble(Console.readLine("Enter new price: "));
        int newStock = Integer.parseInt(Console.readLine("Enter new stock count: "));

        store.updateVideoGame(code, newTitle, newCategory, newPrice, newStock);
        Console.writeLine("Game details updated successfully!");
    }

    // 4. ELIMINACIÓN
    private void deleteGameForm() {
        Console.writeLine("\n--- DELETE VIDEO GAME ---");
        String code = Console.readLine("Enter game code to remove: ");
        
        if (store.deleteVideoGame(code)) {
            Console.writeLine("Game successfully removed from inventory.");
        } else {
            Console.writeLine("Error: Game code not found.");
        }
    }

    // 5. REGISTRO DE CLIENTES
    private void registerCustomerForm() {
        Console.writeLine("\n--- REGISTER NEW CUSTOMER ---");
        String id = Console.readLine("Enter Customer ID (e.g., C02): ");
        
        if (store.findCustomer(id) != null) {
            Console.writeLine("Customer already exists.");
            return;
        }

        String name = Console.readLine("Enter full name: ");
        String email = Console.readLine("Enter email address: ");

        store.addCustomer(new Customer(id, name, email));
        Console.writeLine("Customer registered successfully.");
    }

    // 6. REGISTRO DE VENTAS
    private void registerSaleForm() {
        Console.writeLine("\n--- TRANSACTION: REGISTER NEW SALE ---");
        if (store.getCustomers().isEmpty() || store.getGames().isEmpty()) {
            Console.writeLine("Error: Cannot perform sales without registered customers or inventory.");
            return;
        }

        String customerId = Console.readLine("Enter customer ID: ");
        String gameCode = Console.readLine("Enter videogame code: ");
        int qty = Integer.parseInt(Console.readLine("Quantity to buy: "));

        Employee currentEmployee = store.getEmployees().get(0); 
        String saleId = "S-" + (store.getSales().size() + 1);

        boolean success = store.registerSale(saleId, gameCode, customerId, currentEmployee, qty);
        if (success) {
            Console.writeLine("Sale successfully processed! Invoice ID: " + saleId);
        } else {
            Console.writeLine("Transaction Failed: Check code, customer ID, or stock availability.");
        }
    }

    // 7. COMPONENTE DE IA: RECOMENDACIÓN DE VIDEOJUEGOS
    private void aiRecommendationForm() {
        Console.writeLine("\n--- AI RECOMENDATION ENGINE ---");
        String customerId = Console.readLine("Enter customer ID to analyze preferences: ");
        Customer customer = store.findCustomer(customerId);

        if (customer == null) {
            Console.writeLine("Customer not found.");
            return;
        }

        Console.writeLine("Running basic AI matching algorithm based on customer history...");
        ArrayList<VideoGame> suggestions = RecommendationEngine.recommendGames(customer, store);

        if (suggestions.isEmpty()) {
            Console.writeLine("AI Note: Not enough purchase history to generate a profile or no matching unowned titles available.");
        } else {
            Console.writeLine("\n>>> AI RECOMMENDED TITLES FOR " + customer.getName().toUpperCase() + " <<<");
            for (VideoGame game : suggestions) {
                Console.writeLine(" - " + game.getTitle() + " (" + game.getCategory() + ") at $" + game.getPrice());
            }
        }
    }

    // 8. CÁLCULO DE INGRESOS Y REPORTES
    private void viewRevenueReport() {
        Console.writeLine("\n--- FINANCIAL & ADMINISTRATIVE REPORT ---");
        Console.writeLine("Total Sales Completed: " + store.getSales().size());
        Console.writeLine("Total Store Revenue Generated: $" + store.calculateTotalRevenue());
        Console.writeLine("Total Active Inventory Item Types: " + store.getGames().size());
        Console.writeLine("Total Registered Clients: " + store.getCustomers().size());
    }
}