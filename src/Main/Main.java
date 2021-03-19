
package Main;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * FUTURE ENHANCEMENT - I feel that a worthwhile improvement to this application would be to incorporate functionality which allowed products to be marked as sold.
 * Once a product was marked as sold its inventory would be decremented by one as would all of the parts that the product contains. This would greatly streamline the
 * tracking of changes in the inventory, because as the program stands, though products and parts can be added or deleted, their inventory levels would have to be
 * repeatedly manually updated. Having inventory levels of parts and products automatically adjust with the sale of products would greatly reduce the amount of work
 * the company would have to do with the software to maintain accurate records. Additionally, this opens even more avenues for increased functionality, as one could then add
 * sales dates and then track inventory changes over time, total cost of products sold, and also give information about total sales and profit, the popularity of parts, and even more information that one
 * would desire from a more powerful inventory and invoicing system.
 * Main class Main.java
 * This class establishes the main entry into application.
 */
public class Main extends Application {
/***/
    @Override
    /**This is the start method which sets the scene on the stage for the application.
      */
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/ViewInventory.fxml"));
        primaryStage.setTitle("Inventory");
        primaryStage.setScene(new Scene(root, 1100, 494));
        primaryStage.show();
    }


    /**
     * @param args Arguments to be passed to the main method.
     * This is the main method which creates parts and products and launches the application.
     */
    public static void main(String[] args) {
        int partId = Inventory.currentPartID();
        InHouse tire = new InHouse(partId,"tire", 24.99, 2, 1, 9, 123);
        partId = Inventory.currentPartID();
        InHouse wheel = new InHouse(partId,"wheel", 22.89, 2, 1, 9, 321);
        partId = Inventory.currentPartID();
        InHouse brake = new InHouse(partId,"brake", 3.79, 4, 1, 9, 222);
        partId = Inventory.currentPartID();
        Outsourced handle = new Outsourced(partId, "handle",4.69, 5, 1,9, "Schwinn");
        Inventory.addPart(tire);
        Inventory.addPart(wheel);
        Inventory.addPart(brake);
        Inventory.addPart(handle);
        //Add sample product
        int productId = Inventory.currentProductID();
        Product bike = new Product(productId, "Trek 2.0", 500.00, 3, 1, 100);
        Product emptyBike = new Product(productId, "Empty Schwinn", 419.99, 5, 1, 55);
        bike.addPartToProduct(tire);
        bike.addPartToProduct(brake);
        bike.addPartToProduct(wheel);
        Inventory.addProduct(emptyBike);
        Inventory.addProduct(bike);

        launch(args);
    }
}
