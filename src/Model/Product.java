package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Product.java
 * This class establishes the product object.
 */
public class Product {


    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * @param id The id of the product.
     * @param name The name of the product.
     * @param price The price of the product.
     * @param stock The stock of the product.
     * @param min The min of the product.
     * @param max The max of the product.
     * This method is the constructor for a product without an associated part list.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param associatedParts The list of parts associated with the product.
     * @param id The id of the product.
     * @param name The name of the product.
     * @param price The price of the product.
     * @param stock The stock of the product.
     * @param min The min of the product.
     * @param max The max of the product.
     * This method is the constructor for a product.
     */
    public Product(ObservableList<Part> associatedParts, int id, String name, double price, int stock, int min, int max) {
        this.associatedParts = associatedParts;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     * @return id
     * This method returns the product id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param id The id of the product.
     * This method sets the product id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return name
     * This method gets the product name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name The name of the product.
     * This method sets the product name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return price
     * This method returns the product price.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @param price The price of the product.
     * * This method sets the product price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return stock
     * This method returns the product stock.
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * @param stock The stock of the product.
     * This method sets the product stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return min
     * This method returns the product min.
     */
    public int getMin() {
        return this.min;
    }

    /**
     * @param min The min of the product.
     * This method sets the product min.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return max
     * This method gets the product max.
     */
    public int getMax() {
        return this.max;
    }

    /**
     * @param max The max of the product.
     * This method sets the product max.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return associatedParts
     * This method gets parts associated with a product.
     */
    public ObservableList<Part> getAllParts(){return associatedParts;}

    /**
     * @param part A part associated with the product.
     * This method associates a part with a product.
     */
    public void addPartToProduct(Part part){
        associatedParts.add(part);
    }
}