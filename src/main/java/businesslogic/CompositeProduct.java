package businesslogic;

import java.util.*;
/**
 * Clasa pentru produsele compuse;
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class CompositeProduct extends MenuItem{
    /**
     * Lista de produse continute
     */
    private Set<MenuItem> containedProducts;
    /**
     * Titlul produsului
     */
    private  String title;

    /**
     * Constructor ce initializeza lista
     */
    public CompositeProduct(){
        containedProducts= new HashSet<>();
    }

    /**
     * Constructor
     * @param title titlu
     * @param containedProducts lista produse continute
     */
    public CompositeProduct(String title,Set<MenuItem> containedProducts){
        this.title=title;
        this.containedProducts=containedProducts;
    }

    /**
     * Constructor
     * @param title titlu
     */
    public CompositeProduct(String title){ this.title=title; }

    /**
     * Afaugare produs nou
     * @param product produsul de adaugat
     * @return daca s-a adaugat sau nu
     */
    public boolean addNewProduct(MenuItem product){
        return this.containedProducts.add(product);
    }

    /**
     * Setter
     * @param title nume produs
     */
    public void setTitle(String title){
        this.title=title;
    }

    /**
     * Suprascriere equals in functie de nume
     * @param o obiectul de tip basa product sau composite
     * @return ture or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompositeProduct || o instanceof BaseProduct)) return false;
        if(o instanceof CompositeProduct) {
            CompositeProduct that = (CompositeProduct) o;
            return title.equals(that.getTitle());
        }
        else {
            BaseProduct that = (BaseProduct) o;
            return title.equals(that.getTitle());
        }
    }

    /**
     * Calculare hashcode pe nume
     * @return hashcode-ul
     */
    @Override
    public int hashCode() {
        return title.hashCode();
    }

    /**
     * Calculare pret
     * @return pret
     */
    public Double computePrice(){
        Double price=0.0;
        for(MenuItem menuItem:this.containedProducts){
            price+=menuItem.computePrice();
        }
        return price;
    }

    /**
     * Calculare proteine
     * @return cantitate proteine
     */
    public Integer computeProteins(){
        Integer proteins=0;
        for(MenuItem menuItem: this.containedProducts){
            if(menuItem instanceof BaseProduct)
                proteins+=((BaseProduct) menuItem).getProteins();
            else proteins+=((CompositeProduct)menuItem).computeProteins();
        }
        return proteins;
    }

    /**
     * Calculare grasimi
     * @return cantitate grasimi
     */
    public Integer computeFats(){
        Integer fats =0;
        for(MenuItem menuItem: this.containedProducts){
            if(menuItem instanceof BaseProduct)
                fats +=((BaseProduct) menuItem).getFat();
            else fats +=((CompositeProduct)menuItem).computeFats();
        }
        return fats;
    }
    /**
     * Calculare calorii
     * @return cantitate calorii
     */
    public Integer computeCalories(){
        Integer calories =0;
        for(MenuItem menuItem: this.containedProducts){
            if(menuItem instanceof BaseProduct)
                calories +=((BaseProduct) menuItem).getCalories();
            else calories +=((CompositeProduct)menuItem).computeCalories();
        }
        return calories;
    }
    /**
     * Calculare sodiu
     * @return cantitate sodiu
     */
    public Integer computeSodium(){
        Integer sodium =0;
        for(MenuItem menuItem: this.containedProducts){
            if(menuItem instanceof BaseProduct)
                sodium +=((BaseProduct) menuItem).getSodium();
            else sodium +=((CompositeProduct)menuItem).computeSodium();
        }
        return sodium;
    }
    /**
     * Calculare rating
     * @return rating
     */
    public Double computeRating(){
        int count=0;
        Double sum=0.0;
        for(MenuItem menuItem: this.containedProducts){
            if(menuItem instanceof BaseProduct){
                sum +=((BaseProduct) menuItem).getRating();
                count++;
            }
            else {
                sum +=((CompositeProduct)menuItem).computeRating();
                count++;
            }
        }
        return count==0? null: sum/count;
    }

    /**
     * getter
     * @return nume produs
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter
     * @return lista produse continute
     */
    public Set<MenuItem> getContainedProducts() {
        return containedProducts;
    }

    /**
     * Suprascriere toString
     * @return afisare produs compus
     */
    @Override
    public String toString() {
        String toReturn=title+"{\n   ";
        for(MenuItem menuItem:containedProducts){
            toReturn=toReturn+menuItem+"\n   ";
        }
        return toReturn+"} PRICE: "+this.computePrice();
    }
}
