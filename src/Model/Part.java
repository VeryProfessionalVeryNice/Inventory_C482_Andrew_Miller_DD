package Model;
/**
 * Part.java
 * This class establishes the Part object which is abstract.
 */
public abstract class Part {

        private int id;
        private String name;
        private double price;
        private int stock;
        private int min;
        private int max;

    /**
     * @param id The id of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The stock of the part.
     * @param min  The min of the part.
     * @param max The max of the part.
     * This method is the constructor for a part.
     */
        public Part(int id, String name, double price, int stock, int min, int max) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.min = min;
            this.max = max;
        }

    /**
     * @return id
     * This method returns the part id.
     */
        public int getId() {
            return this.id;
        }

    /**
     * @param id The id of the part.
     * This method sets the part id.
     */
        public void setId(int id) {
            this.id = id;
        }

    /**
     * @return  name
     * This method gets the part name.
     */
        public String getName() {
            return this.name;
        }

    /**
     * @param name The name of the part.
     * This method sets the part name.
     */
        public void setName(String name) {
            this.name = name;
        }

    /**
     * @return price
     * This method returns the part price.
     */
        public double getPrice() {
            return this.price;
        }

    /**
     * @param price The price of the part.
     * This method sets the part price.
     */
        public void setPrice(double price) {
            this.price = price;
        }

    /**
     * @return stock
     * This method returns the part stock.
     */
        public int getStock() {
            return this.stock;
        }

    /**
     * @param stock The stock of the part.
     * This method sets the part stock.
     */
        public void setStock(int stock) {
            this.stock = stock;
        }

    /**
     * @return min
     * This method returns the part min.
     */
        public int getMin() {
            return this.min;
        }

    /**
     * @param min The min of the part.
     * This method sets the part min.
     */
        public void setMin(int min) {
            this.min = min;
        }

    /**
     * @return max
     * This method gets the part max.
     */
        public int getMax() {
            return this.max;
        }

    /**
     * @param max The max of the part
     * This method sets the part max.
     */
        public void setMax(int max) {
            this.max = max;
        }
}
