package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Controller class ViewAddPart.java
 * This class establishes functionality of the Add Part screen.
 */
public class ViewAddPart implements Initializable {
    public AnchorPane sceneAddPart;
    public Label lblAddPartMachineID;
    public RadioButton radBtnAddPartInHouse;
    public RadioButton radBtnAddPartOutsourced;
    public TextField txtPartID;
    public TextField txtAddPartName;
    public TextField txtAddPartInventory;
    public TextField txtAddPartPrice;
    public TextField txtAddPartMax;
    public TextField txtAddPartMachineID;
    public TextField txtAddPartMin;
    public Button btnAddPartSave;
    public Button btnAddPartCancel;
    public Label lblAddPartCompanyName;
    public TextField txtAddPartCompany;
    Alert intAlert = new Alert(Alert.AlertType.ERROR);
    Alert doubleAlert = new Alert(Alert.AlertType.ERROR);
    Alert stringAlert = new Alert(Alert.AlertType.ERROR);
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);

    /**
     * @param url The url parameter.
     * @param resourceBundle
     * This method initializes the AddPart view and generates a partID.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int partID = Inventory.currentPartID();
        this.txtPartID.setText(String.valueOf(partID));
    }

    /**
     * @param mouseEvent User clicks on InHouse radio button.
     * This method disables or enables the appropriate controls based on InHouse radio button selection.
     */
    public void radBtnInHouseSelect(MouseEvent mouseEvent) {
        this.radBtnAddPartOutsourced.setSelected(false);
        this.txtAddPartCompany.setText((String)null);
        this.txtAddPartCompany.setDisable(true);
        this.txtAddPartMachineID.setDisable(false);
        this.lblAddPartCompanyName.setVisible(false);
        this.txtAddPartCompany.setVisible(false);
        this.lblAddPartMachineID.setVisible(true);
        this.txtAddPartMachineID.setVisible(true);
    }

    /**
     * @param mouseEvent User clicks on Outsourced radio button.
     * This method disables or enables the appropriate controls based on Outsourced radio button selection.
     */
    public void radBtnOutsourcedSelect(MouseEvent mouseEvent) {
        this.radBtnAddPartInHouse.setSelected(false);
        this.txtAddPartMachineID.setText((String)null);
        this.txtAddPartMachineID.setDisable(true);
        this.txtAddPartCompany.setDisable(false);
        this.lblAddPartMachineID.setVisible(false);
        this.txtAddPartMachineID.setVisible(false);
        this.lblAddPartCompanyName.setVisible(true);
        this.txtAddPartCompany.setVisible(true);
    }

    /**
     * @param mouseEvent User clicks on save button.
     * @throws IOException
     * This method validates user input then creates a new part.
     */
    public void btnAddPartSavePress(MouseEvent mouseEvent) throws IOException {
        Integer partID = Integer.parseInt(String.valueOf(this.txtPartID.getText()));
        String name;
        Integer stock;
        Integer min;
        Integer max;
        Integer machineID;
        Double price;
            if (isPriceDouble(String.valueOf(txtAddPartPrice.getText()))==true){
                price = Double.parseDouble(txtAddPartPrice.getText());
            if (isFieldNotEmpty(String.valueOf(txtAddPartName.getText())) == true){
                name = txtAddPartName.getText();
            if(isIntCheck(String.valueOf(this.txtAddPartInventory.getText()))==true) {
                stock = Integer.parseInt(String.valueOf(this.txtAddPartInventory.getText()));
                if(isIntCheck(String.valueOf(this.txtAddPartMin.getText()))==true){
                    min = Integer.parseInt(String.valueOf(this.txtAddPartMin.getText()));
                    if(isIntCheck(String.valueOf(this.txtAddPartMax.getText()))==true){
                        max = Integer.parseInt(String.valueOf(this.txtAddPartMax.getText()));
                        if(areMinMaxInvValid(min,max,stock) == true){
                        if (radBtnAddPartInHouse.isSelected()) {
                            if(isIntCheck(String.valueOf(this.txtAddPartMachineID.getText()))==true){
                            machineID = Integer.parseInt(String.valueOf(this.txtAddPartMachineID.getText()));
                            Part newPart = new InHouse(partID, name, price, stock, min, max, machineID);
                            Inventory.addPart(newPart);
                         exitToInventory(mouseEvent);}
                        }
                        if (radBtnAddPartOutsourced.isSelected()) {
                                 if (isFieldNotEmpty(txtAddPartCompany.getText())==true){
                                String companyName = this.txtAddPartCompany.getText();
                                Part newPart = new Outsourced(partID, name, price, stock, min, max, companyName);
                                Inventory.addPart(newPart);
                                exitToInventory(mouseEvent);}
                        } }
                    }
                }
            }
        }
    }}


    /**
     * @param mouseEvent User clicks on cancel button.
     * @throws IOException
     * This method cancels part creation and returns user to main inventory screen.
     */
    public void btnAddPartCancelPress(MouseEvent mouseEvent) throws IOException {
        confirmAlert.setTitle("Exit Add Part Screen?");
        confirmAlert.setContentText("Are you sure you would like leave without adding a part?");
        confirmAlert.setHeaderText("Returning to main inventory screen");
        Optional<ButtonType> cancelResponse = confirmAlert.showAndWait();
        if( cancelResponse.get() == ButtonType.OK){
            exitToInventory(mouseEvent);}

    }

    /**
     * @param mouseEvent User clicks on exit button.
     * @throws IOException
     * This method returns the application to the main inventory screen.
     */
    public void exitToInventory(MouseEvent mouseEvent) throws IOException {
        Parent addPartParent = (Parent) FXMLLoader.load(this.getClass().getResource("/View/ViewInventory.fxml"));
        Scene nextScene = new Scene(addPartParent);
        Stage thisStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        thisStage.setTitle("Inventory");
        thisStage.setScene(nextScene);

    }

    /**
     * @param userInputString String from a text box.
     * @return bool
     * This method verifies that user input is a valiud integer.
     */
    public boolean isIntCheck(String userInputString){
        Boolean bool = false;
        if ((userInputString == null || userInputString.length()==0)){
            intAlert.setTitle("Error");
            intAlert.setHeaderText("Invalid Input");
            intAlert.setContentText("Form contains no input where an integer is required.");
            intAlert.showAndWait();}
        else
            try {
                int userInputInt = Integer.parseInt(userInputString);
                if (userInputInt >= 0){
                    bool = true;
                }
            } catch (NumberFormatException e) {
                intAlert.setTitle("Error");
                intAlert.setHeaderText("Invalid Input");
                intAlert.setContentText("Form contains non-integer input where an integer is required.");
                intAlert.showAndWait();
            }

        return bool;
    }

    /**
     * @param userInputString String from a text box.
     * @return bool
     * This method verifies that user input is a valid double.
     */
    public boolean isPriceDouble(String userInputString){
        Boolean bool = false;
        if ((userInputString == null || userInputString.length()==0)){

            doubleAlert.setTitle("Error");
            doubleAlert.setHeaderText("Invalid Input");
            doubleAlert.setContentText("Form contains no input where a price should be specified");
            doubleAlert.showAndWait();}

        else {
            try {
                double userInputDouble = Double.parseDouble(userInputString);
                if (userInputDouble == 0) {
                    doubleAlert.setTitle("Error");
                    doubleAlert.setHeaderText("Invalid Input");
                    doubleAlert.setContentText("Price must be greater than 0.");
                    doubleAlert.showAndWait();
                }
                if (userInputDouble > 0) {
                    bool = true;
                }
            } catch (NumberFormatException e) {
                doubleAlert.setTitle("Error");
                doubleAlert.setHeaderText("Invalid Input");
                doubleAlert.setContentText("Form contains non-numerical input in price field.");
                doubleAlert.showAndWait();
            }
        }return bool;
    }

    /**
     * @param userInputString String from a text box.
     * @return bool
     * This method verifies that the user provided string input into a text box.
     */
    public boolean isFieldNotEmpty(String userInputString){
        Boolean bool = false;
        if ((userInputString == null || userInputString.length()==0)){
            stringAlert.setTitle("Error");
            stringAlert.setHeaderText("Invalid Input");
            stringAlert.setContentText("Form contains no input where text is required.");
            stringAlert.showAndWait();}
        else {bool = true;}
        return bool;
    }

    /**
     * @param minInput Min converted to int from text box string.
     * @param maxInput Max converted to int from text box string.
     * @param invInput Inv converted to int from text box string.
     * @return bool
     * This method verifies that min, max, and inv are integers and that they follow the appropriate logic.
     */
    public boolean areMinMaxInvValid(int minInput, int maxInput, int invInput){
        boolean minMaxValid = false;
        boolean invInRange = false;
        boolean overallValid = false;
        if (minInput < maxInput){
            minMaxValid = true;
        }
        else{
            stringAlert.setTitle("Error");
            stringAlert.setHeaderText("Invalid Min/Max");
            stringAlert.setContentText("Min must be lower than Max");
            stringAlert.showAndWait();}

        if (invInput >= minInput && invInput <= maxInput){
            invInRange = true;
        }
        else{
            stringAlert.setTitle("Error");
            stringAlert.setHeaderText("Invalid Inventory Level");
            stringAlert.setContentText("Inventory level must be within range of min and max.");
            stringAlert.showAndWait();}

        if (invInRange == true && minMaxValid == true){
            overallValid = true;
        }
        return overallValid;
    }



}
