package businesslogic;

/**
 * Clasa pentru produsele de baza;
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class BaseProduct extends MenuItem{
    /**
     * nume produs
     */
    private final String title;
    /**
     * rating produs
     */
    private Double rating;
    /**
     * cantitate calorii
     */
    private Integer calories;
    /**
     * cantitate proteine
     */
    private Integer proteins;
    /**
     * cantitate grasimi
     */
    private Integer fat;
    /**
     * cantitate sodiu
     */
    private Integer sodium;
    /**
     *pret produs
     */
    private Double price;

    /**
     * Constructor cu tipurile corespunzatoare campurilor
     * @param title nume produs
     * @param rating  rating produs
     * @param calories cantitate calorii
     * @param proteins cantitate proteine
     * @param fats cantitate grasimi
     * @param sodium cantitate sodiu
     * @param price pret produs
     */
    public BaseProduct(String title, Double rating, Integer calories,
                       Integer proteins, Integer fats, Integer sodium, Double price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.proteins = proteins;
        this.fat = fats;
        this.sodium = sodium;
        this.price = price;
    }
    /**
     * Constructor cu toti parametrii String
     * @param title nume produs
     * @param rating  rating produs
     * @param calories cantitate calorii
     * @param protein cantitate proteine
     * @param fat cantitate grasimi
     * @param sodium cantitate sodiu
     * @param price pret produs
     */
    public BaseProduct(String title,String rating,String calories,
                       String protein,String fat, String sodium, String price ) throws NumberFormatException{
            this.title = title;
            this.rating = Double.parseDouble(rating);
            this.calories = Integer.parseInt(calories);
            this.proteins = Integer.parseInt(protein);
            this.fat = Integer.parseInt(fat);
            this.sodium =Integer.parseInt(sodium);
            this.price =Double.parseDouble(price);

    }
    /**
     * Constructor doar pe nume
     * @param title nume
     */
    public BaseProduct(String title){
        this.title=title;
    }

    /**
     * Metoda de compute price
     * @return pretul
     */
    @Override
    public Double computePrice(){
        return this.price;
    }

    /**
     * Suprascriere metoda equals
     * @param o obiect de tip BaseProduct
     * @return true sau false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompositeProduct || o instanceof BaseProduct)) return false;
        if(o instanceof CompositeProduct) {
            CompositeProduct that = (CompositeProduct) o;
            return title.equals(that.getTitle());
        }else {
            BaseProduct that = (BaseProduct) o;
            return title.equals(that.getTitle());
        }
    }

    /**
     * Metoda pt calcul hashcode
     * @return hashcode-ul
     */
    @Override
    public int hashCode() {return title.hashCode(); }

    /**
     *
     * @return afisare produs
     */
    @Override
    public String toString() {
        return title + " price:" + price;
    }

    /**
     * Getter
     * @return nume
     */
    public String getTitle() { return title; }
    /**
     * Getter
     * @return rating
     */
    public Double getRating() {
        return rating;
    }
    /**
     * Getter
     * @return calorii
     */
    public Integer getCalories() {
        return calories;
    }
    /**
     * Getter
     * @return proteine
     */
    public Integer getProteins() {
        return proteins;
    }
    /**
     * Getter
     * @return fat
     */
    public Integer getFat() {
        return fat;
    }
    /**
     * Getter
     * @return sodiu
     */
    public Integer getSodium() {
        return sodium;
    }
    /**
     * Getter
     * @return pret
     */
    public Double getPrice() {
        return price;
    }
}
