package domain;

import java.io.Serializable;
import java.util.Date;

public class Sale implements Serializable {
    private static final long serialVersionUID = 1L;

    private String saleId;
    private VideoGame videoGame;
    private Customer customer;
    private Employee employee; 
    private int quantity;
    private double totalAmount;
    private Date date;

    public Sale(String saleId, VideoGame videoGame, Customer customer, Employee employee, int quantity) {
        this.saleId = saleId;
        this.videoGame = videoGame;
        this.customer = customer;
        this.employee = employee;
        this.quantity = quantity;
        this.totalAmount = videoGame.getPrice() * quantity;
        this.date = new Date(); 
    }

    public String getSaleId() { return saleId; }
    public VideoGame getVideoGame() { return videoGame; }
    public Customer getCustomer() { return customer; }
    public Employee getEmployee() { return employee; }
    public int getQuantity() { return quantity; }
    public double getTotalAmount() { return totalAmount; }
    public Date getDate() { return date; }
    
}
