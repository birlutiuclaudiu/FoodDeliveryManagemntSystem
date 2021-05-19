package presentation;

import businesslogic.DeliveryService;
import businesslogic.MenuItem;
import model.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa definire controller pentru interfata client
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class ClientController {
    /**
     * delivery service asociat
     */
    private DeliveryService deliveryService;
    /**
     * client view asociat
     */
    private ClientView clientView;
    /**
     * Produse adaugate in comanda
     */
    private List<MenuItem> menuItems;
    /**
     * User logat
     */
    private User user;
    /**
     * logview asociat pentru a se intoarce window-ul de logat
     */
    private LogView logView;

    /**
     * Constructor
     * @param deliveryService deliveryservice
     * @param clientView clientView asociat
     * @param logView logview asociat
     */
    public ClientController(DeliveryService deliveryService, ClientView clientView,LogView logView) {
        this.deliveryService = deliveryService;
        this.clientView = clientView;
        this.logView=logView;
        if(deliveryService!=null && deliveryService.getProducts()!=null)
             this.clientView.setCreateOrderPanel(deliveryService);

        this.clientView.addShowListener(e->{if(deliveryService==null || deliveryService.getProducts()==null){
              clientView.showMessage("No products");return;}
              clientView.setShowMenuPanel(deliveryService);
        });
        this.clientView.addSearchListener(new SearchListener());
        this.clientView.addAddBaseListener(new BaseToOrderListener());
        this.clientView.addAddCompListener(new CompToOrderListener());
        this.clientView.addFinalizeListener(new FinalizeOrderListener());
        this.clientView.addClosingListener(logView);
    }

    /**
     * Actualizare interfata client la o noua conectare
     */
    public void newConnectionSetup(){
        if(deliveryService!=null && deliveryService.getProducts()!=null)
            this.clientView.setCreateOrderPanel(deliveryService);
    }

    /**
     * Listener pentru butonul de search
     */
    public class SearchListener implements ActionListener{
        /**
         * Metoda suprascrisa
         * @param e listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] arg=clientView.getProductInfo();
             Double rating=null;
             Integer calories=null,proteins=null, fat=null, sodium=null;
             Double price=null;
             try{
                 rating=Double.parseDouble(arg[1]);
             }catch(NumberFormatException exc){rating=null;}
            try{
                calories=Integer.parseInt(arg[2]);
            }catch(NumberFormatException exc){calories=null;}
            try{
                proteins=Integer.parseInt(arg[3]);
            }catch(NumberFormatException exc){proteins=null;}
            try{
                fat=Integer.parseInt(arg[4]);
            }catch(NumberFormatException exc){fat=null;}
            try{
                sodium=Integer.parseInt(arg[5]);
            }catch(NumberFormatException exc){sodium=null;}
            try{
                price=Double.parseDouble(arg[6]);
            }catch(NumberFormatException exc){price=null;}
            if(deliveryService.getProducts()==null) {
                clientView.showMessage("No products imported");
                return;
            }
            if(deliveryService.searchProduct(arg[0],rating,calories,proteins,fat,sodium,price)!=null) {
                clientView.setResultsPanel(deliveryService.searchProduct(arg[0], rating, calories, proteins, fat, sodium, price));
            }
        }
    }

    /**
     *Subclasa- Listener pentru  butonul de adaugare produs baza la comanda
     */
    public class BaseToOrderListener implements ActionListener{
        /**
         * Metoda suprascrisa
         * @param e listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(menuItems==null)
                menuItems=new ArrayList<>();
            if(clientView.getBaseProduct()!=null)
                 menuItems.add(clientView.getBaseProduct());
        }
    }
    /**
     *Subclasa- Listener pentru  butonul de adaugare produs compus la comanda
     */
    public class CompToOrderListener implements ActionListener{
        /**
         * Metoda suprascrisa
         * @param e listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(menuItems==null)
                menuItems=new ArrayList<>();
            if(clientView.getCompProduct()!=null)
                menuItems.add(clientView.getCompProduct());
        }
    }
    /**
     *Subclasa- Listener pentru  butonul de creare o noua  comanda
     */
    public class FinalizeOrderListener implements ActionListener{
        /**
         * Metoda suprascrisa
         * @param e listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(menuItems==null){
                clientView.showMessage("Not products selected");
                return;
            }
            deliveryService.createNewOrder(user,menuItems);
            menuItems=null;
        }
    }

    /**
     * Setarea user conectat la interfata clientului
     * @param user user
     */
    public void setConnectedUser(User user){
        this.user=user;
    }


}
