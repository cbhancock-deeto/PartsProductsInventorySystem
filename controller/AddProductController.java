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
import javafx.collections.FXCollections;
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
import static model.Inventory.partIdGenerator;
import static model.Inventory.productIdGenerator;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class AddProductController implements Initializable {

    
    @FXML
    private TextField productMaxTxt;

    @FXML
    private TextField productMinTxt;

    @FXML
    private Label productIDLbl;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productInvTxt;

    @FXML
    private TextField productCostTxt;

    @FXML
    private TextField productSearchTxt;

    @FXML
    private TableView<Part> productTableView1;

    @FXML
    private TableColumn<Part, ?> partIdCol1;

    @FXML
    private TableColumn<Part, ?> partNameCol1;

    @FXML
    private TableColumn<Part, ?> invLevelCol1;

    @FXML
    private TableColumn<Part, ?> pricePerUnitCol1;

    @FXML
    private TableView<Part> productTableView2;

    @FXML
    private TableColumn<Part, ?> partIdCol2;

    @FXML
    private TableColumn<Part, ?> partNameCol2;

    @FXML
    private TableColumn<Part, ?> invLevelCol2;

    @FXML
    private TableColumn<Part, ?> pricePerUnitCol2;

    Stage stage;
    Parent scene;
    int id;
    
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
    
    @FXML
    void onActionSearchParts(ActionEvent event) {
        
        filterParts(productSearchTxt.getText());
        
        productTableView1.setItems(Inventory.getAllFilteredParts());
        partIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLevelCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTableView1.getSelectionModel().select(selectPart(1));
    }

    
    private static ObservableList<Part> tempAssociatedParts
            = FXCollections.observableArrayList();
    
    @FXML
    void onActionAddPart(ActionEvent event) {
        // Check to see if selected item exists in associated parts
        Boolean exists = false;
        
        for (Part part : tempAssociatedParts)
        {
            if (part.getId() == (productTableView1.getSelectionModel().getSelectedItem()).getId())
            {
                exists = true;
            }
        }
        
        if (!exists){
            tempAssociatedParts.add(productTableView1.getSelectionModel().getSelectedItem());
        }
        else{
            System.out.println("Part being associated is already associated with product.");
        }
        
    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to remove"
                + " association between product and selected part?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            tempAssociatedParts.remove(productTableView2.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void onActionOpenMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancelling will reset all fields.\n"
                        + "Are you sure you would like to return to the main menu?");
        
        Optional<ButtonType> result = alert.showAndWait();
       
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionSaveChanges(ActionEvent event) throws IOException {

        try{

            String name = productNameTxt.getText();
            int min = Integer.parseInt(productMinTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());
            int stock = Integer.parseInt(productInvTxt.getText());
            double price = Double.parseDouble(productCostTxt.getText());

            if (max < min){
                throw new Exception();
            }

            Product newProduct = new Product(id,name,price,stock,min,max);

            newProduct.addAssociatedPartList(tempAssociatedParts);
            Inventory.addProduct(newProduct);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid maximum and minimum values");
            alert.setContentText("Please enter a maximum value that is less than minimum inventory"
                    + " value");
            alert.showAndWait();
        }
    }
    
    
    public Part selectPart(int id){
        for (Part part : Inventory.getAllParts()){
            if (part.getId() == id)
                return part;
        }
        return null;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        id = productIdGenerator();
        productIDLbl.setText(String.valueOf(id));
        
        // Clear temp associated parts list so that bottom parts menu is clear
        tempAssociatedParts.clear();
        
        // Top parts menu
        productTableView1.setItems(Inventory.getAllParts());
        partIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLevelCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTableView1.getSelectionModel().select(selectPart(1));
        
        // Bottom parts menu
        productTableView2.setItems(tempAssociatedParts);
        partIdCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLevelCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTableView2.getSelectionModel().select(selectPart(1));
        
    }    
}
