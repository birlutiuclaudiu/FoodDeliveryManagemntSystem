package businesslogic;
import data.FileWriter;
import data.Serializator;
import model.UserType;
import model.User;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Clasa pentru operatiile clientilor si administratorului;
 * Gestiunea restaurantului
 * Implementeaza interfata IDeliveryServiceProcessing -design by contract
 * Subiect pentru employee
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class DeliveryService extends Observable implements IDeliveryServiceProcessing{
    /**
     * Multimea comenzilor
     */
    private Map<Order, Collection<MenuItem>> orders=new HashMap<>();
    /**
     * Menul restaurantului
     */
    private Set<MenuItem> menu;
    /**
     * Multimea useri-lor
     */
    private Set<User> users;
    /**
     * Ultimul id folosit pentru user
     */
    private Integer maxUserId;
    /**
     * Ultimul id folosit pentru comanda
     */
    private Integer maxOrderId;

    /**
     * Constructor
     * Creeaza obiectele necesare sau le importeaza
     */
    public DeliveryService(){
        Serializator< Map<Order, Collection<MenuItem>>>  serializatorOrder=new Serializator<>();
        Serializator<Set<MenuItem>> serializatorMenu=new Serializator<>();
        Serializator<Set<User>> serializatorUsers=new Serializator<>();
        Serializator<Integer> serializatorInt=new Serializator<>();
        try {
            orders=serializatorOrder.loadData("orders");
            users=serializatorUsers.loadData("users");
            menu=serializatorMenu.loadData("menu");
            maxUserId=serializatorInt.loadData("maxUserId");
            maxOrderId=serializatorInt.loadData("maxOrderId");
            setChanged(); notifyObservers(orders);
        } catch (IOException | ClassNotFoundException e) {
            maxOrderId=0;
            maxUserId=0;
            orders=new HashMap<>();
            users=new HashSet<>();
            users.add(new User(maxUserId++,"admin", "admin", UserType.ADMIN));
            users.add(new User(maxUserId,"employee", "employee", UserType.EMP));
        }
    }

    @Override
    public boolean importProducts() {
        menu=new HashSet<>();
        assert isWellFormed();
        int sizePreUser= users.size(), sizePreOrders=orders.size();

        menu=  FileWriter.initializeList();

        assert sizePreUser==users.size() &&  sizePreOrders==orders.size() && isWellFormed();
        return menu != null;
    }

    @Override
    public boolean deleteProduct(MenuItem menuItem) {
        assert isWellFormed();
        int sizePreUser= users.size(), sizePreOrders=orders.size();
        assert menuItem!=null;

        if(menu==null) return false;
        int sizeMenuPre=menu.size();

        MenuItem product = menu.stream()
                .filter(p ->p.equals(menuItem))
                .findFirst()
                .orElse(null);
        System.out.println("Deleting " +product);
        boolean deleted=menu.remove(product);

        if(deleted)
            assert menu.size()==sizeMenuPre-1;
        else
            assert menu.size()==sizeMenuPre;
        assert sizePreUser==users.size() && sizePreOrders==orders.size() && isWellFormed();
        return deleted;
    }

    @Override
    public boolean editProduct(MenuItem menuItem){
        if(menu==null) return false;
        assert isWellFormed();
        int sizePreUser= users.size(), sizePreOrders=orders.size(), sizeMenuPre=menu.size();

        MenuItem product = menu.stream()
                .filter(p ->p.equals(menuItem))
                .findFirst()
                .orElse(null);
        if(product==null) return false;
        boolean boolResult= menu.remove(product) && menu.add(menuItem);

        assert sizePreUser==users.size() && sizePreOrders==orders.size()  && menu.size()==sizeMenuPre && isWellFormed();
        return boolResult;
    }

    @Override
    public boolean addProduct(MenuItem menuItem){
        if(menu==null) return false;
        assert isWellFormed();
        int sizePreUser= users.size(), sizePreOrders=orders.size(),sizeMenuPre=menu.size();

        boolean boolResult= menu.add(menuItem);
        if(boolResult)
            assert menu.size()==sizeMenuPre+1;
        else
            assert menu.size()==sizeMenuPre;

        assert sizePreUser==users.size() && sizePreOrders==orders.size() && isWellFormed();
        return boolResult;
    }

    @Override
    public void createNewOrder(User user, List<MenuItem> menuItems) {
        assert isWellFormed();
        int sizePreUser= users.size(), sizePreOrders=orders.size(),sizeMenuPre=menu.size();

        Order order=new Order(maxOrderId,user.getId());
        maxOrderId++;
        orders.put(order,menuItems);
        setChanged();
        notifyObservers(orders);
        FileWriter.generateBill(order, user, menuItems);

        assert sizePreUser==users.size() && sizePreOrders==orders.size() && sizeMenuPre==menu.size() && isWellFormed();
    }
    @Override
    public boolean generateReportTime(int startHour, int endHour){
        assert isWellFormed();
        assert startHour>-1 && endHour<25;
        int sizePreUser= users.size();
        int sizePreOrders=orders.size();
        int sizeMenuPre=menu.size();
        Map<Order, Collection<MenuItem>> result=orders.entrySet().stream()
                .filter(map-> map.getKey().getOrderHour()>startHour && map.getKey().getOrderHour()<endHour)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        boolean boolRes= FileWriter.createReportTime(result,"Comenzi intre orele "+startHour +"--"+endHour+"\n");

        assert sizePreUser==users.size()&&  sizePreOrders==orders.size()&& sizeMenuPre==menu.size() && isWellFormed();
        return boolRes;
    }
    @Override
    public boolean generateReportProducts(int number){
        assert isWellFormed() && number>-1;
        int sizePreUser= users.size() , sizePreOrders=orders.size(), sizeMenuPre=menu.size();
        List<Collection<MenuItem>> result= orders.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        List<MenuItem> completeList=new ArrayList<>();
        result.forEach(completeList::addAll);
        Map<MenuItem,Long> finalResult = completeList.stream()
                .collect(Collectors.groupingBy(e -> e,Collectors.counting()));
        List<MenuItem> menuItemList=finalResult.entrySet().stream()
                .filter(map->map.getValue()>number)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        boolean boolRes= FileWriter.createReportProduct(menuItemList,
                                        "The products ordered more than " + number + " times so far");
        assert sizePreUser==users.size() && sizePreOrders==orders.size() && sizeMenuPre==menu.size() && isWellFormed();
        return boolRes;
    }
    @Override
    public boolean generateReportClient(int numberOrders, int value){
        assert isWellFormed() && numberOrders>-1 && value>-1;
        int sizePreUser= users.size(), sizePreOrders=orders.size(),sizeMenuPre=menu.size();

        Set<Order> result=orders.entrySet().stream()
                .filter(map-> (double)map.getValue().stream()
                        .mapToDouble(MenuItem::computePrice)
                        .sum()>value)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        List<Integer> clientIdList=result.stream().map(Order::getClientID).collect(Collectors.toList());
        Map<Integer,Long> clientsId=clientIdList.stream()
                .collect(Collectors.groupingBy(e -> e,Collectors.counting()));
        Set<Integer> finalList=clientsId.entrySet().stream()
                .filter(map->map.getValue()>numberOrders)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        Set<User> userList=users.stream()
                .filter(u->finalList.contains(u.getId()) && u.getType()== UserType.CLIENT)
                .collect(Collectors.toSet());
        boolean boolRes= FileWriter.createReportClients(userList,clientsId,"The clients that have ordered more than " +numberOrders+
                " times and the value of the order was higher than "+value);
        assert sizePreUser==users.size() && sizePreOrders==orders.size() && sizeMenuPre==menu.size() && isWellFormed();
        return boolRes;
    }
    @Override
    public boolean generateReportDay(int day, int month, int year){
        assert isWellFormed();
        int sizePreUser= users.size(), sizePreOrders=orders.size(), sizeMenuPre=menu.size();

        Map<Order, Collection<MenuItem>> ordersWithinDate=orders.entrySet().stream()
                .filter(map-> map.getKey().getOrderDay().equals(day)
                        && map.getKey().getOrderYear().equals(year) && map.getKey().getOrderMonth().equals(month))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<Collection<MenuItem>> result= ordersWithinDate.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        List<MenuItem> completeList=new ArrayList<>();
        result.forEach(completeList::addAll);
        Map<MenuItem,Long> finalResult = completeList.stream()
                .collect(Collectors.groupingBy(e -> e,Collectors.counting()));
        boolean boolRes= FileWriter.createReportDay(finalResult,"The products ordered within "+day+" "+month+" " +year);

        assert sizePreUser==users.size() && sizePreOrders==orders.size() && sizeMenuPre==menu.size() && isWellFormed();
        return boolRes;
    }
    @Override
    public Set<MenuItem> searchProduct(String title, Double rating, Integer calories,
                                       Integer proteins, Integer fats, Integer sodium, Double price ) {
        assert isWellFormed();
        int sizePreUser= users.size(),sizePreOrders=orders.size(), sizeMenuPre=menu.size();
        Set<MenuItem> toReturn= menu.stream()
                .filter(p->{
                    if(p instanceof BaseProduct) {
                        BaseProduct item = (BaseProduct) p;
                        return ((title == null || title.equals("")) || item.getTitle().contains(title)) &&
                                ((rating==null) || rating.equals(item.getRating())) &&
                                (calories == null || calories.equals(item.getCalories())) &&
                                (proteins == null || proteins.equals(item.getProteins())) &&
                                (fats == null || fats.equals(item.getFat())) &&
                                (sodium == null || sodium.equals(item.getSodium())) &&
                                (price == null || price.equals(item.getPrice()));
                    } else{
                        CompositeProduct item = (CompositeProduct) p;
                        return ((title == null || title.equals("")) || item.getTitle().contains(title)) &&
                                ((rating==null) || rating.equals(item.computeRating())) &&
                                (calories == null || calories.equals(item.computeCalories())) &&
                                (proteins == null || proteins.equals(item.computeProteins())) &&
                                (fats == null || fats.equals(item.computeFats())) &&
                                (sodium == null || sodium.equals(item.computeSodium())) &&
                                (price == null || price.equals(item.computePrice()));
                    } })
                .collect(Collectors.toSet());
        assert sizePreUser==users.size() && sizePreOrders==orders.size() && sizeMenuPre==menu.size() && isWellFormed();
        return toReturn;
    }
    /**
     * Getter
     * @return multimea produselor din menu
     */
    public Set<MenuItem> getProducts(){
        return this.menu;
    }
    /**
     * Metoda de inregistrare client
     * @param user user
     * @return true or false
     */
    public boolean registerClient(User user){
        return users.add(user);
    }
    /**
     * Metoda ce verifica daca userul e inregistrat
     * @param user contine username-ul si parola
     * @return user-ul sau null in caz ca nu e inregistrat
     */
    public User getConnection(User user){
        User user1=null;
        user1=this.users.stream().filter(p->p.equals(user) && p.getType()==user.getType()).findFirst().orElse(null);
        return user1;
    }
    /**
     *Se foloseste user-id-ul si se incrementeaza
     * @return id-ul disponibil
     */
    public int useMaxId(){
        int toReturn =this.maxUserId;
        maxUserId++;
        return toReturn;
    }
    /**
     * Metoda pentru salavarea campurilr din delivery service la inchiderea aplicatiei
     */
    public void saveData(){
        Serializator< Map<Order, Collection<MenuItem>>>  serializatorOrder=new Serializator<>();
        serializatorOrder.save(orders,"orders");
        Serializator<Set<MenuItem>> serializatorMenu=new Serializator<>();
        serializatorMenu.save(menu,"menu");
        Serializator<Set<User>> serializatorUsers=new Serializator<>();
        serializatorUsers.save(users,"users");
        Serializator<Integer> serializatorInt=new Serializator<>();
        serializatorInt.save(maxUserId,"maxUserId");
        serializatorInt.save(maxOrderId,"maxOrderId");
    }
    /**
     * Metoda isWellFormed()
     * @return true sau false: campurile obiectului delivery sunt consistente sau nu
     */
    protected boolean isWellFormed(){
        if(!users.contains(new User(0,"admin","admin", UserType.ADMIN)))
            return false;
        if(!users.contains(new User(0,"employee","employee", UserType.EMP)))
            return false;
        if(menu==null)
            return false;
        return maxOrderId >= 0 && maxUserId >= 0;
    }
}
