
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;
/**
 *Inventory.java
 * This class establishes the Inventory object which will is capable of holding parts and products.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static int partID = 1000;
    private static int productID = 100;


    /**
     * @return allParts
     * This method returns a list of all parts.
     */
    public static ObservableList<Part> getParts() {
        return allParts;
    }

    /**
     * @return allProducts
     * This method returns a list of all products.
     */
    public static ObservableList<Product> getProducts() {
        return allProducts;
    }

    /**
     * @param part The part being checked by a search.
     * @param searchText The user search criteria.
     * @return bool
     * This method determines if there is a part with a name matching the search criteria.
     */
    private boolean searchFindsPart(Part part, String searchText){
        return (part.getName().contains(searchText.toLowerCase())) ||
                Integer.valueOf(part.getId()).toString().equals(searchText.toLowerCase());
    }

    /**
     * @param list The list of parts to be searched.
     * @param searchText The user search criteria.
     * @return partSearchResult
     * This method returns a list of parts matching search criteria.
     */
    private ObservableList<Part> partSearchResult(List<Part> list, String searchText){
        List<Part> filteredList = new ArrayList<>();
        for (Part part : list){
            if(searchFindsPart(part, searchText)) filteredList.add(part);
        }
        return FXCollections.observableList(filteredList);
    }

    /**
     * @param newPart The part to be added.
     * This method adds a new part to the list of parts.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct The product to be added.
     * This method adds a new product to the list of products.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * @param partId The id of the part being sought.
     * @return Part
     * This method returns a part based on partId.
     */
    public static Part lookupPart(int partId) {
        return (Part)allParts.get(partId);
    }

    /**
     * @param productId The id of the product being sought.
     * @return Product
     * This method returns a product based on productId.
     */
    public static Product lookupProduct(int productId) {
        return (Product)allProducts.get(productId);
    }

    /**
     * @param selectedPart The part which the user currently has selected.
     * This method deletes a part from the parts list.
     */
    public static void deletePart(Part selectedPart){
    allParts.remove(selectedPart);
    }

    /**
     * @param partName The name of the part to be retrieved.
     * @return lookupPart
     * This method finds parts with matching partName.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        for (Part part : allParts){
            if(part.getName() == partName){
                matchingParts.add(part);
            }
    }
        return matchingParts;
    }

    /**
     * @param productName The name of the product to be retrieved.
     * @return lookupProduct
     * This method returns products with matching productName.
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> matchingProducts= FXCollections.observableArrayList();
        for (Product product : allProducts){
            if(product.getName() == productName){
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    /**
     * @return allParts
     * This method returns all parts in inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return allProducts
     * This method returns all products in inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * @return partID
     * This method generates the appropriate partID.
     */
    public static int currentPartID() {
        ++partID;
        return partID;
    }


    /**
     * @return productID
     * This method generates the appropriate productID.
     */
    public static int currentProductID() {
        ++productID;
        return productID;
    }

    /**
     * @param selectedProduct The product the user currently has selected.
     * This method deletes a product from inventory.
     */
    public static void deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }
}
