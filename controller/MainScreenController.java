/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.OutSourced;
import model.Part;
import model.Product;

/**
 *
 * @author cblai
 */
public class MainScreenController implements Initializable {
    
    
    @FXML
    private TextField partsSearchTxt;

    @FXML
    private TableView<Part> IMSPartsTableView;

    @FXML
    private TableColumn<Part, ?> partIdCol;

    @FXML
    private TableColumn<Part, ?> partNameCol;

    @FXML
    private TableColumn<Part, ?> invPartLevelCol;

    @FXML
    private TableColumn<Part, ?> partPricePerUnitCol;

    @FXML
    private TextField productsSearchTxt;

    @FXML
    private TableView<Product> IMSProductsTableView;

    @FXML
    private TableColumn<Product, ?> productIdCol;

    @FXML
    private TableColumn<Product, ?> productNameCol;

    @FXML
    private TableColumn<Product, ?> invProductLevelCol;

    @FXML
    private TableColumn<Product, ?> productPricePerUnitCol;

        
    Stage stage;
    Parent scene;
    
    @FXML
    void deletePart(ActionEvent event) throws IOException { 
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete"
                + " selected part?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
        
            removePart(IMSPartsTableView.getSelectionModel().getSelectedItem().getId());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show(); 
        }
    }
    
    public boolean removePart (int id){
        for (Part part : Inventory.getAllParts()){
            if (part.getId() == id){
                return Inventory.getAllParts().remove(part);
            }
        }
        return false;
    }

    @FXML
    void deleteProduct(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete"
                + " selected product?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            Inventory.deleteProduct(IMSProductsTableView.getSelectionModel().getSelectedItem());


            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show(); 
        }
    }

    @FXML
    void exitApplication(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionSearchParts(ActionEvent event) {
  
        filterParts(partsSearchTxt.getText());
        
        IMSPartsTableView.setItems(Inventory.getAllFilteredParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invPartLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        IMSPartsTableView.getSelectionModel().select(selectPart(1));
      
    }
    
    public ObservableList<Part> filterParts(String partName){
        
        if (!(Inventory.getAllFilteredParts().isEmpty()))
        {
            Inventory.getAllFilteredParts().clear();
        }
        
        for (Part part : Inventory.getAllParts()){
            if (part.getName().contains(partName)){
                Inventory.getAllFilteredParts().add(part);
            }
        }
        
        if (Inventory.getAllParts().isEmpty())
            return Inventory.getAllParts();
        else
            return Inventory.getAllFilteredParts();
    }
    
    
    public ObservableList<Product> filterProducts(String productName){
        
        if (!(Inventory.getAllFilteredProducts().isEmpty()))
        {
            Inventory.getAllFilteredProducts().clear();
        }
        
        for (Product product : Inventory.getAllProducts()){
            if (product.getName().contains(productName)){
                Inventory.getAllFilteredProducts().add(product);
            }
        }
        
        if (Inventory.getAllProducts().isEmpty())
            return Inventory.getAllProducts();
        else
            return Inventory.getAllFilteredProducts();
    }
    

    @FXML
    void onActionSearchProducts(ActionEvent event) {
        
        filterProducts(productsSearchTxt.getText());
        
        IMSProductsTableView.setItems(Inventory.getAllFilteredProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invProductLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        IMSProductsTableView.getSelectionModel().select(selectProduct(1));
    }

    @FXML
    void openAddPartMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    @FXML
    void openAddProductMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    @FXML
    void openModifyPartMenu(ActionEvent event) throws IOException {   
        
        /*
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();*/
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModPart.fxml"));
        loader.load();
        
        ModPartController MPController = loader.getController();
        MPController.sendPart(IMSPartsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();      
    }

    @FXML
    void openModifyProductMenu(ActionEvent event) throws IOException {

       /* stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();*/
       
      
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModProduct.fxml"));
        loader.load();
      
        ModProductController MPController = loader.getController();
        MPController.sendProduct(IMSProductsTableView.getSelectionModel().getSelectedItem());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.showAndWait();
        
    }
    
public Part selectPart(int id){
        
        for (Part part : Inventory.getAllParts()){
            
            if (part.getId() == id)
                return part;
        }
        return null;
}

public Product selectProduct(int id){
        
        for (Product product : Inventory.getAllProducts()){
            
            if (product.getId() == id)
                return product;
        }
        return null;
}
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Initializing Parts menu
        IMSPartsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invPartLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        IMSPartsTableView.getSelectionModel().select(selectPart(1));
        
        //Initializing Products menu
        IMSProductsTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invProductLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        IMSProductsTableView.getSelectionModel().select(selectProduct(1));
        
    }     
}
