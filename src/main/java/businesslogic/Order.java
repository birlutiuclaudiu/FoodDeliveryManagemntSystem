package businesslogic;



import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * Clasa comanda
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class Order implements Serializable,Comparable<Order> {
    /**
     * Id-ul comenzii
     */
    private Integer orderID;
    /**
     * Id-ul clientului
     */
    private Integer clientID;
    /**
     * Data comenzii
     */
    private Date orderDate;

    /**
     * Cosntuctor
     * @param orderID id comanda
     * @param clientID  id client
     */
    public Order(Integer orderID, Integer clientID) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.orderDate = new Date();

    }

    /**
     * Getter
     * @return id
     */
    public Integer getOrderID() {
        return orderID;
    }

    /**
     * Getter
     * @return ora
     */
    public Integer getOrderHour(){
        return this.orderDate.getHours();
    }

    /**
     * Getter
     * @return an
     */
    public Integer getOrderYear(){
        LocalDate localDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getYear();
    }

    /**
     * Getter
     * @return zi
     */
    public Integer getOrderDay(){
        LocalDate localDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getDayOfMonth();
    }

    /**
     * Getter
     * @return luna
     */
    public Integer getOrderMonth(){
        LocalDate localDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return  localDate.getMonthValue();
    }

    /**
     * Getter
     * @return client id
     */
    public Integer getClientID() {
        return clientID;
    }

    /**
     * Setter
     * @param orderID id order
     */
    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    /**
     * Suprascriere equals
     * @param o object
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return orderID.equals(order.orderID) && clientID.equals(order.clientID) && orderDate.equals(order.orderDate);
    }

    /**
     * Suprascriere hashcode
     * @return haschcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderID, clientID, orderDate);
    }

    /**
     * Suprascriere toString
     * @return afisarea comanda
     */
    @Override
    public String toString(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return "Order: "+orderID +"\n Client_id: "+clientID+"\n Date: "+formatter.format(orderDate);
    }

    /**
     * Suprascriere compare to
     * @param o order
     * @return -1,0,1
     */
    @Override
    public int compareTo(Order o) {
      return this.orderID.compareTo(o.getOrderID());
    }
}
