/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareifinalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inventory;
import static model.Inventory.partIdGenerator;
import static model.Inventory.productIdGenerator;
import model.OutSourced;
import model.Product;

/**
 *
 * @author cblai
 */
public class SoftwareIFinalProject extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        OutSourced part1 = new OutSourced(partIdGenerator(),"Wrench",4.25,12,5,25,"Home Depot");
        Inventory.addPart(part1);
        
        OutSourced part2 = new OutSourced(partIdGenerator(),"Screwdriver",3.75,23,2,30, "Home Depot");
        Inventory.addPart(part2);

        OutSourced part3 = new OutSourced(partIdGenerator(),"Flamethrower Battery",30.20,17,14,70, "Home Depot");
        Inventory.addPart(part3);
        
        OutSourced part4 = new OutSourced(partIdGenerator(),"Wrenches",4.25,12,5,25,"Home Depot");
        Inventory.addPart(part4);
        
        OutSourced part5 = new OutSourced(partIdGenerator(),"Tool",4.25,12,5,25,"Home Depot");
        Inventory.addPart(part5);
        
        Product product1 = new Product(productIdGenerator(),"Flamethrower",425.40,5,2,23);
        Inventory.addProduct(product1);
        
        Product product2 = new Product(productIdGenerator(),"Swimming Pool",1025,17,10,30);
        Inventory.addProduct(product2);
        
        Product product3 = new Product(productIdGenerator(),"Swimming Pools",10244,13,10,30);
        Inventory.addProduct(product3);
        
        Product product4 = new Product(productIdGenerator(),"Carpentry Tool",345.23,17,15,40);
        Inventory.addProduct(product4);
        
        launch(args);
    }
    
}
