package presentation;

import businesslogic.BaseProduct;
import businesslogic.CompositeProduct;
import businesslogic.DeliveryService;
import businesslogic.MenuItem;

import javax.swing.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Clasa ce contine metode sattice de creare JTable si Jlist-uri
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class Tables {
    /**
     * Creeaza tabel cu produsele de baza
     * @param deliveryService deliveryService asociat
     * @return tabelul
     */
    public static JTable createTable(DeliveryService deliveryService){
        Set<MenuItem> menu=deliveryService.getProducts();
        Set<MenuItem> baseProducts=  menu.stream()
                .filter(p->p instanceof BaseProduct)
                .collect(Collectors.toSet());
        String[] header={"Title", "Rating", "Calories","Protein", "Fat","Sodium", "Price"};

        if(baseProducts.size()>0)
            return new JTable(getData(baseProducts),header);
        else
            return new JTable(new String[][]{{""}},new String[]{ "empty"});
    }

    /**
     * Creaza tabel cu o lista de produse primite ca parametru
     * @param products produse
     * @return tabel
     */
    public static JTable createTable(Collection<MenuItem> products){
        String[] header={"Title", "Rating", "Calories","Protein", "Fat","Sodium", "Price"};
        Set<MenuItem> baseProducts=  products.stream()
                .filter(p->p instanceof BaseProduct)
                .collect(Collectors.toSet());
        if(products.size()>0)
            return new JTable(getData(baseProducts),header);
        else
            return new JTable(new String[][]{{""}},new String[]{ "empty"});
    }

    /**
     * Metoda statica de populare a datelor tabelului
     * @param baseProducts colectie produse de baza
     * @return matrice de string-uri
     */
    private static String[][] getData(Collection<MenuItem> baseProducts){
        String[][] data=new String[baseProducts.size()][7];
        int row=0;
        for(MenuItem baseProduct:baseProducts){
            BaseProduct baseProduct1=(BaseProduct) baseProduct;
            data[row][0]=baseProduct1.getTitle();
            data[row][1]=baseProduct1.getRating().toString();
            data[row][2]=baseProduct1.getCalories().toString();
            data[row][3]=baseProduct1.getProteins().toString();
            data[row][4]=baseProduct1.getFat().toString();
            data[row][5]=baseProduct1.getSodium().toString();
            data[row][6]=baseProduct1.getPrice().toString();
            row++;
        }
        return data;
    }

    /**
     * Creare lista produse
     * @param deliveryService deliveryService
     * @param mode mode-0 compuse , mode=1 de baza
     * @return lista de produse
     */
    public static JList<Object> createList(DeliveryService deliveryService, int mode){
        Set<MenuItem> menu=deliveryService.getProducts();

        Set<MenuItem> compositeProduct=  menu.stream()
                .filter(p->{
                    if(mode==0)
                        return p instanceof CompositeProduct;
                    else
                        return p instanceof BaseProduct;})
                .collect(Collectors.toSet());
        Object[] data=mode==0? new CompositeProduct[compositeProduct.size()]: new BaseProduct[compositeProduct.size()];
        int row=0;
        for(MenuItem composite:compositeProduct){
            if(mode==0) {
                CompositeProduct compositeProduct1 = (CompositeProduct) composite;
                data[row]=compositeProduct1;
            }else {
                BaseProduct compositeProduct1 = (BaseProduct) composite;
                data[row]=compositeProduct1;
            }
            row++;
        }
        return new JList<Object>(data);
    }

    /**
     * Creare lista produse compuse
     * @param menuItems  colectia de produse
     * @return lista de tip JList
     */
    public static JList<Object> createList(Collection<MenuItem> menuItems){
       Object[] data=new CompositeProduct[menuItems.size()];
       int row=0;
        for(MenuItem product:menuItems ){
            if(product instanceof CompositeProduct) {
                data[row]=(CompositeProduct)product;
                row++;
            }
        }
        return new JList<Object>(data);
    }
}
