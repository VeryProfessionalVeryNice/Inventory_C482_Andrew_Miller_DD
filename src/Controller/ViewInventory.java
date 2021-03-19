
package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Controller class ViewInventory.java
 * This class establishes functionality of the main inventory screen.
 */
public class ViewInventory implements Initializable {
    public AnchorPane sceneInventory;
    public Button btnAddPart;
    public Button btnModifyPart;
    public Button btnDeletePart;
    public Label partsLbl;
    public TextField txtSearchPart;
    public TableView partsTable;
    public TableColumn colPartID;
    public TableColumn colPartName;
    public TableColumn colInvLevelParts;
    public TableColumn colCostPerUnitParts;
    public Button btnExit;
    public Button btnAddProduct;
    public Button btnModifyProduct;
    public Button btnDeleteProduct;
    public Label lblProducts;
    public TextField txtSearchProduct;
    public TableView productsTable;
    public TableColumn colProductID;
    public TableColumn colProductName;
    public TableColumn colInvLevelProduct;
    public TableColumn colCostPerUnitProducts;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static Product selectedProduct;
    public static Part selectedPart;
    public Label lblPartsResults;
    public Button btnPartSearch;
    public Label lblProductResults;
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);


    /**
     * @param url The url parameter.
     * @param resourceBundle
     * This Inventory initialize method populates the tables and creates a listener for the search box.
     * RUNTIME ERROR - I worked a long time trying to populate the inventory screen tables with data.
     * The tables did not say "no content in table" as before I had tried to populate them, but that had no rows of parts or products actually displaying.
     * I reviewed how to populate tables several times and could not ascertain the source of my troubles. Finally, I deleted all the lines of code and completely rewrote them and got them to work.
     * I realized that I had been misnaming the types that went into the columns. I was using the "partID" or "partName" which I used to reference these values elsewhere in the project so that
     *  the difference between parts and products were more readily apparent but in the actual Part object creation these fields were simply known as "id" or "name" without the "part" designation
     *  in their name. Once I correctly named the fields as designated in their object creation everything worked as expected.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    partsTable.setItems(Inventory.getAllParts());
        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInvLevelParts.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colCostPerUnitParts.setCellValueFactory(new PropertyValueFactory<>("price"));

    productsTable.setItems(Inventory.getAllProducts());
        colProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInvLevelProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colCostPerUnitProducts.setCellValueFactory(new PropertyValueFactory<>("price"));

        FilteredList<Part> partFilteredList = new FilteredList<>(allParts, p -> true);

        txtSearchPart.textProperty().addListener((observableValue, oldValue, newValue) -> {
            partFilteredList.setPredicate(part -> {if (newValue==null||newValue.isEmpty()){
            return true; }

            String lowerFilter = newValue.toLowerCase();

            if (part.getName().toLowerCase().contains(lowerFilter)){
                return true; }
            else if (String.valueOf(part.getId()).contains(newValue)){
                return true;
            }
            return false;
            });
                });
        SortedList<Part> sortedPartsList = new SortedList<>(partFilteredList);
        sortedPartsList.comparatorProperty().bind(partsTable.comparatorProperty());
        //partsTable.setItems(sortedPartsList);
    }


    /**
     * @param mouseEvent User clicks on add button.
     * @throws IOException
     * This method sends the application to the AddPart view.
     */
    public void btnPartAddPress(MouseEvent mouseEvent) throws IOException {
        Parent addPartParent = (Parent) FXMLLoader.load(this.getClass().getResource("/View/ViewAddPart.fxml"));
        Scene nextScene = new Scene(addPartParent);
        Stage thisStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        thisStage.setTitle("Add Parts");
        thisStage.setScene(nextScene);
    }

    /**
     * @param mouseEvent User clicks on modify part button.
     * @throws IOException
     * This method sends the application to the ModifyPart view.
     */
    public void btnPartModifyPress(MouseEvent mouseEvent) throws IOException {
        selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            errorAlert.setTitle("No part selected");
            errorAlert.setContentText("Select a part to modify.");
            errorAlert.showAndWait();
        }
        else{
            Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/View/ViewModifyPart.fxml"));
            Node node = (Node)mouseEvent.getSource();
            Stage thisStage = (Stage)node.getScene().getWindow();
            Scene nextScene = new Scene(root);
            thisStage.setScene(nextScene);
            thisStage.setTitle("Modify Parts");}
    }

    /**
     * @return selectedPart
     * This method returns the selected part.
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }

    /**
     * @return selectedProduct
     * This method returns the selected product.
     */
    public static Product getSelectedProduct(){return selectedProduct;}

    /**
     * @param mouseEvent User clicks on exit button.
     * This method allows the user to exit the application.
     */
    public void btnExitClick(MouseEvent mouseEvent) {
        confirmAlert.setTitle("Exit Program?");
        confirmAlert.setContentText("Are you sure you would like to close the application?");
        confirmAlert.setHeaderText("Close Confirmation");
        Optional<ButtonType> deleteResponse = confirmAlert.showAndWait();
        if( deleteResponse.get() == ButtonType.OK){
        Platform.exit();
    }}

    /**
     * @param mouseEvent User clicks on add product button.
     * @throws IOException
     * This method send the application to the Add Product view.
     */
    public void btnProductAddPress(MouseEvent mouseEvent) throws IOException {
        Parent addPartParent = (Parent) FXMLLoader.load(this.getClass().getResource("/View/ViewAddProduct.fxml"));
        Scene nextScene = new Scene(addPartParent);
        Stage thisStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        thisStage.setTitle("Add Products");
        thisStage.setScene(nextScene);
    }

    /**
     * @param mouseEvent User clicks on modify product button.
     * @throws IOException
     * This method sends the application to the Modify Product view.
     */
    public void btnProductModifyPress(MouseEvent mouseEvent) throws IOException {

        selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null){
            errorAlert.setTitle("No Product Selected");
            errorAlert.setContentText("Select a product to modify.");
            errorAlert.showAndWait();
        }
        else{
            Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/View/ViewModifyProduct.fxml"));
            Node node = (Node)mouseEvent.getSource();
            Stage thisStage = (Stage)node.getScene().getWindow();
            Scene nextScene = new Scene(root);
            thisStage.setScene(nextScene);
            thisStage.setTitle("Modify Products");
        }

    }


    /**
     * @param mouseEvent User clicks on delete part button.
     * This method deletes a part.
     */
    public void btnDeletePartClicked(MouseEvent mouseEvent) {
        selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            errorAlert.setTitle("Part Not Deleted");
            errorAlert.setContentText("No part was selected");
            errorAlert.showAndWait();
        }
        else{
            confirmAlert.setTitle("Confirm Part Deletion?");
            confirmAlert.setContentText("Are you sure you would like to delete " + selectedPart.getName() + "?");
            confirmAlert.setHeaderText("Pending Deletion");
            Optional<ButtonType> deleteResponse = confirmAlert.showAndWait();
            if( deleteResponse.get() == ButtonType.OK){
                Inventory.deletePart(selectedPart);}

    }}


    /**
     * @param mouseEvent User clicks on delete product button.
     * This method deletes a product.
     */
    public void btnDeleteProductClicked(MouseEvent mouseEvent) {
        selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null){
            errorAlert.setTitle("Product Not Deleted");
            errorAlert.setContentText("No product was selected");
            errorAlert.showAndWait();
        }
        else if (selectedProduct.getAllParts().isEmpty()){
            confirmAlert.setTitle("Confirm Product Deletion?");
            confirmAlert.setContentText("Are you sure you would like to delete " + selectedProduct.getName() + "?");
            confirmAlert.setHeaderText("Pending Deletion");
            Optional<ButtonType> deleteResponse = confirmAlert.showAndWait();
            if( deleteResponse.get() == ButtonType.OK){
                Inventory.deleteProduct(selectedProduct);}

        }
        else {
            errorAlert.setTitle("Product Not Deleted");
            errorAlert.setContentText("Product contains parts, please remove parts from " + selectedProduct.getName()+  " before deleting.");
            errorAlert.setHeaderText("Unable to delete");

            errorAlert.showAndWait();
            }
    }


    /**
     * @param actionEvent User types in product search box.
     * This method updates the product table based on user search text.
     */
    public void productSearchChange(KeyEvent actionEvent) {
        if (txtSearchProduct.getText().isEmpty()){
            productsTable.setItems(Inventory.getAllProducts());
        }
        lblProductResults.setVisible(false);
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> matchingProductsList = FXCollections.observableArrayList();
        String searchProductString = txtSearchProduct.getText().toLowerCase();
        for (Product p : allProducts) {
            if (String.valueOf(p.getId()).contains(searchProductString) || p.getName().toLowerCase().contains(searchProductString))
            { matchingProductsList.add(p); }
        }
        productsTable.setItems(matchingProductsList);
        if (txtSearchProduct.getText() != null && matchingProductsList.isEmpty()){
            lblProductResults.setVisible(true);
        }
    }


    /**
     * @param keyEvent User types in part search box.
     * This method updates the part table based on user search text.
     */
    public void partSearchChange(KeyEvent keyEvent) {
        if (txtSearchPart.getText().isEmpty()){
            partsTable.setItems(Inventory.getAllParts());
        }
        lblPartsResults.setVisible(false);
        ObservableList<Part> allPartsList = Inventory.getAllParts();
        ObservableList<Part> matchingPartsList = FXCollections.observableArrayList();
        String searchPartString = txtSearchPart.getText().toLowerCase();
        for (Part p : allPartsList) {
            if (String.valueOf(p.getId()).contains(searchPartString) || p.getName().toLowerCase().contains(searchPartString))
            { matchingPartsList.add(p); }
        }
        partsTable.setItems(matchingPartsList);
        if (txtSearchPart.getText() != null && matchingPartsList.isEmpty()){
            lblPartsResults.setVisible(true);
        }
    }
}
