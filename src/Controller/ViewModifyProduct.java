package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Controller class ViewModifyProduct.java
 * This class establishes functionality of the Modify Product screen.
 */
public class ViewModifyProduct implements Initializable {
    public TextField txtModifyProductSearch;
    public TableView tblModifyPartAssociated;
    public TableColumn colModifyProductAssocID;
    public TableColumn colModifyProductAssocName;
    public TableColumn colModifyProductAssocInv;
    public TableColumn colModifyProductAssocCost;
    public TableView tblModifyPartAll;
    public TableColumn colModifyProductAllID;
    public TableColumn colModifyProductAllName;
    public TableColumn colModifyProductAllInv;
    public TableColumn colModifyProductAllCost;
    public Button btnModifyProductRemove;
    public Button btnModifyProductCancel;
    public Button btnModifyProductSave;
    public TextField txtModifyProductID;
    public TextField txtModifyProductName;
    public TextField txtModifyProductInv;
    public TextField txtModifyProductPrice;
    public TextField txtModifyProductMax;
    public TextField txtModifyProductMin;
    public Button btnModifyProductAdd;
    public AnchorPane sceneModifyProduct;
    Part selectedPart;
    Product selectedProduct;
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert intAlert = new Alert(Alert.AlertType.ERROR);
    Alert doubleAlert = new Alert(Alert.AlertType.ERROR);
    Alert stringAlert = new Alert(Alert.AlertType.ERROR);
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    private ObservableList<Part> tempAssociatedPartsList = FXCollections.observableArrayList();


    /**
     * @param url The url parameter.
     * @param resourceBundle
     * This is the ModifyProduct initialize method which populates the tables and text boxes.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = ViewInventory.getSelectedProduct();
        associatedPartsList = selectedProduct.getAllParts();
        tempAssociatedPartsList = selectedProduct.getAllParts();

        colModifyProductAssocID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colModifyProductAssocName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colModifyProductAssocInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colModifyProductAssocCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        colModifyProductAllID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colModifyProductAllName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colModifyProductAllInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colModifyProductAllCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblModifyPartAll.setItems(Inventory.getAllParts());
        tblModifyPartAssociated.setItems(tempAssociatedPartsList);


        txtModifyProductID.setText(String.valueOf(selectedProduct.getId()));
        txtModifyProductName.setText(String.valueOf(selectedProduct.getName()));
        txtModifyProductInv.setText(String.valueOf(selectedProduct.getStock()));
        txtModifyProductMin.setText(String.valueOf(selectedProduct.getMin()));
        txtModifyProductMax.setText(String.valueOf(selectedProduct.getMax()));
        txtModifyProductPrice.setText(String.valueOf(selectedProduct.getPrice()));
        tblModifyPartAssociated.refresh();
    }

    /**
     * @param mouseEvent The user clicks on the remove part button.
     * This method removes a part from the product.
     */
    public void btnModifyPartRemovePress(MouseEvent mouseEvent) {
        Part selectedPart = (Part) tblModifyPartAssociated.getSelectionModel().getSelectedItem();
        confirmAlert.setTitle("Confirm Part Removal?");
        confirmAlert.setContentText("Are you sure you would like to remove " + selectedPart.getName() + " from " + selectedProduct.getName() + "?");
        confirmAlert.setHeaderText("Pending Removal");
        Optional<ButtonType> deleteResponse = confirmAlert.showAndWait();
        if (deleteResponse.get() == ButtonType.OK) {
            tempAssociatedPartsList.remove(selectedPart);

            tblModifyPartAssociated.setItems(tempAssociatedPartsList);
        }
    }


