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
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import static model.Inventory.partIdGenerator;
import model.OutSourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class ModPartController implements Initializable {

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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    public void sendPart(Part part){
        id = part.getId();
        idLbl.setText(String.valueOf(id));
        partNameTxt.setText(part.getName());
        partInvTxt.setText(String.valueOf(part.getStock()));
        partCostTxt.setText(String.valueOf(part.getPrice()));
        partMaxTxt.setText(String.valueOf(part.getMax()));
        partMinTxt.setText(String.valueOf(part.getMin()));
        

        if (part instanceof OutSourced)
            partTypeTxt.setText(((OutSourced) part).getCompanyName());
        else
            partTypeTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
            
    }
    

    @FXML
    private void onActionShowInHouse(ActionEvent event) {
        partTypeLbl.setText("Machine Id");
    }

    @FXML
    private void onActionShowOutsourced(ActionEvent event) {
        partTypeLbl.setText("Company Name");
    }

    @FXML
    private void onActionSavePart(ActionEvent event) throws IOException {
        try{
            
        String name = partNameTxt.getText();
        int inventory = Integer.parseInt(partInvTxt.getText());
        double cost = Double.parseDouble(partCostTxt.getText());
        int max = Integer.parseInt(partMaxTxt.getText());
        int min = Integer.parseInt(partMinTxt.getText());
        
        if (max < min)
        {
            throw new Exception("Max inventory must be less than minimum inventory");
        }

            //Check if part exists & what part type it is (inhouse/outsourced)
        Boolean exists = false;
        Boolean inHouse = true;

        for (Part part : Inventory.getAllParts()){
            if (part.getId() == id){
                exists = true;
                if (part instanceof OutSourced){
                    inHouse = false;
                }
                break;
            }
        }

        // If part type (inhouse or outsourced) is being changed to
        // opposite, create new placeholder part then replace part
        if (inHouseRBtn.isSelected())
        {
            int machineId = Integer.parseInt(partTypeTxt.getText());
            InHouse modPart = new InHouse(id,name,cost,inventory,max,min,machineId);
            Inventory.replacePart(modPart,id-1);
         }
        else if(!inHouseRBtn.isSelected())
        {
            String companyName = partTypeTxt.getText();
            OutSourced modPart = new OutSourced(id,name,cost,inventory,max,min,companyName);
            Inventory.replacePart(modPart,id-1);
        }


        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Invalid maximum and minimum values");
            alert.setContentText("Please enter a maximum value that is less than minimum inventory"
                    + " value");
            alert.showAndWait();
        }
        
    }

    @FXML
    private void onActionOpenMainMenu(ActionEvent event) throws IOException {
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
    
}
