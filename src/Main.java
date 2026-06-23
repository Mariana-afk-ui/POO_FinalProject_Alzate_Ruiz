import domain.Store;
import domain.Employee;
import domain.Customer;
import domain.VideoGame;
import domain.Category;
import ui.MenuConsole;
import data.DataHandler; // <--- Importamos la nueva capa de datos

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing Video Game Store System...");

        // 1. CARGAMOS LA TIENDA DESDE EL ARCHIVO
        // Si ya existía un archivo guardado, traerá todo el inventario viejo.
        Store myStore = DataHandler.loadStore();

        // 2. CARGA DE DATOS DE RESPALDO (Solo si el sistema está totalmente vacío)
        // Esto evita que se te dupliquen los datos cada vez que abres y cierras el programa.
        if (myStore.getGames().isEmpty() && myStore.getCustomers().isEmpty()) {
            System.out.println("[SYSTEM INFO] Injecting first-time demo data...");
            
            Employee defaultEmployee = new Employee("admin01", "John Doe", "Administrator");
            myStore.getEmployees().add(defaultEmployee);

            myStore.addVideoGame(new VideoGame("G01", "Elden Ring", Category.RPG, 59.99, 10));
            myStore.addVideoGame(new VideoGame("G02", "FIFA 26", Category.SPORTS, 69.99, 5));
            
            myStore.addCustomer(new Customer("C01", "Alice Smith", "alice@email.com"));
        }

        // 3. ARRANCAMOS LA INTERFAZ
        MenuConsole interfaceConsole = new MenuConsole(myStore);
        interfaceConsole.start();

        // 4. AL SALIR (Opción 9)
        // Cuando el bucle de la consola termina, guardamos el estado actual antes de apagar la RAM.
        System.out.println("Saving progress before closing...");
        DataHandler.saveStore(myStore);
        System.out.println("System closed safely.");
    }
}