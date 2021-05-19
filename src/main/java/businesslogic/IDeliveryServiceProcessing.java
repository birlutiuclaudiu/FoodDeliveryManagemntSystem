package businesslogic;

import model.User;
import java.util.List;
import java.util.Set;

/**
 * Interfata pentru operatiile efectuate de client si administrator
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public interface IDeliveryServiceProcessing {
    /**
     * Functia de import produse administrator
     * @pre true
     * @post menu!=null and user.size()==user.size()@pre and orders.size()==orders.size()@pre
     * @return daca s-au importat sau nu
     * @invariant isWellFormed()
     */
    public boolean importProducts();
    /**
     * Metoda de stergere a unui produs
     * @param menuItem produsul de sters
     * @return daca s-a sters sau nu
     * @pre menuItem!=null
     * @post menu.getSize()==getSize()@pre-1 ==> menu.contains(menuItem)@pre
     * @post menu.getSize()==getSize()@pre ==> !menu.contains(menuItem)@pre
     * @post !menu.contains(menuItem)
     * @invariant isWellFormed()
     */
    public boolean deleteProduct(MenuItem menuItem);

    /**
     *Metoda pentru editarea unui produs
     * @param menuItem obiectul de modificat
     * @return daca s-a facut modificarea sau nu
     * @pre menuItem!=null and menu.contains(menuItem)
     * @post menu.contains(menuItem) and user.size()==user.size()@pre and orders.size()==orders.size()@pre
     * @invariant isWellFormed()
     */
    public boolean editProduct(MenuItem menuItem);

    /**
     * Metoda de adaugare produs nou
     * @param menuItem produsul de adaugat
     * @return daca s-a adaugat sau nu
     * @pre menuItem!=null
     * @post menu.size()==menu.size()@pre+1 ==>!menu.contains(menuItem)@pre
     * @post menu.size()==menu.size()@pre   ==>menu.contains(menuItem)@pre
     * @post user.size()==user.size()@pre and orders.size()==orders.size()@pre and menu.size()==menu.size()@pre
     * @invariant isWellFormed()
     */
    public boolean addProduct(MenuItem menuItem);

    /**
     * Metoda de generare raport time interval orders
     * @param startHour prima ora
     * @param endHour a doua ora din range
     * @return daca s-a generat sau nu raportul
     * @pre startHour>-1 and endHour<25 and startHour<endHour+1
     * @post menu.size()==menu.size()@pre and user.size()==user.size()@pre and orders.size()==orders.size()@pre
     * @invariant isWellFormed()
     */
    public boolean generateReportTime(int startHour, int endHour);

    /**
     * Metoda de generare raport produse cumparate de un numar mai mare decat cel specificat
     * @param number numarul trimis ca parametru
     * @return daca s-a generat sau nu raportul
     * @pre number>0
     * @post menu.size()==menu.size()@pre and user.size()==user.size()@pre and orders.size()==orders.size()@pre
     * @invariant isWellFormed()
     */
    public boolean generateReportProducts(int number);

    /**
     * Metoda de generare raport clienti care au comandat de mai multe ori si au platit peste o anumita suma
     * @param numberOrders numarul de comenzi
     * @param value valoare minima de plata
     * @return  daca s-a generat sau nu raportul
     * @pre numberOrder>-1 and value>-1
     * @post menu.size()==menu.size()@pre and user.size()==user.size()@pre and orders.size()==orders.size()@pre
     * @invariant isWellFormed()
     */
    public boolean generateReportClient(int numberOrders, int value);

    /**
     * Metoda de generare raport produse comandate intr-o zi anume
     * @param day zi
     * @param month luna
     * @param year an
     * @return daca s-a generat sau nu raportul
     * @pre true
     * @post menu.size()==menu.size()@pre and user.size()==user.size()@pre and orders.size()==orders.size()@pre
     * @invariant isWellFormed()
     */
    public boolean generateReportDay(int day, int month, int year);

    /**
     *Metoda pentru crearea unei comenzi calculand pretul si generearea unei facturi
     * @param user user
     * @param menuItems produsele pe care le comanda
     * @pre user!=null and menuItems!=null
     * @post orders.size()==orders.size()@pre+1 and menu.size()==menu.size()@pre and user.size()==user.size()@pre
     * @invariant isWellFormed()
     */
    public void createNewOrder(User user, List<MenuItem> menuItems);

    /**
     * Metoda pentru cautarea de produse dupa anumite filtre
     * @param title titlu produs
     * @param rating rating produs
     * @param calories cantitate calorii produs
     * @param proteins cantitate proteine produs
     * @param fats cantitate grasimi produs
     * @param sodium cantitate de sodiu produs
     * @param price pret produs
     * @return multimea de produse ce respeca filtrele
     * @pre true
     * @post menu.size()==menu.size()@pre and user.size()==user.size()@pre and orders.size()==orders.size()@pre
     * @invariant isWellFormed()
     */
    public Set<MenuItem> searchProduct(String title, Double rating, Integer calories,
                                       Integer proteins, Integer fats, Integer sodium, Double price );
}
