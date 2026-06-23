package domain;

import java.io.Serializable;

public class VideoGame implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String title;
    private Category category;
    private double price;
    private int stock;

    public VideoGame(String code, String title, Category category, double price, int stock) {
        this.code = code;
        this.title = title;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public void reduceStock(int quantity){
        if (this.stock >= quantity) {
            this.stock -= quantity;
        }
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return title + " [" + category + "] - $" + price + " (Stock: " + stock + ")";
    }
}

    
