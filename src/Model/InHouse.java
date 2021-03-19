
package Model;
/**
 * Inhouse.Java
 * This class established the InHouse part object which inherits from the Part class.
 */
public class InHouse extends Part {
    private int machineID;

    /**
     * @param id The id of a part.
     * @param name The name of a part.
     * @param price The price of a part.
     * @param stock The stock of a part.
     * @param min The min of a part.
     * @param max The max of a part.
     * @param machineID The machineID for a part.
     * This is the constructor for creating an InHouse Part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
        this.setMin(min);
        this.setMax(max);
        this.setMachineID(machineID);
    }

    /**
     * @return machineID
     * This method returns the machineID.
     */
    public int getMachineID() {
        return this.machineID;
    }

    /**
     * @param machineID The machineID related to an inhouse part.
     * This method sets the machineID.
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}