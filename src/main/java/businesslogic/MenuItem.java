package businesslogic;
import java.io.Serializable;
/**
 * Clasa abstracta pentru produsele din meniu
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public abstract class MenuItem implements Serializable {
    /**
     * Metoda abstracta de calculare a pretului
     * @return pretul total
     */
    public abstract Double computePrice();
}
