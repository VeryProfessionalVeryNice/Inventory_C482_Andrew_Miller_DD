package Controller;

import Model.InHouse;
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
 * Controller class ViewAddProduct.java

 * This class establishes functionality of the Add Product screen.
 */
public class ViewAddProduct implements Initializable {
    public AnchorPane sceneAddProduct;
    public TextField txtAddProductSearch;
    public TableView tblAddProductAssociated;
    public TableColumn colAddProductAssocID;
    public TableColumn colAddProductAssocName;
    public TableColumn colAddProductAssocInv;
    public TableColumn colAddProductAssocCost;
    public TableView tblAddProductAll;
    public TableColumn colAddProductAllID;
    public TableColumn colAddProductAllName;
    public TableColumn colAddProductAllInv;
    public TableColumn colAddProductAllCost;
    public Button btnAddProductRemove;
    public Button btnAddProductCancel;
    public Button btnAddProductSave;
    public TextField txtAddProductID;
    public TextField txtAddProductName;
    public TextField txtAddProductInv;
    public TextField txtAddProductPrice;
    public TextField txtAddProductMax;
    public TextField txtAddProductMin;
    public Button btnAddProductAdd;
    public Label lblProductSearch;
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    private ObservableList<Part> tempAssociatedPartsList = FXCollections.observableArrayList();
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert intAlert = new Alert(Alert.AlertType.ERROR);
    Alert doubleAlert = new Alert(Alert.AlertType.ERROR);
    Alert stringAlert = new Alert(Alert.AlertType.ERROR);


    /**
     * @param url The url param.
     * @param resourceBundle
     * This initialize method for the Add Product view populates the table and generates a productID.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int productID = Inventory.currentProductID();
        this.txtAddProductID.setText(String.valueOf(productID));

        colAddProductAssocID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAddProductAssocName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddProductAssocInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colAddProductAssocCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblAddProductAll.setItems(Inventory.getAllParts());
        colAddProductAllID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAddProductAllName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddProductAllInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colAddProductAllCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        tempAssociatedPartsList = associatedPartsList;
    }

    /**
     * @param mouseEvent User clicks on cancel button.
     * @throws IOException
     * This method aborts the creation of a new product and returns user to main inventory screen.
     */
    public void btnAddProductCancelPress(MouseEvent mouseEvent) throws IOException {
        tblAddProductAssociated.refresh();
        confirmAlert.setTitle("Exit Add Product Screen?");
        confirmAlert.setContentText("Are you sure you would like leave without adding a product?");
        confirmAlert.setHeaderText("Returning to main inventory screen");
        Optional<ButtonType> cancelResponse = confirmAlert.showAndWait();
        if( cancelResponse.get() == ButtonType.OK){
            exitToInventory(mouseEvent);}
    }

    /**
     * @param mouseEvent User clicks on save button.
     * @throws IOException
     * This method validates user input and creates a new product.
     */
    public void btnAddProductSavePress(MouseEvent mouseEvent) throws IOException {
        associatedPartsList = tblAddProductAssociated.getItems();
        Integer productID = Integer.parseInt(txtAddProductID.getText());
        String productName;
        Double productPrice;
        Integer productStock;
        Integer productMin;
        Integer productMax;
        if (isPriceDouble(String.valueOf(txtAddProductPrice.getText()))==true){
            productPrice = Double.parseDouble(txtAddProductPrice.getText());
            if (isFieldNotEmpty(String.valueOf(txtAddProductName.getText())) == true){
                productName = txtAddProductName.getText();
                if(isIntCheck(String.valueOf(this.txtAddProductInv.getText()))==true) {
                    productStock = Integer.parseInt(String.valueOf(this.txtAddProductInv.getText()));
                    if(isIntCheck(String.valueOf(this.txtAddProductMin.getText()))==true){
                        productMin = Integer.parseInt(String.valueOf(this.txtAddProductMin.getText()));
                        if(isIntCheck(String.valueOf(this.txtAddProductMax.getText()))==true){
                            productMax = Integer.parseInt(String.valueOf(this.txtAddProductMax.getText()));
                            if(areMinMaxInvValid(productMin,productMax,productStock) == true){
                                Product addedProduct = new Product(associatedPartsList, productID, productName, productPrice, productStock, productMin, productMax);
                                Inventory.addProduct(addedProduct);
                                        exitToInventory(mouseEvent);}
    }}}}}}


    /**
     * @param mouseEvent User clicks on add button.
     * This method adds a part to a product.
     */
    public void btnAddProductAddPress(MouseEvent mouseEvent) {
        Part selectedPart = (Part) tblAddProductAll.getSelectionModel().getSelectedItem();
        tempAssociatedPartsList.add(selectedPart);
        tblAddProductAssociated.setItems(tempAssociatedPartsList);
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
     * @param mouseEvent User clicks on remove button.
     * This method removes a part from a product.
     */
    public void btnRemovePartClick(MouseEvent mouseEvent) {
        Part selectedPart = (Part) tblAddProductAll.getSelectionModel().getSelectedItem();
        tempAssociatedPartsList.remove(selectedPart);
        //associatedPartsList.remove(selectedPart);
        tblAddProductAssociated.setItems(tempAssociatedPartsList);

    }

    /**
     * @param keyEvent User types in search box.
     * This method searches products and updates the product table with each user key press in the search text box.
     */
    public void searchAddPartProductKeyType(KeyEvent keyEvent) {
        if (txtAddProductSearch.getText().isEmpty()){
            tblAddProductAll.setItems(Inventory.getAllParts());
        }
        lblProductSearch.setVisible(false);
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> matchingProductsList = FXCollections.observableArrayList();
        String searchPartsString = txtAddProductSearch.getText().toLowerCase();
        for (Part p : allParts) {
            if (String.valueOf(p.getId()).contains(searchPartsString) || p.getName().toLowerCase().contains(searchPartsString))
            { matchingProductsList.add(p); }
        }
        tblAddProductAll.setItems(matchingProductsList);
        if (txtAddProductSearch.getText() != null && matchingProductsList.isEmpty()){
            lblProductSearch.setVisible(true);
        }

    }

    /**
     * @param userInputString User input into text box.
     * @return bool
     * This method validates that user input integer data into text box.
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
     * @param userInputString User input into price text box.
     * @return bool
     * This method validates that the user input a double into a text box.
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
     * @param userInputString User input into text box.
     * @return bool
     * This method validates that the user provided input into a text box.
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
     * @param minInput Min string text box input converted to int.
     * @param maxInput Max string text box input converted to int.
     * @param invInput Inv string text box input converted to int.
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
