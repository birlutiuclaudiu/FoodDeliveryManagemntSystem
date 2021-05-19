package data;

import businesslogic.BaseProduct;
import businesslogic.MenuItem;
import businesslogic.Order;
import model.User;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Clasa pentru scrire/ citire fisiere
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class FileWriter {
    /**
     * Metoda statica ce citeste fisierul produs.csv pentru popularea meniului
     * @return multimea produselor
     */
    public static Set<MenuItem> initializeList() {
        String fileName = "products.csv";
        Set<MenuItem> menu = new HashSet<MenuItem>();
        try {
            Stream<String> lines = Files.lines(Paths.get(fileName));
            menu = lines
                    .skip(1)
                    .map(line -> {
                        List<String> sir = Arrays.asList(line.split(","));
                        return new BaseProduct(sir.get(0), sir.get(1), sir.get(2), sir.get(3),
                                sir.get(4), sir.get(5), sir.get(6));
                    })
                    .filter(distinctByKey(BaseProduct::getTitle))
                    .collect(Collectors.toSet());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return menu;
    }

    /**
     * Pentru distinct
     * @param keyExtractor key
     * @param <T> generic
     * @return predicate
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * Metoda de generare a chitantei
     * @param order comanda
     * @param user user-ul
     * @param menuItems lista de produse
     */
    public static void generateBill(Order order, User user, List<MenuItem> menuItems){
        double totalPrice=menuItems.stream()
                .mapToDouble(MenuItem::computePrice)
                .sum();
        BufferedWriter buffer=getBuffer("Order_"+order.getOrderID()+"_");
        if(buffer==null)
            return;
        try {
            buffer.write(order.toString()+"\n ");
            buffer.write("User_name: "+user.getUserName()+"\n   Products\n    ");
            for(MenuItem menuItem: menuItems)
                buffer.write(menuItem.toString()+"\n    ");
            buffer.write("Total price: "+totalPrice);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                buffer.close();
            } catch (IOException e) {
                System.out.println("File could not be created");
            }
        }
    }

    /**
     * Metoda creare raport interval de timp
     * @param orders lista comenzi
     * @param message mesaj
     * @return true or false
     */
    public static boolean createReportTime( Map<Order, Collection<MenuItem>> orders, String message){
        BufferedWriter buffer=getBuffer("Report1_time_");
        if(buffer==null) return false;

        Set<Map.Entry<Order, Collection<MenuItem>>> entrySet=orders.entrySet();
        StringBuilder stringBuilder=new StringBuilder(message);
        for(Map.Entry<Order, Collection<MenuItem>> entry: entrySet){
            stringBuilder.append(entry.getKey());
            stringBuilder.append("\n   ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\n-------------------------------------------------------------------------\n");
        }
        try {
            buffer.write(stringBuilder.toString());
        } catch (IOException e) {
            System.out.println("Could not write in file"); return false;
        }finally {
            try {
                buffer.close();
            } catch (IOException e) {
                System.out.println("File could not be created");
            }
        }
        return true;
    }

    /**
     * Metoda creare raport produse cumparate de mai multe ori
     * @param menuItemList lista produse
     * @param message mesaj
     * @return true or false
     */
    public static boolean createReportProduct( List<MenuItem> menuItemList, String message){
        BufferedWriter buffer=getBuffer("Report2_products_");
        if(buffer==null) return false;
        StringBuilder stringBuilder=new StringBuilder("   "+message+"\n");
        int i=1;
        for(MenuItem menuItem: menuItemList ){
            stringBuilder.append(i);
            stringBuilder.append(".");
            stringBuilder.append(menuItem);
            stringBuilder.append("\n");
            i++;
        }
        try {
            buffer.write(stringBuilder.toString());
            buffer.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Metoda creare raport clienti
     * @param users useri
     * @param clientsId clintid
     * @param message mesaj
     * @return true or false
     */
    public static boolean createReportClients( Collection<User> users,Map<Integer,Long> clientsId, String message){
        BufferedWriter buffer=getBuffer("Report3_clients_");
        if(buffer==null) return false;
        StringBuilder stringBuilder=new StringBuilder("   "+message+"\n");
        int i=1;
        for(User user: users ){
            stringBuilder.append(i);
            stringBuilder.append(".");
            stringBuilder.append(user);
            stringBuilder.append(" TIMES: ");
            stringBuilder.append(clientsId.get(user.getId()));
            stringBuilder.append("\n");
            i++;
        }
        try {
            buffer.write(stringBuilder.toString());
            buffer.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Metoda creare raport produse cumparate intr-o zi
     * @param products produse
     * @param message mesaj
     * @return true or false
     */
    public static boolean createReportDay(Map<MenuItem,Long> products, String message){
        BufferedWriter buffer=getBuffer("Report4_date_");
        if(buffer==null) return false;
        StringBuilder stringBuilder=new StringBuilder("   "+message+"\n");
        AtomicInteger i= new AtomicInteger(1);
        products.forEach((key,value)->{
            stringBuilder.append(i.get()); i.getAndIncrement();
            stringBuilder.append(".");
            stringBuilder.append(key);
            stringBuilder.append("occurrences: ");
            stringBuilder.append(value);
            stringBuilder.append("\n");
        });
        try {
            buffer.write(stringBuilder.toString());
            buffer.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Metoda pribvate de creare buffer scriere
     * @param name nume
     * @return buffer
     */
    private static BufferedWriter getBuffer(String name){
        java.io.FileWriter file=null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy.MM.dd..HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        BufferedWriter buffer=null;
        try {
            file = new java.io.FileWriter(name+ dtf.format(now) + ".txt");
            buffer=new BufferedWriter(file);
        }catch (IOException e) {
            System.out.println("File could not be created");
        }
        return buffer;
    }

}
