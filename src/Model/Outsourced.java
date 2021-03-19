package Model;
/**

 * Outsourced.java
 * This class established the Outsourced part object which inherits from the Part class.
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * @param id The id of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The stock of the part.
     * @param min The min of the part.
     * @param max The max of the part.
     * @param companyName The company name associated with the outsourced part.
     * This method is the constructor for a new Outsourced part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
        this.setMin(min);
        this.setMax(max);
        this.companyName = companyName;
    }

    /**
     * @return companyName
     * This method returns the part companyName.
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * @param companyName The company name associated with the outsourced part.
     * This method sets the part companyName.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}