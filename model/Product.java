
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    
    private static ObservableList<Part> associatedParts
            = FXCollections.observableArrayList();
    
    private int id, stock, min, max;
    private String name;
    private double price;
    
    public Product(int id, String name, 
                   double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    public static void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    public static void addAssociatedPartList(ObservableList<Part> partList){
        associatedParts = partList;
    }
    
    public static void deleteAssociatedPart(Part part){
        associatedParts.remove(part);
    }
    
    public static ObservableList<Part> getAllAssociateParts(){
        return associatedParts;
    }
    
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    public void setMin(int min){
        this.min = min;
    }
    public void setMax(int max){
        this.max = max;
    }
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public int getStock(){
        return stock;
    }
    public int getMin(){
        return min;
    }
    public int getMax(){
        return max;
    }
}
