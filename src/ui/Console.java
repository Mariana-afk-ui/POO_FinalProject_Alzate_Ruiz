package ui;

import java.util.Scanner;

public class Console {
    // Un único Scanner estático para toda la aplicación
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Reemplaza al System.out.println(). 
     * Recibe cualquier objeto (un texto, un número, un videojuego) y lo imprime.
     */
    public static void writeLine(Object message) {
        System.out.println(message);
    }

    /**
     * Muestra un mensaje al usuario (prompt) y se queda esperando 
     * a que escriba una línea de texto en el teclado.
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
