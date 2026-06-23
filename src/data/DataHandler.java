package data;

import domain.Store;
import java.io.*;

public class DataHandler {
    // Nombre del archivo binario donde se guardará todo
    private static final String FILE_PATH = "store_data.dat";

    /**
     * Guarda el objeto Store completo (con todas sus listas) en el disco duro.
     */
    public static void saveStore(Store store) {
        // ObjectOutputStream convierte el objeto y sus listas en un flujo de bytes
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(store);
            System.out.println("[SYSTEM INFO] Data successfully saved to disk.");
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to save data: " + e.getMessage());
        }
    }

    /**
     * Lee el archivo desde el disco duro y reconstruye el objeto Store original.
     */
    public static Store loadStore() {
        File file = new File(FILE_PATH);
        
        // Si el archivo no existe (como la primera vez que abras el programa),
        // devolvemos una tienda vacía para que no tire error.
        if (!file.exists()) {
            System.out.println("[SYSTEM INFO] No previous data found. Creating new database.");
            return new Store();
        }

        // ObjectInputStream lee los bytes y vuelve a armar el objeto Store en la RAM
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Store loadedStore = (Store) ois.readObject();
            System.out.println("[SYSTEM INFO] Data successfully loaded from last session.");
            return loadedStore;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[ERROR] Failed to load data. Creating empty store: " + e.getMessage());
            return new Store();
        }
    }
}