    /**
     * @param mouseEvent The user clicks on the save button.
     * @throws IOException
     * This method saves the changes made to a product.
     */
    public void btnModifyProductSavePress(MouseEvent mouseEvent) throws IOException {
        Integer productID = Integer.parseInt(txtModifyProductID.getText());
        associatedPartsList = tblModifyPartAssociated.getItems();
        String productName;
        Double productPrice;
        Integer productStock;
        Integer productMin;
        Integer productMax;
        if (isPriceDouble(String.valueOf(txtModifyProductPrice.getText())) == true) {
            productPrice = Double.parseDouble(txtModifyProductPrice.getText());
            if (isFieldNotEmpty(String.valueOf(txtModifyProductName.getText())) == true) {
                productName = txtModifyProductName.getText();
                if (isIntCheck(String.valueOf(this.txtModifyProductInv.getText())) == true) {
                    productStock = Integer.parseInt(String.valueOf(this.txtModifyProductInv.getText()));
                    if (isIntCheck(String.valueOf(this.txtModifyProductMin.getText())) == true) {
                        productMin = Integer.parseInt(String.valueOf(this.txtModifyProductMin.getText()));
                        if (isIntCheck(String.valueOf(this.txtModifyProductMax.getText())) == true) {
                            productMax = Integer.parseInt(String.valueOf(this.txtModifyProductMax.getText()));
                            if (areMinMaxInvValid(productMin, productMax, productStock) == true) {
                                Product addedProduct = new Product(associatedPartsList, productID, productName, productPrice, productStock, productMin, productMax);
                                Inventory.addProduct(addedProduct);
                                Inventory.deleteProduct(selectedProduct);
                                exitToInventory(mouseEvent);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param mouseEvent The user clicks on the add part button.
     * This method adds a part to a product.
     */
    public void btnModifyPartAddPress(MouseEvent mouseEvent) {
        Part selectedPart = (Part) tblModifyPartAll.getSelectionModel().getSelectedItem();
        tempAssociatedPartsList.add(selectedPart);
        tblModifyPartAssociated.setItems(tempAssociatedPartsList);
    }

    /**
     * @param mouseEvent The user clicks on the cancel button.
     * @throws IOException
     * This method disregards changes made to a product and returns to main Inventory view.
     */
    public void btnModifyProductCancelPress(MouseEvent mouseEvent) throws IOException {
        confirmAlert.setTitle("Exit Modify Product Screen?");
        confirmAlert.setContentText("Are you sure you would like leave without modifying " + selectedProduct.getName() + "?");
        confirmAlert.setHeaderText("Returning to main inventory screen");
        Optional<ButtonType> cancelResponse = confirmAlert.showAndWait();
        if (cancelResponse.get() == ButtonType.OK) {
            tblModifyPartAssociated.setItems(associatedPartsList);
            tblModifyPartAssociated.refresh();
            exitToInventory(mouseEvent);
        }
    }

    /**
     * @param mouseEvent A button which sends the user to the main inventory view was clicked.
     * @throws IOException
     * This method returns the user to the main inventory view.
     */
    public void exitToInventory(MouseEvent mouseEvent) throws IOException {
        Parent addPartParent = (Parent) FXMLLoader.load(this.getClass().getResource("/View/ViewInventory.fxml"));
        Scene nextScene = new Scene(addPartParent);
        Stage thisStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        thisStage.setTitle("Inventory");
        thisStage.setScene(nextScene);

    }


    /**
     * @param keyEvent The user types in the product search box.
     * This method updates the product table every time the search string changes.
     */
    public void txtModifyProductKeyPress(KeyEvent keyEvent) {
        if (txtModifyProductSearch.getText().isEmpty()) {
            tblModifyPartAll.setItems(Inventory.getAllParts());
        }
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> matchingProductsList = FXCollections.observableArrayList();
        String searchPartsString = txtModifyProductSearch.getText().toLowerCase();
        for (Part p : allParts) {
            if (String.valueOf(p.getId()).contains(searchPartsString) || p.getName().toLowerCase().contains(searchPartsString)) {
                matchingProductsList.add(p);
            }
        }
        tblModifyPartAll.setItems(matchingProductsList);
    }


    /**
     * @param userInputString User input from text box.
     * @return bool
     * This method checks if a string entered by the user is an integer.
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
     * @param userInputString User input from Inv text box.
     * @return bool
     * This method checks if a string entered by the user is a double.
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
     * @param userInputString User input from text box.
     * @return bool
     * This method checks that the user entered a string in a text box.
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
