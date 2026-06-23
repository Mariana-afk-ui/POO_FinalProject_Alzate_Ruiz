package domain;

import java.util.ArrayList;

public class RecommendationEngine {

    /**
     * Método principal de IA: Recomienda juegos a un cliente basado en su categoría más comprada.
     * * @param customer El cliente para el que queremos las recomendaciones.
     * @param store    La tienda que contiene el inventario y el historial de ventas.
     * @return Una lista de videojuegos recomendados.
     */
    public static ArrayList<VideoGame> recommendGames(Customer customer, Store store) {
        ArrayList<VideoGame> recommendations = new ArrayList<>();
        
        // 1. Obtener el historial de videojuegos que ya compró este cliente
        ArrayList<VideoGame> purchasedGames = new ArrayList<>();
        for (Sale sale : store.getSales()) {
            if (sale.getCustomer().getId().equals(customer.getId())) {
                purchasedGames.add(sale.getVideoGame());
            }
        }

        // Si el cliente nunca ha comprado nada, no hay datos para la IA. 
        // Devolvemos una lista vacía (o podríamos devolver los más vendidos).
        if (purchasedGames.isEmpty()) {
            return recommendations; 
        }

        // 2. CONTAR CATEGORÍAS: Averiguar cuál es la categoría favorita del cliente
        // Creamos un arreglo para contar los votos de cada categoría del Enum
        Category[] categories = Category.values();
        int[] categoryCounts = new int[categories.length];

        for (VideoGame game : purchasedGames) {
            Category cat = game.getCategory();
            // Buscamos la posición de la categoría en el enum para sumarle 1
            categoryCounts[cat.ordinal()]++;
        }

        // Encontrar cuál posición tiene el conteo más alto
        int maxIndex = 0;
        for (int i = 1; i < categoryCounts.length; i++) {
            if (categoryCounts[i] > categoryCounts[maxIndex]) {
                maxIndex = i;
            }
        }
        
        Category favoriteCategory = categories[maxIndex];

        // 3. FILTRAR RECOMENDACIONES: Buscar juegos de esa categoría que no haya comprado
        for (VideoGame game : store.getGames()) {
            // Regla A: Debe ser de su categoría favorita
            // Regla B: Debe tener stock disponible
            if (game.getCategory() == favoriteCategory && game.getStock() > 0) {
                
                // Regla C: No debe haberlo comprado antes
                boolean alreadyPurchased = false;
                for (VideoGame purchased : purchasedGames) {
                    if (purchased.getCode().equalsIgnoreCase(game.getCode())) {
                        alreadyPurchased = true;
                        break;
                    }
                }

                if (!alreadyPurchased) {
                    recommendations.add(game);
                }
            }
        }

        return recommendations;
    }
}   
