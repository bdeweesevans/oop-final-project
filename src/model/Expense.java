package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;

    private float price;
    private ExpenseType type;
    private String name;
    private String description;
    private String storeName;
    private LocalDate date;

    public Expense(float price, ExpenseType type, String name,
                   String description, String storeName, LocalDate date) {
        this.price = price;
        this.type = type;
        this.name = name;
        this.description = description;
        this.storeName = storeName;
        this.date = date;
    }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public ExpenseType getType() { return type; }
    public void setType(ExpenseType type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
