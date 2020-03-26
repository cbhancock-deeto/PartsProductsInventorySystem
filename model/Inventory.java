
package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
    private static ObservableList<Product> allProducts 
                    = FXCollections.observableArrayList();
    
    private static ObservableList<Product> filteredProducts 
                    = FXCollections.observableArrayList();
    
    private static int partId = 1;
    private static int productId = 1;
    
    
    
    public static int partIdGenerator(){
        return partId;
    }
    
    public static int productIdGenerator(){
        return productId;
    }
    
    public static void partIdIncrement(){
        partId++;
    }
    
    public static void productIdIncrement(){
        productId++;
    }
    
    public static void addProduct(Product product){
        allProducts.add(product);
        productIdIncrement();
    }
    
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    
    public static ObservableList<Product> getAllFilteredProducts(){
        return filteredProducts;
    }

    private static ObservableList<Part> allParts 
                    = FXCollections.observableArrayList();
    
    private static ObservableList<Part> filteredParts 
                    = FXCollections.observableArrayList();
    
    public static void addPart(Part part){
        allParts.add(part);
        partIdIncrement();
    }
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    public static ObservableList<Part> getAllFilteredParts(){
        return filteredParts;
    }
    
    public static void deletePart(Part part){
        allParts.remove(part);
    }
    
    public static void deleteProduct(Product product){
        allProducts.remove(product);
    }
           
    
    public static void replacePart(Part part, int index){
        allParts.set(index, part);
    }
    
    public static void replaceProduct(Product product, int index){
        allProducts.set(index,product);
    }
    
    
}
