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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import static model.Inventory.partIdGenerator;
import static model.Inventory.partIdIncrement;
import model.OutSourced;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class AddPartController implements Initializable {

    @FXML
    private Label idLbl;
    
    @FXML
    private Label partTypeLbl;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partCostTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;
    
    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private RadioButton outSourcedRBtn;
    
    @FXML
    private TextField partTypeTxt;


    Stage stage;
    Parent scene;
    int id;
    
        @FXML
    void onActionShowInHouse(ActionEvent event) {
        partTypeLbl.setText("Machine Id");
    }

    @FXML
    void onActionShowOutsourced(ActionEvent event) {
        partTypeLbl.setText("Company Name");
    }
    
    @FXML
    void onActionOpenMainMenu(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancelling will erase all fields.\n"
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
    void onActionSavePart(ActionEvent event) throws IOException {

       try
       {
            String name = partNameTxt.getText();
            int inventory = Integer.parseInt(partInvTxt.getText());
            double cost = Double.parseDouble(partCostTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
  
            if (max < min)
            {
                throw new Exception();
            }
            
            if (inHouseRBtn.isSelected()){
                int machineId = Integer.parseInt(partTypeTxt.getText());
                Inventory.addPart(new InHouse(id, name, cost, inventory, min, max,machineId));
            }
            else{
                String company = partTypeTxt.getText();
                Inventory.addPart(new OutSourced(id, name, cost, inventory, min, max,company));
            }
                
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
       catch(Exception e)
       {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid maximum and minimum values");
            alert.setContentText("Please enter a maximum value that is less than the minimum inventory"
                    + " value");
            alert.showAndWait();
       }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id = partIdGenerator();
        idLbl.setText(String.valueOf(id));
    }    
}
