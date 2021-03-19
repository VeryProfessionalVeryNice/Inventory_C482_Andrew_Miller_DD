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
 * Controller class ViewModifyPart.java
 * This class establishes functionality of the Modify Part screen.
 */
public class ViewModifyPart implements Initializable {
    public AnchorPane sceneModifyPart;
    public Label lblModifyParkMachineID;
    public RadioButton radBtnModifyPartInHouse;
    public RadioButton radBtnModifyPartOutsourced;
    public TextField txtModifyPartID;
    public TextField txtModifyPartName;
    public TextField txtModifyPartInventory;
    public TextField txtModifyPartPrice;
    public TextField txtModifyPartMax;
    public TextField txtModifyPartMachineID;
    public TextField txtModifyPartMin;
    public Button btnModifyPartSave;
    public Button btnModifyPartCancel;
    public Label lblModifyPartCompanyName;
    public TextField txtModifyPartCompany;
    private Part selectedPart;
    Alert intAlert = new Alert(Alert.AlertType.ERROR);
    Alert stringAlert = new Alert(Alert.AlertType.ERROR);
    Alert doubleAlert = new Alert(Alert.AlertType.ERROR);
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);


    /**
     * @param url The url parameter.
     * @param resourceBundle
     * This ModifyPart initialize method populates the fields with the appropriate data from the part being modified.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = ViewInventory.getSelectedPart();
    txtModifyPartID.setText(String.valueOf(selectedPart.getId()));
    txtModifyPartName.setText(selectedPart.getName());
    txtModifyPartInventory.setText(String.valueOf(selectedPart.getStock()));
    txtModifyPartMin.setText(String.valueOf(selectedPart.getMin()));
    txtModifyPartMax.setText(String.valueOf(selectedPart.getMax()));
    txtModifyPartPrice.setText(String.valueOf(selectedPart.getPrice()));
    if (selectedPart instanceof InHouse){
        radBtnModifyPartInHouse.setSelected(true);
        radBtnModifyPartOutsourced.setSelected(false);
        this.lblModifyPartCompanyName.setVisible(false);
        this.txtModifyPartCompany.setVisible(false);
        this.lblModifyParkMachineID.setVisible(true);
        this.txtModifyPartMachineID.setVisible(true);
        txtModifyPartMachineID.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));

    }
    if (selectedPart instanceof Outsourced){
        radBtnModifyPartOutsourced.setSelected(true);
        radBtnModifyPartInHouse.setSelected(false);
        this.lblModifyParkMachineID.setVisible(false);
        this.txtModifyPartMachineID.setVisible(false);
        this.lblModifyPartCompanyName.setVisible(true);
        this.txtModifyPartCompany.setVisible(true);
        txtModifyPartCompany.setText(((Outsourced) selectedPart).getCompanyName());
    }


    }

    /**
     * @param mouseEvent The user clicks on the save button.
     * @throws IOException
     * This method validates user input then saves the users modifications to a product.
     */
    public void btnModifyPartSavePress(MouseEvent mouseEvent) throws IOException {
        Integer partID = selectedPart.getId();
        String name;
        Integer stock;
        Integer min;
        Integer max;
        Integer machineID;
        Double price;
        if (isPriceDouble(String.valueOf(txtModifyPartPrice.getText()))==true){
            price = Double.parseDouble(txtModifyPartPrice.getText());
            if (isFieldNotEmpty(String.valueOf(txtModifyPartName.getText())) == true){
            name = txtModifyPartName.getText();
                if(isIntCheck(String.valueOf(this.txtModifyPartInventory.getText()))==true) {
                stock = Integer.parseInt(String.valueOf(this.txtModifyPartInventory.getText()));
                    if(isIntCheck(String.valueOf(this.txtModifyPartMin.getText()))==true){
                    min = Integer.parseInt(String.valueOf(this.txtModifyPartMin.getText()));
                     if(isIntCheck(String.valueOf(this.txtModifyPartMax.getText()))==true){
                        max = Integer.parseInt(String.valueOf(this.txtModifyPartMax.getText()));
                         if(areMinMaxInvValid(min,max,stock) == true){
                            if (radBtnModifyPartInHouse.isSelected()) {
                                if(isIntCheck(String.valueOf(this.txtModifyPartMachineID.getText()))==true){
                                    machineID = Integer.parseInt(String.valueOf(this.txtModifyPartMachineID.getText()));
                                    Part newPart = new InHouse(partID, name, price, stock, min, max, machineID);
                                    Inventory.addPart(newPart);
                                    Inventory.deletePart(selectedPart);
                                    exitToInventory(mouseEvent);
                                }
                            }
                            if (radBtnModifyPartOutsourced.isSelected()) {
                                if (isFieldNotEmpty(txtModifyPartCompany.getText())==true){
                                    String companyName = this.txtModifyPartCompany.getText();
                                    Part newPart = new Outsourced(partID, name, price, stock, min, max, companyName);
                                    Inventory.addPart(newPart);
                                    Inventory.deletePart(selectedPart);
                                    exitToInventory(mouseEvent);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    }


    /**
     * @param mouseEvent The user clicks on the cancel button.
     * @throws IOException
     * This method discards modifications to a product and returns to main inventory screen.
     */
    public void btnModifyPartCancelPress(MouseEvent mouseEvent) throws IOException {
        confirmAlert.setTitle("Exit Modify Part Screen?");
        confirmAlert.setContentText("Are you sure you would like leave without modifying " + selectedPart.getName() + "?");
        confirmAlert.setHeaderText("Returning to main inventory screen");
        Optional<ButtonType> cancelResponse = confirmAlert.showAndWait();
        if( cancelResponse.get() == ButtonType.OK){
            exitToInventory(mouseEvent);}
        }

    /**
     * @param mouseEvent The user clicks on the exit button.
     * @throws IOException
     * This method returns the user to the main inventory screen.
     */
    public void exitToInventory(MouseEvent mouseEvent) throws IOException {
        Parent addPartParent = (Parent) FXMLLoader.load(this.getClass().getResource("/View/ViewInventory.fxml"));
        Scene nextScene = new Scene(addPartParent);
        Stage thisStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        thisStage.setTitle("Inventory");
        thisStage.setScene(nextScene);

    }

    /**
     * @param mouseEvent The user clicks on the InHouse radio button.
     * This method disables or enables the appropriate controls based on InHouse radio button selection.
     */
    public void radBtnInHouseClick(MouseEvent mouseEvent) {
        this.radBtnModifyPartOutsourced.setSelected(false);
        this.txtModifyPartCompany.setText((String)null);
        this.txtModifyPartCompany.setDisable(true);
        this.txtModifyPartMachineID.setDisable(false);
        this.lblModifyPartCompanyName.setVisible(false);
        this.txtModifyPartCompany.setVisible(false);
        this.lblModifyParkMachineID.setVisible(true);
        this.txtModifyPartMachineID.setVisible(true);
    }
    /**
     * @param mouseEvent The user clicks on the Outsourced radio button.
     * This method disables or enables the appropriate controls based on Outsourced radio button selection.
     */
    public void rabBtnOutsourcedClick(MouseEvent mouseEvent) {
        this.radBtnModifyPartInHouse.setSelected(false);
        this.txtModifyPartMachineID.setText((String)null);
        this.txtModifyPartMachineID.setDisable(true);
        this.txtModifyPartCompany.setDisable(false);
        this.lblModifyParkMachineID.setVisible(false);
        this.txtModifyPartMachineID.setVisible(false);
        this.lblModifyPartCompanyName.setVisible(true);
        this.txtModifyPartCompany.setVisible(true);
    }

    /**
     * @param userInputString User input form text box.
     * @return bool
     * This method verifies that the user inputted string is an integer.
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
     * @param userInputString User input from text box.
     * @return bool
     * This method verifies that the user input a string into a text box.
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

    /**
     * @param userInputString User input from Inv text box.
     * @return bool
     * This method verifies that the user input a double into a textbox.
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
}
