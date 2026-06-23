package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {
    private static final long serialVersionUID = 1L;

    
    private ArrayList<VideoGame> games;
    private ArrayList<Customer> customers;
    private ArrayList<Employee> employees;
    private ArrayList<Sale> sales;

    public Store() {
        this.games = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.sales = new ArrayList<>();
    }

    // ==========================================
    //  OPERACIONES PARA VIDEOJUEGOS (CRUD)
    // ==========================================
    
    // REGISTRO
    public void addVideoGame(VideoGame game) {
        games.add(game);
    }

    // BÚSQUEDA por código
    public VideoGame findVideoGame(String code) {
        for (VideoGame game : games) {
            if (game.getCode().equalsIgnoreCase(code)) {
                return game;
            }
        }
        return null; // No lo encontró
    }

    // MODIFICACIÓN
    public boolean updateVideoGame(String code, String newTitle, Category newCategory, double newPrice, int newStock) {
        VideoGame game = findVideoGame(code);
        if (game != null) {
            game.setTitle(newTitle);
            game.setCategory(newCategory);
            game.setPrice(newPrice);
            game.setStock(newStock);
            return true; // Modificado con éxito
        }
        return false;
    }

    // ELIMINACIÓN
    public boolean deleteVideoGame(String code) {
        VideoGame game = findVideoGame(code);
        if (game != null) {
            games.remove(game);
            return true;
        }
        return false;
    }

    // ==========================================
    //  OPERACIONES PARA CLIENTES (CRUD)
    // ==========================================
    
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer findCustomer(String id) {
        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    // ==========================================
    //  LOGÍTICA DE NEGOCIO: REGISTRO DE VENTAS
    // ==========================================
    
    public boolean registerSale(String saleId, String gameCode, String customerId, Employee employee, int quantity) {
        VideoGame game = findVideoGame(gameCode);
        Customer customer = findCustomer(customerId);

        // Validaciones de negocio antes de vender
        if (game == null || customer == null) return false;
        if (game.getStock() < quantity) return false; // No hay suficiente stock

        // Aplicar el cambio: reducir stock del juego
        game.reduceStock(quantity);

        // Crear la venta y guardarla en el historial
        Sale newSale = new Sale(saleId, game, customer, employee, quantity);
        sales.add(newSale);
        return true;
    }

    // REQUISITO: Cálculo de ingresos totales
    public double calculateTotalRevenue() {
        double total = 0;
        for (Sale sale : sales) {
            total += sale.getTotalAmount();
        }
        return total;
    }

    // ==========================================
    //  GETTERS (Para que Swing pueda listar los datos)
    // ==========================================
    public ArrayList<VideoGame> getGames() { return games; }
    public ArrayList<Customer> getCustomers() { return customers; }
    public ArrayList<Employee> getEmployees() { return employees; }
    public ArrayList<Sale> getSales() { return sales; }

    // Métodos para cargar datos masivamente cuando usemos persistencia
    public void setGames(ArrayList<VideoGame> games) { this.games = games; }
    public void setCustomers(ArrayList<Customer> customers) { this.customers = customers; }
    public void setEmployees(ArrayList<Employee> employees) { this.employees = employees; }
    public void setSales(ArrayList<Sale> sales) { this.sales = sales; }
}
